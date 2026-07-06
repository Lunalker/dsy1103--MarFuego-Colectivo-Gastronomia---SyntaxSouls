package com.marfuego.ms_locales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Arranca el microservicio de locales y mesas. Lleva los locales y sus mesas, y
 * aplica la regla R3, que es el ciclo de la mesa: LIBRE, RESERVADA u OCUPADA,
 * después LIMPIEZA por 15 minutos y de nuevo LIBRE.
 */
@SpringBootApplication
public class MsLocalesApplication {

	/**
	 * Punto de entrada del programa: aquí Spring Boot levanta el microservicio.
	 *
	 * @param args argumentos que se pasan por consola al iniciar
	 */
	public static void main(String[] args) {
		SpringApplication.run(MsLocalesApplication.class, args);
	}

}
