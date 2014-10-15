package org.familysearch.gal.application.rest.api.endpoints;

import static org.familysearch.gal.shared.common.GALMediaTypes.APPLICATION_GAL_JSON;
import static org.familysearch.gal.shared.common.GALMediaTypes.APPLICATION_GAL_XML;

import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

public interface PartnerEndpoints {

    /**
     * Responsible to give Partner information
     */
    @GET
    @Path("/{partnerid}")
    @Produces({ APPLICATION_GAL_XML, MediaType.APPLICATION_XML, APPLICATION_GAL_JSON, MediaType.APPLICATION_JSON})
    public Response read(@Context Request request, @PathParam("partnerid") UUID partnerId);
    
}
