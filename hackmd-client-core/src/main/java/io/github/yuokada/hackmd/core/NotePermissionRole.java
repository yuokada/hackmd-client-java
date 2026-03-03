package io.github.yuokada.hackmd.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum NotePermissionRole {
  OWNER("owner"),
  SIGNED_IN("signed_in"),
  GUEST("guest");

  private final String value;

  NotePermissionRole(String value) {
    this.value = value;
  }

  @JsonCreator
  public static NotePermissionRole fromValue(String raw) {
    for (var role : values()) {
      if (role.value.equalsIgnoreCase(raw)) {
        return role;
      }
    }
    throw new IllegalArgumentException("Unknown NotePermissionRole: " + raw);
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
