package io.github.yuokada.hackmd.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum NotePublishType {
  EDIT("edit"),
  VIEW("view"),
  SLIDE("slide"),
  BOOK("book");

  private final String value;

  NotePublishType(String value) {
    this.value = value;
  }

  @JsonCreator
  public static NotePublishType fromValue(String raw) {
    for (var type : values()) {
      if (type.value.equalsIgnoreCase(raw)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Unknown NotePublishType: " + raw);
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
