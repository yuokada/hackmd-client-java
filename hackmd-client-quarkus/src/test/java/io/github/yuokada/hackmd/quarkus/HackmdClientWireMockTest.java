package io.github.yuokada.hackmd.quarkus;

import java.util.List;
import jakarta.inject.Inject;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.quarkiverse.wiremock.devservice.ConnectWireMock;
import io.quarkus.test.junit.QuarkusTest;

import io.github.yuokada.hackmd.core.HackmdClient;
import io.github.yuokada.hackmd.core.Team;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@ConnectWireMock
class HackmdClientWireMockTest {

  @Inject HackmdClient hackmdClient;

  WireMock wiremock;

  @BeforeEach
  public void beforeEach() {
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

    wiremock.register(
        get(urlEqualTo("/v1/notes/demo-note-001"))
            .withHeader("Authorization", equalTo("Bearer test-token"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("demo-note-001.json")));

    // Fallback for list notes
    wiremock.register(
        get(urlEqualTo("/v1/notes/note-not-found"))
            .withHeader("Authorization", equalTo("Bearer test-token"))
            .atPriority(10)
            .willReturn(aResponse().withStatus(404)));

    /*
     * Teams API stubs can be added here similarly
     */
    wiremock.register(
        get(urlEqualTo("/v1/teams"))
            .withHeader("Authorization", equalTo("Bearer test-token"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("teams_list_ok.json")));
  }

  @Test
  @DisplayName("lists notes via WireMock stub")
  void listNotes_is_stubbed_via_wiremock() {
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

  @Test
  @DisplayName("fetches note details via WireMock")
  void getNotesUsesRecordedWireMockStub() {
    var note = hackmdClient.getNote("demo-note-001");

    assertTrue(note.isPresent());
    var first = note.get();
    assertEquals("demo-note-001", first.id());
    assertEquals("Sample onboarding checklist", first.title());
    assertEquals(List.of("sample", "onboarding"), first.tags());

    verify(
        getRequestedFor(urlEqualTo("/v1/notes/demo-note-001"))
            .withHeader("Authorization", equalTo("Bearer test-token"))
            .withHeader("User-Agent", equalTo("hackmd-client-tests")));
  }

  @Test
  @DisplayName("missing note results in Optional.empty()")
  void getNotFoundNoteUsesRecordedWireMockStub() {
    var note = hackmdClient.getNote("note-not-found");
    assertTrue(note.isEmpty());

    verify(
        getRequestedFor(urlEqualTo("/v1/notes/note-not-found"))
            .withHeader("Authorization", equalTo("Bearer test-token"))
            .withHeader("User-Agent", equalTo("hackmd-client-tests")));
  }

  // Teams API test example
  @Test
  @DisplayName("lists teams via WireMock stub")
  void listTeams_is_stubbed_via_wiremock() {
    List<Team> teams = hackmdClient.listTeams();
    assertFalse(teams.isEmpty());

    var firstTeam = teams.get(0);
    assertEquals("e9ed1dcd-830f-435c-9fe2-d53d5f191666", firstTeam.id());
    // TODO: implement listTeams() method in HackmdClient
  }
}
