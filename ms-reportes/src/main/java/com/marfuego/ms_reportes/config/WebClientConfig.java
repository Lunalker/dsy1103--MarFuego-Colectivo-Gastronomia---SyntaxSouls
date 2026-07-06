package com.marfuego.ms_reportes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Aquí se crean los WebClient para hablar con cada microservicio. Cada uno
 * queda con su nombre (por ejemplo localesWebClient) y se inyecta en el cliente
 * que corresponde.
 */
@Configuration
public class WebClientConfig {

    @Value("${ms.locales.url}")
    private String localesUrl;

    @Value("${ms.menu.url}")
    private String menuUrl;

    @Value("${ms.inventario.url}")
    private String inventarioUrl;

    @Value("${ms.pedidos.url}")
    private String pedidosUrl;

    /**
     * WebClient que apunta a ms-locales.
     *
     * @return el cliente ya armado con la URL de locales
     */
    @Bean
    public WebClient localesWebClient() {
        return WebClient.builder().baseUrl(localesUrl).build();
    }

    /**
     * WebClient que apunta a ms-menu.
     *
     * @return el cliente ya armado con la URL de menú
     */
    @Bean
    public WebClient menuWebClient() {
        return WebClient.builder().baseUrl(menuUrl).build();
    }

    /**
     * WebClient que apunta a ms-inventario.
     *
     * @return el cliente ya armado con la URL de inventario
     */
    @Bean
    public WebClient inventarioWebClient() {
        return WebClient.builder().baseUrl(inventarioUrl).build();
    }

    /**
     * WebClient que apunta a ms-pedidos.
     *
     * @return el cliente ya armado con la URL de pedidos
     */
    @Bean
    public WebClient pedidosWebClient() {
        return WebClient.builder().baseUrl(pedidosUrl).build();
    }
}
