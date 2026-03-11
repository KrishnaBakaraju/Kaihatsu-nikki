# Kaihatsu Nikki – A Development Diary Application

**Kaihatsu Nikki** (開発日記) is a **Spring Boot** REST API for tracking personal development progress with hierarchical organization: Users → Categories → Subcategories → Entries, plus daily diary functionality.

## 🎯 Project Overview

This project demonstrates core **Spring Boot** concepts including:
- **Spring Data JPA** for database persistence
- **RESTful API design** with proper HTTP methods
- **Dependency Injection** via constructor-based DI
- **Entity relationships** (One-to-Many, Many-to-One)
- **Optional-based null safety** for cleaner code
- **JSON serialization** with `@JsonManagedReference` / `@JsonBackReference`
- **Path-based resource hierarchy** for nested REST endpoints

---

## 🏗️ Architecture

### Domain Model

The application uses a **hierarchical entity model**:

```
User
├── Categories
│   └── SubCategories
│       └── SubCategoryEntries
└── DailyDiary
```

**Core entities:**
- `User` – owns categories and diary entries
- `Category` – groups related subcategories per user
- `SubCategory` – specific tracking area within a category
- `SubCategoryEntry` – timestamped progress entry
- `DailyDiary` – daily reflections

### Service & Repository Layer

**Spring Data JPA repositories** handle all database operations with automatic CRUD implementation.

**Service layer** encapsulates business logic and entity relationships.

### REST Controllers

**Spring REST controllers** expose the API with path-based resource hierarchy and user-scoped operations.

---

## 🚀 Key Spring Boot Features Used

### 1. **Dependency Injection (Constructor-Based)**
All services are injected via constructor, promoting testability and loose coupling:

```java
public UserCategoryController(CategoryService categoryService,
                          SubCategoryService subCategoryService,
                          UserService userService) {
    this.categoryService = categoryService;
    this.subCategoryService = subCategoryService;
    this.userService = userService;
}
```

### 2. **@RestController & @RequestMapping**
Declares a REST endpoint controller with hierarchical path mapping:

```java
@RestController
@RequestMapping("/api/users/{userId}/categories")
public class UserCategoryController { ... }
```

This creates user-scoped category endpoints:
- `GET /api/users/{userId}/categories`
- `POST /api/users/{userId}/categories`
- `GET /api/users/{userId}/categories/{categoryId}`
- `DELETE /api/users/{userId}/categories/{categoryId}`

### 3. **@PathVariable for URL Parameters**
Extracts dynamic values from the URI path:

```java
@GetMapping("/{categoryId}")
public ResponseEntity<Category> getCategoryForUser(
        @PathVariable Long userId,
        @PathVariable Long categoryId) { ... }
```

Maps to: `GET /api/users/{userId}/categories/{categoryId}`

### 4. **@RequestBody for JSON Deserialization**
Automatically deserializes incoming JSON to Java objects:

```java
@PostMapping
public ResponseEntity<Category> createCategoryForUser(
        @PathVariable Long userId,
        @RequestBody Category category) { ... }
```

Request body is deserialized to a `Category` object.

### 5. **Optional<T> for Null Safety**
Replaces null checks with functional-style chaining:

```java
return userService.getUserById(userId)
        .flatMap(user -> categoryService.getCategoryById(categoryId)
                .filter(cat -> cat.getUser().getId().equals(user.getId())))
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
```

- `.flatMap()` – chain operations without null unwrapping
- `.filter()` – verify business rules (ownership check)
- `.map()` – transform to success response
- `.orElse()` – provide fallback for not-found case

### 6. **ResponseEntity for HTTP Response Control**
Fine-grained control over HTTP status codes and response bodies:

```java
ResponseEntity.ok(saved)              // 200 OK
ResponseEntity.noContent().build()    // 204 No Content
ResponseEntity.notFound().build()     // 404 Not Found
```

### 7. **Spring Data JPA Integration**
Repositories extend `JpaRepository` to eliminate boilerplate CRUD code. Example queries:

```java
Optional<User> findByUsername(String username);
```

### 8. **User-Scoped Data Access**
Enforces ownership validation before operations:

```java
.filter(cat -> cat.getUser().getId().equals(user.getId()))
```

Ensures users can only access their own categories.

---

## 📋 Setup & Run

### Prerequisites
- **Java 21 JDK**
- **MySQL 8+** running on `localhost:3310`
- Default credentials in `application.properties`:
  - Username: `kaihatsu_user`
  - Password: `kaihatsu_pass`
  - Database: `kaihatsu_db`

### Build
```sh
./mvnw clean package
```

### Run
```sh
./mvnw spring-boot:run
```

Server starts on **http://localhost:8080**

### Run Tests
```sh
./mvnw test
```

---

## 📡 API Endpoints

### User Management

**Register a user**
```bash
POST /api/users/register
Content-Type: application/json

{
  "username": "john_doe",
  "password": "secret"
}
```

**Get user by username**
```bash
GET /api/users/{username}
```

### Category Management (User-Scoped)

**Get all categories for a user**
```bash
GET /api/users/{userId}/categories

Response: 200 OK
[
  {
    "id": 1,
    "name": "Spring Boot Learning",
    "user": { "id": 1, "username": "john_doe" }
  }
]
```

**Get single category (user-scoped)**
```bash
GET /api/users/{userId}/categories/{categoryId}

Response: 200 OK
{
  "id": 1,
  "name": "Spring Boot Learning",
  "user": { "id": 1, "username": "john_doe" }
}
```

Returns `404 Not Found` if:
- User doesn't exist
- Category doesn't exist
- User doesn't own the category

**Create new category for a user**
```bash
POST /api/users/{userId}/categories
Content-Type: application/json

{
  "name": "Spring Boot Learning"
}

Response: 200 OK
{
  "id": 1,
  "name": "Spring Boot Learning",
  "user": { "id": 1, "username": "john_doe" }
}
```

**Delete category for a user**
```bash
DELETE /api/users/{userId}/categories/{categoryId}

Response: 204 No Content
```

Returns `404 Not Found` if user doesn't own the category.

### Subcategories

**Get subcategories for a category**
```bash
GET /api/users/{userId}/categories/{categoryId}/subcategories
```

**Create subcategory**
```bash
POST /api/users/{userId}/categories/{categoryId}/subcategories
Content-Type: application/json

{
  "name": "REST API Design"
}
```

### Subcategory Entries

**Log a subcategory entry**
```bash
POST /api/users/{userId}/subcategories/{subCategoryId}/entries
Content-Type: application/json

{
  "date": "2024-01-15",
  "details": "Learned Optional chaining",
  "progressValue": 85
}
```

---

## 🎓 Spring Boot Concepts in UserCategoryController

| Concept | Example |
|---------|---------|
| **@RestController** | Declares REST endpoint class |
| **@RequestMapping** | Base path `/api/users/{userId}/categories` |
| **@GetMapping, @PostMapping, @DeleteMapping** | HTTP method handlers |
| **@PathVariable** | Extract `{userId}`, `{categoryId}` from URL |
| **@RequestBody** | Deserialize JSON request to `Category` |
| **Constructor Injection** | DI of `CategoryService`, `SubCategoryService`, `UserService` |
| **ResponseEntity<T>** | Type-safe HTTP responses with status codes |
| **Optional.flatMap()** | Chain operations without null unwrapping |
| **Optional.filter()** | Validate ownership (user-scoped access) |
| **Optional.map()** | Transform to success response |
| **Optional.orElse()** | Fallback for not-found |

---

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/example/kaihatsu_nikki/
│   │   ├── KaihatsuNikkiApplication.java
│   │   ├── controller/
│   │   │   ├── UserCategoryController.java    (User-scoped category CRUD)
│   │   │   ├── SubCategoryController.java
│   │   │   ├── SubCategoryEntryController.java
│   │   │   ├── DailyDiaryController.java
│   │   │   └── UserController.java
│   │   ├── service/         (business logic)
│   │   ├── repository/      (data access via JPA)
│   │   └── model/           (JPA entities)
│   └── resources/
│       └── application.properties
└── test/
    └── java/.../KaihatsuNikkiApplicationTests.java
```

---

## 🔐 Security Notes

⚠️ **Current implementation lacks:**
- Authentication/authorization
- Password hashing (stored as plain text)
- Input validation
- CORS configuration

**For production, add:**
- Spring Security
- JWT or OAuth2
- BCrypt password encoding
- Bean validation (`@Valid`, `@NotNull`, etc.)
- HTTPS

---

## 💡 Design Patterns Used

1. **Repository Pattern** – Abstract data access layer
2. **Service Layer Pattern** – Encapsulate business logic
3. **Constructor Injection** – Dependency management
4. **Optional Chaining** – Null safety without null checks
5. **RESTful Resource Hierarchy** – User-scoped nested endpoints
6. **Ownership Validation** – User can only access own data

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit changes with clear messages
4. Push and open a pull request

---

## 📄 License

Add your license here.

---

**Built with ❤️ using Spring Boot 3.5.6 & Java
