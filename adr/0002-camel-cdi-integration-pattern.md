# ADR-0002: Camel CDI Integration Pattern

## Status

Accepted

## Context

The camel-with-cdi module needed to demonstrate integration between Apache Camel and CDI (Contexts and Dependency Injection). The goal was to create a standalone Java application that showcases modern enterprise integration patterns using Jakarta CDI and Apache Camel.

## Decision

We chose to implement Apache Camel with CDI integration using:

- **Apache Camel 3.20.5**: For enterprise integration patterns and message routing
- **Jakarta CDI 4.0.1**: For dependency injection and bean management
- **Weld 4.0.4.Final**: As the CDI implementation
- **Java 21**: For modern Java features and long-term support
- **Timer-based message generation**: Every 5 seconds to demonstrate continuous processing
- **Mock endpoints**: For testing without external dependencies

## Consequences

### Positive

- **Modern Standards**: Uses Jakarta CDI 4.0.1, which is the latest standard for dependency injection
- **Java 21 Compatibility**: Leverages modern Java features and ensures long-term support
- **Testability**: Mock endpoints allow for comprehensive testing without external dependencies
- **Standalone**: Can run independently without application server
- **Learning Value**: Demonstrates enterprise integration patterns in a simple, understandable way

### Negative

- **Version Complexity**: Different Camel versions across modules (3.20.5 vs 2.16.5 vs 2.17.2)
- **Dependency Management**: Requires careful management of CDI and Camel dependencies
- **Learning Curve**: Developers need to understand both Camel and CDI concepts
- **Limited Real-world Usage**: Standalone CDI applications are less common than Spring Boot applications 