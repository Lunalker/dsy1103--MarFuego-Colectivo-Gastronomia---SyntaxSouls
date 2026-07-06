package com.marfuego.ms_reportes.client;

import com.marfuego.ms_reportes.dto.externo.PedidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * Cliente que llama por REST a ms-pedidos con WebClient para traer datos de
 * los pedidos.
 */
@Component
public class PedidosClient {

    @Autowired
    @Qualifier("pedidosWebClient")
    private WebClient webClient;

    /**
     * Le pide a ms-pedidos todos los pedidos.
     *
     * @return la lista de pedidos
     */
    public List<PedidoDTO> listarPedidos() {
        return webClient.get()
                .uri("/api/v1/pedidos")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PedidoDTO>>() {})
                .block();
    }

    /**
     * Le pide a ms-pedidos los pedidos de un local.
     *
     * @param localId el id del local
     * @return los pedidos de ese local
     */
    public List<PedidoDTO> listarPedidosPorLocal(Long localId) {
        return webClient.get()
                .uri("/api/v1/pedidos/local/{localId}", localId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PedidoDTO>>() {})
                .block();
    }
}
