package io.github.yuokada.hackmd.quarkus;

import io.github.yuokada.hackmd.core.CreateNoteRequest;
import io.github.yuokada.hackmd.core.Note;
import io.github.yuokada.hackmd.core.NoteSummary;
import io.github.yuokada.hackmd.core.Team;
import io.github.yuokada.hackmd.core.UpdateNoteRequest;
import io.github.yuokada.hackmd.core.UserProfile;
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
import java.util.List;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

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

  @GET
  @Path("/notes")
  List<NoteSummary> listNotes();

  @GET
  @Path("/notes/{noteId}")
  Note getNote(@PathParam("noteId") String noteId);

  @POST
  @Path("/notes")
  Note createNote(CreateNoteRequest request);

  @DELETE
  @Path("/notes/{noteId}")
  void deleteNote(@PathParam("noteId") String noteId);

  @PATCH
  @Path("/notes/{noteId}")
  Note updateNote(@PathParam("noteId") String noteId, UpdateNoteRequest request);

  @GET
  @Path("/teams")
  List<Team> listTeams();

  @GET
  @Path("/teams/{teamPath}/notes")
  List<NoteSummary> listTeamNotes(@PathParam("teamPath") String teamPath);

  @POST
  @Path("/teams/{teamPath}/notes")
  Note createTeamNote(@PathParam("teamPath") String teamPath, CreateNoteRequest request);

  @GET
  @Path("/teams/{teamPath}/notes/{noteId}")
  Note getTeamNote(@PathParam("teamPath") String teamPath, @PathParam("noteId") String noteId);

  @PATCH
  @Path("/teams/{teamPath}/notes/{noteId}")
  Note updateTeamNote(
      @PathParam("teamPath") String teamPath,
      @PathParam("noteId") String noteId,
      UpdateNoteRequest request);

  @DELETE
  @Path("/teams/{teamPath}/notes/{noteId}")
  void deleteTeamNote(@PathParam("teamPath") String teamPath, @PathParam("noteId") String noteId);

  @GET
  @Path("/me")
  UserProfile getCurrentUser();

  @GET
  @Path("/history")
  List<NoteSummary> getHistory(@QueryParam("limit") Integer limit);
}
