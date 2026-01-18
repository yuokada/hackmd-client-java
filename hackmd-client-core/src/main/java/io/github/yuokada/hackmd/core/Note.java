package io.github.yuokada.hackmd.core;

import java.util.List;

/**
 * Detailed view of a note returned by GET /notes/{noteId}.
 */
public record Note(String id, String title, String content, List<String> tags) {}
