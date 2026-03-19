# Product Comparison API

A RESTful backend API built with Spring Boot to support a product comparison feature.

The purpose of this project is to provide clear and efficient endpoints for retrieving product data and comparing items based on key attributes such as name, image URL, description, price, rating, and specifications.

The implementation keeps the solution simple, readable, and easy to run locally while following solid backend design practices.

---

## Overview

This API allows clients to:

- retrieve the full product catalog
- retrieve a subset of products by ID
- compare products and return both the selected product data and a structured comparison summary

The current implementation uses an **in-memory repository with seeded sample data**, which keeps the project lightweight and easy to evaluate without requiring external infrastructure.

---

## Tech Stack

- Java 21
- Spring Boot 3.2.5
- Spring Web
- Maven
- JUnit 5
- Mockito

### Included Dependencies

The project also includes:

- Spring Data JPA
- H2 Database
- Jakarta Validation
- Hibernate Validator

> Note: although JPA and H2 are included as dependencies, the current product catalog is served from an in-memory repository rather than a persistent database.

---

## API Design

The API follows a simple layered design to keep responsibilities clearly separated.

### Architecture Diagram

```text
Client
  │
  │ HTTP Request
  ▼
ProductController
  │
  │ delegates business logic
  ▼
ProductService
  │
  │ retrieves product data
  ▼
ProductRepository
  │
  ▼
In-memory product catalog

ProductService
  │
  │ builds comparison result
  ▼
Comparison Response DTO
  │
  │ HTTP Response
  ▼
Client
```

### Design Approach

- **Controller layer**
    - Exposes REST endpoints
    - Handles request mapping and input reception

- **Service layer**
    - Contains the comparison logic
    - Validates incoming requests
    - Builds the response payload

- **Repository layer**
    - Provides access to product data
    - Uses a seeded in-memory list to simulate persistence

- **DTO layer**
    - Defines request and response contracts

- **Global exception handling**
    - Centralizes API error responses
    - Keeps controllers cleaner and responses more consistent

---

## Main Endpoints

### 1. Get all products

**GET** `/products`

Returns the full list of available products.

#### Example request

```bash
curl http://localhost:8080/products
```

---

### 2. Get products by IDs

**GET** `/products?ids=1,2`

Returns only the products that match the provided IDs.

#### Example request

```bash
curl "http://localhost:8080/products?ids=1,2"
```

---

### 3. Compare products

**POST** `/products/compare`

Accepts a list of product IDs and returns the selected products along with a structured comparison result.

#### Request body

```json
{
  "productIds": [1, 2]
}
```

#### Example request

```bash
curl -X POST http://localhost:8080/products/compare \
  -H "Content-Type: application/json" \
  -d '{"productIds":[1,2]}'
```

#### Example response

```json
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
```

---

## Comparison Logic

The comparison service currently computes:

- **priceDifference**  
  Absolute difference between the prices of the first two matched products

- **ratingDifference**  
  Absolute difference between the ratings of the first two matched products

- **specDifferences**  
  A dynamic map containing only the specification keys whose values differ between the first two matched products

### Implementation Note

The request accepts a list of product IDs, but the current comparison logic calculates the result using the **first two matched products**.

For predictable behavior, the intended use is to send exactly **two product IDs** per comparison request.

---

## Seed Data

The application starts with a small in-memory product catalog:

| ID | Product      | Price  | Rating |
|----|--------------|--------|--------|
| 1  | iPhone 13    | 1200.0 | 4.5    |
| 2  | Samsung S22  | 1000.0 | 4.3    |
| 3  | Xiaomi Mi 11 | 800.0  | 4.2    |

This keeps the project self-contained and easy to review.

---

## Setup Instructions

### Prerequisites

- Java 21
- Maven

### Run the application

From the project root, run:

```bash
mvn spring-boot:run
```

The API will be available at:

```text
http://localhost:8080
```

---

## Error Handling

The project includes centralized exception handling for invalid input and unexpected runtime errors.

### Examples of handled scenarios

- empty or missing product ID list
- fewer than two product IDs for comparison
- insufficient products found for a comparison request
- unexpected internal server errors

### Example error response

```json
{
  "timestamp": "2026-03-19T12:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "At least two product ids are required",
  "path": "/products/compare"
}
```

---

## Testing

Run the test suite with:

```bash
mvn test
```

### Current Coverage

The service-layer unit tests cover:

- successful product comparison
- validation when fewer than two IDs are provided
- validation when not enough products are found

The tests use **Mockito** to isolate the service logic from the repository.

---

## Key Architectural Decisions

### 1. In-memory repository instead of a real database

The exercise explicitly allows simulated persistence. Using an in-memory repository keeps the project focused on API behavior, business logic, and error handling rather than database setup.

### 2. Layered structure

Separating controller, service, and repository responsibilities improves readability and makes the code easier to maintain and extend.

### 3. Centralized exception handling

A global exception handler provides a consistent error format across the API and prevents controllers from being cluttered with repetitive error-handling logic.

### 4. Dynamic specification comparison

Instead of hardcoding every comparable attribute, product specifications are stored in a map and compared dynamically. This makes the comparison logic more flexible for fields such as RAM, storage, or screen size.

---

## Limitations

This project intentionally keeps the implementation compact. Current limitations include:

- product data is not persisted in a real database
- the comparison logic is effectively pairwise even though the request accepts a list
- there is no pagination, sorting, or advanced filtering
- there is no OpenAPI or Swagger documentation yet
- there is no authentication or authorization layer

---

## Possible Improvements

If this project were extended beyond the scope of the exercise, the next logical steps would be:

- replace the in-memory repository with a real persistence layer using JPA and H2/PostgreSQL
- add Swagger/OpenAPI documentation
- support true multi-product comparison
- add integration tests for controller endpoints
- containerize the application with Docker
- add CI validation for build and test execution