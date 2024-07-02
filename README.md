# Lalabrand

[![GitHub license](https://img.shields.io/badge/license-AGPL--3.0-blue.svg)](https://github.com/denomelchenko/lalabrand/blob/master/LICENSE)

Backend for an online store using Java Spring Boot.

## Table of Contents

- [About](#about)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Configuration](#configuration)
- [Technologies Used](#technologies-used)
- [License](#license)

## About

Lalabrand is a robust backend solution for managing an online store, providing various functionalities such as product management, user authentication, and order processing. Built with Spring Boot, it leverages modern Java frameworks and tools to ensure scalability, security, and ease of use.

## Features

- **User Management**: Register, login, and manage user profiles with secure authentication.
- **Product Catalog**: CRUD operations for products, categories, sizes and other entities.
- **Order Processing**: Efficient handling of customer orders, from creation to completion.
- **Email Notifications**: Automated emails for password update.
- **Security**: JWT-based authentication and authorization(refresh token and access token).
- **Database Management**: Uses MySQL for data persistence.
- **GraphQL API**: Provides a flexible and efficient API for data querying.

## Installation

To set up the project locally:

1. **Clone the repository:**

   ```bash
   git clone https://github.com/denomelchenko/lalabrand.git
   cd lalabrand
   ```

2. **Set up the MySQL database:**

   Create a database named `lalabrand_db` and configure your `application.properties` with the appropriate credentials.

3. **Build the project:**

   ```bash
   ./mvnw clean install
   ```

4. **Run the application:**

   ```bash
   ./mvnw spring-boot:run
   ```

   Alternatively, you can use Docker to run the application:

   ```bash
   docker-compose up
   ```

## Usage

Once the application is running, you can access the following endpoints:

- **GraphQL API**: `http://localhost:8080/graphql`

## Configuration

Configuration details can be found in the `application.properties.origin` file located under `src/main/resources`.
You need to rename it to application.properties and edit origin properties to your local properties .
Key configurations include:

- **Database Configuration**: Set your MySQL database URL, username, and password.
- **JWT Settings**: Configure your JWT secret key and expiration time.
- **Email Server**: Configure your SMTP server details for email notifications.

## Technologies Used

- **Java 17**
- **Spring Boot**
- **GraphQL**
- **MySQL**
- **JWT Authentication**
- **Lombok**
- **Docker**
- **Maven**
- **Flyway**

## Database projection
![lalabrand db](https://github.com/denomelchenko/lalabrand/assets/124204526/e61aab4f-518b-4790-b290-3715f980e337)
## License

This project is licensed under the AGPL-3.0 License - see the [LICENSE](https://github.com/denomelchenko/lalabrand/blob/master/LICENSE) file for details.
