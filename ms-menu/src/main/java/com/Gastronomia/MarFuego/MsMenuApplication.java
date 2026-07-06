package com.Gastronomia.MarFuego;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Arranca el microservicio de menú. Se encarga de los platos y de si están
 * disponibles en cada local. Aplica la regla R1 (disponibilidad del plato) y la
 * R5 (que el precio sea al menos 3 veces el costo). Lo consultan ms-pedidos y
 * ms-reportes.
 */
@SpringBootApplication
public class MsMenuApplication {

	/**
	 * Punto de entrada del programa: aquí Spring Boot levanta el microservicio.
	 *
	 * @param args argumentos que se pasan por consola al iniciar
	 */
	public static void main(String[] args) {
		SpringApplication.run(MsMenuApplication.class, args);
	}

}
