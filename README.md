# 📦 SakilaSpring

A Spring Boot application illustrating basic REST APIs backed by a database using the **Sakila sample schema**. Includes simple CRUD operations for films and example implementations using both JDBC and in-memory data access.

This project is ideal for learning Spring Boot fundamentals, JDBC integration, layered architecture, and RESTful API design.

---

## 🧠 Overview

This application demonstrates:

- Spring Boot application structure
- RESTful endpoints for managing `Film` entities
- Swappable DAO implementations (JDBC + in-memory)
- Clean dependency injection patterns
- Simple CLI runner for demos
- Basic integration testing
- Externalized database configuration

---

## 🧩 Features

- ✔ Retrieve all films  
- ✔ Get a film by ID  
- ✔ Create a new film  
- ✔ Delete a film  
- ✔ Switch between DAO layers  
- ✔ Auto-configured Database Connectivity

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/brytdcyt-bot/SakilaSpring.git
cd SakilaSpring


---

The application expects an existing Sakila MySQL database.
You can download the official schema & data from:
https://dev.mysql.com/doc/sakila/en/

---

Import using:
```bash
mysql -u root -p < sakila-schema.sql
mysql -u root -p < sakila-data.sql

---

Build the application
./mvnw clean package

---
Run the application:
The application required database credentials at startup:

###

``` bash
java -jar target/SakilaSpring-0.0.1-SNAPSHOT.jar <dbUsername> <dbPassword>

---
Database URL must be supplied via application.properties:
datasource.url=jdbc:mysql://localhost:3306/sakila

