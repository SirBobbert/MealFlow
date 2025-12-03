# MealFlow

Backend for a personal meal planning and shopping list app.

MealFlow lets you:

* Create and manage recipes
* Build meal plans
* Generate shopping lists from your meal plans
* Secure everything behind JWT based authentication

> Status: Work in progress / learning project

---

## Table of Contents

* [Tech Stack](#tech-stack)
* [Features](#features)
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
  * [Meal plans](#meal-plans)
  * [Shopping lists](#shopping-lists)
* [Development Notes](#development-notes)
* [Roadmap](#roadmap)
* [License](#license)

---

## Tech Stack

* Java (Spring Boot)
* Maven
* Spring Web
* Spring Data JPA (Hibernate)
* Spring Security with JWT
* MySQL
* Lombok

---

## Features

* User registration and login with JWT
* Recipe CRUD

  * Ingredients connected to recipes
  * Categories for ingredients
* Meal plans

  * Link recipes to days and meal types
  * Per entry serving overrides
* Shopping list generation

  * Build a shopping list from a meal plan
  * Aggregate ingredient amounts across all recipes in the plan
* Basic error handling and validation

---

## Architecture

Typical Spring Boot layered architecture:

* `controller` packages expose REST endpoints
* `service` packages handle business logic
* `repository` packages use Spring Data JPA to talk to MySQL
* `model` packages contain JPA entities
* `auth` packages handle:

  * JWT filter and token validation
  * User details
  * Security configuration and CORS

The backend is designed to be consumed by a separate frontend (for example a React app running on `http://localhost:5174`).

---

## Getting Started

### Prerequisites

You need:

* Java 17 or newer
* Maven
* MySQL 8 (or compatible)
* Git

### Clone and build

```bash
git clone https://github.com/SirBobbert/MealFlow.git
cd MealFlow

# optional: run tests and build
./mvnw clean verify
```

On Windows:

```bash
mvnw.cmd clean verify
```

### Database setup

Create a database in MySQL, for example:

```sql
CREATE DATABASE mealflow CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Make sure you have a user with access to that database.

There is a SQL seed script under `src/main/resources/sql_scripts` (for example for users, ingredients, sample recipes).
You can run it manually or wire it into your local setup as you prefer.

### Configuration

Configure your connection and security settings in `application.properties` or `application.yml`.

Typical properties to set:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mealflow
spring.datasource.username=YOUR_USER
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update

# JWT config (property names may differ, check the code)
jwt.secret=change-me
jwt.expiration-ms=3600000
```

Make sure the CORS configuration in the security config allows your frontend origin
(for example `http://localhost:5174`).

### Run the app

```bash
./mvnw spring-boot:run
```

The backend will usually start on:

* `http://localhost:8080`

---

## API Overview

This is a high level overview. The exact request and response shapes are defined in the DTO classes under `src/main/java/.../dto`.

### Auth

* `POST /api/users/register`
  Register a new user.

* `POST /api/users/login`
  Authenticate and receive a JWT token in the response body.
  You need to send the token as a `Bearer` token in `Authorization` for protected endpoints.

### Recipes

* `GET /api/recipes`
  List all recipes for the current user or globally (depends on implementation).

* `GET /api/recipes/{id}`
  Get a single recipe including its ingredients.

* `POST /api/recipes`
  Create a new recipe with ingredients.

* `PUT /api/recipes/{id}`
  Update an existing recipe.

* `DELETE /api/recipes/{id}`
  Delete a recipe.

### Meal plans

* `GET /api/mealplans`
  List meal plans for the current user.

* `GET /api/mealplans/{id}`
  Get a single meal plan with its entries.

* `POST /api/mealplans`
  Create a new meal plan.
  The request includes:

  * A name for the meal plan
  * A list of entries with:

    * Recipe id
    * Meal type (for example BREAKFAST, LUNCH, DINNER)
    * Optional servings override

* `PUT /api/mealplans/{id}`
  Update an existing meal plan (name, entries etc).

* `DELETE /api/mealplans/{id}`
  Delete a meal plan.

### Shopping lists

Shopping lists are derived from meal plans.

* `POST /api/mealplans/{id}/shopping-list`
  Generate a shopping list from the meal plan with id `{id}`.
  Ingredients across all recipes in the plan are grouped and their amounts are summed.
  If you call this endpoint multiple times for the same meal plan,
  the service is designed to not create duplicate list items
  but reuse or update existing ones.

* `GET /api/mealplans/{id}/shopping-list`
  Get the shopping list items for a meal plan.

---

## Development Notes

* Entities use Lombok for boilerplate reduction (`@Getter`, `@Setter`, `@Builder`, etc).
* Relationships:

  * `User` 1 - n `MealPlan`
  * `MealPlan` 1 - n `MealPlanEntries`
  * `Recipe` 1 - n `RecipeIngredient`
  * `Ingredient` 1 - n `RecipeIngredient` and 1 - n `ShoppingListItems`
* Some entities use `orphanRemoval = true` and cascading to keep the domain model consistent when you add or remove entries.

There are also SQL helper scripts in `src/main/resources/sql_scripts` for:

* Resetting tables (`TRUNCATE`)
* Seeding users, ingredients, recipes

Use them during development to quickly reset to a known state.

---

## Roadmap

Planned or potential improvements:

* Ingredient API implementation (with data on each ingredient from Frida food data)
* Improved frontend
* Tests
* Sharing recipes between users
* Docker support
* Shopping list price comparison via supermarket web scraping
---

## License

Licensed under the MIT License. See the [`LICENSE`](https://github.com/SirBobbert/MealFlow/blob/main/LICENSE) file for more details.
