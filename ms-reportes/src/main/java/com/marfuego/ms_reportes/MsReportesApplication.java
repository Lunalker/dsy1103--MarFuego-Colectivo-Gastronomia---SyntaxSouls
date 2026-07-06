package com.marfuego.ms_reportes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Arranca el microservicio de reportes. Este no tiene base de datos propia: lo
 * que hace es llamar por REST a los otros microservicios (locales, menú,
 * inventario y pedidos) y juntar esa info para armar reportes.
 */
@SpringBootApplication
public class MsReportesApplication {

    /**
     * Punto de entrada del programa: aquí Spring Boot levanta el microservicio.
     *
     * @param args argumentos que se pasan por consola al iniciar
     */
    public static void main(String[] args) {
        SpringApplication.run(MsReportesApplication.class, args);
    }
}
