package io.github.yuokada.hackmd.quarkus;

import java.util.List;
import java.util.Optional;

import io.github.yuokada.hackmd.core.CreateNoteRequest;
import io.github.yuokada.hackmd.core.HackmdException;
import io.github.yuokada.hackmd.core.HackmdMetadataClient;
import io.github.yuokada.hackmd.core.HackmdResponseMetadata;
import io.github.yuokada.hackmd.core.HackmdResult;
import io.github.yuokada.hackmd.core.Note;
import io.github.yuokada.hackmd.core.NoteSummary;
import io.github.yuokada.hackmd.core.Team;
import io.github.yuokada.hackmd.core.UpdateNoteRequest;
import io.github.yuokada.hackmd.core.UserProfile;

public class HackmdMetadataClientImpl implements HackmdMetadataClient {

  private final HackmdRestClient restClient;
  private final HackmdRetryExecutor retryExecutor;

  public HackmdMetadataClientImpl(HackmdRestClient restClient, HackmdClientConfig config) {
    this.restClient = restClient;
    this.retryExecutor = new HackmdRetryExecutor(config);
  }

  @Override
  public HackmdResult<List<NoteSummary>> listNotes() {
    var value = retryExecutor.run(true, restClient::listNotes);
    return new HackmdResult<>(value, metadata());
  }

  @Override
  public HackmdResult<Optional<Note>> getNote(String noteId) {
    Optional<Note> value = retryExecutor.run(true, () -> {
      try {
        return Optional.ofNullable(restClient.getNote(noteId));
      } catch (HackmdException e) {
        if (e.getStatusCode() == 404) {
          return Optional.empty();
        }
        throw e;
      }
    });
    return new HackmdResult<>(value, metadata());
  }

  @Override
  public HackmdResult<Note> createNote(CreateNoteRequest request) {
    var value = retryExecutor.run(false, () -> restClient.createNote(request));
    return new HackmdResult<>(value, metadata());
  }

  @Override
  public HackmdResult<Note> updateNote(String noteId, UpdateNoteRequest request) {
    var value = retryExecutor.run(false, () -> restClient.updateNote(noteId, request));
    return new HackmdResult<>(value, metadata());
  }

  @Override
  public HackmdResponseMetadata deleteNote(String noteId) {
    retryExecutor.run(false, () -> {
      restClient.deleteNote(noteId);
      return null;
    });
    return metadata();
  }

  @Override
  public HackmdResult<List<Team>> listTeams() {
    var value = retryExecutor.run(true, restClient::listTeams);
    return new HackmdResult<>(value, metadata());
  }

  @Override
  public HackmdResult<List<NoteSummary>> listTeamNotes(String teamPath) {
    var value = retryExecutor.run(true, () -> restClient.listTeamNotes(teamPath));
    return new HackmdResult<>(value, metadata());
  }

  @Override
  public HackmdResult<Note> createTeamNote(String teamPath, CreateNoteRequest request) {
    var value = retryExecutor.run(false, () -> restClient.createTeamNote(teamPath, request));
    return new HackmdResult<>(value, metadata());
  }

  @Override
  public HackmdResult<Optional<Note>> getTeamNote(String teamPath, String noteId) {
    Optional<Note> value = retryExecutor.run(true, () -> {
      try {
        return Optional.ofNullable(restClient.getTeamNote(teamPath, noteId));
      } catch (HackmdException e) {
        if (e.getStatusCode() == 404) {
          return Optional.empty();
        }
        throw e;
      }
    });
    return new HackmdResult<>(value, metadata());
  }

  @Override
  public HackmdResult<Note> updateTeamNote(
      String teamPath, String noteId, UpdateNoteRequest request) {
    var value =
        retryExecutor.run(false, () -> restClient.updateTeamNote(teamPath, noteId, request));
    return new HackmdResult<>(value, metadata());
  }

  @Override
  public HackmdResponseMetadata deleteTeamNote(String teamPath, String noteId) {
    retryExecutor.run(false, () -> {
      restClient.deleteTeamNote(teamPath, noteId);
      return null;
    });
    return metadata();
  }

  @Override
  public HackmdResult<UserProfile> getCurrentUser() {
    var value = retryExecutor.run(true, restClient::getCurrentUser);
    return new HackmdResult<>(value, metadata());
  }

  @Override
  public HackmdResult<List<NoteSummary>> getHistory(Integer limit) {
    var value = retryExecutor.run(true, () -> restClient.getHistory(limit));
    return new HackmdResult<>(value, metadata());
  }

  private HackmdResponseMetadata metadata() {
    return HackmdResponseMetadataFilter.consume();
  }
}
