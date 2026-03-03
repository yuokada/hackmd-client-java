# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Maven multi-module Java 17 project implementing a **transport-agnostic HackMD API client** with a Quarkus MicroProfile Rest Client backend. The core module has no Quarkus dependency; the Quarkus module adapts it.

## Build & Test Commands

```bash
# Run all tests
./mvnw -q -DskipTests=false test

# Run a single test class
./mvnw test -pl hackmd-client-quarkus -Dtest=HackmdClientWireMockTest

# Run a single test method
./mvnw test -pl hackmd-client-quarkus -Dtest=HackmdClientWireMockTest#listNotes_is_stubbed_via_wiremock

# Check code formatting (Google Java Format)
./mvnw spotless:check

# Apply code formatting
./mvnw spotless:apply

# Run example app in dev mode
./mvnw -pl hackmd-client-example quarkus:dev
```

## Module Architecture

```
hackmd-client-core        Pure Java — HackmdClient interface, DTOs (records), enums, HackmdException
hackmd-client-quarkus     Quarkus implementation — REST client, CDI producer, auth filter, error mapper
hackmd-client-example     PicoCLI demo app showing full note lifecycle
```

### Key Class Relationships

- **`HackmdClient`** (core) — transport-agnostic interface with 14 methods covering notes, team notes, teams, and user profile.
- **`HackmdClientImpl`** (quarkus) — implements `HackmdClient`; delegates to `HackmdRestClient` and converts HTTP 404 responses to `Optional.empty()`.
- **`HackmdRestClient`** (quarkus) — JAX-RS interface annotated with `@RegisterRestClient(configKey="hackmd")`, `@RegisterProvider(HackmdBearerAuthFilter)`, and `@RegisterProvider(HackmdErrorResponseMapper)`.
- **`HackmdBearerAuthFilter`** — `ClientRequestFilter` that injects `Authorization: Bearer {token}` and `User-Agent` headers from `HackmdClientConfig`.
- **`HackmdErrorResponseMapper`** — `ResponseExceptionMapper` mapping HTTP 400+ to `HackmdException`.
- **`HackmdClientProducer`** — CDI `@Produces @ApplicationScoped` bean; the entry point for injection in downstream apps.

## Configuration

The required properties for `hackmd-client-quarkus`:

```properties
quarkus.rest-client.hackmd.url=https://api.hackmd.io   # base URL, no /v1 suffix
hackmd.token=REPLACE_ME
hackmd.user-agent=my-app/1.0                            # optional
```

Test properties live in `hackmd-client-quarkus/src/test/resources/application.properties` and point to the WireMock dev service.

## Testing Infrastructure

Tests use **Quarkus WireMock Dev Service** (`quarkus-wiremock`). Stub mappings are in `hackmd-client-quarkus/src/test/resources/mappings/` and response bodies in `__files/`. The two test classes are:

- `HackmdClientWireMockTest` — happy-path tests for list/get operations; verifies the `Authorization` header.
- `AuthorizationSwitchingStubTest` — verifies behavior when the token changes between requests.

### Recording New WireMock Stubs

1. Start `./mvnw quarkus:dev` (WireMock starts automatically).
2. Begin recording: `POST http://localhost:${WIREMOCK_PORT}/__admin/recordings/start` with `targetBaseUrl=https://api.hackmd.io`.
3. Exercise the client.
4. Stop recording: `POST http://localhost:${WIREMOCK_PORT}/__admin/recordings/stop`.
5. Save generated stubs to `src/test/resources/mappings/` and `__files/`.

## DTOs

All DTOs in `hackmd-client-core` are Java **records**. Key types:

| Record | Purpose |
|---|---|
| `Note` | Full note detail (18 fields incl. permissions, URLs, timestamps) |
| `NoteSummary` | Lightweight list item |
| `Team` | Team metadata |
| `UserProfile` | Current user + embedded teams list |
| `CreateNoteRequest` / `UpdateNoteRequest` | Mutation payloads |

Enums (`NotePublishType`, `NotePermissionRole`, `TeamVisibility`) use `@JsonProperty` for lowercase JSON mapping.

## Workflow Orchestration

### 1. Plan Mode Default

- Enter plan mode for ANY non-trivial task (3+ steps or architectural decisions)
- If something goes sideways, STOP and re-plan immediately — don't keep pushing
- Use plan mode for verification steps, not just building
- Write detailed specs upfront to reduce ambiguity
- For complex tasks, separate roles:
  - Plan Author
  - Staff Engineer Reviewer
- Do not begin implementation until Plan Review passes

### 2. Subagent Strategy

- Use subagents liberally to keep main context window clean
- Offload research, exploration, and parallel analysis to subagents
- For complex problems, throw more compute at it via subagents
- One task per subagent for focused execution
- For large changes, use parallel sessions (3–5) or git worktrees
- Keep execution contexts isolated

### 3. Self-Improvement Loop
- After ANY correction from the user: update `tasks/lessons.md` with the pattern
- If the pattern is systemic, update this CLAUDE.md
- Write rules for yourself that prevent the same mistake
- Ruthlessly iterate on these lessons until mistake rate drops
- Review lessons at session start for relevant project

### 4. Verification Before Done

- Never mark a task complete without proving it works
- Diff behavior between main and your changes when relevant
- Ask yourself: "Would a staff engineer approve this?"
- Run tests, check logs, demonstrate correctness
- When fixing bugs, require:
  - Logs
  - Failing test output
  - Clear reproduction steps
- Validate against CI expectations before marking done

### 5. Demand Elegance

- For non-trivial changes: pause and ask “Is there a more elegant way?”
- If a fix feels hacky: “Knowing everything I know now, implement the elegant solution.”
- Skip this for simple, obvious fixes — don’t over-engineer
- Challenge your own work before presenting it

### 6. Autonomous Bug Fixing

- When given a bug report: just fix it.
- Point at logs, errors, failing tests — then resolve them
- Do not require hand-holding
- Assume ownership of full resolution path:
  - Root cause
  - Fix
  - Validation
- Require zero context switching from the user
- Fix failing CI tests proactively

### 7. Operational Discipline
- Automate formatting/linting after modifications when environment allows
- Maintain reusable slash-commands for frequent workflows
- Version-control command templates under `.claude/commands/`
- Pre-approve safe commands when permissions model supports it
- Optimize for minimal context switching

---

## Task Management

1. **Plan First**: Write the plan to `tasks/todo.md` with checkable items
2. **Verify Plan**: Check in before starting implementation
3. **Track Progress**: Mark items complete as you go
4. **Explain Changes**: Provide a high-level summary at each step
5. **Document Results**: Add a review section to `tasks/todo.md`
6. **Capture Lessons**:
    - Update `tasks/lessons.md` after corrections
   - Escalate recurring issues into CLAUDE.md rules

---

## Core Principles

- **Simplicity First**: Make every change as simple as possible. Impact minimal code.
- **No Laziness**: Find root causes. No temporary fixes. Maintain senior developer standards.
- **Minimal Impact**: Changes should only touch what’s necessary. Avoid introducing bugs.
- **Systemic Thinking**: Fix classes of problems, not isolated instances
- **Parallelize Intelligently**: Use additional compute or sessions when complexity demands it
