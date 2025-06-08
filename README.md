# curbelo_alvaro_iborra_lenny_UT6UT7_NotasAPI
API REST en Java + Spring Boot que permite gestionar Usuarios y sus Notas de forma persistente, usando JPA.

# curbelo_alvaro_iborra_lenny_UT6UT7_NotasAPI

## Descripción del proyecto

API REST desarrollada en Java con Spring Boot que permite gestionar usuarios y sus notas de forma persistente utilizando JPA y MySQL. Incluye validación, manejo global de errores y endpoints para CRUD de usuarios y notas.

## Instrucciones de ejecución

1. **Clona el repositorio:**
   ```sh
   git clone https://github.com/curbeloalvaro/curbelo_alvaro_iborra_lenny_UT6UT7_NotasAPI.git
   cd curbelo_alvaro_iborra_lenny_UT6UT7_NotasAPI/actev

2. **Configura la base de datos MySQL**
    Crea una base de datos llamada UsuariosNotas.
    Edita el archivo src/main/resources/application.properties y pon tu usuario y contraseña de MySQL si es necesario.

3. **Compila y ejecuta la aplicación**
    En Linux/Mac: ./mvnw spring-boot:run
    En Windows: mvnw.cmd spring-boot:run

4. **Accede a la API**
    La API estará disponible en http://localhost:8080
