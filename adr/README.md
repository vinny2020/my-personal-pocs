# Architecture Decision Records (ADRs)

This directory contains Architecture Decision Records (ADRs) for the my-personal-pocs project.

## What are ADRs?

Architecture Decision Records are short text documents that capture important architectural decisions made during the development of a project. They provide context for future developers about why certain decisions were made.

## ADR Format

Each ADR follows this structure:

- **Title**: A descriptive title
- **Status**: Proposed, Accepted, Deprecated, Superseded
- **Context**: The situation that led to the decision
- **Decision**: The architectural decision that was made
- **Consequences**: The resulting context after applying the decision

## ADR Index

- [ADR-0001](0001-record-architecture-decisions.md) - Record Architecture Decisions
- [ADR-0002](0002-camel-cdi-integration-pattern.md) - Camel CDI Integration Pattern
- [ADR-0003](0003-currency-service-async-design.md) - Currency Service Async Design
- [ADR-0004](0004-spring-boot-camel-integration.md) - Spring Boot Camel Integration

## Adding New ADRs

To add a new ADR:

1. Create a new file with the next sequential number (e.g., `0005-decision-name.md`)
2. Use the template from ADR-0001 as a starting point
3. Update this README.md to include the new ADR in the index
4. Commit the ADR with a descriptive commit message

## References

- [ADR Tools](https://adr.github.io/)
- [ADR GitHub](https://github.com/joelparkerhenderson/architecture_decision_record) 