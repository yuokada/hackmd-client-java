# HackMD API Client (Quarkus Rest Client) - Skeleton

This is a Maven multi-module skeleton for providing a **transport-agnostic core** and a **Quarkus implementation** based on MicroProfile Rest Client.

## Modules

- `hackmd-client-core`
  - Pure Java (JDK 17) API, DTOs, and exceptions.
  - No Quarkus dependency.

- `hackmd-client-quarkus`
  - Quarkus 3.20 implementation using `quarkus-rest-client-jackson`.
  - Adds a `ClientRequestFilter` that injects `Authorization: Bearer ...`.
  - Ships with `META-INF/beans.xml` so the produced `HackmdClient` bean can be discovered when this module is used as a CDI dependency in other Quarkus applications.

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

## WireMock Standalone JAR

The standalone JAR lets you run WireMock as an independent process — no Quarkus
dev service required. This is useful for recording stubs in a plain shell, or for
serving existing mappings while developing outside the test lifecycle.

### Download

```bash
curl -Lo wiremock-standalone-3.13.2.jar \
  https://repo1.maven.org/maven2/org/wiremock/wiremock-standalone/3.13.2/wiremock-standalone-3.13.2.jar
```

Or add it to your Maven wrapper cache:

```xml
<!-- fetch only; not added to any module's compile/runtime classpath -->
<dependency>
  <groupId>org.wiremock</groupId>
  <artifactId>wiremock-standalone</artifactId>
  <version>3.13.2</version>
</dependency>
```

### Serve existing stubs (playback)

Point `--root-dir` at the directory that contains `mappings/` and `__files/`:

```bash
java -jar wiremock-standalone-3.13.2.jar \
  --port 8080 \
  --root-dir hackmd-client-quarkus/src/test/resources
```

Then set the REST client base URL to `http://localhost:8080` and call the client
normally. WireMock returns whatever is recorded in the mapping files.

### Record new stubs from the real API

```bash
# 1. Start WireMock in proxy-and-record mode
java -jar wiremock-standalone-3.13.2.jar \
  --port 8080 \
  --root-dir hackmd-client-quarkus/src/test/resources \
  --proxy-all https://api.hackmd.io \
  --record-mappings

# 2. Point the client at WireMock and exercise the endpoints you want to capture
export HACKMD_API_URL=http://localhost:8080
# ... invoke hackmdClient methods or run the example app ...

# 3. Stop WireMock (Ctrl-C). Generated stubs appear in:
#      hackmd-client-quarkus/src/test/resources/mappings/
#      hackmd-client-quarkus/src/test/resources/__files/
```

Alternatively, trigger recording via the Admin API while WireMock is already
running (same approach as the Quarkus Dev Service section above):

```bash
curl -X POST "http://localhost:8080/__admin/recordings/start" \
  -H 'Content-Type: application/json' \
  -d '{
    "targetBaseUrl": "https://api.hackmd.io",
    "captureHeaders": {
      "Authorization": {"caseInsensitive": true},
      "User-Agent":    {"caseInsensitive": true}
    }
  }'

# ... send requests ...

curl -X POST "http://localhost:8080/__admin/recordings/stop"
```

### Useful flags

| Flag | Description |
|------|-------------|
| `--port <n>` | HTTP port (default 8080) |
| `--root-dir <path>` | Directory containing `mappings/` and `__files/` |
| `--proxy-all <url>` | Proxy unmatched requests to `<url>` |
| `--record-mappings` | Persist proxied requests as stub files |
| `--match-headers <header,...>` | Include these headers in generated request matchers |
| `--print-all-network-traffic` | Log every request/response to stdout |

See the full reference at <https://wiremock.org/docs/standalone/java-jar/>.

## Release

### Prerequisites

Ensure code formatting is clean before releasing.

```bash
# Auto-fix any formatting violations
./mvnw spotless:apply

# Commit the fixes
git add pom.xml '**/pom.xml'
git commit -m "fix: apply spotless formatting"
```

### Running a Release

```bash
# Clean up any leftover state from a previously failed release
./mvnw release:clean

# Prepare the release (prompts for version numbers)
./mvnw release:clean release:prepare

# Perform the release (builds and publishes artifacts)
./mvnw release:perform
```

### Troubleshooting

#### Release fails due to Spotless format violations

`release:prepare` internally forks a `clean verify` build, so any `-Dspotless.check.skip=true`
passed on the command line is not forwarded to the forked build.
Run `./mvnw spotless:apply` to fix formatting before releasing.
