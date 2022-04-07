# Instrumentation Sensors

[![Android Build](https://img.shields.io/badge/Java-Spring%20Boot-orange)](https://spring.io/projects/spring-boot) [![Spring JPA](https://img.shields.io/badge/Spring-JPA-blue)](https://spring.io/projects/spring-data-jpa) [![Model Mapper ](https://img.shields.io/badge/ModelMapper-%20passing-green)](http://modelmapper.org/) [![Postgres](https://img.shields.io/badge/Postgres-%20SQL-blue)](https://www.postgresql.org/) [![Swagger](https://img.shields.io/badge/Swagger-passing-green)](https://swagger.io/)

This is a simple RESTful Web Service with Spring Boot - Instrumentation Sensors. 

## Features
* Java Spring Boot
* Spring JPA (Java Persistence API)
* Model Mapper
* Postgres SQL
* Swagger

## Features Updated (07-04-2022)
* Cloudinary SDK added for MultipartFile (image) upload.
* @PostMapping and @PutMapping updated with MultipartFile.
* @GetMapping new endpoint [/instrumentation/sensors/extra] added to get extras.
* @DeleteMapping updated to delete image while deleting entity.

## Prerequisite
To build this project, you require:
- IntelliJ IDEA 2021.1.3 or above
- Maven 4.0.0 or above 

## Test it out 
- Test this app on https://instrumentation-sensors.herokuapp.com  

## Sample 🌠
An Android application that uses this REST API
- [SensorApp](https://github.com/ikechiU/InstrumentationSensors-android)

## Libraries
*   [Spring Boot Web](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web)
*   [Model Mapper](http://modelmapper.org/)
*   [Jackson dataformat](https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-xml)
*   [Spring JPA](https://spring.io/projects/spring-data-jpa)
*   [Postgres](https://www.postgresql.org/)
*   [Swagger](https://swagger.io/)
*   [Cloudinary](https://cloudinary.com/documentation)

## Author
Ikechi Ucheagwu 
