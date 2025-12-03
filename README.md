# MealFlow

Backend for a personal meal planning and shopping list app.

MealFlow lets you:

* Create and manage recipes with ingredients, units, and instructions
* Build weekly meal plans (breakfast, lunch, dinner)
* Automatically generate shopping lists from meal plans
* Combine ingredient amounts across recipes (aggregation)
* Scale servings per meal entry
* Secure everything with JWT authentication

> Status: Work in progress / learning project

---

## Table of Contents

* [Tech Stack](#tech-stack)
* [Core Functionality](#core-functionality)
* [User Stories](#user-stories)
* [Architecture](#architecture)
* [Getting Started](#getting-started)

  * [Prerequisites](#prerequisites)
  * [Clone and build](#clone-and-build)
  * [Database setup](#database-setup)
  * [Configuration](#configuration)
  * [Run the app](#run-the-app)
* [API Overview](#api-overview)

  * [Auth](#auth)
  * [Recipes](#recipes)
  * [Meal Plans](#meal-plans)
  * [Meal Plan Entries](#meal-plan-entries)
  * [Shopping Lists](#shopping-lists)
* [Frontend Overview](#frontend-overview)
* [Roadmap](#roadmap)
* [License](#license)

---

## Tech Stack

* Java 17+
* Spring Boot
* Spring Web
* Spring Data JPA (Hibernate)
* Spring Security with JWT
* MySQL 8
* Maven
* Lombok

---

## Core Functionality

* Store recipes including ingredients, units, and amounts

* Create weekly meal plans with date + meal type

* Automatically build shopping lists from meal plans

* Aggregate duplicate ingredient amounts (e.g. 300 g + 200 g chicken)

* Scale servings per meal entry

---

## User Stories

* I can register, log in, and log out
* I can create, edit, and delete recipes
* I can add ingredients:

  * Name
  * Amount and unit (e.g. 500 g, 2 pcs)
* I can build a weekly meal plan:

  * Select dates and meal types (BREAKFAST, LUNCH, DINNER)
  * Select recipes
  * Override servings per entry
* I can generate a shopping list that:

  * Collects all needed ingredients
  * Sums duplicate amounts

---

## Architecture

Layered Spring Boot backend:

* `controller` – REST endpoints
* `service` – core business logic
* `repository` – database access via JPA
* `model` – JPA entities
* `auth` – security, JWT filter, user details, CORS rules

Backend is designed for use with a separate frontend (e.g. React on `http://localhost:5174`).

---

## Getting Started

### Prerequisites

* Java 17 or newer
* Maven
* MySQL
* Git

### Clone and build

```bash
git clone https://github.com/SirBobbert/MealFlow.git
cd MealFlow
./mvnw clean verify
```

On Windows:

```bash
mvnw.cmd clean verify
```

### Database setup

```sql
CREATE DATABASE mealflow CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Seed scripts exist in `src/main/resources/sql_scripts`.

### Configuration

Set DB + JWT config inside `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mealflow
spring.datasource.username=YOUR_USER
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update

jwt.secret=change-me
jwt.expiration-ms=3600000
```

Make sure CORS allows your frontend domain.

### Run the app

```bash
./mvnw spring-boot:run
```

Backend defaults to:

* `http://localhost:8080`

---

## API Overview

DTO request/response types are defined under `src/main/java/.../dto`.

---

### Auth

* `POST /api/auth/register`
* `POST /api/auth/login`
* `GET /api/auth/me` (current user)

---

### Recipes

* `GET /api/recipes`
* `POST /api/recipes`
* `GET /api/recipes/{id}`
* `PUT /api/recipes/{id}`
* `DELETE /api/recipes/{id}`

Includes ingredient data and categories.

---

### Meal Plans

* `GET /api/meal-plans?from=YYYY-MM-DD&to=YYYY-MM-DD`
* `POST /api/meal-plans`
* `GET /api/meal-plans/{id}`
* `PUT /api/meal-plans/{id}`
* `DELETE /api/meal-plans/{id}`

Full CRUD support is planned.

---

### Meal Plan Entries

* `POST /api/meal-plans/{id}/entries`
* `PUT /api/meal-plan-entries/{id}`
* `DELETE /api/meal-plan-entries/{id}`

Each entry holds:

* Recipe reference
* Meal type
* Servings override

---

### Shopping Lists

* `POST /api/meal-plans/{id}/generate-shopping-list`

  * Aggregates ingredient amounts
  * Avoids duplicates if generated multiple times
* `GET /api/meal-plans/{id}/shopping-list`
* `PATCH /api/shopping-list-items/{id}` toggle checked

---

## Frontend Overview

Planned web UI (React/Vite):

* Login / Register
* Recipe List (search, filters)
* Recipe Detail (button: “Use in meal plan”)
* Recipe Create/Edit (dynamic ingredient list)
* Weekly Meal Plan grid (Mon–Sun, 3 meals/day)
* Shopping List (checkboxes, sorted view)

---

## Roadmap

Near-term goals:

* Add shopping list checkoff functionality

* Full CRUD support for Ingredients (own endpoints, search, better category handling)

* Full CRUD support for Meal Plans

* Improved UX and frontend layout

* CI pipeline and automated testing

* Docker Compose for DB + backend startup

* Export shopping list as PDF

* Budget mode:

  * Ingredient pricing
  * Total cost per meal plan

* “Use what I have” mode:

  * Suggest recipes based on available ingredients

* Sharing recipes between users

---

## License

MIT License. See [`LICENSE`](LICENSE).
