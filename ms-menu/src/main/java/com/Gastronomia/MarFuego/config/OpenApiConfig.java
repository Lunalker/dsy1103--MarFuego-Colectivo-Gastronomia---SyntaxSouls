package com.Gastronomia.MarFuego.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Swagger para el microservicio de menú. Acá se arma la info
 * que se ve en /swagger-ui.html (título, descripción, versión y contacto).
 */
@Configuration
public class OpenApiConfig {

    /**
     * Arma la documentación base del microservicio de menú.
     *
     * @return el objeto OpenAPI con la info del servicio
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MarFuego - API de Menú").title("MarFuego - API de Locales y Mesas")
                        .description("Microservicio de gestión del menú de platos. "
                                + "Implementa las reglas R1 (disponibilidad del plato) y R5 (margen mínimo 300%).").description("Microservicio de gestión del Menu. "
                                + "Implementa la regla R3 (estado del Menu). "
                        )
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Benjamin Olea, Deiner Quero, Matias Jeldres")
                                .email("benj.olea@duocuc.cl, de.quero@duocuc.cl, mati.jeldres@duocuc.cl")
                                .url("https://github.com/Lunalker/dsy1103--MarFuego-Colectivo-Gastronomia---SyntaxSouls")));
    }
}

