package io.github.yuokada.hackmd.quarkus;

import io.github.yuokada.hackmd.core.CreateNoteRequest;
import io.github.yuokada.hackmd.core.Note;
import io.github.yuokada.hackmd.core.NoteSummary;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

import java.util.List;

/**
 * Low-level REST client mapping to HackMD endpoints.
 *
 * <p>Base URL is configured via quarkus.rest-client.hackmd.url, and this client adds /v1 prefix.
 */
@Path("/v1")
@RegisterRestClient(configKey = "hackmd")
@RegisterProvider(HackmdBearerAuthFilter.class)
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
}
