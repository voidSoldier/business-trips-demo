# Business trips demo
Demo CRUD + enrichment app for business trips

- Save, Update, Delete: Producer -> RabbitMQ queue -> Consumer -> DB
- Read: directly from DB
- Enrichment: geocoding (real coordinates/default values) - Feign Client

## Stack:
- Java 11
- Spring Boot
- Rest API
- MongoDB
- RabbitMQ
- Spring Security + Token Bearer
- Spring Cloud (OpenFeign)
- Tests (Stubs, WireMock)


