# device-management-app
### Description 
REST API application for managing device resources

### Run Locally
```bash
./mvn clean package -DskipTests 
```

```bash
docker build -t springboot-restfull-webservices:latest . 
```

```bash
cd ./scripts/docker
docker compose up -d
```

```bash
docker ps
```

```bash
docker compose down
```

### Backlog

DMA-00 Create Git Repository

DMA-01 Create Java 21 Maven Project With Structure

DMA-02 Create Docker and MySQl Settings

DMA-03 Create Entity, Repository, Service and Controller Layers

DMA-04 Implement Create Function and Exception Handling

DMA-05 Implement Get, Update, Delete Functions

DMA-06 Implement Search Filters

DMA-07 Implement Documentation

### Tech Stack
- Java 21
- Spring Boot
- Spring Data JPA
- MySQL
- Docker
- Maven
- JUnit & Mockito
- Lombok
- Swagger
- MapStruct
- SLF4J
- Spring Security
- Spring Boot Actuator