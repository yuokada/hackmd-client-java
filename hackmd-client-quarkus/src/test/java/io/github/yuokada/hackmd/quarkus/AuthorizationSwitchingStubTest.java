package io.github.yuokada.hackmd.quarkus;

import jakarta.inject.Inject;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.quarkiverse.wiremock.devservice.ConnectWireMock;
import io.quarkus.test.junit.QuarkusTest;

import io.github.yuokada.hackmd.core.HackmdClient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@ConnectWireMock
public class AuthorizationSwitchingStubTest {

  @Inject HackmdClient hackmdClient;

  WireMock wiremock;

  @BeforeEach
  public void beforeEach() {
    // wiremock = new WireMock("localhost", 8080); // Adjust host and port as necessary
    wiremock.register(
        get(urlEqualTo("/me"))
            .withHeader("Authorization", equalTo("Bearer test-token"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("me_ok.json")));

    wiremock.register(
        get(urlEqualTo("/me"))
            .withHeader("Authorization", equalTo("Bearer bad"))
            .willReturn(
                aResponse()
                    .withStatus(400)
                    .withHeader("content-language", "dev")
                    .withBody("Bad Request")));

    wiremock.register(
        get(urlEqualTo("/me"))
            .atPriority(10) // フォールバック
            .willReturn(aResponse().withStatus(401)));

    // List notes stub
    wiremock.register(
        get(urlEqualTo("/v1/notes"))
            .withHeader("Authorization", equalTo("Bearer test-token"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("notes_list_ok.json")));
    // Fallback for list notes
    wiremock.register(
        get(urlEqualTo("/v1/notes")).atPriority(10).willReturn(aResponse().withStatus(401)));
  }

  @Test
  void stub_should_switch_response_by_authorization_header() {
    var notes = hackmdClient.listNotes();
    assertTrue(!notes.isEmpty());
    assertEquals("demo-note-001", notes.get(0).id());
  }
}
