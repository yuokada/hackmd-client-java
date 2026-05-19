package io.github.yuokada.hackmd.core;

/** Transport SPI used by core client logic to send HTTP requests. */
public interface HackmdTransport {

  HackmdResponse send(HackmdRequest request) throws HackmdException;
}
