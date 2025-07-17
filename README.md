# BookManager Library System

This is a typical CRUD system, suitable for practicing entity modeling, repository manipulation, RESTful API construction and system layering architecture.

---

📘 Project Introduction

BookManager is a simple library system supporting user registration, login, reader management, role-based authorization, and JWT with Redis-based blacklist logout mechanism.

---

## 🛠️ Tech Stack

- Java 17
- Spring Boot 3
- Spring Security + JWT
- Spring Data JPA
- Hibernate Validation
- Swagger / OpenAPI 3
- Redis (JWT 黑名單)
- Docker + Docker Compose

---

## 🚀 Feature List

- ✅ User Registration / Login / Logout
- ✅ JWT Authentication + Blacklist Cancellation Mechanism
- ✅ Borrower CRUD (Admin / Staff Privilege)
- ✅ Permission Control (ADMIN / STAFF / USER)
- ✅ Swagger API Documentation & Testing
- ✅ Dockerized Deployment

---

## 🧪 Test Accounts

| Email             | Role  | Password |
| ----------------- | ----- | -------- |
| alice@example.com | ADMIN | 123456   |
| bob@example.com   | STAFF | 123456   |
| cathy@example.com | USER  | 123456   |

---

## ⚙️ How to Run

### Local Run

### Docker Compose

---

## 🔐 JWT Authentication Flow

1. Use `/auth/register` to register your account.
2. Use `/auth/login` to get the JWT Token.
3. add `Authorization: Bearer <token>` to the Swagger or Postman Header.
4. you can use a protected API (e.g. GET `/readers`)
5. use `/auth/logout` to invalidate (blacklist) the Token

---

## API Documentation

Visit the website:
    http://localhost:8080/swagger-ui.html


---

##  📁 Project Structure

bookmanager/
├── controller/        // controller (API)
├── service/           // business logic
├── repository/        // JPA data access layer
├── dto/               // request/response data format
├── security/          // JWT and login authentication logic
├── model/             // Database Entity
├── config/            // Spring Security Settings
└── application.yml    // Environment variables and configurations

---

## 🧑‍💻 Contact

By D9 (https://github.com/I-am-dNine) —