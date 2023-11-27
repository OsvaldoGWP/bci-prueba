# Proyecto test para BCI

## Stack

- OpenJDK 17
- Gradle 8+
- Spring Boot 3+
- JUnit 5 (Jupiter)
- H2

## Commands

Build

```bash
./gradlew build
```

Run Unit tests

```bash
./gradlew test
```

Comando para ejecutar la aplicación, una vez que levante ir a la url de swagger para probar la API

```bash
./gradlew bootRun
```

## Swagger

```bash
http://localhost:8080/swagger-ui
```

## Notas

- Se deja scripts en la raiz del proyecto en la carpeta database, para probar el proyecto no se necesita ejecutar los
  scripts, ya que se ejecutan automáticamente al activar la property spring.jpa.hibernate.ddl-auto=create-drop