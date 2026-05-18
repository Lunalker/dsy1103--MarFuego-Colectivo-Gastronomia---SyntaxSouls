package com.marfuego.ms_reportes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

// Aqui creamos los "telefonos" para llamar a cada microservicio.
// Cada @Bean crea un WebClient configurado con la URL base de un MS.
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

    @Bean
    public WebClient localesWebClient() {
        return WebClient.builder().baseUrl(localesUrl).build();
    }

    @Bean
    public WebClient menuWebClient() {
        return WebClient.builder().baseUrl(menuUrl).build();
    }

    @Bean
    public WebClient inventarioWebClient() {
        return WebClient.builder().baseUrl(inventarioUrl).build();
    }

    @Bean
    public WebClient pedidosWebClient() {
        return WebClient.builder().baseUrl(pedidosUrl).build();
    }
}
