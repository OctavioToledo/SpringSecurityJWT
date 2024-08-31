# Spring Security JWT API

This is a simple API built with Spring Boot that uses Spring Security and JWT (JSON Web Token) for user authentication and authorization.

## Features

- User registration
- User authentication (login)
- JWT token generation and validation
- Role-based access control (RBAC)

## Technologies Used

- Java
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Maven
- MySQL
- Postman

## Getting Started

### Prerequisites

- Java 17 
- Maven 3.8 or higher

### API Endpoints
1. User Registration 

   URL: /auth/sign-up

   Method: POST

   Request Body:

{

"username": "user",

"password": "1234",

"roleRequest":{

"roleListName": ["ADMIN"]
}

}
