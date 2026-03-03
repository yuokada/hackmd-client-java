package io.github.yuokada.hackmd.core;

/** Request body for PATCH /notes or team notes. */
public record UpdateNoteRequest(
    String parentFolderId,
    String permalink,
    NotePermissionRole writePermission,
    NotePermissionRole readPermission,
    String content) {}
