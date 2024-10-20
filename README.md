# Things Organizer

**Things Organizer** is an API designed for organizing Things into Containers, with the flexibility to manage Contents associated with any of the Things, Containers, or even other Contents. The project is built using **Java Spring Boot** and leverages different storage solutions for optimal data organization.

## Features

- Organize **Things** into **Containers**
- Manage **Contents** that can belong to Things, Containers, or other Contents
- **Spring MVC**-based web services for API interactions
- Use **SQL Server** to store Things and Containers
- Use **MongoDB** to store Contents for enhanced scalability and flexibility
- **Swagger** integration for interactive API documentation

## Technologies Used

- **Java Spring Boot**: The main framework used to build the REST API.
- **Spring MVC**: To handle HTTP requests and implement web services.
- **Spring Data JPA**: For SQL Server integration and managing the Things and Containers.
- **Spring Data MongoDB**: To manage the storage of Contents in MongoDB.
- **Spring Cloud**: for developing microservices, utilized Naming-server, config-server, api-gateway and Feign .
- **Docker**: To containerize the application, making it easy to deploy and manage.
- **SQL Server**: For relational data storage, handling Things and Containers.
- **MongoDB**: NoSQL database used for storing flexible, schema-less Content data.
- **Swagger**: For generating API documentation and allowing users to explore the API interactively.
- **JUnit**: For unit testing the application.
- **Maven**: For project build, dependency management, and version control.

## Installation

### Prerequisites

- **Java 17**
- **SQL Server**
- **MongoDB**
- **Maven**
- **Docker** (optional, if using Docker for deployment)

## Usage

Once the application is running, you can interact with the API using tools like **Postman** ,**cURL** or the configured swagger document URL. The API allows you to:

- Add **Things** and **Containers**
- Associate **Contents** with Things, Containers, or other Contents
- Enjoy the **job** putting all the Things with no Container into the root one.
- Use the **Swagger UI** to test and explore the API endpoints