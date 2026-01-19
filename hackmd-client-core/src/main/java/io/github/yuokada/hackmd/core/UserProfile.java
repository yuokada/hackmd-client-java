package io.github.yuokada.hackmd.core;

import java.util.List;

/** Current HackMD user profile returned by GET /me. */
public record UserProfile(
    String id,
    String email,
    String name,
    String userPath,
    String photo,
    List<Team> teams,
    boolean upgraded) {}
