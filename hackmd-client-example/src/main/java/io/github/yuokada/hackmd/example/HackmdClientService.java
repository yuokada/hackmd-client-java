package io.github.yuokada.hackmd.example;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import picocli.CommandLine.Command;
import io.quarkus.picocli.runtime.annotations.TopCommand;

import io.github.yuokada.hackmd.core.CreateNoteRequest;
import io.github.yuokada.hackmd.core.HackmdClient;
import io.github.yuokada.hackmd.core.Note;
import io.github.yuokada.hackmd.core.NoteCommentPermission;
import io.github.yuokada.hackmd.core.NotePermissionRole;
import io.github.yuokada.hackmd.core.NoteSummary;
import io.github.yuokada.hackmd.core.UpdateNoteRequest;

@TopCommand
@ApplicationScoped
@Command(name = "hackmd-client", mixinStandardHelpOptions = true)
public class HackmdClientService implements Runnable {

  @Inject HackmdClient hackmdClient;

  @Override
  public void run() {
    var notes = hackmdClient.listNotes();

    if (notes.isEmpty()) {
      System.out.println("No notes returned. Check your token or create a note first.");
      return;
    }

    System.out.printf("Fetched %d notes:%n", notes.size());
    notes.stream().sorted(Comparator.comparing(NoteSummary::title)).limit(5).forEach(this::print);

    demoLifecycle();
  }

  private void print(NoteSummary note) {
    System.out.printf("- %s (%s) tags=%s%n", note.title(), note.id(), note.tags());
  }

  private void demoLifecycle() {
    System.out.println("\n--- Demo: create/update/delete note ---");

    var request =
        new CreateNoteRequest(
            "CLI demo note " + System.currentTimeMillis(),
            "Initial content created from hackmd-client-example.",
            NotePermissionRole.GUEST,
            NotePermissionRole.OWNER,
            NoteCommentPermission.OWNERS,
            null,
            List.of("demo", "cli"));

    Note created = null;
    try {
      created = hackmdClient.createNote(request);
      System.out.printf("Created note %s with id=%s%n", created.title(), created.id());

      var updated =
          hackmdClient.updateNote(
              created.id(), new UpdateNoteRequest(null, null, null, null, "Updated content"));
      var content = Optional.ofNullable(updated.content()).orElse("");
      System.out.printf("Updated note %s (content length=%d)%n", updated.id(), content.length());

      Optional<Note> fetched = hackmdClient.getNote(created.id());
      System.out.printf(
          "Fetched updated note: %s%n", fetched.map(Note::content).orElse("<missing>"));
    } finally {
      if (created != null) {
        hackmdClient.deleteNote(created.id());
        System.out.printf("Deleted note %s%n", created.id());
      }
    }
  }
}
