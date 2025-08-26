# Users Management
API Rest para manejo básico de usuarios, cuenta con dos modulos, auth y usuarios:
- Auth: Genera la sesion para la autenticacion (JWT)
- Usuarios: CRUD de usuarios

## Ejecutar con Docker Compose
Para ejecutarlo se debe usar el comando:
``` shell
  docker compose up --build
```
Esto levanta dos contenedores de docker:
- Un servidor de base de datos (Postgres)
- Un servidor de aplicaciones

## Datos por defecto
Por defecto se crea un usuario para poder acceder y hacer uso de la aplicación:
``` json
{
  "correo": "wmachuca@admin.local",
  "nombres": "Wilmer",
  "contrasena": "Clave123",
  "telefono": "3121231231",
  "apellidos": "Machuca"
}
```

## Documentacion
Se puede abrir la documentacion viendola en Swagger:
http://localhost:8080/swagger-ui/index.html#/

## Notas del proyecto
- Las credenciales no viajan encriptadas por que asumo que se va a usar HTTPS
- Es necesario iniciar sesion con el endpoint en swagger y copiar el token en el boton de Authorize en swagger.
- La base de datos se crea por defecto usando las migraciones de flywaty, solo se requiere poner los datos de la BD en el application.prperties
- El endpoint de login no requiere autenticacion
- Se usa Hibernate para el acceso a la BD y records para los DTOs, por eso hay dos carpetas de modelos (model y dto)
