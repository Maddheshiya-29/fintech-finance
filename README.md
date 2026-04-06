# Fintech


#  Finance Data Processing and Access Control Backend Project (Backend)

##  Overview
This is a **Spring Boot-based Finance Management System** that provides REST APIs for managing users, transactions, and financial dashboards.

The system includes:
- Role-Based Access Control (RBAC)
- Secure authentication using JWT
- Income & Expense tracking
- Dashboard analytics (monthly, yearly, filtered)

---

##  Tech Stack
- **Backend:** Java, Spring Boot  
- **Security:** Spring Security + JWT  
- **Database:** MySQL  
- **ORM:** Spring Data JPA / Hibernate  
- **Build Tool:** Maven  

---

##  Roles & Permissions
- **ADMIN**
  - Full access (users, transactions, dashboard)

- **ANALYST**
  - View users & financial insights

- **VIEWER**
  - View limited data (own records)

---

##  Features

### User Management
- Register new users
- Check username availability
- Fetch users with pagination
- Update user details
- Fetch user by username

###  Authentication
- Login with username & password
- JWT token generation
- Secure APIs using roles

###  Transactions
- View user transaction records
- View all transaction summaries
- Get transaction details
- Update transaction summary & details
- Soft delete transactions

###  Dashboard
- Overall summary (Income, Expense, Balance)
- Filtered summary (Year + Quarter)
- Yearly summary
- Monthly trend analysis
- Recent transactions

---

## API Endpoints

###  User APIs

| POST   | /user/login | Authenticate user 

| POST   | /user/register | Register new user (ADMIN only) 

| GET    | /user/username-exists | Check username 

| GET    | /user/roles | Get available roles |

---

###  Admin APIs

| GET    | /admin/users | Get users (paginated) 

| PATCH  | /admin/updateUser/{id} | Update user 

| GET    | /admin/user-by-username | Get user details |

---

###  Dashboard APIs

| GET    | /dashboard/overall-summary | Overall stats 

| GET    | /dashboard/filtered-summary | Filter by year & quarter 

| GET    | /dashboard/yearly-summary | Year summary 

| GET    | /dashboard/recentTransactions | Recent transactions 

| GET    | /dashboard/monthly-trend | Monthly trend 

---

###  Transaction APIs

| GET    | /transaction/user-records | User transactions 

| GET    | /transaction/all-transaction-summary | All summaries 

| GET    | /transaction/transaction-details/{id} | Transaction details 

| PATCH  | /transaction/update-transaction-summary/{id} | Update summary 

| PATCH  | /transaction/update-transaction-detail/{id} | Update detail 

| PATCH  | /transaction/delete-transaction-summary/{id} | Delete summary 

| PATCH  | /transaction/delete-transaction-detail/{id} | Delete detail 

---
## Request Parameter Formats

### Year Format:
YYYY-YY
Example: 2026-27


### Quarter Values:
Q1, Q2, Q3, Q4


### Transaction Type:
Income / Expense


---

##  Key Functionalities

### Password Update Logic
- Requires old password
- New password must be different
- Confirm password must match

###  Soft Delete
- Data is not permanently deleted
- Uses deleted = true flag

###  Auto Update Summary
- Updating/deleting transaction details automatically updates summary amount

Authentication Flow:

1- Login using /user/login

2- Receive JWT token

3- Add token in headers

4- Authorization: Bearer <token


📁 Project Structure

com.fintech.finance

│── controllers

│── services

│── repositories

│── entities

│── dtos

│── security

---

