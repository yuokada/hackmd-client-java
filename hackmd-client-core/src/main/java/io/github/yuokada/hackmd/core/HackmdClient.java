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
}
