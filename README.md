# Sistema de Inventario y Ventas (Spring Boot)

Aplicación web para gestionar productos, compras a proveedores y ventas a clientes, con autenticación por roles (`admin` y `cliente`) y vistas HTML con Thymeleaf.

## Tecnologías

- Java 21
- Spring Boot 3.5.10
- Spring Web
- Spring Data JPA
- Thymeleaf
- MySQL (principal)
- Maven Wrapper (`mvnw`, `mvnw.cmd`)

## Funcionalidades principales

- Inicio de sesión y cierre de sesión con sesión HTTP.
- Registro de nuevos usuarios cliente.
- Panel de administrador con alertas de inventario:
- Stock bajo (`stock < 10`).
- Productos inactivos (`estado_producto = "I"`).
- Gestión de productos (API REST).
- Gestión de proveedores y representantes legales.
- Registro de pedidos de compra (incrementa stock).
- Historial de compras.
- Flujo de compra de cliente (genera venta y descuenta stock).
- Historial de compras del cliente y facturas electrónicas.

## Estructura del proyecto

```text
src/main/java/com/Tienda/webapp
|- config/            # Inicialización de datos
|- controller/        # Controladores MVC y REST
|- model/             # Entidades JPA
|- repository/        # Repositorios Spring Data
|- service/           # Lógica de negocio

src/main/resources
|- static/            # CSS, JS, imágenes
|- templates/         # Vistas Thymeleaf
|- application.properties
```

## Requisitos

- JDK 21
- MySQL Server activo
- Base de datos creada: `tienda`

## Configuración

El proyecto usa estas propiedades en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tienda
spring.datasource.username=********
spring.datasource.password=************
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=8080
```

Ajusta `username` y `password` según tu entorno antes de ejecutar.

## Ejecución

En Windows (PowerShell):

```powershell
.\mvnw.cmd spring-boot:run
```

En Linux/macOS:

```bash
./mvnw spring-boot:run
```

Aplicación disponible en:

- `http://localhost:8080/`

## Endpoints principales

### Web (Thymeleaf)

- `GET /` login
- `POST /login` autenticación
- `GET /logout` cerrar sesión
- `GET /registro` formulario de registro
- `POST /registro` registro de cliente
- `GET /admin` dashboard administrador
- `GET /cliente` vista cliente
- `GET /mis-compras` historial de compras del usuario
- `GET /facturas` lista de facturas
- `GET /factura-electronica/{id}` detalle de factura

### API REST

Productos:

- `GET /api/productos`
- `GET /api/productos/{id}`
- `POST /api/productos`
- `DELETE /api/productos/{id}`

Ventas:

- `POST /api/ventas/finalizar`

Compras (admin):

- `POST /api/compras/guardar`

## Roles de usuario

- `idRol = 1`: administrador
- `idRol = 2`: cliente

Los usuarios registrados desde `/registro` se crean por defecto como cliente (`idRol = 2`).

## Scripts SQL incluidos

- `local.session.sql`: script base de creación de tablas e inserts iniciales.
- `update_images.sql`: actualiza imágenes y categorías de productos.

## Notas importantes

- El proyecto usa `spring.jpa.hibernate.ddl-auto=update`, por lo que Hibernate puede ajustar el esquema automáticamente según entidades.
- Existe un inicializador (`DataInitializer`) que completa/actualiza imágenes de productos al iniciar.
- La seguridad está basada en sesión y validaciones en controladores (no usa Spring Security).
