package com.marfuego.ms_inventario.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Swagger para el microservicio de inventario. Acá se arma la
 * info que aparece en la interfaz de Swagger (título, descripción, versión y
 * contacto), que se puede abrir en /swagger-ui.html.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Arma la documentación base del microservicio de inventario.
     *
     * @return el objeto OpenAPI con toda la info del servicio
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MarFuego - API de Inventario")
                        .description("Microservicio de gestión de inventario de ingredientes. "
                                + "Implementa la regla R2 (control de stock mínimo y alertas de reposición).")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Benjamin Olea, Deiner Quero, Matias Jeldres")
                                .email("benj.olea@duocuc.cl, de.quero@duocuc.cl, mati.jeldres@duocuc.cl")
                                .url("https://github.com/Lunalker/dsy1103--MarFuego-Colectivo-Gastronomia---SyntaxSouls")));
    }
}
