# Vincent's Proof of Concepts and Sanity Checks
[![Java CI](https://github.com/vinny2020/my-personal-pocs/workflows/Java%20CI/badge.svg)](https://github.com/vinny2020/my-personal-pocs/actions?query=workflow%3A%22Java+CI%22)

This repository contains three Java modules demonstrating different integration patterns and technologies. All modules have been upgraded to **Java 21** and use modern dependency management.

## 📦 Modules

### 🐪 **camel-with-cdi** - Apache Camel with CDI Integration
A standalone Java application demonstrating Apache Camel integration with CDI (Contexts and Dependency Injection).

**Features:**
- Timer-based message generation (every 5 seconds)
- CDI dependency injection for beans and endpoints
- Mock endpoints for testing
- Modern Jakarta CDI API (Java 21 compatible)

**Technology Stack:**
- Apache Camel 3.20.5
- Jakarta CDI 4.0.1
- Weld 4.0.4.Final (CDI implementation)
- SLF4J with Reload4j logging

**How to run:**
```bash
cd camel-with-cdi
mvn clean install
mvn camel:run
```

### 💱 **currency-service-fixerio** - Currency Exchange Service
A backend service demonstrating the effects of Brexit on currency exchange rates using the Fixer.io API.

**Features:**
- Real-time currency exchange rate fetching
- Brexit impact analysis on GBP rates
- Async HTTP requests with Camel
- Comprehensive test suite with mocked responses
- Support for trending rate analysis

**Technology Stack:**
- Apache Camel 2.16.5 (with Java 21 compatibility)
- Spring Boot integration
- Netty HTTP components
- Logback logging
- JUnit testing with mocked endpoints

**API Endpoints:**
- Currency exchange rate retrieval
- Historical rate analysis
- Trending rate calculations

**How to run:**
```bash
cd currency-service-fixerio
mvn clean install
mvn spring-boot:run
```

**Note:** Requires a Fixer.io API key for production use. Test environment uses mocked responses.

### 🌐 **demomvc** - Spring Boot with Camel Web Services
A Spring Boot application demonstrating web service integration using Apache Camel and Netty.

**Features:**
- RESTful web services
- HTTP request/response processing
- Query string parameter handling
- Echo service endpoints
- Spring Boot auto-configuration with `RouteBuilder`
- Upgraded to modern Spring Boot and Apache Camel

**Technology Stack:**
- Spring Boot 4.0.2
- Apache Camel 4.17.0
- Netty HTTP components

**Available Endpoints:**
- `GET /echo-query-string` - Echo query parameters
- `POST /echo-post-body` - Echo POST request body

**How to run:**
```bash
cd demomvc
mvn clean install
mvn spring-boot:run
```

The service will be available at `http://localhost:9090`

## 🚀 Getting Started

### Prerequisites
- Java 21 (OpenJDK or Oracle JDK)
- Maven 3.6+

### Build All Modules
```bash
mvn clean install
```

### Run Individual Modules
Each module can be run independently. See the specific instructions above for each module.

## 🔧 CI/CD

This project uses GitHub Actions for continuous integration:
- **Java 21** with Temurin distribution
- **Maven caching** for faster builds
- **Automatic testing** on push/PR
- **Multi-module support**

## 📝 Notes

- All modules have been upgraded to **Java 21** compatibility
- JAXB dependencies added for Java 21 compatibility
- Modern dependency versions throughout
- Comprehensive test coverage with mocked services
- GitHub Actions replaces legacy Travis CI

## 🤝 Contributing

Feel free to submit issues and enhancement requests!
