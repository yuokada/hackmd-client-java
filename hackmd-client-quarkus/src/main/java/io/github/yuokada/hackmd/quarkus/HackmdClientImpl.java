package io.github.yuokada.hackmd.quarkus;

import java.util.List;
import java.util.Optional;

import io.github.yuokada.hackmd.core.CreateNoteRequest;
import io.github.yuokada.hackmd.core.HackmdClient;
import io.github.yuokada.hackmd.core.HackmdException;
import io.github.yuokada.hackmd.core.Note;
import io.github.yuokada.hackmd.core.NoteSummary;
import io.github.yuokada.hackmd.core.Team;
import io.github.yuokada.hackmd.core.UpdateNoteRequest;
import io.github.yuokada.hackmd.core.UserProfile;

public class HackmdClientImpl implements HackmdClient {

  private final HackmdRestClient restClient;
  private final HackmdRetryExecutor retryExecutor;

  public HackmdClientImpl(HackmdRestClient restClient, HackmdClientConfig config) {
    this.restClient = restClient;
    this.retryExecutor = new HackmdRetryExecutor(config);
  }

  @Override
  public List<NoteSummary> listNotes() {
    return retryExecutor.run(true, restClient::listNotes);
  }

  @Override
  public Optional<Note> getNote(String noteId) {
    return retryExecutor.run(true, () -> {
      try {
        return Optional.ofNullable(restClient.getNote(noteId));
      } catch (HackmdException e) {
        if (e.getStatusCode() == 404) {
          return Optional.empty();
        }
        throw e;
      }
    });
  }

  @Override
  public Note createNote(CreateNoteRequest request) {
    return retryExecutor.run(false, () -> restClient.createNote(request));
  }

  @Override
  public Note updateNote(String noteId, UpdateNoteRequest request) {
    return retryExecutor.run(false, () -> restClient.updateNote(noteId, request));
  }

  @Override
  public void deleteNote(String noteId) {
    retryExecutor.run(false, () -> {
      restClient.deleteNote(noteId);
      return null;
    });
  }

  @Override
  public List<Team> listTeams() {
    return retryExecutor.run(true, restClient::listTeams);
  }

  @Override
  public List<NoteSummary> listTeamNotes(String teamPath) {
    return retryExecutor.run(true, () -> restClient.listTeamNotes(teamPath));
  }

  @Override
  public Note createTeamNote(String teamPath, CreateNoteRequest request) {
    return retryExecutor.run(false, () -> restClient.createTeamNote(teamPath, request));
  }

  @Override
  public Optional<Note> getTeamNote(String teamPath, String noteId) {
    return retryExecutor.run(true, () -> {
      try {
        return Optional.ofNullable(restClient.getTeamNote(teamPath, noteId));
      } catch (HackmdException e) {
        if (e.getStatusCode() == 404) {
          return Optional.empty();
        }
        throw e;
      }
    });
  }

  @Override
  public Note updateTeamNote(String teamPath, String noteId, UpdateNoteRequest request) {
    return retryExecutor.run(false, () -> restClient.updateTeamNote(teamPath, noteId, request));
  }

  @Override
  public void deleteTeamNote(String teamPath, String noteId) {
    retryExecutor.run(false, () -> {
      restClient.deleteTeamNote(teamPath, noteId);
      return null;
    });
  }

  @Override
  public UserProfile getCurrentUser() {
    return retryExecutor.run(true, restClient::getCurrentUser);
  }

  @Override
  public List<NoteSummary> getHistory(Integer limit) {
    return retryExecutor.run(true, () -> restClient.getHistory(limit));
  }
}
