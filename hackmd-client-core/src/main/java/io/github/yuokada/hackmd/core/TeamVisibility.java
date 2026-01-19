package io.github.yuokada.hackmd.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Visibility for HackMD teams. */
public enum TeamVisibility {
  @JsonProperty("public")
  PUBLIC,

  @JsonProperty("private")
  PRIVATE
}
