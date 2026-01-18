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

## WireMock Recording Steps

You can generate WireMock stubs directly from the real HackMD API by using the Quarkus WireMock Dev Service. The workflow below follows the official documentation (<https://docs.quarkiverse.io/quarkus-wiremock/dev/index.html>).

1. **Start the WireMock Dev Service**
   - When you run `./mvnw quarkus:dev` or `./mvnw test` with the `quarkus-wiremock` dependency on the classpath, WireMock starts automatically.
   - Note the value of `quarkus.wiremock.devservices.port` (random if unspecified) and reuse that port for the HTTP calls below. You may also fix the port in `application.properties`.

2. **Begin a recording session**
   - Trigger the WireMock Admin API to start recording. Targeting the real HackMD API would look like this:
     ```bash
     curl -X POST "http://localhost:${WIREMOCK_PORT}/__admin/recordings/start" \
       -H 'Content-Type: application/json' \
       -d '{
         "targetBaseUrl": "https://api.hackmd.io",
         "captureHeaders": {"Authorization": {"caseInsensitive": true}, "User-Agent": {"caseInsensitive": true}}
       }'
     ```
   - Set `targetBaseUrl` to the real service you want to record and optionally list headers under `captureHeaders` that should be persisted.

3. **Send real requests via the client**
   - Invoke `hackmdClient` (or run the relevant tests) as usual. WireMock proxies the requests to HackMD, capturing the responses and matchers on the fly.

4. **Stop the session and extract stubs**
   - Stop recording:
     ```bash
     curl -X POST "http://localhost:${WIREMOCK_PORT}/__admin/recordings/stop"
     ```
   - The returned JSON lists every generated stub. Save the needed ones under `src/test/resources/mappings` and `__files`, which Quarkus WireMock reads by default.

5. **Run tests with the saved stubs**
   - With `quarkus.wiremock.devservices.files-mapping=src/test/resources` (default), you can simply run `./mvnw test` and WireMock will return the recorded responses.

See the WireMock record/playback reference (<https://wiremock.org/docs/record-playback/>) for more details and advanced options.
