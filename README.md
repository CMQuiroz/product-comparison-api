# Product Comparison API

## Overview
This project is a RESTful API built with Spring Boot that allows retrieving and comparing products based on different attributes such as price, rating, and specifications.

The main goal is to provide a structured comparison between products using clean architecture and backend best practices.

---

## Tech Stack

- Java 21
- Spring Boot 3
- Spring Web
- Spring Data JPA
- H2 Database
- Maven
- JUnit 5
- Mockito

---

## Architecture

The application follows a layered architecture:

- **Controller**: Handles HTTP requests and responses
- **Service**: Contains the business logic
- **Repository**: Provides access to product data
- **DTOs**: Define request and response contracts
- **Exception Handler**: Centralizes API error handling

This structure helps keep responsibilities separated and improves maintainability and scalability.

---

## Endpoints

### Get all products
Returns all available products  
`GET /products`

### Get products by IDs
Returns products filtered by IDs  
`GET /products?ids=1,2`

### Compare products
Compares products by price, rating, and specifications  
`POST /products/compare`

#### Request body
```json
{
  "productIds": [1, 2]
}

{
  "products": [
    {
      "id": 1,
      "name": "iPhone 13",
      "description": "Apple smartphone",
      "image": "https://example.com/iphone.jpg",
      "price": 1200.0,
      "rating": 4.5,
      "specs": {
        "ram": "4GB",
        "storage": "128GB",
        "screen": "6.1"
      }
    },
    {
      "id": 2,
      "name": "Samsung S22",
      "description": "Samsung flagship",
      "image": "https://example.com/samsung.jpg",
      "price": 1000.0,
      "rating": 4.3,
      "specs": {
        "ram": "8GB",
        "storage": "256GB",
        "screen": "6.1"
      }
    }
  ],
  "comparison": {
    "priceDifference": 200.0,
    "ratingDifference": 0.2,
    "specDifferences": {
      "ram": ["4GB", "8GB"],
      "storage": ["128GB", "256GB"]
    }
  }
}

{
  "products": [
    {
      "id": 1,
      "name": "iPhone 13",
      "description": "Apple smartphone",
      "image": "https://example.com/iphone.jpg",
      "price": 1200.0,
      "rating": 4.5,
      "specs": {
        "ram": "4GB",
        "storage": "128GB",
        "screen": "6.1"
      }
    },
    {
      "id": 2,
      "name": "Samsung S22",
      "description": "Samsung flagship",
      "image": "https://example.com/samsung.jpg",
      "price": 1000.0,
      "rating": 4.3,
      "specs": {
        "ram": "8GB",
        "storage": "256GB",
        "screen": "6.1"
      }
    }
  ],
  "comparison": {
    "priceDifference": 200.0,
    "ratingDifference": 0.2,
    "specDifferences": {
      "ram": ["4GB", "8GB"],
      "storage": ["128GB", "256GB"]
    }
  }
}

Error Handling

The API uses a global exception handler to return consistent error responses.

Example error response

Logging

Logging is implemented using SLF4J across the main application layers:

Controller layer for incoming requests

Service layer for business flow and validations

Exception handler for controlled and unexpected errors

This improves traceability and observability.

Logging

Logging is implemented using SLF4J across the main application layers:

Controller layer for incoming requests

Service layer for business flow and validations

Exception handler for controlled and unexpected errors

This improves traceability and observability.

Testing

Unit tests were added for the service layer using JUnit 5 and Mockito.

Covered scenarios

successful product comparison

invalid comparison request with fewer than two product IDs

comparison request with insufficient products found

This helps validate the core business logic in isolation.

How to Run

Clone the repository

Open the project in IntelliJ IDEA

Make sure JDK 21 is configured

Run Application.java

The application will start on:

http://localhost:8080

Testing

Unit tests were added for the service layer using JUnit 5 and Mockito.

Covered scenarios

successful product comparison

invalid comparison request with fewer than two product IDs

comparison request with insufficient products found

This helps validate the core business logic in isolation.

How to Run

Clone the repository

Open the project in IntelliJ IDEA

Make sure JDK 21 is configured

Run Application.java

The application will start on:

http://localhost:8080

How to Test

The API can be tested using Postman.

Valid compare request

{
  "productIds": [1, 2]
}

{
  "productIds": [1, 2]
}

Invalid compare request

{
  "productIds": [1]
}

{
  "productIds": [1]
}

Design Decisions

DTOs were used to separate API contracts from internal models

A layered architecture was used to keep responsibilities separated

A global exception handler was added to standardize API error responses

Logging was added to improve traceability and debugging

Unit tests were added to validate the service layer independently