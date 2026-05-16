# Clientes Cuentas Microservicio

## 1. Descripción

Microservicio REST para consulta de clientes y gestión de cuentas bancarias.

Permite:
- consultar clientes paginados
- consultar clientes mayores de edad
- consultar clientes cuya suma total de saldos supera una cantidad
- crear cuentas bancarias
- actualizar saldos de cuentas existentes

La persistencia se realiza en H2 en memoria, la API está documentada con OpenAPI/Swagger y la solución sigue una arquitectura hexagonal pragmática.

## 2. Stack técnico

| Tecnología | Uso |
|---|---|
| Java 21 | Lenguaje y runtime |
| Spring Boot 3.5.x | Framework principal |
| Maven | Build y dependencias |
| Spring Web | API REST |
| Spring Data JPA | Acceso a datos |
| H2 Database | Base de datos en memoria |
| Bean Validation | Validación de requests y parámetros |
| Springdoc OpenAPI | Documentación Swagger/OpenAPI |
| Lombok | Reducción de código repetitivo |
| JUnit 5 | Testing unitario/integración |
| Mockito | Mocks en tests unitarios |
| MockMvc | Testing de integración REST |

## 3. Arquitectura

Estructura por capas:

- `domain`:
  - Entidades de dominio.
  - Excepciones propias.
- `application`:
  - Puertos de entrada.
  - Puertos de salida.
  - Servicios/casos de uso.
- `infrastructure`:
  - Adaptador REST.
  - DTOs.
  - Mappers.
  - Manejo de errores REST.
  - Adaptador de persistencia.
  - Repositories JPA.
  - Configuración.

Esquema de flujo:

`REST Controller` -> `UseCase` -> `Service` -> `RepositoryPort` -> `PersistenceAdapter` -> `JpaRepository` -> `H2`

Los controllers no acceden directamente a repositories y la capa `application` no depende de DTOs REST.

## 4. Decisiones técnicas

### 4.1 Nomenclatura en español

Dado que el enunciado define entidades, endpoints y campos en castellano, se ha mantenido la nomenclatura principal en español para respetar el contrato solicitado y facilitar la trazabilidad con el PDF de la prueba.

### 4.2 Uso de BigDecimal para importes

Aunque el enunciado pueda representar importes como números simples, se usa `BigDecimal` para importes monetarios. Evita problemas de precisión propios de `double/float` y es una práctica más segura para cantidades económicas.

### 4.3 Paginación

Los endpoints de listado devuelven respuesta paginada para evitar devolver colecciones completas y mejorar escalabilidad. Se soportan parámetros `page`, `size` y `sort`.

### 4.4 Filtros en base de datos

Los filtros no se hacen cargando todos los datos en memoria:
- clientes mayores de edad: filtrado mediante query
- clientes con saldo total superior a una cantidad: cálculo en base de datos con `JOIN/GROUP BY/HAVING`

Esta decisión sigue el criterio de rendimiento y escalabilidad indicado para la prueba, evitando cargar colecciones completas para filtrarlas posteriormente en memoria.

### 4.5 Carga explícita de cuentas y open-in-view=false

Se mantiene `spring.jpa.open-in-view=false`.  
No se usa `FetchType.EAGER` ni transacciones en controllers.

Para evitar `LazyInitializationException`, los clientes se consultan paginados y luego se cargan explícitamente sus cuentas mediante una query `LEFT JOIN FETCH` filtrada por los DNIs de la página actual. Esto evita cargar datos innecesarios y mantiene la paginación.

### 4.6 Creación de cuentas bancarias

El enunciado indica que, al crear una cuenta bancaria, si el cliente existe se asocia la cuenta y si no existe, se pueda crear un nuevo cliente.

En esta implementación, el request de alta solo contiene `dniCliente`, `tipoCuenta` y `total`, y no incluye `nombre`, `apellidos` ni `fechaNacimiento`.

Crear automáticamente un cliente implicaría generar datos incompletos o inventados. Por ello, la implementación requiere que el cliente exista previamente. Si el cliente no existe, la API devuelve `404 Cliente no encontrado`.

### 4.7 Dependencias y seguridad

Se fijó explícitamente `commons-lang3` para resolver un warning de seguridad asociado a `CVE-2025-48924` en una dependencia transitiva.
Aunque no forma parte de la lógica de negocio, se declara para controlar la versión resuelta por Maven.

No se añade Spring Security porque la prueba no solicita autenticación/autorización y añadirlo cambiaría el alcance.

## 5. Modelo de datos

- `Cliente`
- `CuentaBancaria`
- Relación `1:N` (`Cliente` -> `CuentaBancaria`)
- `TipoCuenta`: `PREMIUM`, `NORMAL`, `JUNIOR`

Datos iniciales cargados desde `data.sql`:
- 5 clientes
- 7 cuentas bancarias

## 6. Endpoints principales

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/clientes` | Obtiene clientes paginados con sus cuentas |
| GET | `/clientes/mayores-de-edad` | Obtiene clientes mayores de edad |
| GET | `/clientes/con-cuenta-superior-a/{cantidad}` | Obtiene clientes cuya suma total de cuentas supera la cantidad indicada |
| GET | `/clientes/{dni}` | Obtiene un cliente por DNI |
| POST | `/cuentas` | Crea una cuenta bancaria |
| PUT | `/cuentas/{idCuenta}` | Actualiza el saldo de una cuenta bancaria |

## 7. Documentación OpenAPI / Swagger

Swagger UI:  
`http://localhost:8080/swagger-ui/index.html`

OpenAPI JSON:  
`http://localhost:8080/v3/api-docs`

La documentación se genera con Springdoc OpenAPI. Se usa una interfaz Java anotada por cada controller para mantener separado el contrato REST de la implementación.

## 8. Cómo ejecutar el proyecto

Requisitos:

```bash
java --version
mvn -version
```

Versiones esperadas:
- Java 21
- Maven

Validar build y tests:

```bash
mvn clean test
```

Arrancar la aplicación:

```bash
mvn spring-boot:run
```

La aplicación queda disponible en:

`http://localhost:8080`

## 9. Base de datos H2

H2 Console:  
`http://localhost:8080/h2-console`

Datos de conexión:
- JDBC URL: `jdbc:h2:mem:clientes_cuentas_db`
- User Name: `sa`
- Password: *(vacío)*

## 10. Ejemplos curl

Obtener clientes paginados:

```bash
curl -X GET "http://localhost:8080/clientes?page=0&size=10&sort=dni,asc"
```

Obtener clientes mayores de edad:

```bash
curl -X GET "http://localhost:8080/clientes/mayores-de-edad?page=0&size=10&sort=dni,asc"
```

Obtener clientes con cuentas superiores a una cantidad:

```bash
curl -X GET "http://localhost:8080/clientes/con-cuenta-superior-a/50000?page=0&size=10&sort=dni,asc"
```

Obtener cliente por DNI:

```bash
curl -X GET "http://localhost:8080/clientes/11111111A"
```

Crear cuenta bancaria:

```bash
curl -X POST "http://localhost:8080/cuentas" \
  -H "Content-Type: application/json" \
  -d '{
    "dniCliente": "11111111A",
    "tipoCuenta": "NORMAL",
    "total": 50000.00
  }'
```

Actualizar saldo de cuenta bancaria:

```bash
curl -X PUT "http://localhost:8080/cuentas/1" \
  -H "Content-Type: application/json" \
  -d '{
    "total": 180000.00
  }'
```

## 11. Manejo de errores

Se usa `ProblemDetail` y los errores se centralizan en `GlobalExceptionHandler`.  
No se capturan excepciones manualmente en controllers.

Casos principales:
- cliente no encontrado -> `404`
- cuenta bancaria no encontrada -> `404`
- validación de request -> `400`
- enum inválido -> `400`
- parámetros inválidos -> `400`
- error inesperado -> `500`

Ejemplo:

```json
{
  "type": "about:blank",
  "title": "Cliente no encontrado",
  "status": 404,
  "detail": "No existe ningún cliente con DNI 99999999Z"
}
```

## 12. Testing

Unitarios:
- `ClienteServiceTest`
- `CuentaBancariaServiceTest`
- JUnit 5 + Mockito
- No levantan contexto Spring

Integración:
- `ClienteControllerIntegrationTest`
- `CuentaBancariaControllerIntegrationTest`
- `@SpringBootTest`
- `@AutoConfigureMockMvc`
- H2
- Sin mocks
- `@Transactional` para rollback tras cada test y evitar contaminación entre casos

Comando:

```bash
mvn clean test
```

Resultado actual:
- 28 tests
- 0 failures

## 13. Colección Postman

Se incluye una colección Postman para pruebas manuales/E2E:

`postman/Test E2E clientes-cuentas-microservicio.postman_collection.json`

La colección usa la variable `baseUrl = http://localhost:8080`.

Se ha validado con Postman Runner ejecutando 5 iteraciones sin errores.

## 14. Alcance

No se ha añadido autenticación/autorización porque no forma parte del alcance solicitado.

Se prioriza el alcance solicitado: API REST, persistencia, validaciones, documentación, manejo de errores y testing.

En un entorno productivo podrían añadirse Spring Security, observabilidad, perfiles por entorno, métricas y logging avanzado.
