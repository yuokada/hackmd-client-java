package io.github.yuokada.hackmd.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EnumJsonMappingTest {

  private ObjectMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new ObjectMapper();
  }

  // --- NotePermissionRole ---

  @Test
  @DisplayName("NotePermissionRole serializes to lowercase JSON string")
  void notePermissionRole_serialization() throws Exception {
    assertEquals("\"owner\"", mapper.writeValueAsString(NotePermissionRole.OWNER));
    assertEquals("\"signed_in\"", mapper.writeValueAsString(NotePermissionRole.SIGNED_IN));
    assertEquals("\"guest\"", mapper.writeValueAsString(NotePermissionRole.GUEST));
  }

  @Test
  @DisplayName("NotePermissionRole deserializes from lowercase JSON string")
  void notePermissionRole_deserialization() throws Exception {
    assertEquals(NotePermissionRole.OWNER, mapper.readValue("\"owner\"", NotePermissionRole.class));
    assertEquals(
        NotePermissionRole.SIGNED_IN, mapper.readValue("\"signed_in\"", NotePermissionRole.class));
    assertEquals(NotePermissionRole.GUEST, mapper.readValue("\"guest\"", NotePermissionRole.class));
  }

  @Test
  @DisplayName("NotePermissionRole rejects unknown JSON value")
  void notePermissionRole_unknownValue_throws() {
    assertThrows(Exception.class, () -> mapper.readValue("\"unknown\"", NotePermissionRole.class));
  }

  // --- NotePublishType ---

  @Test
  @DisplayName("NotePublishType serializes to lowercase JSON string")
  void notePublishType_serialization() throws Exception {
    assertEquals("\"edit\"", mapper.writeValueAsString(NotePublishType.EDIT));
    assertEquals("\"view\"", mapper.writeValueAsString(NotePublishType.VIEW));
    assertEquals("\"slide\"", mapper.writeValueAsString(NotePublishType.SLIDE));
    assertEquals("\"book\"", mapper.writeValueAsString(NotePublishType.BOOK));
  }

  @Test
  @DisplayName("NotePublishType deserializes from lowercase JSON string")
  void notePublishType_deserialization() throws Exception {
    assertEquals(NotePublishType.EDIT, mapper.readValue("\"edit\"", NotePublishType.class));
    assertEquals(NotePublishType.VIEW, mapper.readValue("\"view\"", NotePublishType.class));
    assertEquals(NotePublishType.SLIDE, mapper.readValue("\"slide\"", NotePublishType.class));
    assertEquals(NotePublishType.BOOK, mapper.readValue("\"book\"", NotePublishType.class));
  }

  // --- NoteCommentPermission ---

  @Test
  @DisplayName("NoteCommentPermission serializes to lowercase JSON string")
  void noteCommentPermission_serialization() throws Exception {
    assertEquals("\"disabled\"", mapper.writeValueAsString(NoteCommentPermission.DISABLED));
    assertEquals("\"forbidden\"", mapper.writeValueAsString(NoteCommentPermission.FORBIDDEN));
    assertEquals("\"owners\"", mapper.writeValueAsString(NoteCommentPermission.OWNERS));
    assertEquals(
        "\"signed_in_users\"", mapper.writeValueAsString(NoteCommentPermission.SIGNED_IN_USERS));
    assertEquals("\"everyone\"", mapper.writeValueAsString(NoteCommentPermission.EVERYONE));
  }

  @Test
  @DisplayName("NoteCommentPermission deserializes from lowercase JSON string")
  void noteCommentPermission_deserialization() throws Exception {
    assertEquals(
        NoteCommentPermission.DISABLED,
        mapper.readValue("\"disabled\"", NoteCommentPermission.class));
    assertEquals(
        NoteCommentPermission.OWNERS, mapper.readValue("\"owners\"", NoteCommentPermission.class));
    assertEquals(
        NoteCommentPermission.SIGNED_IN_USERS,
        mapper.readValue("\"signed_in_users\"", NoteCommentPermission.class));
  }

  // --- TeamVisibility ---

  @Test
  @DisplayName("TeamVisibility serializes to lowercase JSON string")
  void teamVisibility_serialization() throws Exception {
    assertEquals("\"public\"", mapper.writeValueAsString(TeamVisibility.PUBLIC));
    assertEquals("\"private\"", mapper.writeValueAsString(TeamVisibility.PRIVATE));
  }

  @Test
  @DisplayName("TeamVisibility deserializes from lowercase JSON string")
  void teamVisibility_deserialization() throws Exception {
    assertEquals(TeamVisibility.PUBLIC, mapper.readValue("\"public\"", TeamVisibility.class));
    assertEquals(TeamVisibility.PRIVATE, mapper.readValue("\"private\"", TeamVisibility.class));
  }
}
