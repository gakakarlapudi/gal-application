package org.familysearch.gal.application.rest.api.endpoints;

import static org.familysearch.gal.shared.common.GALMediaTypes.APPLICATION_GAL_XML;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import org.familysearch.gal.application.rest.api.model.ApplicationRepresentation;
import org.familysearch.gal.shared.common.GALMediaTypes;

public interface ApplicationEndpoints {

    /**
     * Responsible to give Application information
     */

    @GET
    @Produces({ APPLICATION_GAL_XML, MediaType.APPLICATION_XML, GALMediaTypes.APPLICATION_GAL_JSON,
               MediaType.APPLICATION_JSON })
    public Response list(@Context Request request, @QueryParam("sort") List<String> sort,
                         @QueryParam("asc") Boolean asc,
                         @QueryParam("page") Integer page,
                         @QueryParam("pagesize") Integer pagesize);

    @GET
    @Path("/{applicationid}")
    @Produces({ APPLICATION_GAL_XML, MediaType.APPLICATION_XML, GALMediaTypes.APPLICATION_GAL_JSON,
               MediaType.APPLICATION_JSON })
    public Response read(@Context Request request, @PathParam("applicationid") UUID applicationId);

    @POST
    @Path("/")
    @Produces({ APPLICATION_GAL_XML, MediaType.APPLICATION_XML, GALMediaTypes.APPLICATION_GAL_JSON,
               MediaType.APPLICATION_JSON })
    @Consumes({ APPLICATION_GAL_XML, MediaType.APPLICATION_XML, GALMediaTypes.APPLICATION_GAL_XML,
               GALMediaTypes.APPLICATION_GAL_JSON, MediaType.APPLICATION_JSON })
    public Response create(@Context Request request,
                           ApplicationRepresentation applicationRepresentation);

    @PUT
    @Path("/{applicationid}")
    @Produces({ APPLICATION_GAL_XML, MediaType.APPLICATION_XML, GALMediaTypes.APPLICATION_GAL_JSON,
               MediaType.APPLICATION_JSON })
    @Consumes({ APPLICATION_GAL_XML, MediaType.APPLICATION_XML, GALMediaTypes.APPLICATION_GAL_XML,
               GALMediaTypes.APPLICATION_GAL_JSON, MediaType.APPLICATION_JSON })
    public Response update(@Context Request request, @PathParam("applicationid") UUID applicationId,
                           ApplicationRepresentation applicationRepresentation);

    @DELETE
    @Path("/{applicationid}")
    @Produces({ APPLICATION_GAL_XML, MediaType.APPLICATION_XML, GALMediaTypes.APPLICATION_GAL_JSON,
               MediaType.APPLICATION_JSON })
    public Response delete(@Context Request request, @PathParam("applicationid") UUID applicationId);
}
