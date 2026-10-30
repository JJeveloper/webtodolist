# WebTodoList

Aplicación web para organizar tareas personales y compartir tareas públicas con otros usuarios. El proyecto permite registrar usuarios, iniciar sesión, crear tareas con fechas y visibilidad, consultar el progreso y llevar un seguimiento diario de su cumplimiento.

## Características

- **Registro e inicio de sesión:** creación de cuentas mediante identificación, nombres y contraseña.
- **Validación de cédula ecuatoriana:** validación personalizada del formato y dígito verificador.
- **Gestión de tareas personales:** creación, consulta, detalle y eliminación de tareas propias.
- **Visibilidad de tareas:** cada tarea puede configurarse como `PUBLICA` o `PRIVADA`.
- **Listado de tareas públicas:** consulta paginada de tareas públicas creadas por los usuarios.
- **Seguimiento diario:** al crear una tarea se genera un registro de seguimiento por cada día del intervalo, incluyendo las fechas de inicio y fin.
- **Estados diarios:** cada registro puede marcarse como `CUMPLIDA`, `ENPROCESO` o `NOCUMPLIDA`.
- **Control de fechas futuras:** el servicio impide actualizar el estado de un día de seguimiento cuya fecha todavía no ha llegado.
- **Cálculo de progreso:** porcentaje calculado a partir de los días cumplidos respecto del total de días de la tarea.
- **Paginación:** listados de tareas y seguimiento diario paginados.
- **Validación de fechas:** evita rangos donde la fecha de inicio sea posterior a la fecha de fin.
- **Protección de recursos:** las consultas de detalle de tareas personales filtran por el usuario autenticado; la eliminación verifica que la tarea pertenezca al usuario.
- **Manejo centralizado de excepciones MVC:** mensajes para errores de fechas, recursos inexistentes, integridad referencial y acceso denegado.

## Tecnologías

| Tecnología | Uso |
|---|---|
| Java 21 | Lenguaje de programación |
| Spring Boot 4.1.0 | Framework principal |
| Spring MVC | Controladores y flujo web |
| Spring Data JPA | Persistencia y consultas |
| Hibernate / Jakarta Persistence | Mapeo objeto-relacional |
| Spring Security | Autenticación y autorización |
| BCrypt | Codificación de contraseñas |
| Thymeleaf | Motor de plantillas |
| Jakarta Bean Validation | Validación de formularios |
| MySQL | Base de datos relacional |
| Maven | Gestión de dependencias y compilación |
| Bootstrap 5.3.8 | Estilos y componentes de interfaz |

## Arquitectura del proyecto

El proyecto sigue una organización por capas para separar las responsabilidades:

```text
src/
└── main/
    ├── java/com/todolist/webtodolist/
    │   ├── controller/
    │   ├── exception/
    │   ├── model/
    │   │   ├── dto/
    │   │   ├── entity/
    │   │   └── validation/
    │   ├── repository/
    │   ├── security/
    │   ├── service/
    │   │   ├── impl/
    │   │   └── interfaces/
    │   └── WebtodolistApplication.java
    └── resources/
        ├── static/
        │   ├── css/
        │   └── js/
        ├── templates/
        │   └── fragments/
        └── application.properties
```

## Modelo de datos principal

- **Usuario:** almacena identificación, nombres, contraseña codificada, fecha de registro, estado de habilitación y rol.
- **Rol:** representa el rol asignado a cada usuario.
- **Tarea:** contiene título, descripción, fechas de inicio y fin, visibilidad y usuario propietario.
- **SeguimientoTarea:** registra el estado de una tarea para una fecha concreta.
- **Comentario:** entidad relacionada con una tarea y con el usuario que realiza el comentario.

Relaciones principales:

```text
Rol 1 ───── N Usuario
Usuario 1 ─ N Tarea
Usuario 1 ─ N Comentario
Tarea 1 ─── N SeguimientoTarea
Tarea 1 ─── N Comentario
```

## Seguridad

La aplicación utiliza Spring Security con autenticación basada en formulario y sesión HTTP.

- Página de inicio de sesión personalizada en `/login`.
- `UserDetailsService` consulta al usuario por su identificación.
- `DaoAuthenticationProvider` valida las credenciales.
- Las contraseñas se codifican mediante `BCryptPasswordEncoder`.
- Las rutas protegidas requieren autenticación.
- El cierre de sesión se realiza mediante `/logout`.
- La protección CSRF permanece habilitada.

## Requisitos previos

- JDK 21.
- MySQL Server.
- Git (opcional).
- Maven o Maven Wrapper incluido en el proyecto.

## Configuración de la base de datos

Crea la base de datos:

```sql
CREATE DATABASE webtodolistdb
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
```

Configura las credenciales en `src/main/resources/application.properties`:

```properties
spring.application.name=webtodolist
server.port=8090
spring.datasource.driver-class.name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.url=jdbc:mysql://localhost:3306/webtodolistdb
```

## Rutas principales

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/login` | Inicio de sesión |
| GET | `/registro` | Formulario de registro |
| POST | `/guardarusuario` | Registro de usuario |
| GET | `/tareas/publicas` | Listado de tareas públicas |
| GET | `/mostrarmistareas` | Tareas del usuario autenticado |
| GET | `/agregartarea` | Formulario de creación |
| POST | `/guardartarea` | Guardar tarea y generar seguimientos |
| GET | `/detalle-tarea/{id}` | Detalle y seguimiento |
| POST | `/seguimiento/{id}/estado` | Actualizar estado diario |
| POST | `/eliminar-tarea/{id}` | Eliminar tarea propia |
| POST | `/logout` | Cerrar sesión |

## Lógica del seguimiento y progreso

Al guardar una tarea, el servicio genera un registro `SeguimientoTarea` para cada fecha desde `fechaInicio` hasta `fechaFin`, ambas inclusive.

Cada registro comienza con el estado `ENPROCESO`.

El progreso se calcula mediante:

```text
Progreso (%) = (días cumplidos / total de días de la tarea) × 100
```

El seguimiento se muestra paginado y el servicio comprueba que la fecha del registro no sea posterior al día actual antes de permitir cambiar su estado.

## Registro de usuarios

La aplicación cuenta con una ventana de registro que permite a los nuevos usuarios crear una cuenta para acceder a las funcionalidades de WebTodoList.

Desde esta ventana, el usuario puede ingresar sus datos personales y credenciales de acceso para crear su cuenta.

## Base de datos

El proyecto incluye un script SQL para facilitar la creación de la base de datos y sus respectivas tablas.

**Ubicación del archivo:**

```text
database/
└── webtodolist.sql
```


### Importar la base de datos

1. Clonar o descargar el repositorio.
2. Abrir MySQL Workbench.
3. Abrir el archivo `database/webtodolist.sql`.
4. Ejecutar el script SQL.
5. Verificar que la base de datos se haya creado correctamente.
6. Configurar las credenciales de conexión en `application.properties`.

La base de datos utilizada por la aplicación se llama `webtodolistdb`.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/webtodolistdb
spring.datasource.username=root
spring.datasource.password=TU_CONTRASEÑA
```

**Nota:** debes modificar el usuario y la contraseña según tu configuración local de MySQL.

---

**Funcionalidades del registro:**

- Formulario para ingresar nombres, nombre de usuario, correo electrónico y contraseña.
- Validación de los campos obligatorios.
- Validación del formato del correo electrónico.
- Validación de la contraseña.
- Comprobación que la cedula sea unica.
- Asignación automática del rol `USER` para los usuarios que se registran desde el formulario público.
- Al completar el registro correctamente, el usuario puede iniciar sesión y comenzar a administrar sus tareas.

## Mejoras futuras

- Implementar operaciones para crear, editar y eliminar comentarios en tareas públicas.
- Incorporar filtros funcionales por título, estado, visibilidad y orden.
- Centralizar la configuración mediante variables de entorno.
- Añadir pruebas unitarias y de integración.
- Incorporar migraciones de base de datos.

---

**WebTodoList** — organiza tus tareas, registra tu progreso y comparte tus objetivos.
