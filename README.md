# Leave-management-system
The Symplora Leave Management System is a web-based application built with Java Spring Boot  and REST APIs that helps organizations streamline the process of managing employee leave requests. It provides separate logins for Employees and HR/Admins, ensuring smooth communication and faster approval cycles.

Features

Employee Module
Employee login
Submit leave requests (with start date, end date, and reason)
View leave status

HR Module
HR login
Approve or reject leave requests
Manage employee leave records

General
Secure authentication (login verification with DB)
Clean separation of layers: Controller, Service, Repository
Uses relational database (e.g., MySQL/PostgreSQL) with JPA/Hibernate

🛠️ Tech Stack
Backend: Spring Boot, Spring MVC, Spring Data JPA, Hibernate
Database:  PostgreSQL
Server: Tomcat (embedded with Spring Boot)
