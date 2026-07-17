# StockPilot - Stock Management Web Application

StockPilot is a web application developed with **Spring Boot** for managing product inventory in a simple and organized way.  
The application allows an administrator to manage products, categories, suppliers, stock movements, threshold alerts, and commercial documents.

---

## Project Objective

The main objective of this project is to provide a complete web-based stock management solution.  
The application tracks available quantities, records incoming and outgoing stock, detects products approaching stock-out, and generates commercial documents in PDF format.

---

## Main Features

- Secure administrator authentication
- Dashboard with key performance indicators
- Complete product management
- Category management
- Supplier management
- Stock movement recording
- Tracking of incoming stock, outgoing stock, and deliveries
- Automatic threshold alert detection
- Alert resolution
- Commercial document creation
- PDF document generation and download
- Responsive web interface with Thymeleaf and Bootstrap

---

## Technologies Used

- Java
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA
- Thymeleaf
- H2 Database / MySQL
- Apache PDFBox
- Bootstrap
- Maven

---

## Project Architecture

The project follows a layered architecture:

```text
src/main/java
 └── com.example.gestionstock
      ├── config
      ├── controller
      ├── model
      ├── repository
      └── service
```

### Package Roles

| Package | Description |
|---|---|
| `config` | Security configuration and data initialization |
| `controller` | Web route management |
| `model` | JPA entities and enumerations |
| `repository` | Spring Data JPA interfaces |
| `service` | Business logic and PDF generation |

---

## Application Modules

### Authentication

Access to the application is protected by a login page.
Only the administrator can access the internal features.

### Dashboard

The dashboard displays key information:

* total number of products;
* total stock value;
* number of suppliers;
* number of active alerts;
* most recent recorded movements;
* products approaching stock-out.

### Products

The product module allows the administrator to:

* view the product list;
* add a new product;
* update an existing product;
* delete a product;
* search for a product;
* associate a product with a category and supplier.

### Stock Movements

The stock movement module records:

* incoming stock;
* outgoing stock;
* deliveries.

The product quantity is automatically updated after each movement.

### Alerts

The alert module tracks products whose available quantity is less than or equal to the configured threshold.
An alert can be active or resolved.

### Commercial Documents

The document module creates commercial documents and allows them to be downloaded in PDF format.

---

## Screenshots

The main application screenshots are:

```text
01-login.png
02-dashboard.png
03-products.png
04-product-form.png
05-movements.png
06-alerts.png
07-documents.png
08-categories.png
09-suppliers.png
```

They illustrate the complete user journey, from authentication to stock management.

---

## Installation and Execution

### 1. Clone the Project

```bash
git clone https://github.com/bgoussama/gestion_stock.git
cd gestion_stock
```

### 2. Run the Application with Maven

On Windows:

```bash
.\mvnw.cmd spring-boot:run
```

On Linux or macOS:

```bash
./mvnw spring-boot:run
```

### 3. Access the Application

Open a browser and visit:

```text
http://localhost:8080
```

---

## Login Credentials

```text
Username: admin
Password: admin123
```

> These default credentials are intended for local demonstration only and should be changed before any deployment.

---

## Database

By default, the application can use an H2 database for testing and development.

Example H2 console address:

```text
http://localhost:8080/h2-console
```

The exact settings depend on the configuration defined in:

```text
src/main/resources/application.properties
```

---

## Tests

Run the automated tests with:

```bash
.\mvnw.cmd test
```

The tests verify:

* correct startup of the Spring application context;
* operation of the PDF generation service.

---

## Main Routes

| Route | Method | Description |
|---|---|---|
| `/login` | GET/POST | Authentication |
| `/` | GET | Dashboard |
| `/products` | GET/POST | List and add products |
| `/products/{id}/edit` | GET | Update a product |
| `/products/{id}/delete` | POST | Delete a product |
| `/movements` | GET/POST | Manage stock movements |
| `/alerts` | GET | List alerts |
| `/alerts/{id}/resolve` | POST | Resolve an alert |
| `/documents` | GET/POST | Manage documents |
| `/documents/{id}/pdf` | GET | Download a PDF document |
| `/categories` | GET/POST | Manage categories |
| `/suppliers` | GET/POST | Manage suppliers |

---

## Simplified Project Structure

```text
gestion_stock
├── src
│   ├── main
│   │   ├── java
│   │   │   └── ...
│   │   └── resources
│   │       ├── static
│   │       ├── templates
│   │       └── application.properties
│   └── test
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

---

## Possible Improvements

* Add multiple user roles
* Add a warehouse manager role
* Link commercial documents to stock movements
* Add batch management
* Add storage location management
* Add a barcode system
* Add more unit and functional tests
* Add a REST API for a future mobile application

---

## Author

Project developed as part of an academic assignment.

**Application:** StockPilot  
**Type:** Stock management web application  
**Technologies:** Spring Boot, Thymeleaf, Spring Data JPA, Spring Security
