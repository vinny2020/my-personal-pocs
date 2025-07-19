# ADR-0004: Spring Boot Camel Integration

## Status

Accepted

## Context

The demomvc module needed to demonstrate web service integration using Apache Camel within a Spring Boot application. The goal was to create RESTful web services that could handle HTTP requests and responses with query string parameters and POST body processing.

## Decision

We chose to implement Spring Boot with Camel integration using:

- **Spring Boot 1.4.0**: For application framework and auto-configuration
- **Apache Camel 2.17.2**: For HTTP routing and message processing
- **Netty HTTP Components**: For HTTP client/server communication
- **Spring Boot FatJar Integration**: For standalone deployment
- **RESTful Endpoints**: Echo services for query parameters and POST body
- **Query String Handling**: Demonstrates parameter processing capabilities

## Consequences

### Positive

- **Familiar Framework**: Spring Boot is widely used and well-documented
- **Auto-configuration**: Spring Boot provides automatic configuration and dependency management
- **Standalone Deployment**: FatJar allows easy deployment without external containers
- **RESTful Design**: Clean, stateless API design following REST principles
- **Parameter Processing**: Demonstrates handling of different HTTP request types

### Negative

- **Outdated Versions**: Uses older Spring Boot (1.4.0) and Camel (2.17.2) versions
- **Limited Functionality**: Basic echo services don't demonstrate complex business logic
- **Version Fragmentation**: Different versions across modules create maintenance complexity
- **Security Considerations**: Older versions may have security vulnerabilities
- **Feature Limitations**: Older Spring Boot version lacks modern features and improvements 