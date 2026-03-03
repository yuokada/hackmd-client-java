---
applyTo:
  - "hackmd-client-core/src/test/java/**"
---

# Core tests (JUnit 5)

- JUnit Jupiter (JUnit 5) conventions: Assertions.*, @Nested, @ParameterizedTest as appropriate.
- Using jackson-databind in tests is OK (e.g., ObjectMapper) since databind is test-scoped.
- Add edge cases + failure cases (invalid payloads, missing fields, unexpected values).
