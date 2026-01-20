package io.github.yuokada.hackmd.quarkus;

import java.util.List;
import java.util.Optional;

import io.github.yuokada.hackmd.core.CreateNoteRequest;
import io.github.yuokada.hackmd.core.HackmdClient;
import io.github.yuokada.hackmd.core.Note;
import io.github.yuokada.hackmd.core.NoteSummary;
import io.github.yuokada.hackmd.core.Team;
import io.github.yuokada.hackmd.core.UpdateNoteRequest;
import io.github.yuokada.hackmd.core.UserProfile;

public class HackmdClientImpl implements HackmdClient {

  private final HackmdRestClient restClient;

  public HackmdClientImpl(HackmdRestClient restClient) {
    this.restClient = restClient;
  }

  @Override
  public List<NoteSummary> listNotes() {
    return restClient.listNotes();
  }

  @Override
  public Optional<Note> getNote(String noteId) {
    try {
      return Optional.ofNullable(restClient.getNote(noteId));
    } catch (RuntimeException e) {
      // In real SDK, map 404 to Optional.empty(), others to HackmdException.
      return Optional.empty();
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

  @Override
  public List<Team> listTeams() {
    return restClient.listTeams();
  }

  @Override
  public List<NoteSummary> listTeamNotes(String teamPath) {
    return restClient.listTeamNotes(teamPath);
  }

  @Override
  public Note createTeamNote(String teamPath, CreateNoteRequest request) {
    return restClient.createTeamNote(teamPath, request);
  }

  @Override
  public Optional<Note> getTeamNote(String teamPath, String noteId) {
    try {
      return Optional.ofNullable(restClient.getTeamNote(teamPath, noteId));
    } catch (RuntimeException e) {
      return Optional.empty();
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

  @Override
  public UserProfile getCurrentUser() {
    return restClient.getCurrentUser();
  }

  @Override
  public List<NoteSummary> getHistory(Integer limit) {
    return restClient.getHistory(limit);
  }
}
