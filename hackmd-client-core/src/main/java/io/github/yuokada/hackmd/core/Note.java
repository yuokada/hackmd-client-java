package io.github.yuokada.hackmd.core;

import java.net.URL;
import java.util.List;

/** Detailed view of a note returned by GET /notes/{noteId}. */
public record Note(
    String id,
    String title,
    String content,
    List<String> tags,
    long createdAt,
    long titleUpdatedAt,
    long tagsUpdatedAt,
    NotePublishType publishType,
    Long publishedAt,
    URL permalink,
    URL publishLink,
    String shortId,
    long lastChangedAt,
    UserSummary lastChangeUser,
    String userPath,
    String teamPath,
    NotePermissionRole readPermission,
    NotePermissionRole writePermission) {}
