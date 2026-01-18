package io.github.yuokada.hackmd.quarkus;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.github.yuokada.hackmd.core.HackmdClient;
import io.quarkiverse.wiremock.devservice.ConnectWireMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
@ConnectWireMock
class HackmdClientWireMockTest {

  @Inject HackmdClient hackmdClient;

  WireMock wiremock;

  @Test
  void listNotesUsesWireMockStub() {
    var noteResponse =
        """
            [
              {"id":"demo","title":"WireMock note","tags":["wiremock","test"]}
            ]
            """;

    wiremock.register(
        get(urlEqualTo("/v1/notes"))
            .willReturn(
                aResponse().withHeader("Content-Type", "application/json").withBody(noteResponse)));

    var notes = hackmdClient.listNotes();

    Assertions.assertEquals(1, notes.size());
    var first = notes.get(0);
    Assertions.assertEquals("demo", first.id());
    Assertions.assertEquals("WireMock note", first.title());
    Assertions.assertEquals(List.of("wiremock", "test"), first.tags());

    verify(
        getRequestedFor(urlEqualTo("/v1/notes"))
            .withHeader("Authorization", equalTo("Bearer test-token"))
            .withHeader("User-Agent", equalTo("hackmd-client-tests")));
  }
}
