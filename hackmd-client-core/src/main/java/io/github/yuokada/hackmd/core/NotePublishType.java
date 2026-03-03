package io.github.yuokada.hackmd.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum NotePublishType {
  @JsonProperty("edit")
  EDIT,

  @JsonProperty("view")
  VIEW,

  @JsonProperty("slide")
  SLIDE,

  @JsonProperty("book")
  BOOK
}
