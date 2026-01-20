package io.github.yuokada.hackmd.example;

import jakarta.inject.Inject;

import picocli.CommandLine;
import io.quarkus.picocli.runtime.annotations.TopCommand;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class ExampleMain implements QuarkusApplication {

  @Inject CommandLine.IFactory factory;
  @Inject @TopCommand HackmdClientService command;

  @Override
  public int run(String... args) {
    return new CommandLine(command, factory).execute(args);
  }
}
