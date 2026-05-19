package io.github.yuokada.hackmd.core;

/** Value + response metadata pair for advanced API consumers. */
public record HackmdResult<T>(T value, HackmdResponseMetadata metadata) {}
