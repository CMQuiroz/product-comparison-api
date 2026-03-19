# Product Comparison API

## Overview
This project is a RESTful API built with Spring Boot that allows retrieving and comparing products based on different attributes such as price, rating, and specifications.

The main goal is to provide a structured comparison between products using clean architecture and best practices.

---

## Tech Stack

- Java 21
- Spring Boot 3
- Spring Web
- Spring Data JPA
- H2 Database (in-memory)
- Maven

---

## Architecture

The application follows a layered architecture:

- **Controller** → Handles HTTP requests
- **Service** → Contains business logic
- **Repository** → Data access layer
- **DTOs** → Data transfer between layers
- **Exception Handler** → Centralized error handling

---

## Endpoints

### Get all products
Returns all available products  
`GET /products`

### Get products by IDs
Returns products filtered by IDs  
`GET /products?ids=1,2`

### Compare products
Compares products by price, rating and specs  
`POST /products/compare`

