package io.github.yuokada.hackmd.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum NotePermissionRole {
  @JsonProperty("owner")
  OWNER,

  @JsonProperty("signed_in")
  SIGNED_IN,

  @JsonProperty("guest")
  GUEST
}
