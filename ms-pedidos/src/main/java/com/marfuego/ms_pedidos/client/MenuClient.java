package com.marfuego.ms_pedidos.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

// Cliente que llama a ms-menu para verificar si un plato existe y esta disponible.
// Lo usa el PedidoService al crear un pedido (R1).
@Component
public class MenuClient {

    private final WebClient webClient;

    public MenuClient(@Value("${ms.menu.url:http://localhost:8082}") String menuUrl) {
        this.webClient = WebClient.builder().baseUrl(menuUrl).build();
    }

    // R1: verifica si el plato existe y esta marcado como disponible
    public boolean platoDisponible(Long platoId) {
        try {
            Map<?, ?> respuesta = webClient.get()
                    .uri("/api/v1/menu/platos/{id}", platoId)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (respuesta == null) {
                return false;
            }
            Object disponible = respuesta.get("disponible");
            return Boolean.TRUE.equals(disponible);
        } catch (Exception e) {
            // Si ms-menu no responde, asumimos que no esta disponible
            return false;
        }
    }
}
