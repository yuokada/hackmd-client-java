---
applyTo:
  - "hackmd-client-quarkus/src/test/java/**"
  - "hackmd-client-quarkus/src/test/resources/**"
---

# Quarkus tests (JUnit 5 + WireMock) review rules

## Must-have scenarios for HackmdClientImpl
- Verify 404 is mapped to Optional.empty() for:
  - `getNote`
  - `getTeamNote`
- Verify non-404 errors are surfaced (not converted to empty/placeholder values).

## Fault tolerance behavior
- If @Retry is present, tests should cover at least one transient failure path (e.g., first call fails, second succeeds),
  OR explicitly justify why retry is not expected to trigger.
- If @Timeout is present, ensure timeouts are bounded and do not hang tests.

## WireMock fixtures
- Any change under `src/test/resources/**` must state the scenario it represents (401/403/404/429/5xx).
