package io.github.yuokada.hackmd.core;

import java.util.List;

/** Request body for POST /notes. */
public record CreateNoteRequest(
    String title,
    String content,
    NotePermissionRole readPermission,
    NotePermissionRole writePermission,
    NoteCommentPermission commentPermission,
    String permalink,
    List<String> tags) {}
