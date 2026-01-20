package io.github.yuokada.hackmd.example;

import io.quarkus.arc.Arc;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import picocli.CommandLine;

@QuarkusMain
public class ExampleMain implements QuarkusApplication {

  @Override
  public int run(String... args) {
    var command = Arc.container().instance(HackmdClientService.class).get();
    return new CommandLine(command).execute(args);
  }
}
