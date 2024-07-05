# Forohub Alura
Proyecto final de la especialización en **Back-end con Java** del programa **Oracle Next Education.** Consiste en la creación de un foro
para manejar diversos tópicos relacionados a los cursos de la plataforma.

## Reglas de negocio
- **Registro de un tópico:** todos los campos son obligatorios, por lo tanto, es necesario verificar si todos los campos se están ingresando correctamente.
  La API no debe permitir el registro de tópicos duplicados (con el mismo título y mensaje).
- **Modificar un tópico:** las mismas reglas de negocio del registro de un tópico deben aplicarse también en su actualización.
- **Detallar un tópico:** Los datos de los tópicos (título, mensaje, fecha de creación, estado, autor y curso) deben ser devueltos en el cuerpo de la respuesta, en formato JSON.
- **Eliminar un tópico:** debe ser capaz de deshabilitar un tópico mediante el ID.


## Uso

- Clona este repositorio en tu máquina local.
```
git clone https://github.com/aguileraDev/forohub-alura.git
```
- Navega hasta el directorio del proyecto y abre el IDE de tu preferencia.

### Variables de entorno
Recuerda asignar las variables de entorno al sistema.

- DB_URL: la url para la base de datos.
- DB_USERNAME: usuario de la base de datos.
- DB_PASSWORD: contraseña para la base de datos.
- JWT_SECRET: clave secreta para el token JWT.
``` 
spring.datasource.url=${DB_URL} 
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
api.security.secret=${JWT_SECRET}
```
## Dependencias

- Java JDK 17 o superior instalado en tu sistema.
- Lombok
- Spring Web
- Spring Boot DevTools
- Spring Data JPA
- Flyway Migration
- PostgreSQL Driver
- Validation
- Spring Security
- Java Jwt

## Agradecimientos
Gracias a todo el equipo educativo y técnico que hacen posible el funcionamiento del programa Oracle Next Educatión.
## Autor

* **Manuel Aguilera**  - [aguileradev](https://github.com/aguileraDev)