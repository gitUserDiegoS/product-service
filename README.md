# product service API

Service defined over an hexagonal and clean architecture approach,  used for product management 

In this project, I applied the hexagonal and Clean Architecture approach. This approach defines separate modules, such as the Domain module (which includes business logic, models, and use cases) and the Infrastructure module (which includes adapters, entry points, REST clients, etc.). In the business layer, I create contracts that define what the API should do, but not how it should be done. The infrastructure layer then implements these contracts

## Table of Contents

- [Installation](#installation)
- [Relational model diagram](#diagram)
- [Endpoints](#endpoints)
- [Contributing](#contributing)
- [License](#license)

## Installation

1. Execute scripts database located in api user service https://github.com/gitUserDiegoS/ecommerce-user-service

    1.1. Execute script in `scripts/sdscrits.sql` to create and populate initial records
    1.2. Go to develop branch and run the application

Url base de API: `http://localhost:8081`.

## Relational model diagram

![img_1.png](img_1.png)

## Endpoints

### 1. POST - Create product

`http://localhost:8082/api/v1/products`

**Request:**

```json
{
  "sku": "BOOK-POST-3",
  "name": "Post steps 2",
  "description": "modern product 2!",
  "unitPrice": 12.99,
  "active": true,
  "unitsInStock": 2,
  "dateCreated": "2025-10-26",
  "category": {
    "id": 1
  }
}
```

**Response (HTTP 200):**

```json
{
  "id": 14,
  "sku": "BOOK-POST-3",
  "name": "Post steps 2",
  "description": "modern product 2!",
  "unitPrice": 12.99,
  "active": true,
  "unitsInStock": 2,
  "dateCreated": "2025-10-27",
  "lastUpdated": "2025-10-27"
}
```

### 2. GET - retrieve product by id

`http://localhost:8082/api/v1/products/14`


**Response (HTTP 200):**

```json
{
  "id": 14,
  "sku": "BOOK-POST-3",
  "name": "Post steps 2",
  "description": "modern product 2!",
  "unitPrice": 12.99,
  "active": true,
  "unitsInStock": 2,
  "dateCreated": "2025-10-27",
  "lastUpdated": "2025-10-27"
}
```

### 3. PUT - update product

`http://localhost:8082/api/v1/products`

**Request:**

```json
{
  "id": 14,
  "sku": "updated-BOOK-POST",
  "name": "Post steps",
  "description": "modern product!",
  "unitPrice": 10.99,
  "active": true,
  "unitsInStock": 2
}
```

**Response (HTTP 200):**

```json
{
  "id": 14,
  "sku": "updated-BOOK-POST",
  "name": "Post steps",
  "description": "modern product!",
  "unitPrice": 10.99,
  "active": true,
  "unitsInStock": 2,
  "dateCreated": "2025-10-27",
  "lastUpdated": "2025-10-27"
}
```

### 3. DELETE - delete product

`http://localhost:8082/api/v1/products/14`


**Response (HTTP 200):**

```json
{
  "id": 14,
  "sku": "updated-BOOK-POST",
  "name": "Post steps",
  "description": "modern product!",
  "unitPrice": 10.99,
  "active": true,
  "unitsInStock": 2,
  "dateCreated": "2025-10-27",
  "lastUpdated": "2025-10-27"
}
```
### 4. GET - search products with optional filters and pagination

filtros definidos: name, maxPrice, minPrice


`http://localhost:8082/api/v1/products?name=python&minPrice=10&maxPrice=50&page=0&size=5&sort=unitPrice,desc`


**Response (HTTP 200):**

```json
{
  "content": [
    {
      "id": 1,
      "sku": "BOOK-TECH-1000",
      "name": "Crash Course in Python",
      "description": "Learn Python at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!",
      "unitPrice": 14.99,
      "active": true,
      "unitsInStock": 100,
      "dateCreated": "2025-10-26",
      "lastUpdated": null
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 5,
    "sort": {
      "empty": false,
      "unsorted": false,
      "sorted": true
    },
    "offset": 0,
    "unpaged": false,
    "paged": true
  },
  "last": true,
  "totalElements": 1,
  "totalPages": 1,
  "size": 5,
  "number": 0,
  "sort": {
    "empty": false,
    "unsorted": false,
    "sorted": true
  },
  "first": true,
  "numberOfElements": 1,
  "empty": false
}
```


