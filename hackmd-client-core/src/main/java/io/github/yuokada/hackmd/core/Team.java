package io.github.yuokada.hackmd.core;

/** Representation of a HackMD team. */
public record Team(
    String id,
    String ownerId,
    String name,
    String logo,
    String path,
    String description,
    TeamVisibility visibility,
    boolean upgraded,
    double createdAt) {}
