# 🛒 FakeStore API — Test Automation Project

> A complete API testing project covering all modules of the [FakeStore API](https://fakestoreapi.com) — a free, open REST API that simulates a real e-commerce backend for testing and prototyping purposes.

---

## 📌 Project Overview

| Item | Details |
|------|---------|
| **Base URL** | `https://fakestoreapi.com` |
| **Protocol** | HTTPS / REST |
| **Data Format** | JSON |
| **Auth Type** | JWT (Bearer Token) |
| **Total Modules** | 4 (Products, Carts, Users, Auth) |
| **Total Endpoints** | 16 |

---

## 📦 Modules Summary

| # | Module | Endpoints | Description |
|---|--------|-----------|-------------|
| 1 | **Auth** | 1 | User login and JWT token generation |
| 2 | **Products** | 7 | Full CRUD + category-level operations |
| 3 | **Carts** | 5 | Cart management and user cart retrieval |
| 4 | **Users** | 5 | Full user CRUD operations |

> ⚠️ **Note:** Data mutations (POST, PUT, PATCH, DELETE) are simulated — changes do **not** persist in the actual database. Fake IDs and responses are returned for testing purposes.

---

## 🔐 Module 1 — Auth

**Base Path:** `/auth`

Authentication is handled via a login endpoint that returns a **JWT token**. This token can be used for testing protected/authenticated flows.

### Endpoints

| # | Method | Endpoint | Description |
|---|--------|----------|-------------|
| 1 | `POST` | `/auth/login` | Login with username & password, returns JWT token |

### Request Body

```json
{
  "username": "mor_2314",
  "password": "83r5^_"
}
```

### Response

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI..."
}
```

### Test Scenarios
- ✅ Valid credentials → 200 OK with token
- ❌ Invalid credentials → Error response
- ❌ Missing username or password → Validation error
- ❌ Empty request body → Error response

---

## 🛍️ Module 2 — Products

**Base Path:** `/products`

The Products module covers all operations around product listings, individual product retrieval, category-based filtering, and full CRUD. There are **20 products** and **4 categories** available.

### Product Schema

```json
{
  "id": 1,
  "title": "Fjallraven - Foldsack No. 1 Backpack",
  "price": 109.95,
  "description": "Your perfect pack for everyday use...",
  "category": "men's clothing",
  "image": "https://fakestoreapi.com/img/81fAn.jpg",
  "rating": {
    "rate": 3.9,
    "count": 120
  }
}
```

### Endpoints

| # | Method | Endpoint | Description |
|---|--------|----------|-------------|
| 1 | `GET` | `/products` | Get all products |
| 2 | `GET` | `/products/{id}` | Get a single product by ID |
| 3 | `POST` | `/products` | Add a new product |
| 4 | `PUT` | `/products/{id}` | Update a product (full update) |
| 5 | `PATCH` | `/products/{id}` | Update a product (partial update) |
| 6 | `DELETE` | `/products/{id}` | Delete a product |
| 7 | `GET` | `/products/categories` | Get all product categories |

### Query Parameters

| Parameter | Type | Example | Description |
|-----------|------|---------|-------------|
| `limit` | Number | `/products?limit=5` | Limit the number of results |
| `sort` | String | `/products?sort=desc` | Sort results (`asc` or `desc`) |

### Category Filtering

```
GET /products/category/{categoryName}
```

Available categories:
- `electronics`
- `jewelery`
- `men's clothing`
- `women's clothing`

### Test Scenarios
- ✅ Get all products → 200 OK, array of 20 products
- ✅ Get product by valid ID (1–20) → 200 OK
- ✅ Get all categories → 200 OK, array of 4 categories
- ✅ Filter by valid category → 200 OK, filtered list
- ✅ Limit results with `?limit=5` → returns 5 products
- ✅ Sort with `?sort=desc` → descending order
- ✅ Create product (POST) → 200 OK, returns fake ID
- ✅ Full update (PUT) → 200 OK, updated product returned
- ✅ Partial update (PATCH) → 200 OK, updated fields returned
- ✅ Delete product → 200 OK, deleted product returned
- ❌ Get product by invalid ID → Empty/error response
- ❌ Filter by non-existent category → Empty array

---

## 🛒 Module 3 — Carts

**Base Path:** `/carts`

The Carts module manages shopping carts, allowing retrieval, creation, updating, and deletion. There are **20 carts** in the dataset. Carts can also be queried by user or filtered by date range.

### Cart Schema

```json
{
  "id": 1,
  "userId": 1,
  "date": "2020-03-02",
  "products": [
    {
      "productId": 1,
      "quantity": 4
    },
    {
      "productId": 2,
      "quantity": 1
    }
  ]
}
```

### Endpoints

| # | Method | Endpoint | Description |
|---|--------|----------|-------------|
| 1 | `GET` | `/carts` | Get all carts |
| 2 | `GET` | `/carts/{id}` | Get a single cart by ID |
| 3 | `GET` | `/carts/user/{userId}` | Get all carts for a specific user |
| 4 | `POST` | `/carts` | Add a new cart |
| 5 | `PUT` | `/carts/{id}` | Update a cart (full update) |
| 6 | `PATCH` | `/carts/{id}` | Update a cart (partial update) |
| 7 | `DELETE` | `/carts/{id}` | Delete a cart |

### Query Parameters

| Parameter | Type | Example | Description |
|-----------|------|---------|-------------|
| `limit` | Number | `/carts?limit=3` | Limit number of results |
| `sort` | String | `/carts?sort=desc` | Sort by date (`asc` or `desc`) |
| `startdate` | Date | `/carts?startdate=2020-01-01` | Filter carts from this date |
| `enddate` | Date | `/carts?enddate=2020-04-01` | Filter carts up to this date |

### Test Scenarios
- ✅ Get all carts → 200 OK, array of 20 carts
- ✅ Get cart by valid ID → 200 OK
- ✅ Get carts by user ID → 200 OK, user's carts
- ✅ Date range filter → returns carts within range
- ✅ Create new cart (POST) → 200 OK, fake cart ID returned
- ✅ Full update (PUT) → 200 OK, updated cart returned
- ✅ Partial update (PATCH) → 200 OK
- ✅ Delete cart → 200 OK, deleted cart returned
- ❌ Get cart with invalid ID → Empty/error response
- ❌ Get carts for non-existent user → Empty array

---

## 👤 Module 4 — Users

**Base Path:** `/users`

The Users module handles all user profile operations. There are **10 users** available in the dataset, each with a full profile including address and contact details.

### User Schema

```json
{
  "id": 1,
  "email": "john@gmail.com",
  "username": "johnd",
  "password": "m38rmF$",
  "name": {
    "firstname": "John",
    "lastname": "Doe"
  },
  "address": {
    "city": "kilkenny",
    "street": "7835 new road",
    "number": 3,
    "zipcode": "12926-3874",
    "geolocation": {
      "lat": "-37.3159",
      "long": "81.1496"
    }
  },
  "phone": "1-570-236-7033"
}
```

### Endpoints

| # | Method | Endpoint | Description |
|---|--------|----------|-------------|
| 1 | `GET` | `/users` | Get all users |
| 2 | `GET` | `/users/{id}` | Get a single user by ID |
| 3 | `POST` | `/users` | Add a new user |
| 4 | `PUT` | `/users/{id}` | Update a user (full update) |
| 5 | `PATCH` | `/users/{id}` | Update a user (partial update) |
| 6 | `DELETE` | `/users/{id}` | Delete a user |

### Query Parameters

| Parameter | Type | Example | Description |
|-----------|------|---------|-------------|
| `limit` | Number | `/users?limit=5` | Limit number of results |
| `sort` | String | `/users?sort=asc` | Sort results (`asc` or `desc`) |

### Test Scenarios
- ✅ Get all users → 200 OK, array of 10 users
- ✅ Get user by valid ID (1–10) → 200 OK
- ✅ Create user (POST) → 200 OK, returns fake user ID
- ✅ Full update (PUT) → 200 OK, updated user returned
- ✅ Partial update (PATCH) → 200 OK
- ✅ Delete user → 200 OK, deleted user returned
- ❌ Get user with invalid ID → Empty/error response
- ❌ Create user with missing required fields → Error

---

## 📊 Complete Endpoint Reference (All 16)

| # | Module | Method | Endpoint | Description |
|---|--------|--------|----------|-------------|
| 1 | Auth | POST | `/auth/login` | User login → JWT token |
| 2 | Products | GET | `/products` | Get all products |
| 3 | Products | GET | `/products/{id}` | Get single product |
| 4 | Products | POST | `/products` | Create product |
| 5 | Products | PUT | `/products/{id}` | Full update product |
| 6 | Products | PATCH | `/products/{id}` | Partial update product |
| 7 | Products | DELETE | `/products/{id}` | Delete product |
| 8 | Products | GET | `/products/categories` | Get all categories |
| 9 | Carts | GET | `/carts` | Get all carts |
| 10 | Carts | GET | `/carts/{id}` | Get single cart |
| 11 | Carts | GET | `/carts/user/{userId}` | Get carts by user |
| 12 | Carts | POST | `/carts` | Create cart |
| 13 | Carts | PUT | `/carts/{id}` | Full update cart |
| 14 | Users | GET | `/users` | Get all users |
| 15 | Users | GET | `/users/{id}` | Get single user |
| 16 | Users | POST | `/users` | Create user |

---

## 🔧 Tools & Tech Stack

| Tool | Purpose |
|------|---------|
| [Postman](https://postman.com) / REST Client | API testing and request execution |
| JWT | Token-based auth testing |
| JSON | Request/Response data format |

---

## 📝 Notes

- All **write operations** (POST, PUT, PATCH, DELETE) are **non-persistent** — no real data is modified in the database. Fake response data is returned.
- The `sort` parameter accepts `asc` (default) or `desc`.
- JWT tokens returned from `/auth/login` can be used in the `Authorization: Bearer <token>` header for simulating authenticated requests.
- Product IDs range from **1 to 20**.
- User IDs range from **1 to 10**.

---

## 📁 Project Structure

```
fakestoreapi-testing/
├── Auth/
│   └── Login.postman_collection.json
├── Products/
│   ├── GetAllProducts.json
│   ├── GetProductById.json
│   ├── CreateProduct.json
│   ├── UpdateProduct.json
│   ├── DeleteProduct.json
│   └── GetCategories.json
├── Carts/
│   ├── GetAllCarts.json
│   ├── GetCartById.json
│   ├── GetCartsByUser.json
│   ├── CreateCart.json
│   └── UpdateCart.json
├── Users/
│   ├── GetAllUsers.json
│   ├── GetUserById.json
│   └── CreateUser.json
└── README.md
```

---

*FakeStoreAPI — https://fakestoreapi.com*
