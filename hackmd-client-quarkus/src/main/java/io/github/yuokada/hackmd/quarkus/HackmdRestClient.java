package io.github.yuokada.hackmd.quarkus;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.github.yuokada.hackmd.core.CreateNoteRequest;
import io.github.yuokada.hackmd.core.Note;
import io.github.yuokada.hackmd.core.NoteSummary;
import io.github.yuokada.hackmd.core.Team;
import io.github.yuokada.hackmd.core.UpdateNoteRequest;
import io.github.yuokada.hackmd.core.UserProfile;

/**
 * Low-level REST client mapping to HackMD endpoints.
 *
 * <p>Base URL is configured via quarkus.rest-client.hackmd.url, and this client adds /v1 prefix.
 */
@Path("/v1")
@RegisterRestClient(configKey = "hackmd")
@RegisterProvider(HackmdBearerAuthFilter.class)
@RegisterProvider(HackmdErrorResponseMapper.class)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface HackmdRestClient {

  /** List all personal notes for the authenticated user. */
  @GET
  @Path("/notes")
  List<NoteSummary> listNotes();

  /** Retrieve a specific personal note by its identifier. */
  @GET
  @Path("/notes/{noteId}")
  Note getNote(@PathParam("noteId") String noteId);

  /** Create a new personal note with the provided content and metadata. */
  @POST
  @Path("/notes")
  Note createNote(CreateNoteRequest request);

  /** Delete a personal note owned by the current user. */
  @DELETE
  @Path("/notes/{noteId}")
  void deleteNote(@PathParam("noteId") String noteId);

  /** Update a personal note's body, permissions, or metadata. */
  @PATCH
  @Path("/notes/{noteId}")
  Note updateNote(@PathParam("noteId") String noteId, UpdateNoteRequest request);

  /** List teams the current user belongs to. */
  @GET
  @Path("/teams")
  List<Team> listTeams();

  /** List notes stored under the given team workspace. */
  @GET
  @Path("/teams/{teamPath}/notes")
  List<NoteSummary> listTeamNotes(@PathParam("teamPath") String teamPath);

  /** Create a new team note within the specified team workspace. */
  @POST
  @Path("/teams/{teamPath}/notes")
  Note createTeamNote(@PathParam("teamPath") String teamPath, CreateNoteRequest request);

  /** Retrieve a specific team note by team path and note ID. */
  @GET
  @Path("/teams/{teamPath}/notes/{noteId}")
  Note getTeamNote(@PathParam("teamPath") String teamPath, @PathParam("noteId") String noteId);

  /** Update the content or permissions of a team note. */
  @PATCH
  @Path("/teams/{teamPath}/notes/{noteId}")
  Note updateTeamNote(
      @PathParam("teamPath") String teamPath,
      @PathParam("noteId") String noteId,
      UpdateNoteRequest request);

  /** Delete a team note from the specified workspace. */
  @DELETE
  @Path("/teams/{teamPath}/notes/{noteId}")
  void deleteTeamNote(@PathParam("teamPath") String teamPath, @PathParam("noteId") String noteId);

  /** Retrieve the profile information of the authenticated user. */
  @GET
  @Path("/me")
  UserProfile getCurrentUser();

  /** Return recent note history entries, optionally limited in count. */
  @GET
  @Path("/history")
  List<NoteSummary> getHistory(@QueryParam("limit") Integer limit);
}
