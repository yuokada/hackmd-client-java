package io.github.yuokada.hackmd.core;

import java.util.List;

/** Request body for POST /notes. */
public record CreateNoteRequest(
    String title,
    String content,
    String readPermission,
    String writePermission,
    NoteCommentPermission commentPermission,
    String permalink,
    List<String> tags) {}
