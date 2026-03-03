---
applyTo:
  - "hackmd-client-core/src/main/java/**"
---

# Core module review rules (Java 17)

## Dependency boundaries (IMPORTANT)
- Production code MUST NOT depend on `com.fasterxml.jackson.databind.*` (jackson-databind is test-only).
  - Flag any use of ObjectMapper / JsonNode / databind types in `src/main/java`.

## API & compatibility
- Public/protected API changes: call out breaking changes (signature, behavior, exceptions, nullability).
- Do not leak Jackson implementation types in public APIs.

## Data model
- Prefer immutable models (`record` where it improves clarity).
- Keep Jackson annotations minimal; avoid over-annotating.
- Make null/Optional contracts explicit and consistent.

## Error handling
- No swallowed exceptions.
- IO/HTTP-related failures must be mapped to clear domain exceptions or error results.
