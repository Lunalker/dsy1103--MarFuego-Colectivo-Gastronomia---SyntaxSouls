# MarFuego — Colectivo de Gastronomía 🔥🌊

**DSY1103 — Desarrollo Full Stack I | Evaluación Parcial 3**

Sistema de **arquitectura de microservicios** para una cadena de restaurantes del sur de Chile (Puerto Montt, Pelluco, Ancud y Castro). El proyecto está compuesto por 6 microservicios de negocio independientes, un **API Gateway** que centraliza el acceso, y una base de datos MySQL por servicio. Todo se orquesta con Docker.

---

## Equipo

| Nombre          | Usuario GitHub   | Rol                |
|-----------------|------------------|--------------------|
| Matías Jeldres  | HappyPenguin05   | Backend Developer  |
| Deiner Quero    | Lunalker         | Backend Developer  |
| Benjamín Olea   | Binyax           | Backend Developer  |

---

## Arquitectura

Todas las peticiones entran por el **API Gateway** (puerto `8080`), que las enruta al microservicio correspondiente según la ruta. Cada microservicio tiene su propia base de datos.

```
                          ┌────────────────────────┐
   Cliente / Postman ───► │   API Gateway  :8080   │
                          │  (Spring Cloud Gateway)│
                          └───────────┬────────────┘
                                      │  enruta por /api/v1/**
        ┌──────────────┬──────────────┼──────────────┬──────────────┬──────────────┐
        ▼              ▼              ▼              ▼              ▼              ▼
   ms-locales      ms-menu     ms-inventario    ms-pedidos      ms-caja      ms-reportes
     :8081          :8082          :8083           :8084          :8085          :8086
        │              │              │              │              │              │
        ▼              ▼              ▼              ▼              ▼              │ (WebClient)
   MySQL BD        MySQL BD        MySQL BD        MySQL BD        MySQL BD    consume a los demás
```

### Microservicios

| # | Microservicio  | Puerto | Base de datos         | Responsabilidad                                  |
|---|----------------|--------|-----------------------|--------------------------------------------------|
| 0 | **ms-gateway** | 8080   | (sin BD)              | Punto único de entrada; enruta a todos los MS    |
| 1 | ms-locales     | 8081   | marfuego_locales      | Locales y mesas (**R3**)                          |
| 2 | ms-menu        | 8082   | marfuego_menu         | Platos y disponibilidad (**R1**, **R5**)          |
| 3 | ms-inventario  | 8083   | marfuego_inventario   | Stock de ingredientes (**R2**)                    |
| 4 | ms-pedidos     | 8084   | marfuego_pedidos      | Pedidos y sus detalles                            |
| 5 | ms-caja        | 8085   | marfuego_caja         | Boletas y facturas (**R4**)                       |
| 6 | ms-reportes    | 8086   | (sin BD)              | Reportes que consolidan datos de otros MS         |

---

## API Gateway — rutas

El Gateway (Spring Cloud Gateway) centraliza el enrutamiento con predicados de tipo `Path` y filtros. Toda petición se hace contra `http://localhost:8080`.

| Ruta (predicado `Path`)      | Microservicio destino | Puerto interno |
|------------------------------|-----------------------|----------------|
| `/api/v1/locales/**`         | ms-locales            | 8081           |
| `/api/v1/menu/**`            | ms-menu               | 8082           |
| `/api/v1/inventario/**`      | ms-inventario         | 8083           |
| `/api/v1/pedidos/**`         | ms-pedidos            | 8084           |
| `/api/v1/caja/**`            | ms-caja               | 8085           |
| `/api/v1/reportes/**`        | ms-reportes           | 8086           |

**Filtros configurados:**
- `default-filter` `AddResponseHeader=X-MarFuego-Gateway, true` — se aplica a **todas** las respuestas (permite verificar que la petición pasó por el Gateway).
- Filtro por ruta `AddRequestHeader=X-Origen, marfuego-gateway` en `ms-pedidos`.
- Filtro **global** en Java (`LoggingGlobalFilter`) que registra en el log cada petición y su código de respuesta.

Verificación rápida:
```bash
curl -i http://localhost:8080/api/v1/locales
# HTTP/1.1 200 OK
# X-MarFuego-Gateway: true
# []
```

---

## Documentación Swagger / OpenAPI

Cada microservicio expone su propia interfaz Swagger UI (springdoc-openapi):

| Microservicio  | Swagger UI                                   |
|----------------|----------------------------------------------|
| ms-locales     | http://localhost:8081/swagger-ui.html        |
| ms-menu        | http://localhost:8082/swagger-ui.html        |
| ms-inventario  | http://localhost:8083/swagger-ui.html        |
| ms-pedidos     | http://localhost:8084/swagger-ui.html        |
| ms-caja        | http://localhost:8085/swagger-ui.html        |
| ms-reportes    | http://localhost:8086/swagger-ui.html        |

---

## Endpoints principales

Todos accesibles a través del Gateway (`http://localhost:8080` + la ruta).

**ms-locales** (`/api/v1/locales`)
- `GET /api/v1/locales` · `GET /api/v1/locales/{id}` · `POST /api/v1/locales` · `PUT /api/v1/locales/{id}` · `DELETE /api/v1/locales/{id}`
- `GET /api/v1/locales/mesas` · `GET /api/v1/locales/{localId}/mesas` · `GET /api/v1/locales/mesas/estado/{estado}`
- Cambios de estado de mesa (R3): reservar / ocupar / liberar

**ms-menu** (`/api/v1/menu`)
- `GET /api/v1/menu/platos` · `GET /api/v1/menu/platos/{id}` · `POST /api/v1/menu/platos` · `PUT /api/v1/menu/platos/{id}` · `DELETE /api/v1/menu/platos/{id}`

**ms-inventario** (`/api/v1/inventario`)
- `GET /api/v1/inventario/ingredientes` · `GET /api/v1/inventario/ingredientes/alertas` · `GET /api/v1/inventario/ingredientes/{id}`
- `POST /api/v1/inventario/ingredientes` · `PUT /api/v1/inventario/ingredientes/{id}` · `DELETE /api/v1/inventario/ingredientes/{id}`
- `POST /api/v1/inventario/ingredientes/{id}/descontar` (R2)

**ms-pedidos** (`/api/v1/pedidos`)
- `GET /api/v1/pedidos` · `GET /api/v1/pedidos/{id}` · `GET /api/v1/pedidos/local/{localId}` · `GET /api/v1/pedidos/estado/{estado}`
- `POST /api/v1/pedidos` · `PUT /api/v1/pedidos/{id}` · `DELETE /api/v1/pedidos/{id}`

**ms-caja** (`/api/v1/caja`)
- Boletas y facturas (crear, listar, buscar por id, buscar boleta por pedido — R4)

**ms-reportes** (`/api/v1/reportes`)
- `GET /api/v1/reportes/stock-critico` · `GET /api/v1/reportes/rentabilidad-platos` · `GET /api/v1/reportes/resumen-local/{id}`

---

## Reglas de negocio

- **R1 — Disponibilidad del plato:** un plato solo se puede pedir si está marcado como disponible para el día en ese local.
- **R2 — Stock mínimo:** al descontar stock se valida que haya suficiente; si queda bajo el mínimo configurado se genera una alerta.
- **R3 — Estado de mesa:** una mesa OCUPADA no puede recibir reservas. Al cerrar la cuenta pasa a LIMPIEZA por 15 min y luego a LIBRE.
- **R4 — Pago en delivery:** las boletas guardan el `pedidoId` para poder validar que un pedido tipo DELIVERY tenga pago procesado.
- **R5 — Margen mínimo:** el precio de venta de un plato debe ser al menos 3 veces el costo (markup 300%). Si no cumple, se deja una advertencia en los logs.

---

## Comunicación entre microservicios (REST / WebClient)

- **ms-pedidos → ms-menu:** al crear un pedido, verifica la disponibilidad de cada plato (regla R1 cruzada).
- **ms-reportes → ms-locales, ms-menu, ms-inventario, ms-pedidos:** consume los 4 microservicios para armar reportes consolidados (resumen del local, stock crítico y rentabilidad).

---

## Tecnologías

- **Java 17**
- **Spring Boot 4.0.6** en los microservicios (Spring Web, Spring Data JPA, Bean Validation)
- **Spring Cloud Gateway** (Spring Boot 3.3.x) en el `ms-gateway`
- **Spring WebFlux (WebClient)** en `ms-pedidos` y `ms-reportes`
- **MySQL 8**
- **Lombok** y **SLF4J** (logging)
- **springdoc-openapi** (Swagger UI)
- **Docker** y **Docker Compose**
- **Maven** (con wrapper `mvnw`)

---

## Cómo ejecutar el proyecto

### Opción A — Docker (recomendada)

Requisitos: **Docker Desktop**.

Desde la raíz del proyecto:

```bash
docker-compose up --build
```

Esto levanta **MySQL + los 6 microservicios + el API Gateway**. La primera vez tarda unos minutos (descarga dependencias e inicializa MySQL). Cuando todos los servicios muestren `Started ... Application` y el gateway `Netty started on port 8080`, el sistema está listo.

Todo se consume a través del Gateway:

```bash
curl http://localhost:8080/api/v1/menu/platos
curl http://localhost:8080/api/v1/inventario/ingredientes
```

Para apagar todo:

```bash
docker-compose down
```

### Opción B — Local (sin Docker)

Requisitos: **Java 17**, **Maven** (o el wrapper `./mvnw`) y **MySQL 8** corriendo en `localhost:3306` con usuario `root` sin password.

1. Las bases de datos se crean solas gracias a `createDatabaseIfNotExist=true` en cada `application.properties`.
2. Levantar cada microservicio (en terminales separadas):

   ```bash
   cd ms-locales    && ./mvnw spring-boot:run
   cd ms-menu       && ./mvnw spring-boot:run
   cd ms-inventario && ./mvnw spring-boot:run
   cd ms-pedidos    && ./mvnw spring-boot:run
   cd ms-caja       && ./mvnw spring-boot:run
   cd ms-reportes   && ./mvnw spring-boot:run
   ```

3. Levantar el Gateway al final:

   ```bash
   cd ms-gateway && ./mvnw spring-boot:run
   ```

> Importante: en modo local, levanta los 5 microservicios con BD **antes** de `ms-reportes`, porque este los consulta.

### Probar con Postman

En la carpeta `postman/` está la colección lista para importar (`MarFuego.postman_collection.json`). Para probar a través del Gateway, apunta las peticiones al puerto **8080**.

---

## Estructura del repositorio

```
├── ms-gateway/        # API Gateway (Spring Cloud Gateway) — puerto 8080
├── ms-locales/        # Locales y mesas (R3)
├── ms-menu/           # Menú de platos (R1, R5)
├── ms-inventario/     # Inventario de ingredientes (R2)
├── ms-pedidos/        # Pedidos y detalles
├── ms-caja/           # Boletas y facturas (R4)
├── ms-reportes/       # Reportes que consumen a otros MS
├── docker/            # Script de inicialización de MySQL
├── postman/           # Colección de Postman
├── docker-compose.yml # Orquestación de todos los servicios
└── README.md
```

---

## Estado del proyecto

Evaluación Parcial 3 — 2025. Arquitectura de microservicios operativa (local y vía Docker), con API Gateway, documentación Swagger por servicio y despliegue en contenedores.

---

## Licencia

Este proyecto está licenciado bajo la **Licencia MIT**. Consulta el archivo [LICENSE](LICENSE) para los detalles completos.
