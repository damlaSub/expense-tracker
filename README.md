# 💰 Expense Tracker

**Expense Tracker** is a full-stack web application designed to help users manage personal or business finances. Built with **Java 17, Spring Boot**, and **Angular**, the app allows users to **track expenses, categorize them, set budgets, and generate summaries**. It also includes **real-time notifications** via **WebSocket** when users exceed their budgets.

---

## 🚀 Features

- 🔐 **User Authentication** with Spring Security (JWT)
- ➕ **Add, Edit, Delete** expenses
- 📊 **View expenses** by:
  - Category
  - Date range
  - Monthly summaries
- 💡 **Budget tracking** and overspending warnings
- 📂 **Expense categorization**
- 📈 **Summary reports** by month and category
- 🔎 **Filtering and sorting** by date, amount, and category
- 📢 **Real-time notifications** via **WebSocket** when budget limits are exceeded

---

## 🧩 Tech Stack

**Backend**  
- Java 17  
- Spring Boot  
- Spring Security + JWT  
- Spring WebSocket (STOMP)  
- Java Streams & Lambdas  
- JPA for database access

**Frontend**  
- Angular  
- TypeScript  
- Angular Router, Forms  
- REST API with HttpClient  
- **WebSocket client (SockJS + STOMP)** for push notifications

---

## 🧠 Concepts & Topics Used

This project reinforces both **core** and **advanced** Java/backend concepts, including areas often covered in Oracle certification:

### ✅ Core Java
- OOP: **encapsulation**, **inheritance**, **polymorphism**
- Collections Framework: `List`, `Map`, `Set`
- Exception Handling:
  - Checked/unchecked exceptions
  - Custom exceptions (`InvalidExpenseException`)
- Java I/O (optional for file storage)
- Date/Time API

### ✅ Advanced Java
- **Streams & Lambdas** for:
  - Filtering by category/date/amount
  - Grouping and summarizing
- **WebSocket (STOMP)** for budget warning notifications

---

## 📦 Functional Overview

### 1. **Authentication**
- `POST /auth/register` – Register  
- `POST /auth/login` – Login and get JWT  

JWT must be added to `Authorization` headers for all authenticated routes.

---

### 2. **Expense Management**
- `GET /expenses` – Fetch expenses  
- `POST /expenses` – Add new expense  
- `PUT /expenses/{id}` – Edit expense  
- `DELETE /expenses/{id}` – Delete expense  

Each expense includes:
- `amount`
- `category`
- `description`
- `date`

---

### 3. **Budgeting & Summaries**
- Set monthly budget  
- Track remaining balance  
- Summary by category and month  
- **WebSocket alert** when budget is exceeded

---

## ⚠️ Error Handling

Handled globally via `@ControllerAdvice`. Returns clear and consistent error responses.

Example:

```json
{
  "timestamp": "2025-07-30T12:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid expense amount"
}
