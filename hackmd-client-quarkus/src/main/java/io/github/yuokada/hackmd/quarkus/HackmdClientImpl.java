package io.github.yuokada.hackmd.quarkus;

import java.util.List;
import java.util.Optional;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import io.github.yuokada.hackmd.core.CreateNoteRequest;
import io.github.yuokada.hackmd.core.HackmdClient;
import io.github.yuokada.hackmd.core.HackmdException;
import io.github.yuokada.hackmd.core.Note;
import io.github.yuokada.hackmd.core.NoteSummary;
import io.github.yuokada.hackmd.core.Team;
import io.github.yuokada.hackmd.core.UpdateNoteRequest;
import io.github.yuokada.hackmd.core.UserProfile;

public class HackmdClientImpl implements HackmdClient {

  private static final int FT_MAX_RETRIES = 3;
  private static final long FT_DELAY_MS = 500;
  private static final long FT_TIMEOUT_MS = 5000;

  private final HackmdRestClient restClient;

  public HackmdClientImpl(HackmdRestClient restClient) {
    this.restClient = restClient;
  }

  @Retry(maxRetries = FT_MAX_RETRIES, delay = FT_DELAY_MS)
  @Timeout(FT_TIMEOUT_MS)
  @Override
  public List<NoteSummary> listNotes() {
    return restClient.listNotes();
  }

  @Retry(maxRetries = FT_MAX_RETRIES, delay = FT_DELAY_MS)
  @Timeout(FT_TIMEOUT_MS)
  @Override
  public Optional<Note> getNote(String noteId) {
    try {
      return Optional.ofNullable(restClient.getNote(noteId));
    } catch (HackmdException e) {
      if (e.getStatusCode() == 404) {
        return Optional.empty();
      }
      throw e;
    }
  }

  @Override
  public Note createNote(CreateNoteRequest request) {
    return restClient.createNote(request);
  }

  @Override
  public Note updateNote(String noteId, UpdateNoteRequest request) {
    return restClient.updateNote(noteId, request);
  }

  @Override
  public void deleteNote(String noteId) {
    restClient.deleteNote(noteId);
  }

  @Retry(maxRetries = FT_MAX_RETRIES, delay = FT_DELAY_MS)
  @Timeout(FT_TIMEOUT_MS)
  @Override
  public List<Team> listTeams() {
    return restClient.listTeams();
  }

  @Retry(maxRetries = FT_MAX_RETRIES, delay = FT_DELAY_MS)
  @Timeout(FT_TIMEOUT_MS)
  @Override
  public List<NoteSummary> listTeamNotes(String teamPath) {
    return restClient.listTeamNotes(teamPath);
  }

  @Override
  public Note createTeamNote(String teamPath, CreateNoteRequest request) {
    return restClient.createTeamNote(teamPath, request);
  }

  @Retry(maxRetries = FT_MAX_RETRIES, delay = FT_DELAY_MS)
  @Timeout(FT_TIMEOUT_MS)
  @Override
  public Optional<Note> getTeamNote(String teamPath, String noteId) {
    try {
      return Optional.ofNullable(restClient.getTeamNote(teamPath, noteId));
    } catch (HackmdException e) {
      if (e.getStatusCode() == 404) {
        return Optional.empty();
      }
      throw e;
    }
  }

  @Override
  public Note updateTeamNote(String teamPath, String noteId, UpdateNoteRequest request) {
    return restClient.updateTeamNote(teamPath, noteId, request);
  }

  @Override
  public void deleteTeamNote(String teamPath, String noteId) {
    restClient.deleteTeamNote(teamPath, noteId);
  }

  @Retry(maxRetries = FT_MAX_RETRIES, delay = FT_DELAY_MS)
  @Timeout(FT_TIMEOUT_MS)
  @Override
  public UserProfile getCurrentUser() {
    return restClient.getCurrentUser();
  }

  @Retry(maxRetries = FT_MAX_RETRIES, delay = FT_DELAY_MS)
  @Timeout(FT_TIMEOUT_MS)
  @Override
  public List<NoteSummary> getHistory(Integer limit) {
    return restClient.getHistory(limit);
  }
}
