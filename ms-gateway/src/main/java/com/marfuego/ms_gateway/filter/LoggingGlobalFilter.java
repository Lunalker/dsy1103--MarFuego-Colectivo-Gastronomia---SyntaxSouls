package com.marfuego.ms_gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Filtro global del Gateway. Se ejecuta en TODAS las peticiones que pasan por
 * aquí, sin importar a qué microservicio vayan. Deja en el log qué método y qué
 * ruta entró, y al salir registra el código de respuesta. Sirve para tener una
 * traza centralizada de todo el tráfico del sistema en un solo lugar.
 */
@Component
public class LoggingGlobalFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(LoggingGlobalFilter.class);

    /**
     * Registra la petición entrante, deja que siga su curso hacia el
     * microservicio y, cuando vuelve la respuesta, registra el código de estado.
     *
     * @param exchange datos de la petición y la respuesta que atraviesan el Gateway
     * @param chain    cadena de filtros que continúa el procesamiento
     * @return señal reactiva que completa cuando termina el enrutamiento
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var request = exchange.getRequest();
        log.info("[Gateway] --> {} {}", request.getMethod(), request.getURI().getPath());

        return chain.filter(exchange).then(Mono.fromRunnable(() ->
                log.info("[Gateway] <-- {} {} => {}",
                        request.getMethod(),
                        request.getURI().getPath(),
                        exchange.getResponse().getStatusCode())));
    }

    /**
     * Le da la máxima prioridad para que este filtro corra antes que los demás
     * y alcance a registrar la petición completa.
     *
     * @return el orden de ejecución (el más alto posible)
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
