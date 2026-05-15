# MarFuego Colectivo Gastronomia — DSY1103 Desarrollo FullStack 1

Proyecto Semestral EP2 - sistema de microservicios para una cadena de
restaurantes del sur de Chile (Puerto Montt, Pelluco, Ancud y Castro).

## Equipo
| Nombre         | GitHub         |
|----------------|----------------|
| Matias Jeldres | HappyPenguin05 |
| Deiner Quero   | Lunalker       |
| Benjamin Olea  | Binyax         |

## Microservicios

| # | Microservicio   | Puerto | Base de datos        | Responsabilidad |
|---|-----------------|--------|----------------------|-----------------|
| 1 | ms-locales      | 8081   | marfuego_locales     | Locales y mesas (R3) |
| 2 | ms-menu         | 8082   | marfuego_menu        | Platos y disponibilidad (R1, R5) |
| 3 | ms-inventario   | 8083   | marfuego_inventario  | Stock de ingredientes (R2) |
| 4 | ms-pedidos      | 8084   | marfuego_pedidos     | Pedidos y detalles |
| 5 | ms-reportes     | 8086   | (sin BD)             | Reportes que consultan a otros MS |

## Reglas de negocio implementadas

- **R1** Disponibilidad del plato: un plato solo se puede pedir si esta marcado como disponible para el dia en ese local.
- **R2** Stock minimo: al descontar stock se valida que haya suficiente. Si queda bajo el minimo configurado se genera una alerta.
- **R3** Estado de mesa: una mesa OCUPADA no puede recibir reservas. Al cerrar la cuenta pasa a LIMPIEZA por 15 min y despues a LIBRE.
- **R5** Margen minimo: el precio de venta de un plato debe ser al menos 3 veces el costo (markup 300%). Si no cumple se deja una advertencia en los logs.

(R4 se implementara cuando se agregue ms-caja).

## Comunicacion entre microservicios

El microservicio `ms-reportes` consume datos de otros 3 microservicios via WebClient:
- Llama a `ms-locales` para obtener info del local
- Llama a `ms-menu` para obtener los platos
- Llama a `ms-inventario` para obtener stock e ingredientes en alerta
- Llama a `ms-pedidos` para obtener los pedidos del local

## Tecnologias usadas

- Java 17
- Spring Boot 4.0.6 (Spring Web, Spring Data JPA, Bean Validation)
- Spring WebFlux (WebClient) en ms-reportes
- MySQL 8
- Lombok
- SLF4J para los logs
- Maven

## Como ejecutar el proyecto

### 1. Requisitos previos
- Java 17 o superior
- Laragon (o MySQL 8) corriendo en `localhost:3306` con usuario `root` sin password
- IntelliJ IDEA o VSCode

### 2. Crear las bases de datos
Ejecutar en HeidiSQL:

```sql
CREATE DATABASE marfuego_locales CHARACTER SET utf8mb4;
CREATE DATABASE marfuego_menu CHARACTER SET utf8mb4;
CREATE DATABASE marfuego_inventario CHARACTER SET utf8mb4;
CREATE DATABASE marfuego_pedidos CHARACTER SET utf8mb4;
```

### 3. Levantar los microservicios

Desde IntelliJ, abrir cada `*Application.java` y ejecutarlo, o por terminal:

```bash
cd ms-locales    && ./mvnw spring-boot:run
cd ms-menu       && ./mvnw spring-boot:run
cd ms-inventario && ./mvnw spring-boot:run
cd ms-pedidos    && ./mvnw spring-boot:run
cd ms-reportes   && ./mvnw spring-boot:run
```

Importante: levantar primero los 4 microservicios con BD antes de ms-reportes,
porque ms-reportes los va a consultar.

## Estructura del repositorio

```
├── ms-locales/        # Microservicio de locales y mesas
├── ms-menu/           # Microservicio del menu
├── ms-inventario/     # Microservicio del inventario
├── ms-pedidos/        # Microservicio de pedidos
├── ms-reportes/       # Microservicio de reportes (consume otros MS)
├── postman/           # Coleccion de Postman
└── README.md
```

## Estado del proyecto

En desarrollo - EP2 2025
