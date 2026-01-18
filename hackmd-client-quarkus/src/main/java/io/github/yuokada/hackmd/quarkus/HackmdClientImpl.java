package io.github.yuokada.hackmd.quarkus;

import io.github.yuokada.hackmd.core.CreateNoteRequest;
import io.github.yuokada.hackmd.core.HackmdClient;
import io.github.yuokada.hackmd.core.Note;
import io.github.yuokada.hackmd.core.NoteSummary;
import java.util.List;
import java.util.Optional;

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
}
