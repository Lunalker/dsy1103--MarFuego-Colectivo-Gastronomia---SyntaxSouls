package com.marfuego.ms_reportes.client;

import com.marfuego.ms_reportes.dto.externo.LocalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * Cliente que llama por REST a ms-locales con WebClient para traer datos de
 * los locales.
 */
@Component
public class LocalesClient {

    @Autowired
    @Qualifier("localesWebClient")
    private WebClient webClient;

    /**
     * Le pide a ms-locales un local por su id.
     *
     * @param id el id del local
     * @return el local que devuelve ms-locales
     */
    public LocalDTO obtenerLocal(Long id) {
        return webClient.get()
                .uri("/api/v1/locales/{id}", id)
                .retrieve()
                .bodyToMono(LocalDTO.class)
                .block();
    }

    /**
     * Le pide a ms-locales todos los locales.
     *
     * @return la lista de locales
     */
    public List<LocalDTO> listarLocales() {
        return webClient.get()
                .uri("/api/v1/locales")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<LocalDTO>>() {})
                .block();
    }
}
