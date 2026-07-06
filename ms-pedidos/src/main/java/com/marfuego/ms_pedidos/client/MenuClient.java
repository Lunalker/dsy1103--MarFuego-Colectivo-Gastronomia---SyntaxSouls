package com.marfuego.ms_pedidos.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

/**
 * Cliente que llama por REST a ms-menu con WebClient. Lo usa el PedidoService
 * para revisar, al crear un pedido, si un plato existe y está disponible (R1).
 */
@Component
public class MenuClient {

    private final WebClient webClient;

    public MenuClient(@Value("${ms.menu.url:http://localhost:8082}") String menuUrl) {
        this.webClient = WebClient.builder().baseUrl(menuUrl).build();
    }

    /**
     * Le pregunta a ms-menu si un plato existe y está disponible (regla R1). Si
     * ms-menu no responde, devuelve false para no dejar pasar el pedido con un
     * plato que no se pudo confirmar.
     *
     * @param platoId el id del plato que se quiere revisar
     * @return true si el plato está disponible, false si no o si ms-menu no responde
     */
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
            // Si ms-menu no responde, asumimos que no está disponible
            return false;
        }
    }
}
