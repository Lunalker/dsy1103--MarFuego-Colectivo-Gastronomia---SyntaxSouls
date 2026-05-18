package com.marfuego.ms_reportes.client;

import com.marfuego.ms_reportes.dto.externo.PedidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

// Cliente que llama a ms-pedidos
@Component
public class PedidosClient {

    @Autowired
    @Qualifier("pedidosWebClient")
    private WebClient webClient;

    // Trae todos los pedidos
    public List<PedidoDTO> listarPedidos() {
        return webClient.get()
                .uri("/api/v1/pedidos")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PedidoDTO>>() {})
                .block();
    }

    // Trae los pedidos de un local
    public List<PedidoDTO> listarPedidosPorLocal(Long localId) {
        return webClient.get()
                .uri("/api/v1/pedidos/local/{localId}", localId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PedidoDTO>>() {})
                .block();
    }
}
