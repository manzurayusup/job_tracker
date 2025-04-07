# Job Tracker
A simple Spring Boot application that allows users to create accounts and track job applications. 
Built to improve my backend and full-stack skills. This project is still a work in progress and 
will be updated as new features are added. 

Note: I'm currently working on the create, update and delete endpoints for a user. I will be finalizing
the MVP soon (once I've finished implementing the basic features).

## Tech Stack
- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Maven
- IntelliJ IDEA
- Git & GitHub

## Features Implemented
✅ User Creation \
✅ User Deletion \
✅ Update User Info (username, email, password) \
✅ Passwords stored securely (hashed) \
✅ MySQL database integration

## Upcoming Features
- Job Application tracking (CRUD)
- Job Status history
- Authentication & Login
- UI with React (or Thymeleaf)

-------------
## How to Test (in your local)
1. Clone the repository 
```
git clone https://github.com/YOUR_USERNAME/job_tracker.git
cd job_tracker
```
2. Create the database. Start MySQL and run: 
````
mysql -u your_username -p < schema.sql
````
This will create the job_tracker database and any required tables.
3. Configure application.properties. Update with your MySQL credentials. 
````
spring.datasource.url=jdbc:mysql://localhost:3306/job_tracker
spring.datasource.username=your_username
spring.datasource.password=your_password
````
4. Run the application:

    ./mvnw spring-boot:run

5. Test the endpoints using curl.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1) <b>CREATE A USER</b> \
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Endpoint: POST /users/create \
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Description: Creates a new user using URL parameters.

    curl -X POST "http://localhost:8080/users/create?username=johndoe&email=john@example.com&password=secure123"

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2) <b>UPDATE A USER</b> \
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Endpoint: PUT /users/update/{userId} \
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Description: Updates a user. Only send the fields you want to update.

    curl -X PUT "http://localhost:8080/users/update/1" \
      -H "Content-Type: application/json" \
      -d '{
        "username": "john_doe_updated",
        "email": "newemail@example.com",
        "password": "newsecurepass"
      }'
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (Replace 1 with the actual user ID.)

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 3) <b>DELETE A USER</b> \
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Endpoint: DELETE /users/delete/{userId} \
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Description: Deletes the user with the given ID.

    curl -X DELETE "http://localhost:8080/users/delete/1"



## What I'm Learning
- Spring Security basics & how to disable it 
- Handling JPA relationships and schema design 
- RESTful API design 
- DTOs and clean architecture 
- How to use curl for testing 
- Authentication best practices
