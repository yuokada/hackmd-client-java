package io.github.yuokada.hackmd.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Permission level controlling who can comment on a HackMD note. */
public enum NoteCommentPermission {
  @JsonProperty("disabled")
  DISABLED,

  @JsonProperty("forbidden")
  FORBIDDEN,

  @JsonProperty("owners")
  OWNERS,

  @JsonProperty("signed_in_users")
  SIGNED_IN_USERS,

  @JsonProperty("everyone")
  EVERYONE
}
