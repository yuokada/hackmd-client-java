---
applyTo:
  - "hackmd-client-quarkus/src/test/java/**"
  - "hackmd-client-quarkus/src/test/resources/**"
---

# Quarkus tests (JUnit 5 + WireMock)

## Quarkus test conventions
- Use Quarkus test facilities (quarkus-junit5). Keep tests hermetic (no real network).

## WireMock fixtures
- Changes under `src/test/resources/**` (including __files) must explain:
  - Which scenario it covers (happy-path / auth failure / rate-limit / server error, etc.)
  - Why the payload shape matters (field missing/extra, nulls, types)
- Prefer readable, minimal fixtures; avoid huge copies of real responses unless necessary.

## Stability
- No dependence on fixed ports, current time, ordering, or global shared state.
- Tests should run reliably in parallel (or explicitly opt out if truly required).
