# HackMD API Client (Quarkus Rest Client) - Skeleton

This is a Maven multi-module skeleton for providing a **transport-agnostic core** and a **Quarkus implementation** based on MicroProfile Rest Client.

## Modules

- `hackmd-client-core`
  - Pure Java (JDK 17) API, DTOs, and exceptions.
  - No Quarkus dependency.

- `hackmd-client-quarkus`
  - Quarkus 3.20 implementation using `quarkus-rest-client-jackson`.
  - Adds a `ClientRequestFilter` that injects `Authorization: Bearer ...`.

## Build

```bash
mvn -q -DskipTests=false test
```

## Use from a Quarkus app

### 1) Add dependency

```xml
<dependency>
  <groupId>io.github.yuokada</groupId>
  <artifactId>hackmd-client-quarkus</artifactId>
  <version>0.1.0-SNAPSHOT</version>
</dependency>
```

### 2) Configure

```properties
# Base URL without /v1 (the client adds /v1)
quarkus.rest-client.hackmd.url=https://api.hackmd.io

# Your HackMD API token
hackmd.token=REPLACE_ME

# Optional
hackmd.user-agent=my-app/1.0
```

### 3) Inject and call

```java
import io.github.yuokada.hackmd.core.HackmdClient;
import jakarta.inject.Inject;

class MyService {
  @Inject HackmdClient hackmdClient;

  void run() {
    var notes = hackmdClient.listNotes();
    System.out.println(notes.size());
  }
}
```

## Notes

- Endpoint mapping in this skeleton targets common v1 endpoints (e.g. `GET /v1/notes`).
- Extend DTOs and add error mapping (e.g. map 404 to `Optional.empty()`) as needed.
