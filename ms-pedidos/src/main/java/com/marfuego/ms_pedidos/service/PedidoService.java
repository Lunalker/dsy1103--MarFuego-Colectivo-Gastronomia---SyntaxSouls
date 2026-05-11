package com.marfuego.ms_pedidos.service;

import com.marfuego.ms_pedidos.model.DetallePedido;
import com.marfuego.ms_pedidos.model.Pedido;
import com.marfuego.ms_pedidos.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Pedido> listar() {
        return repository.findAll();
    }

    public Pedido obtener(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Pedido crear(Pedido pedido) {

        for (DetallePedido det : pedido.getDetalles()) {

            // ✅ URL CORRECTA (AQUÍ ESTABA EL ERROR)
            String url = "http://localhost:8080/api/v1/menu/platos/" + det.getPlatoId();

            try {
                Object response = restTemplate.getForObject(url, Object.class);

                // ✅ Validación básica
                if (response == null) {
                    throw new RuntimeException("El plato no existe");
                }

            } catch (Exception e) {
                // ✅ Ahora muestra el error real
                throw new RuntimeException("Error al conectar con ms-menu: " + e.getMessage());
            }

            // 🔗 relación detalle → pedido
            det.setPedido(pedido);
        }

        // 🧾 datos automáticos
        pedido.setEstado("PENDIENTE");
        pedido.setFecha(LocalDateTime.now());

        return repository.save(pedido);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}