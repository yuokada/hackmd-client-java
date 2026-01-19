package io.github.yuokada.hackmd.quarkus;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import io.github.yuokada.hackmd.core.HackmdClient;
import io.quarkiverse.wiremock.devservice.ConnectWireMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.util.List;
import org.junit.jupiter.api.Test;

@QuarkusTest
@ConnectWireMock
class HackmdClientWireMockTest {

  @Inject HackmdClient hackmdClient;

  @Test
  void listNotesUsesRecordedWireMockStub() {
    var notes = hackmdClient.listNotes();

    assertFalse(notes.isEmpty());
    var first = notes.get(0);
    assertEquals("demo-note-001", first.id());
    assertEquals("Sample onboarding checklist", first.title());
    assertEquals(List.of("sample", "onboarding"), first.tags());

    verify(
        getRequestedFor(urlEqualTo("/v1/notes"))
            .withHeader("Authorization", equalTo("Bearer test-token"))
            .withHeader("User-Agent", equalTo("hackmd-client-tests")));
  }
}
