# SYT Tech Stack Recap

This document outlines the chosen technology stack for the **SYT (Share Your Taste)** application. It covers the backend, frontend, database, and planned enhancements for each iteration.

## Backend

### 1. Core Framework
- Java with **Spring Boot**
- Swagger via Springdoc

### 2. Persistence Layer
- Hibernate (ORM)
- EhCache
- Spring Data JPA (`JpaRepository`)
- PostgreSQL (main relational database)
- Optional: MapStruct
- Optional: Neo4j (for advanced graph-based features in later iterations) !if you read this, i don't know if this is implemeted yet

### 3. Architecture (Layered)
- Controllers (`@RestController`)
- Services (business logic)
- Repositories (data access)

### 4. Core Utilities
- Lombok
- SLF4J
- Jakarta Bean Validation (`@Email`, `@NotNull`, etc.)

> Error Handling
> `@ControllerAdvice` → global exception handling

### 5. Integration
- CORS configuration allows frontend-backend communication

## Frontend

### Framework
- Angular (TypeScript)

> Communicates with backend via REST API

## Database

### Primary
- PostgreSQL

### Optional
- Neo4j

# Iteration Strategy

## Iteration 1 (Core Functionality)
- Authentication (login/logout/guest)
- View albums & songs
- Rate albums/songs

## Iteration 2
- Comments
- Profiles
- Swagger integration
- Caching

## Iteration 3
- Notifications
- Charts
- Optional: Neo4j recommendations
- Final UI polish
