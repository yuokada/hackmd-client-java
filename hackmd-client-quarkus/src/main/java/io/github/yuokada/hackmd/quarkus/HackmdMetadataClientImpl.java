package io.github.yuokada.hackmd.quarkus;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

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
    return withMetadata(() -> retryExecutor.run(true, restClient::listNotes));
  }

  @Override
  public HackmdResult<Optional<Note>> getNote(String noteId) {
    return withMetadata(() -> retryExecutor.run(true, () -> {
      try {
        return Optional.ofNullable(restClient.getNote(noteId));
      } catch (HackmdException e) {
        if (e.getStatusCode() == 404) {
          return Optional.empty();
        }
        throw e;
      }
    }));
  }

  @Override
  public HackmdResult<Note> createNote(CreateNoteRequest request) {
    return withMetadata(() -> retryExecutor.run(false, () -> restClient.createNote(request)));
  }

  @Override
  public HackmdResult<Note> updateNote(String noteId, UpdateNoteRequest request) {
    return withMetadata(
        () -> retryExecutor.run(false, () -> restClient.updateNote(noteId, request)));
  }

  @Override
  public HackmdResponseMetadata deleteNote(String noteId) {
    return withMetadataOnly(() -> retryExecutor.run(false, () -> {
      restClient.deleteNote(noteId);
      return null;
    }));
  }

  @Override
  public HackmdResult<List<Team>> listTeams() {
    return withMetadata(() -> retryExecutor.run(true, restClient::listTeams));
  }

  @Override
  public HackmdResult<List<NoteSummary>> listTeamNotes(String teamPath) {
    return withMetadata(() -> retryExecutor.run(true, () -> restClient.listTeamNotes(teamPath)));
  }

  @Override
  public HackmdResult<Note> createTeamNote(String teamPath, CreateNoteRequest request) {
    return withMetadata(
        () -> retryExecutor.run(false, () -> restClient.createTeamNote(teamPath, request)));
  }

  @Override
  public HackmdResult<Optional<Note>> getTeamNote(String teamPath, String noteId) {
    return withMetadata(() -> retryExecutor.run(true, () -> {
      try {
        return Optional.ofNullable(restClient.getTeamNote(teamPath, noteId));
      } catch (HackmdException e) {
        if (e.getStatusCode() == 404) {
          return Optional.empty();
        }
        throw e;
      }
    }));
  }

  @Override
  public HackmdResult<Note> updateTeamNote(
      String teamPath, String noteId, UpdateNoteRequest request) {
    return withMetadata(
        () -> retryExecutor.run(false, () -> restClient.updateTeamNote(teamPath, noteId, request)));
  }

  @Override
  public HackmdResponseMetadata deleteTeamNote(String teamPath, String noteId) {
    return withMetadataOnly(() -> retryExecutor.run(false, () -> {
      restClient.deleteTeamNote(teamPath, noteId);
      return null;
    }));
  }

  @Override
  public HackmdResult<UserProfile> getCurrentUser() {
    return withMetadata(() -> retryExecutor.run(true, restClient::getCurrentUser));
  }

  @Override
  public HackmdResult<List<NoteSummary>> getHistory(Integer limit) {
    return withMetadata(() -> retryExecutor.run(true, () -> restClient.getHistory(limit)));
  }

  private <T> HackmdResult<T> withMetadata(Supplier<T> supplier) {
    try {
      return new HackmdResult<>(supplier.get(), HackmdResponseMetadataFilter.consume());
    } finally {
      HackmdResponseMetadataFilter.consume();
    }
  }

  private HackmdResponseMetadata withMetadataOnly(Supplier<Void> action) {
    try {
      action.get();
      return HackmdResponseMetadataFilter.consume();
    } finally {
      HackmdResponseMetadataFilter.consume();
    }
  }
}
