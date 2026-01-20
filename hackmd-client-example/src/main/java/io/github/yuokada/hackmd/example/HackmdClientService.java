package io.github.yuokada.hackmd.example;

import io.github.yuokada.hackmd.core.HackmdClient;
import io.github.yuokada.hackmd.core.NoteSummary;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Comparator;
import picocli.CommandLine.Command;

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
  }

  private void print(NoteSummary note) {
    System.out.printf("- %s (%s) tags=%s%n", note.title(), note.id(), note.tags());
  }
}
