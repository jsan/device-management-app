# device-management-app

### Description 
REST API application for managing device resources

### Features
- Create, Read, Update, Delete operations for device resources.
- Search and filter devices by attributes brand and state.
- Pagination support for listing devices.
- Input validation and error handling.
- API documentation using Swagger.
- Health monitoring with Spring Boot Actuator.
- Containerized using Docker and Docker Compose.
- Unit tests for core business logic.
- Logging with SLF4J.
- DTO mapping with MapStruct.
- MySQL database integration.

### Run Locally
1. Ensure Docker is installed and running on your machine.
2. Clone the repository to your local machine on a folder of your choice:
```bash
./git clone https://github.com/jsan/device-management-app.git 
```
3. Navigate to the project root directory.
4. Build the project using Maven: 
```bash
./mvn clean package -DskipTests 
```
5. Build the Docker image:
```bash
docker build -t device-management-app:latest . 
```
6. Start the application using Docker Compose:
```bash
cd ./scripts/docker
docker compose up -d
```
7. Verify the application is running:
```bash
docker ps
```
8. To stop the application, run:
```bash
docker compose down
```
9. Access the API documentation at: http://localhost:8080/swagger-ui/index.html
10. Access the Actuator endpoints at: http://localhost:8080/actuator

### Prerequisites
- Java 21
- Maven
- Docker
- Docker Compose
- Git
- Postman or any API testing tool

### Backlog

DMA-00 Create Git Repository

DMA-01 Create Java 21 Maven Project With Structure

DMA-02 Create Docker and MySQl Settings

DMA-03 Create Entity, Repository, Service and Controller Layers

DMA-04 Implement Create Function and Exception Handling

DMA-05 Implement Get methods

DMA-06 Implement Search Filters

DMA-07 Update, Delete Functions

DMA-08 Implement Documentation

### Tech Stack
- Java 21
- Spring Boot latest
- Spring Data JPA
- MySQL
- Docker
- Maven for build and dependency management.
- JUnit & Mockito
- Lombok to reduce boilerplate code.
- Swagger
- MapStruct
- SLF4J
- Spring Security
- Spring Boot Actuator
- Java 21 and Spring Boot latest version.

### Spillover
- Documentation error page could not be completed within the initial project scope.

### Future Improvements
- Future enhancements could include adding authentication and authorization using Spring Security.
- Integration tests would be a logical next step to verify the end-to-end flow.
- Implementing caching for frequently accessed data to improve performance.
- Adding more comprehensive logging and monitoring.
- K6 or JMeter for performance testing.
- Kafka integration for event-driven architecture.
- Frontend application using React or Angular.
- CI/CD pipeline setup using GitHub Actions or Jenkins.
- RestClint for inter-service communication.
- WireMock for mocking external services in tests.
- Flyway or Liquibase for database migrations.
