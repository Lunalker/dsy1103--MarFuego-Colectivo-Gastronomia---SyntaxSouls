package com.marfuego.ms_pedidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Arranca el microservicio de pedidos. Lleva los pedidos y sus detalles, y cada
 * vez que se crea uno le pregunta a ms-menu por REST si los platos están
 * disponibles (regla R1 cruzada).
 */
@SpringBootApplication
public class MsPedidosApplication {

	/**
	 * Punto de entrada del programa: aquí Spring Boot levanta el microservicio.
	 *
	 * @param args argumentos que se pasan por consola al iniciar
	 */
	public static void main(String[] args) {
		SpringApplication.run(MsPedidosApplication.class, args);
	}

}
