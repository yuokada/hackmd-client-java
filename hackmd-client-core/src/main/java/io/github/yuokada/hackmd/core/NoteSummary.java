package io.github.yuokada.hackmd.core;

import java.net.URL;
import java.util.List;

/**
 * Summary view of a note returned by GET /notes.
 *
 * <p>Field set is intentionally minimal; add fields as you need.
 */
public record NoteSummary(
    String id,
    String title,
    List<String> tags,
    long createdAt,
    long titleUpdatedAt,
    long tagsUpdatedAt,
    NotePublishType publishType,
    Long publishedAt,
    String permalink,
    URL publishLink,
    String shortId,
    String content,
    long lastChangedAt,
    UserSummary lastChangeUser,
    String userPath,
    String teamPath,
    NotePermissionRole readPermission,
    NotePermissionRole writePermission) {}
