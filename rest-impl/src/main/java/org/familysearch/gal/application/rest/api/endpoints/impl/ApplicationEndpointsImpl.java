package org.familysearch.gal.application.rest.api.endpoints.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.Path;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Variant;

import org.apache.abdera.model.Feed;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.familysearch.engage.foundation.util.LinkBuilderFactory;
import org.familysearch.gal.application.rest.api.endpoints.ApplicationEndpoints;
import org.familysearch.gal.application.rest.api.endpoints.PartnerEndpoints;
import org.familysearch.gal.application.rest.api.endpoints.builders.ApplicationFeedOptions;
import org.familysearch.gal.application.rest.api.model.ApplicationRepresentation;
import org.familysearch.gal.application.service.api.ApplicationResourceService;
import org.familysearch.gal.application.service.api.model.Application;
import org.familysearch.gal.shared.builder.CSVParameterList;
import org.familysearch.gal.shared.builder.FeedBuilder;
import org.familysearch.gal.shared.builder.PageBuilder;
import org.familysearch.gal.shared.builder.TemplateBuilder;
import org.familysearch.gal.shared.builder.TemplateParser;
import org.familysearch.gal.shared.common.GALMediaTypes;
import org.familysearch.gal.shared.mapper.MapperFactory;
import org.familysearch.gal.shared.model.Link;
import org.familysearch.gal.shared.model.Page;
import org.familysearch.gal.shared.model.Template;
import org.familysearch.gal.shared.rest.exception.BadRequestException;
import org.familysearch.gal.shared.rest.exception.ServiceRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.client.ClientResponse.Status;

@Component
@Path("/applications")
public class ApplicationEndpointsImpl implements ApplicationEndpoints {

    private static final Logger LOGGER = Logger
        .getLogger(ApplicationEndpointsImpl.class);

    @Autowired
    private ApplicationResourceService applicationResourceService;

    @Autowired
    private LinkBuilderFactory linkBuilderFactory;

    @Autowired
    private FeedBuilder<Application, ApplicationRepresentation> feedBuilder;

    @Value("${cache.max.age.max}")
    int maxCacheTime;

    @Value("${cache.max.age.min}")
    int minCacheTime;

    private TemplateParser parser;

    private static final String DEFAULT_SORT = "creationTime";
    private static final Boolean DEFAULT_SORT_ORDER = false;

    public ApplicationResourceService getApplicationResourceService() {
        return applicationResourceService;
    }

    public void setApplicationResourceService(
                                              ApplicationResourceService applicationResourceService) {
        this.applicationResourceService = applicationResourceService;
    }

    public LinkBuilderFactory getLinkBuilderFactory() {
        return linkBuilderFactory;
    }

    public void setLinkBuilderFactory(LinkBuilderFactory linkBuilderFactory) {
        this.linkBuilderFactory = linkBuilderFactory;
    }

    public FeedBuilder<Application, ApplicationRepresentation> getFeedBuilder() {
        return feedBuilder;
    }

    public void setFeedBuilder(FeedBuilder<Application, ApplicationRepresentation> feedBuilder) {
        this.feedBuilder = feedBuilder;
    }

    public TemplateParser getParser() {
        return parser;
    }

    public void setParser(TemplateParser parser) {
        this.parser = parser;
    }

    @Override
    public Response read(Request request, UUID applicationId) {
        Application foundApp = applicationResourceService.getApplication(applicationId);

        // return Response.ok(Status.NOT_IMPLEMENTED).build()

        ApplicationRepresentation applicationRepresentation = feedBuilder
            .createRepresentation(request, foundApp);

        return Response.ok(applicationRepresentation).build();

    }

    @Override
    public Response delete(Request request, UUID applictaionId) {
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }

    @Override
    public Response create(Request request,
                           ApplicationRepresentation applicationRepresentation) {

        Response response = null;

        Application application = MapperFactory.instance(ApplicationRepresentation.class, Application.class)
            .from(applicationRepresentation);

        String baseUri = linkBuilderFactory.newBuilder().build();
        Template partnerTemplate = new TemplateBuilder(baseUri)
            .path(PartnerEndpointsEndpointsImpl.class)
            .path(PartnerEndpoints.class, "read").build();
        Link partnerLink = applicationRepresentation.getPartner();
        parser = new TemplateParser(partnerTemplate);
        URI uri;
        try {
            uri = new URI(partnerLink.getHref());
        }
        catch (URISyntaxException e1) {
            throw new ServiceRESTException("Partner read URI is invalid");
        }
        TemplateParser.Result result = parser.parse(uri);
        String partnerId = result.get("partnerid");
        // Validity check to ProjectId
        try {
            UUID partnerUUID = UUID.fromString(partnerId);
            application.setPartnerId(partnerUUID);
        }
        catch (Exception e) {
            throw new BadRequestException("Partner UUID in the URL is not valid");
        }

        Application createdApplication = applicationResourceService.createApplication(application);

        Variant primaryVariant = GALMediaTypes
            .primaryVariant(request, "application");
        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(maxCacheTime);
        String applicationLinkURI = linkBuilderFactory.newBuilder()
            .path(ApplicationEndpointsImpl.class)
            .path(ApplicationEndpoints.class, "read")
            .build(createdApplication.getUuid().toString());
        try {
            response = Response.created(new URI(applicationLinkURI))
                .type(primaryVariant.getMediaType())
                .build();
        }
        catch (URISyntaxException e) {
            throw new ServiceRESTException("Application read URI is invalid");
        }

        return response;
    }

    @Override
    public Response update(Request request, UUID applicationId,
                           ApplicationRepresentation applicationRepresentation) {
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }

    @Override
    public Response list(Request request, List<String> sort, Boolean asc, Integer page, Integer pagesize) {
        if (LOGGER.isTraceEnabled())
            LOGGER.trace("ApplicationEndpoints: Entering list, sort=" + sort + "; & asc=" + asc + "; & page=" + page
                         + "; & pagesize="
                         + pagesize);
        Response response = null;
        List<String> inputSort = null;
        Boolean inputAsc;
        List<Application> applicationList = new ArrayList<Application>();

        // for self link maintain the given sort
        inputAsc = (asc != null) ? new Boolean(asc) : null;
        if (sort == null || sort.isEmpty()) {
            inputSort = null;
            sort = new ArrayList<String>();
            sort.add(DEFAULT_SORT);
        }
        if (asc == null) {
            asc = DEFAULT_SORT_ORDER;
        }

        Page pageObject = new Page();
        if (page != null && pagesize != null) {
            pageObject = new PageBuilder().builder(pagesize, page).asc(asc).build();
        }
        else {
            pageObject = new PageBuilder().builder().asc(asc).build();
        }
        String sortField = StringUtils.join(sort, ",");
        pageObject.setSortColumn(sortField);
        applicationList = applicationResourceService.findAll(pageObject);

        if (!applicationList.isEmpty()) {

            CSVParameterList csvSort = new CSVParameterList(sort);

            ApplicationFeedOptions applicationFeedOptions = new ApplicationFeedOptions();
            applicationFeedOptions.setList(applicationList);
            applicationFeedOptions.setPage(pageObject);
            applicationFeedOptions.setSortOptions(csvSort);

            Map<String, Object> searchOptions = new HashMap<String, Object>();
            searchOptions.put("inputPage", page);
            searchOptions.put("inputPagesize", pagesize);
            searchOptions.put("inputSort", inputSort == null ? null : StringUtils.join(inputSort, ","));
            searchOptions.put("inputAsc", inputAsc);

            applicationFeedOptions.setSearchOptions(searchOptions);

            Feed feed = feedBuilder.createFeed(request, applicationFeedOptions);

            response = createFeedResponse(request, feed);
        }
        else {
            response = Response.status(Status.NO_CONTENT).build();
        }

        if (LOGGER.isTraceEnabled())
            LOGGER.trace("ApplicationEndpoints: Exiting list, response-status=" + response.getStatus());

        return response;
    }

    private Response createFeedResponse(Request request, Feed feed) {
        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(minCacheTime);

        MediaType responseType = MediaType.APPLICATION_ATOM_XML_TYPE;
        Variant variant = request.selectVariant(GALMediaTypes.VARIANTS);
        if (variant.getMediaType().getSubtype().contains("json")) {
            responseType = MediaType.APPLICATION_JSON_TYPE;
        }

        Response response = Response.ok(feed)
            .type(responseType)
            .cacheControl(cacheControl)
            .header(HttpHeaders.VARY, HttpHeaders.ACCEPT)
            .build();

        return response;
    }
}
