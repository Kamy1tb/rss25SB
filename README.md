# RSS25SB - Spring Boot RSS Application

## Overview
RSS25SB is a Spring Boot application for managing RSS feeds. It provides REST endpoints for retrieving and managing RSS data according to the RSS25 specification, with PostgreSQL database integration for persistent storage.

## Features
- REST API for RSS feed management
- XML and HTML representations of RSS feeds
- PostgreSQL database integration
- MVC architecture
- Thymeleaf templates for web views
- XSLT transformations for HTML rendering
- XML validation against XSD schema

## Prerequisites
- Java Development Kit (JDK) 21 or later
- Maven 3.8.0 or later (or use the included Maven wrapper)
- Git (for cloning the repository)
- PostgreSQL (for local development, optional)

## Getting Started

### Cloning the Repository
```bash
git clone https://github.com/yourusername/rss25SB.git
cd rss25SB
```

### Building the Project
```bash
# Using Maven
mvn clean package

# Using Maven Wrapper (Windows)
mvnw.cmd clean package

# Using Maven Wrapper (Linux/Mac)
./mvnw clean package
```

### Running the Application Locally
```bash
# Using Maven
mvn spring-boot:run

# Using Maven Wrapper (Windows)
mvnw.cmd spring-boot:run

# Using Maven Wrapper (Linux/Mac)
./mvnw spring-boot:run
```

The application will start on port 8113 by default. You can access it at http://localhost:8113

### Running with a JAR file
After building the project, you can run the generated JAR file:
```bash
java -jar target/rss25server.war
```

### Database Configuration
By default, the application is configured to connect to a PostgreSQL database hosted on Clever Cloud. For local development, you can modify the `application.properties` file to use a local database:

```properties
# Local PostgreSQL Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/rss25sb
spring.datasource.username=postgres
spring.datasource.password=yourpassword
```

The current configuration connects to a PostgreSQL database hosted on Clever Cloud with the following details:

- Host: ba1y0qcdswqdset2rdhm-postgresql.services.clever-cloud.com
- Port: 50013
- Database: ba1y0qcdswqdset2rdhm
- Username: umaxwzxujzbl78pzixfk

## Deployed Application
The application is deployed on Clever Cloud and can be accessed at:

**URL**: https://app-ba1y0qcdswqdset2rdhm.cleverapps.io

### Accessing the Deployed Application
1. Open your web browser and navigate to https://app-ba1y0qcdswqdset2rdhm.cleverapps.io
2. You'll see the home page with information about the project
3. Use the navigation menu to access different features:
   - **Accueil**: Home page
   - **Xml**: View all feeds in XML format
   - **Html**: View all feeds in HTML format
   - **Insertion**: Add a new feed
   - **Articles**: View and manage articles
   - **Help**: View API documentation

## REST API Endpoints
The application provides the following REST endpoints:

### Main Pages
- `GET /` - Home page with project information
- `GET /help` - Help page with API documentation
- `GET /insert` - Page for inserting new feeds
- `GET /items` - Page for viewing and managing articles

### RSS Feed Management
- `GET /rss25SB/resume/xml` - Get a list of all feeds in XML format
- `GET /rss25SB/resume/html` - Get a list of all feeds in HTML format
- `GET /rss25SB/resume/xml/{id}` - Get a specific article by ID in XML format
- `GET /rss25SB/html/{id}` - Get a specific article by ID in HTML format
- `GET /rss25SB/html/guid/{guid}` - Get a specific article by GUID in HTML format
- `POST /rss25SB/insert` - Add a new feed (XML format)
- `DELETE /rss25SB/delete/{id}` - Delete an article by ID

## Project Structure
The project follows the MVC (Model-View-Controller) pattern:

- **Models**: Located in `fr.univrouen.rss25sb.model`
- **Views**: Thymeleaf templates in `src/main/resources/templates`
- **Controllers**: Located in `fr.univrouen.rss25sb.controllers`
- **Services**: Located in `fr.univrouen.rss25sb.service`
- **Repositories**: Located in `fr.univrouen.rss25sb.repository`
- **Utilities**: Located in `fr.univrouen.rss25sb.util`

## Example XML Feeds
The project includes example XML feeds in the `src/main/resources/examples` directory:
- `basic_feed_example.xml` - A minimal example with required elements
- `complex_feed_example.xml` - A comprehensive example with multiple items
- `scientific_feed_example.xml` - An example for scientific publications
- `events_feed_example.xml` - An example for cultural events

## Running Tests
To run the tests:

```bash
# Using Maven
mvn test

# Using Maven Wrapper (Windows)
mvnw.cmd test

# Using Maven Wrapper (Linux/Mac)
./mvnw test
```
