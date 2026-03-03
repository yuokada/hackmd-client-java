---
applyTo:
  - "hackmd-client-quarkus/src/main/java/**"
  - "hackmd-client-quarkus/src/main/resources/**"
---

# Quarkus module review rules (HackmdClientImpl + REST Client + Fault Tolerance)

## Target classes
- Pay special attention to:
  - `io.github.yuokada.hackmd.quarkus.HackmdClientImpl`
  - `HackmdRestClient` (MicroProfile REST Client interface)

## Fault tolerance policy (MUST enforce)
- All methods in `HackmdClientImpl` that call `restClient.*` MUST have a bounded timeout.
- Retry policy must be consistent and intentional:
  - Do NOT retry on client errors (typically 4xx) unless explicitly justified (e.g., 429 with backoff).
  - Writes (create/update/delete) MUST NOT be retried by default (idempotency risk). If retry is added, reviewer must require an idempotency strategy or proof it is safe.

## Review triggers (FLAG)
- `@Retry(... abortOn = HackmdException.class)`:
  - Flag and ask: is the intention to retry on HackMD API failures? If yes, this setting likely prevents it.
- Any new `@Retry` without clear justification of which failures are transient.
- Any `@Timeout` that is increased without explaining impact on thread usage and UX.

## HTTP error mapping
- 404 -> Optional.empty() pattern in `getNote/getTeamNote` is OK.
- For other statuses (401/403/429/5xx), ensure behavior is explicit:
  - no silent fallback
  - errors surfaced as `HackmdException` (or well-defined domain error)

## Public API cleanliness
- Do not leak Quarkus/MicroProfile/Jackson implementation types in public APIs (keep core types only).
