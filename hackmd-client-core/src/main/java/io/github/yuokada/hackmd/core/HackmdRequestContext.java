package io.github.yuokada.hackmd.core;

/** Request context passed to credential providers for token resolution. */
public record HackmdRequestContext(String operationName, String path, HackmdHttpMethod method) {}
