package com.marfuego.ms_reportes.client;

import com.marfuego.ms_reportes.dto.externo.LocalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

// Cliente que llama a ms-locales para traer datos de locales
@Component
public class LocalesClient {

    @Autowired
    @Qualifier("localesWebClient")
    private WebClient webClient;

    // Trae un local por id
    public LocalDTO obtenerLocal(Long id) {
        return webClient.get()
                .uri("/api/v1/locales/{id}", id)
                .retrieve()
                .bodyToMono(LocalDTO.class)
                .block();
    }

    // Trae todos los locales
    public List<LocalDTO> listarLocales() {
        return webClient.get()
                .uri("/api/v1/locales")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<LocalDTO>>() {})
                .block();
    }
}
