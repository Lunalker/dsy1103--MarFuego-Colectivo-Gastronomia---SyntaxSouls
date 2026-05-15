package com.marfuego.ms_reportes.client;

import com.marfuego.ms_reportes.dto.externo.IngredienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

// Cliente que llama a ms-inventario
@Component
public class InventarioClient {

    @Autowired
    @Qualifier("inventarioWebClient")
    private WebClient webClient;

    // Trae todos los ingredientes
    public List<IngredienteDTO> listarIngredientes() {
        return webClient.get()
                .uri("/api/v1/inventario/ingredientes")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<IngredienteDTO>>() {})
                .block();
    }

    // Trae los ingredientes en alerta (stock bajo el minimo)
    public List<IngredienteDTO> listarAlertas() {
        return webClient.get()
                .uri("/api/v1/inventario/ingredientes/alertas")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<IngredienteDTO>>() {})
                .block();
    }
}
