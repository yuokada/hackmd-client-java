package io.github.yuokada.hackmd.core;

import java.util.List;
import java.util.Optional;

/**
 * Minimal, transport-agnostic HackMD API client.
 *
 * <p>This module intentionally has no dependency on Quarkus or any specific HTTP client.
 */
public interface HackmdClient {

  List<NoteSummary> listNotes();

  Optional<Note> getNote(String noteId);

  Note createNote(CreateNoteRequest request);

  Note updateNote(String noteId, UpdateNoteRequest request);

  void deleteNote(String noteId);

  List<Team> listTeams();

  List<NoteSummary> listTeamNotes(String teamPath);

  Note createTeamNote(String teamPath, CreateNoteRequest request);

  Optional<Note> getTeamNote(String teamPath, String noteId);

  Note updateTeamNote(String teamPath, String noteId, UpdateNoteRequest request);

  void deleteTeamNote(String teamPath, String noteId);

  UserProfile getCurrentUser();

  List<NoteSummary> getHistory(Integer limit);
}
