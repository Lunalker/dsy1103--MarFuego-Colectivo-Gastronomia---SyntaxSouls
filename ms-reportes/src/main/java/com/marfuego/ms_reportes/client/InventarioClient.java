package com.marfuego.ms_reportes.client;

import com.marfuego.ms_reportes.dto.externo.IngredienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * Cliente que llama por REST a ms-inventario con WebClient para traer datos del
 * stock.
 */
@Component
public class InventarioClient {

    @Autowired
    @Qualifier("inventarioWebClient")
    private WebClient webClient;

    /**
     * Le pide a ms-inventario todos los ingredientes.
     *
     * @return la lista de ingredientes
     */
    public List<IngredienteDTO> listarIngredientes() {
        return webClient.get()
                .uri("/api/v1/inventario/ingredientes")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<IngredienteDTO>>() {})
                .block();
    }

    /**
     * Le pide a ms-inventario los ingredientes que están en alerta, o sea los
     * que quedaron bajo su stock mínimo (regla R2).
     *
     * @return los ingredientes bajo el mínimo
     */
    public List<IngredienteDTO> listarAlertas() {
        return webClient.get()
                .uri("/api/v1/inventario/ingredientes/alertas")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<IngredienteDTO>>() {})
                .block();
    }
}
