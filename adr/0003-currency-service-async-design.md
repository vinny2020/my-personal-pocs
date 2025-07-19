# ADR-0003: Currency Service Async Design

## Status

Accepted

## Context

The currency-service-fixerio module needed to demonstrate real-time currency exchange rate fetching with analysis of Brexit's impact on GBP rates. The service required integration with external APIs (Fixer.io) and needed to handle multiple concurrent requests efficiently.

## Decision

We chose to implement an async-based currency service with the following architecture:

- **Apache Camel 2.16.5**: For HTTP integration and message routing
- **Spring Boot Integration**: For application lifecycle management and auto-configuration
- **Async HTTP Requests**: Using Camel's async capabilities for concurrent API calls
- **Netty HTTP Components**: For efficient HTTP client/server communication
- **Mocked Responses**: For testing without requiring real API keys
- **Comprehensive Test Suite**: With mocked endpoints for reliable testing
- **Trending Rate Analysis**: Support for historical and trending calculations

## Consequences

### Positive

- **Performance**: Async processing allows handling multiple concurrent requests efficiently
- **Reliability**: Mocked responses ensure tests run consistently without external dependencies
- **Real-world Scenario**: Demonstrates integration with external APIs and rate limiting considerations
- **Comprehensive Testing**: Extensive test coverage with mocked endpoints
- **Business Logic**: Includes meaningful business logic (Brexit impact analysis) beyond simple CRUD

### Negative

- **External Dependencies**: Requires API keys for production use
- **Rate Limiting**: Subject to external API rate limits and availability
- **Version Inconsistency**: Uses older Camel version (2.16.5) compared to other modules
- **Complexity**: Async programming adds complexity to error handling and debugging
- **Data Freshness**: Depends on external API for real-time data 