package io.github.yuokada.hackmd.core;

import java.util.List;
import java.util.Optional;

/** Advanced HackMD API client that exposes response metadata. */
public interface HackmdMetadataClient {

  HackmdResult<List<NoteSummary>> listNotes();

  HackmdResult<Optional<Note>> getNote(String noteId);

  HackmdResult<Note> createNote(CreateNoteRequest request);

  HackmdResult<Note> updateNote(String noteId, UpdateNoteRequest request);

  HackmdResponseMetadata deleteNote(String noteId);

  HackmdResult<List<Team>> listTeams();

  HackmdResult<List<NoteSummary>> listTeamNotes(String teamPath);

  HackmdResult<Note> createTeamNote(String teamPath, CreateNoteRequest request);

  HackmdResult<Optional<Note>> getTeamNote(String teamPath, String noteId);

  HackmdResult<Note> updateTeamNote(String teamPath, String noteId, UpdateNoteRequest request);

  HackmdResponseMetadata deleteTeamNote(String teamPath, String noteId);

  HackmdResult<UserProfile> getCurrentUser();

  HackmdResult<List<NoteSummary>> getHistory(Integer limit);
}
