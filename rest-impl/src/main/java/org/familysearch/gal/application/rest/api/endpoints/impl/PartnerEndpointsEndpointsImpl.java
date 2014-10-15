package org.familysearch.gal.application.rest.api.endpoints.impl;

import java.util.UUID;

import javax.ws.rs.Path;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.familysearch.gal.application.rest.api.endpoints.PartnerEndpoints;
import org.springframework.stereotype.Component;

@Component
@Path("/partners")
public class PartnerEndpointsEndpointsImpl implements PartnerEndpoints {

    private static final Logger LOGGER = Logger
            .getLogger(PartnerEndpointsEndpointsImpl.class);

    @Override
    public Response read(Request request, UUID partnerId) {
        return null;
    }


}
