package com.marfuego.ms_reportes.service;

import com.marfuego.ms_reportes.client.InventarioClient;
import com.marfuego.ms_reportes.client.LocalesClient;
import com.marfuego.ms_reportes.client.MenuClient;
import com.marfuego.ms_reportes.client.PedidosClient;
import com.marfuego.ms_reportes.dto.externo.IngredienteDTO;
import com.marfuego.ms_reportes.dto.externo.LocalDTO;
import com.marfuego.ms_reportes.dto.externo.PedidoDTO;
import com.marfuego.ms_reportes.dto.externo.PlatoDTO;
import com.marfuego.ms_reportes.dto.reporte.RentabilidadPlatoDTO;
import com.marfuego.ms_reportes.dto.reporte.ResumenLocalDTO;
import com.marfuego.ms_reportes.dto.reporte.StockCriticoDTO;
import com.marfuego.ms_reportes.exception.ComunicacionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Aquí se arman los reportes. Como este microservicio no tiene base de datos
 * propia, llama a los otros con los clientes WebClient y junta toda la info en
 * un solo reporte: resumen del local, stock crítico y rentabilidad (regla R5).
 */
@Service
public class ServiceReportes {

    private static final Logger log = LoggerFactory.getLogger(ServiceReportes.class);

    @Autowired
    private LocalesClient localesClient;

    @Autowired
    private MenuClient menuClient;

    @Autowired
    private InventarioClient inventarioClient;

    @Autowired
    private PedidosClient pedidosClient;

    /**
     * Arma el resumen de un local juntando datos de ms-locales, ms-menu y
     * ms-pedidos en un solo reporte.
     *
     * @param localId el id del local
     * @return el resumen del local ya armado
     */
    public ResumenLocalDTO resumenDeLocal(Long localId) {
        log.info("Generando resumen del local id={}", localId);

        // 1. Datos del local (llamada a ms-locales)
        LocalDTO local;
        try {
            local = localesClient.obtenerLocal(localId);
        } catch (Exception e) {
            throw new ComunicacionException(
                    "No se pudo obtener el local " + localId + " desde ms-locales", e);
        }

        // 2. Platos del local (llamada a ms-menu)
        List<PlatoDTO> platos;
        try {
            platos = menuClient.listarPlatos();
        } catch (Exception e) {
            throw new ComunicacionException(
                    "No se pudo obtener los platos desde ms-menu", e);
        }

        // Filtramos solo los platos del local que estamos consultando
        List<PlatoDTO> platosDelLocal = new ArrayList<>();
        for (PlatoDTO plato : platos) {
            if (plato.getLocalId() != null && plato.getLocalId().equals(localId)) {
                platosDelLocal.add(plato);
            }
        }

        int platosDisponibles = 0;
        for (PlatoDTO plato : platosDelLocal) {
            if (Boolean.TRUE.equals(plato.getDisponible())) {
                platosDisponibles++;
            }
        }

        // 3. Pedidos del local (llamada a ms-pedidos)
        List<PedidoDTO> pedidos;
        try {
            pedidos = pedidosClient.listarPedidosPorLocal(localId);
        } catch (Exception e) {
            throw new ComunicacionException(
                    "No se pudo obtener los pedidos desde ms-pedidos", e);
        }

        int pedidosPendientes = 0;
        double ventasTotales = 0.0;
        for (PedidoDTO pedido : pedidos) {
            if ("PENDIENTE".equals(pedido.getEstado())) {
                pedidosPendientes++;
            }
            if (pedido.getTotal() != null) {
                ventasTotales += pedido.getTotal();
            }
        }

        // 4. Armamos el reporte
        ResumenLocalDTO reporte = new ResumenLocalDTO();
        reporte.setLocalId(local.getId());
        reporte.setNombreLocal(local.getNombre());
        reporte.setUbicacion(local.getUbicacion());
        reporte.setTotalPlatos(platosDelLocal.size());
        reporte.setPlatosDisponibles(platosDisponibles);
        reporte.setTotalPedidos(pedidos.size());
        reporte.setPedidosPendientes(pedidosPendientes);
        reporte.setVentasTotales(ventasTotales);

        log.info("Resumen generado para local {}: {} pedidos, ${} en ventas",
                local.getNombre(), pedidos.size(), ventasTotales);
        return reporte;
    }

    /**
     * Devuelve los ingredientes que están en stock crítico, preguntándole a
     * ms-inventario cuáles quedaron bajo el mínimo (regla R2).
     *
     * @return los ingredientes en stock crítico
     */
    public List<StockCriticoDTO> stockCritico() {
        log.info("Generando reporte de stock critico");

        List<IngredienteDTO> alertas;
        try {
            alertas = inventarioClient.listarAlertas();
        } catch (Exception e) {
            throw new ComunicacionException(
                    "No se pudo obtener las alertas desde ms-inventario", e);
        }

        List<StockCriticoDTO> resultado = new ArrayList<>();
        for (IngredienteDTO ingrediente : alertas) {
            StockCriticoDTO dto = new StockCriticoDTO();
            dto.setIngredienteId(ingrediente.getId());
            dto.setNombreIngrediente(ingrediente.getNombre());
            dto.setStockActual(ingrediente.getStockActual());
            dto.setStockMinimo(ingrediente.getStockMinimo());
            dto.setUnidadMedida(ingrediente.getUnidadMedida());
            dto.setCantidadFaltante(ingrediente.getStockMinimo() - ingrediente.getStockActual());
            resultado.add(dto);
        }
        return resultado;
    }

    /**
     * Calcula la rentabilidad de cada plato (la diferencia entre precio y costo,
     * regla R5) con los datos que trae de ms-menu.
     *
     * @return la rentabilidad de cada plato
     */
    public List<RentabilidadPlatoDTO> rentabilidadPlatos() {
        log.info("Generando reporte de rentabilidad de platos");

        List<PlatoDTO> platos;
        try {
            platos = menuClient.listarPlatos();
        } catch (Exception e) {
            throw new ComunicacionException(
                    "No se pudo obtener los platos desde ms-menu", e);
        }

        List<RentabilidadPlatoDTO> resultado = new ArrayList<>();
        for (PlatoDTO plato : platos) {
            RentabilidadPlatoDTO dto = new RentabilidadPlatoDTO();
            dto.setPlatoId(plato.getId());
            dto.setNombrePlato(plato.getNombre());
            dto.setCategoria(plato.getCategoria());
            dto.setPrecioVenta(plato.getPrecioVenta());
            dto.setCostoProduccion(plato.getCostoProduccion());

            // Calculamos el margen absoluto y el porcentaje de ganancia de cada plato (R5)
            if (plato.getCostoProduccion() != null && plato.getPrecioVenta() != null
                    && plato.getCostoProduccion() > 0) {
                dto.setMargenAbsoluto(plato.getPrecioVenta() - plato.getCostoProduccion());
                dto.setMargenPorcentaje(
                        (plato.getPrecioVenta() / plato.getCostoProduccion()) * 100);
            }
            dto.setCumpleMargen300(Boolean.TRUE.equals(plato.getCumpleMargen()));
            resultado.add(dto);
        }
        return resultado;
    }
}
