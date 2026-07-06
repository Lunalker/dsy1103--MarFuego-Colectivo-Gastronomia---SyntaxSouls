package com.marfuego.ms_inventario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Arranca el microservicio de inventario. Este servicio lleva el control del
 * stock de los ingredientes y se encarga de la regla R2, que es avisar cuando
 * un ingrediente queda por debajo de su mínimo. Lo consultan ms-pedidos (para
 * descontar stock) y ms-reportes.
 */
@SpringBootApplication
public class MsInventarioApplication {

	/**
	 * Punto de entrada del programa: aquí Spring Boot levanta el microservicio.
	 *
	 * @param args argumentos que se pasan por consola al iniciar
	 */
	public static void main(String[] args) {
		SpringApplication.run(MsInventarioApplication.class, args);
	}

}
