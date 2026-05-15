package com.marfuego.ms_reportes.client;

import com.marfuego.ms_reportes.dto.externo.PlatoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

// Cliente que llama a ms-menu para traer datos de los platos
@Component
public class MenuClient {

    @Autowired
    @Qualifier("menuWebClient")
    private WebClient webClient;

    // Trae todos los platos
    public List<PlatoDTO> listarPlatos() {
        return webClient.get()
                .uri("/api/v1/menu/platos")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PlatoDTO>>() {})
                .block();
    }

    // Trae los platos de un local
    public List<PlatoDTO> listarPlatosPorLocal(Long localId) {
        return webClient.get()
                .uri("/api/v1/menu/platos/local/{localId}/disponibles", localId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PlatoDTO>>() {})
                .block();
    }
}
