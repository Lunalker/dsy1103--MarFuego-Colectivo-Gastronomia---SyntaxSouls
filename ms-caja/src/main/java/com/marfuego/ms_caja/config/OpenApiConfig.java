package com.marfuego.ms_caja.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

    @Configuration
    public class OpenApiConfig {
        @Bean
        public OpenAPI customOpenAPI() {
            return new OpenAPI()
                    .info(new Info()
                            .title("MarFuego - API de Caja")
                            .description("Microservicio de gestión de la caja. "
                                    + "Implementa la regla R4 (Pago en delivery). "
                            )
                            .version("1.0.0")
                            .contact(new Contact()
                                    .name("Benjamin Olea, Deiner Quero, Matias Jeldres")
                                    .email("benj.olea@duocuc.cl, de.quero@duocuc.cl, mati.jeldres@duocuc.cl")
                                    .url("https://github.com/Lunalker/dsy1103--MarFuego-Colectivo-Gastronomia---SyntaxSouls")));
        }
    }



