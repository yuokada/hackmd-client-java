package io.github.yuokada.hackmd.core;

import java.util.List;

/**
 * Summary view of a note returned by GET /notes.
 *
 * <p>Field set is intentionally minimal; add fields as you need.
 */
public record NoteSummary(String id, String title, List<String> tags) {}
