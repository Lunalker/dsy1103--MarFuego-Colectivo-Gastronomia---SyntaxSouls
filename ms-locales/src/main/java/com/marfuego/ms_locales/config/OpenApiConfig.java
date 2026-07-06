package com.marfuego.ms_locales.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Swagger para el microservicio de locales y mesas. Arma la
 * info que se ve en /swagger-ui.html.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Arma la documentación base del microservicio de locales.
     *
     * @return el objeto OpenAPI con la info del servicio
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MarFuego - API de Locales y Mesas")
                        .description("Microservicio de gestión de locales y mesas del sistema MarFuego. "
                                + "Implementa la regla R3 (estado de mesas). "
                        )
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Benjamin Olea, Deiner Quero, Matias Jeldres")
                                .email("benj.olea@duocuc.cl, de.quero@duocuc.cl, mati.jeldres@duocuc.cl")
                                .url("https://github.com/Lunalker/dsy1103--MarFuego-Colectivo-Gastronomia---SyntaxSouls")));
    }
}
