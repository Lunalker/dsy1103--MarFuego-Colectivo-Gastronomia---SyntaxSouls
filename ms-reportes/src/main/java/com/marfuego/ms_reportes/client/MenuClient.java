package com.marfuego.ms_reportes.client;

import com.marfuego.ms_reportes.dto.externo.PlatoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * Cliente que llama por REST a ms-menu con WebClient para traer datos de los
 * platos.
 */
@Component
public class MenuClient {

    @Autowired
    @Qualifier("menuWebClient")
    private WebClient webClient;

    /**
     * Le pide a ms-menu todos los platos.
     *
     * @return la lista de platos
     */
    public List<PlatoDTO> listarPlatos() {
        return webClient.get()
                .uri("/api/v1/menu/platos")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PlatoDTO>>() {})
                .block();
    }

    /**
     * Le pide a ms-menu los platos de un local.
     *
     * @param localId el id del local
     * @return los platos de ese local
     */
    public List<PlatoDTO> listarPlatosPorLocal(Long localId) {
        return webClient.get()
                .uri("/api/v1/menu/platos/local/{localId}/disponibles", localId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PlatoDTO>>() {})
                .block();
    }
}
