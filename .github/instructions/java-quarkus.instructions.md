---
applyTo:
  - "hackmd-client-quarkus/src/main/java/**"
  - "hackmd-client-quarkus/src/main/resources/**"
---

# Quarkus module review rules (DI + REST Client + Fault Tolerance)

## Dependency / layering
- Keep `hackmd-client-core` free from Quarkus/Jackson-databind concerns.
- Quarkus module may use REST Client + Jackson for (de)serialization, but do not leak Quarkus/Jackson impl types into public APIs.

## CDI / configuration
- Prefer CDI-managed beans (quarkus-arc). Avoid manual singletons/static state.
- Configuration must be externalized (application.properties/yaml). No hard-coded endpoints/tokens.
- Any new config key: ensure naming is consistent and document defaults.

## REST client correctness
- Validate request/response mapping: headers, auth, status code handling, error bodies.
- No silent fallback on non-2xx responses. Ensure errors are surfaced as clear exceptions/results.

## Fault tolerance (smallrye)
- If adding @Retry/@Timeout/@CircuitBreaker/@Fallback:
  - Avoid retrying non-transient errors (e.g., 4xx).
  - Avoid "retry + client retry" double-retry.
  - Ensure timeouts are bounded and consistent with downstream SLAs.
  - Fallback must not hide failures unless explicitly intended and documented.
