
# 📝 Blog App

A production-grade, secure, and scalable RESTful API for a Blogging Platform built with **Spring Boot 3** and **Java 21**.

This project demonstrates advanced backend concepts including **JWT Authentication**, **Role-Based Access Control (RBAC)**, **Database Migrations**, **Integration Testing with Testcontainers**, and **Cloud Deployment**.

-----

## 🚀 Key Features

* **🔐 Authentication & Security:**

    * Secure **Sign Up** & **Sign In** using **JWT (JSON Web Tokens)**.
    * Password hashing with **BCrypt**.
    * **Role-Based Access Control (RBAC):** Distinction between `USER` and `ADMIN` roles.
    * **Ownership Logic:** Users can only update/delete their *own* posts/comments (unless Admin).

* **📝 Content Management:**

    * **Posts:** Create, Read (Pagination & Sorting), Update, Delete.
    * **Categories:** Organize posts into categories.
    * **Comments:** Engage with content (includes cascade deletion logic).

* **⚙️ Robust Architecture:**

    * **DTO Pattern:** Strict separation between Internal Entities and External API Contracts.
    * **Global Error Handling:** Centralized exception handling returning standardized JSON error responses (404, 403, 400).
    * **Data Seeder:** Automatically populates the database with dummy data in `dev` profile for easy testing.

* **🧪 Professional Testing:**

    * **Integration Tests:** End-to-End testing using **Testcontainers** (Real PostgreSQL in Docker).
    * **Unit Tests:** Business logic verification using **Mockito**.

-----

## 🛠️ Tech Stack

* **Language:** Java 21
* **Framework:** Spring Boot 3.5.7
* **Database:** PostgreSQL
* **ORM:** Spring Data JPA (Hibernate)
* **Migrations:** Flyway
* **Security:** Spring Security & JJWT
* **Testing:** JUnit 5, Mockito, Testcontainers, REST Assured
* **Documentation:** SpringDoc OpenAPI (Swagger UI)
* **DevOps:** Docker, Docker Compose

-----

## 🏗️ Database Schema

The application uses a relational database model with the following relationships:

* **User** `1:N` **Post**
* **Category** `1:N` **Post**
* **User** `1:N` **Comment**
* **Post** `1:N` **Comment** (Cascade Delete)

-----

## 🚀 Getting Started

### Prerequisites

* Java 21 SDK
* Docker & Docker Compose
* Maven

### 1\. Clone the Repository

```bash
git clone https://github.com/abhilashkk1857/blog-app.git
cd blog-app
```

### 2\. Configure Environment Variables

The application requires specific environment variables for security. You can set these in your IDE or terminal.

| Variable | Description | Example |
| :--- | :--- | :--- |
| `PG_URL` | Database Connection URL | `jdbc:postgresql://localhost:5432/blog_db` |
| `PG_USER` | Database Username | `postgres` |
| `PG_PASSWORD` | Database Password | `password` |
| `JWT_SECRET_KEY` | Secret for signing tokens | `your_super_secret_base64_key` |
| `JWT_EXPIRES_IN`| Token validity (ms) | `86400000` (24 hours) |

### 3\. Run with Docker Compose (Recommended)

This starts the PostgreSQL database and the Application in containers.

```bash
docker-compose up --build
```

### 4\. Access the Application

* **API Root:** `http://localhost:8080/api/v1`
* **Swagger UI:** `http://localhost:8080/swagger-ui.html`
* **Actuator Health:** `http://localhost:8080/actuator/health`

-----

## 🧪 Running Tests

To run the full suite of Unit and Integration tests (requires Docker to be running for Testcontainers):

```bash
./mvnw test
```

-----

## 📚 API Documentation

The API is fully documented using **Swagger / OpenAPI**.
Visit the live docs to interact with endpoints, generate client SDKs, or test authentication.

> **Live Demo:** [https://blog-app-o4e8.onrender.com](https://blog-app-o4e8.onrender.com)

-----

## 📂 Project Structure

```text
src/
├── main/
│   ├── java/com/kk/blog_app/
│   │   ├── config/          # Security, Swagger configs
│   │   ├── controller/      # REST Controllers (API Layer)
│   │   ├── domain/          # Entities, DTOs and Mappers
│   │   ├── exception/       # Global Exception Handler and Custom Exceptions
│   │   ├── repository/      # JPA Repositories (Data Layer)
│   │   ├── security/        # JWT Filter & UserDetailsService
│   │   └── service/         # Business Logic Layer
│   └── resources/
│       ├── db/migration/    # Flyway SQL Scripts
│       └── application.yaml # App Config
└── test/                    # Integration & Unit Tests
```

-----

### Author

**Abhilash K K**

* LinkedIn: [Abhilash K K ](https://linkedin.com/in/abhilash-k-k-22a880258)
* GitHub: [abhilashkk1857](https://github.com/abhilashkk1857)
