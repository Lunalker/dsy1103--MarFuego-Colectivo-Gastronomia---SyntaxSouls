package com.marfuego.ms_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Arranca el API Gateway de MarFuego. Este es la única puerta de entrada del
 * sistema: recibe todas las peticiones en el puerto 8080 y, según la ruta, las
 * reenvía al microservicio que corresponde (locales, menú, inventario, pedidos,
 * caja o reportes). Así los clientes no tienen que conocer el puerto de cada MS.
 */
@SpringBootApplication
public class MsGatewayApplication {

	/**
	 * Punto de entrada del programa: aquí Spring Boot levanta el Gateway.
	 *
	 * @param args argumentos que se pasan por consola al iniciar
	 */
	public static void main(String[] args) {
		SpringApplication.run(MsGatewayApplication.class, args);
	}

}
