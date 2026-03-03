package io.github.yuokada.hackmd.core;

import java.net.URL;
import java.util.List;

/**
 * Note item as returned by list endpoints (e.g. {@code GET /notes}, {@code GET
 * /teams/{teamPath}/notes}).
 *
 * <p>The HackMD API returns the same field set for both list and detail responses; this record
 * mirrors that shape. Use {@link Note} when working with single-note detail endpoints.
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
