# CRM API

This project is a RESTful API for managing customers, built with Spring Boot. It includes integration with an Oracle database and MinIO for image storage.

## Prerequisites

- Docker and Docker Compose installed.
- Postman or any HTTP client to test the endpoints.
- MinIO server installed.

## Configuring the Oracle Database

To configure the Oracle database, use the following command to run the Oracle XE image in Docker:

```bash
docker run -d -p 1522:1521 -e ORACLE_PASSWORD=oracle gvenzl/oracle-xe
```
Accessing the Database

```bash
User: system
Password: oracle
JDBC URL: jdbc:oracle:thin:@//localhost:1522/FREEPDB1

```
## Configuring MinIO
Download and Install MinIO: If you do not have MinIO installed, you can download it from the official MinIO website.

Start MinIO: Open a terminal and navigate to the folder where the MinIO binary is located. Then run the following command to start the server:
```bash
./minio server start
  
```
## Accessing MinIO
```bash
URL: http://127.0.0.1:9000
User: minioadmin
Password: minioadmin
```

## Creating a Bucket in MinIO
```bash
Access the MinIO console at http://127.0.0.1:9000.
Log in with the previous credentials.
Create a new bucket (for example, crmbucket).
```

## Configuring the Connection in application.yml
Make sure your application.yml file is correctly configured to connect to Oracle and MinIO:
```bash
spring:
datasource:
url: jdbc:oracle:thin:@//localhost:1521/FREEPDB1
username: system
password: oracle
driver-class-name: oracle.jdbc.OracleDriver
jpa:
hibernate:
ddl-auto: update
show-sql: true
minio:
url: http://localhost:9000
bucket-name: crmbucket
access-key: minioadmin
secret-key: minioadmin
```
## Docker Compose Configuration

```bash
To simplify the configuration of the development environment, Docker Compose is used. Make sure your `docker-compose.yml` file contains the following:

services:
  crm-api:
    build:
      context: .
      dockerfile: Dockerfile
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:oracle:thin:@//oracle-db:1522/FREEPDB1
      SPRING_DATASOURCE_USERNAME: system
      SPRING_DATASOURCE_PASSWORD: oracle
      SPRING_DATASOURCE_DRIVER_CLASS_NAME: oracle.jdbc.OracleDriver
      SPRING_JPA_HIBERNATE_DDL_AUTO: update
      SPRING_JPA_SHOW_SQL: "true"
      MINIO_URL: http://minio:9000
      MINIO_BUCKET_NAME: crmbucket
      MINIO_ACCESS_KEY: minioadmin
      MINIO_SECRET_KEY: minioadmin
    depends_on:
      - oracle-db
      - minio

  oracle-db:
    image: gvenzl/oracle-xe
    ports:
      - "1522:1521"
    environment:
      ORACLE_PASSWORD: oracle

  minio:
    image: minio/minio
    ports:
      - "9000:9000"
    environment:
      MINIO_ROOT_USER: minioadmin
      MINIO_ROOT_PASSWORD: minioadmin
    command: server /data
    volumes:
      - minio_data:/data

volumes:
  minio_data:
    driver: local
    
```
## Build and Run

To build and run the project, use:

docker-compose up --build
This will start all the services defined in the docker-compose.yml.

## Accessing the Endpoints:

```bash
You can access the API endpoints using Postman. Make sure to obtain an authentication token before making requests that require specific roles.

### Authentication
Make a POST request to /auth/login to obtain a token:

URL: http://localhost:8080/auth/login
Method: POST
Body:
json
Copy code
{
    "username": "your_username",
    "password": "your_password"
}
Save the returned token to use in subsequent requests.

### Example Request with Token
To make a request to a protected endpoint, add the token in the header:

Headers:
Authorization: Bearer {token}

### Example of Image Upload
You can upload images to the API using the following endpoint:

URL: http://localhost:8080/{id}/upload-image
Method: POST
Parameters:
file: the image you wish to upload.

```
### Conclusion
This project uses Docker and MinIO to facilitate development and image management. With this guide, you should be able to set up your environment and use the API easily.


### Notes on Formatting
- Each section is clearly marked, with specific instructions for each part of the process.
- Commands and code snippets are properly formatted for easy reading and execution.
- Be sure to replace "your_username" and "your_password" in the authentication example with the actual credentials you use in your application.

```