package com.marfuego.ms_caja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Arranca el microservicio de caja. Lleva las boletas y las facturas. Aplica la
 * regla R4: las boletas guardan el id del pedido, así se puede saber si un
 * pedido de tipo DELIVERY ya tiene su pago hecho.
 */
@SpringBootApplication
public class MsCajaApplication {

	/**
	 * Punto de entrada del programa: aquí Spring Boot levanta el microservicio.
	 *
	 * @param args argumentos que se pasan por consola al iniciar
	 */
	public static void main(String[] args) {
		SpringApplication.run(MsCajaApplication.class, args);
	}

}
