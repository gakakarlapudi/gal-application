package org.familysearch.gal.application.rest.api.endpoints.builders;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Variant;

import org.apache.abdera.i18n.iri.IRI;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.familysearch.engage.foundation.util.LinkBuilderFactory;
import org.familysearch.gal.application.rest.api.endpoints.ApplicationEndpoints;
import org.familysearch.gal.application.rest.api.endpoints.PartnerEndpoints;
import org.familysearch.gal.application.rest.api.endpoints.impl.ApplicationEndpointsImpl;
import org.familysearch.gal.application.rest.api.endpoints.impl.PartnerEndpointsEndpointsImpl;
import org.familysearch.gal.application.rest.api.model.ApplicationRepresentation;
import org.familysearch.gal.application.service.api.model.Application;
import org.familysearch.gal.application.service.api.model.ApplicationLocale;
import org.familysearch.gal.shared.builder.DefaultFeedBuilder;
import org.familysearch.gal.shared.builder.FeedBuilder;
import org.familysearch.gal.shared.builder.FeedIdBuilder;
import org.familysearch.gal.shared.builder.FeedOptions;
import org.familysearch.gal.shared.builder.TemplateBuilder;
import org.familysearch.gal.shared.builder.TemplateLinkBuilder;
import org.familysearch.gal.shared.common.GALMediaTypes;
import org.familysearch.gal.shared.common.GALQNames;
import org.familysearch.gal.shared.common.Relation;
import org.familysearch.gal.shared.mapper.MapperFactory;
import org.familysearch.gal.shared.model.Link;
import org.familysearch.gal.shared.model.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * Feed builder to create Atom Feed for List of Applications and Application Representation
 * 
 * @see FeedBuilder
 * @author gakakarlapudi
 * @param <M>
 * @param <R>
 * 
 */
@Component
public class ApplicationFeedBuilder extends DefaultFeedBuilder<Application, ApplicationRepresentation>
                implements FeedBuilder<Application, ApplicationRepresentation> {

    private static final Logger LOGGER = Logger
        .getLogger(ApplicationFeedBuilder.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LinkBuilderFactory linkBuilderFactory;

    private static final String TOTAL_ROWS = "total_rows";

    /**
     * Return the LinkBuilderFactory instance.
     * 
     * @return the linkBuilderFactory
     */
    public LinkBuilderFactory getLinkBuilderFactory() {
        return linkBuilderFactory;
    }

    /**
     * Set the setLinkBuilderFactory instance.
     * 
     * @param linkBuilderFactory
     *            the linkBuilderFactory to set
     */
    public void setLinkBuilderFactory(LinkBuilderFactory linkBuilderFactory) {
        this.linkBuilderFactory = linkBuilderFactory;
    }

    /**
     * Return the ObjectMapper instance.
     * 
     * @return the objectMapper
     */
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * Set the setObjectMapper instance.
     * 
     * @param objectMapper
     *            the objectMapper to set
     */
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.familysearch.gal.shared.builder.FeedBuilder#createRepresentation
     * (javax.ws.rs.core.Request, java.lang.Object)
     */
    @Override
    public ApplicationRepresentation createRepresentation(Request request, Application model) {
      /*  UUID appId = model.getUuid();
        String baseUri = linkBuilderFactory.newBuilder().build();*/
        ApplicationRepresentation applicationRepresentation = MapperFactory.instance(
                                                                         Application.class, ApplicationRepresentation.class)
            .from(model);

        String relationsLink = linkBuilderFactory.newBuilder()
                        .path(PartnerEndpointsEndpointsImpl.class)
                        .path(PartnerEndpoints.class, "read")
                        .build(model.getPartnerId().toString());
        Link partnerLink = new Link.Builder()
        .href(relationsLink)
        .build();
        
        applicationRepresentation.setPartner(partnerLink);
        
        return applicationRepresentation;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.familysearch.gal.shared.builder.DefaultFeedBuilder#doCreateFeedHeader(org
     * .apache.abdera.model.Feed,
     * org.familysearch.gal.shared.builder.FeedOptions)
     */
    @Override
    protected Feed doCreateFeedHeader(Feed feed, FeedOptions<Application> feedOptions) {
        GALQNames.declareNamespace(feed);
        feed.addAuthor("FamilySearch Product Gallery");

        UUID applicationId = feedOptions.getSearchOption("applicationId", UUID.class);
        feed.setId(new FeedIdBuilder()
            .identifier("application", applicationId)
            .rel(Relation.RELATIONS_APPLICATIONS.rel()).build());

        feed.setTitle("List of applications");

        feed.setUpdated(new Date());

        return feed;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.familysearch.gal.shared.builder.DefaultFeedBuilder#doCreateFeedLink(org
     * .apache.abdera.model.Feed, javax.ws.rs.core.Request,
     * org.familysearch.gal.shared.builder.FeedOptions)
     */
    @Override
    protected Feed doCreateFeedLink(Feed feed, Request request, FeedOptions<Application> feedOptions) {
        String baseUri = linkBuilderFactory.newBuilder().build();
        Integer totalRows = 0;
        if (feedOptions.getSearchOptions().get(TOTAL_ROWS) != null) {
            totalRows = Integer.valueOf(String.valueOf(feedOptions.getSearchOptions().get(TOTAL_ROWS)));
        }

        Template applicationTemplate = new TemplateBuilder(baseUri)
            .path(ApplicationEndpointsImpl.class)
            .queryParams(ApplicationEndpoints.class, "list").build();
        applicationTemplate.setMethod(HttpMethod.GET.toString());

        String sort = (String) feedOptions.getSearchOptions().get("inputSort");
        Integer pageSize = (Integer) feedOptions.getSearchOptions().get("inputPageSize");
        Integer pageNum = (Integer) feedOptions.getSearchOptions().get("inputPageNum");
        Boolean asc = (Boolean) feedOptions.getSearchOptions().get("inputAsc");

        Link linkToApplicationResource = new TemplateLinkBuilder(applicationTemplate)
            .param("sort", (sort != null) ? sort : null)
            .param("asc", (asc != null) ? String.valueOf(asc) : null)
            .param("pagenum", (pageNum != null) ? String.valueOf(pageNum) : null)
            .param("pagesize", (pageSize != null) ? String.valueOf(pageSize) : null)
            .build();
        feed.addLink(linkToApplicationResource.getHref(), Relation.SELF.rel())
            .setMimeType(MediaType.APPLICATION_ATOM_XML);

        if (totalRows != null) {
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("sort", (sort != null) ? sort : null);
            paramMap.put("asc", (asc != null) ? String.valueOf(asc) : null);
            paramMap.put("pagenum", (pageNum != null) ? String.valueOf(pageNum) : null);
            paramMap.put("pagesize", (pageSize != null) ? String.valueOf(pageSize) : null);
            addPaginationLinks(feed, applicationTemplate, paramMap, feedOptions);
        }
        return feed;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.familysearch.gal.shared.builder.DefaultFeedBuilder#doCreateEntryHeader(
     * org.apache.abdera.model.Entry, java.lang.Object,
     * javax.ws.rs.core.Request)
     */
    @Override
    protected void doCreateEntryHeader(Entry entry, Application model, Request request) {
        UUID applicationid = model.getUuid();

        entry.setAttributeValue(GALQNames.REL,
                                Relation.RELATIONS_APPLICATION.rel());
        entry.setAttributeValue(GALQNames.UUID, applicationid.toString());

        entry.setId(new FeedIdBuilder().identifier("application", applicationid).build());

        entry.setTitle(String.format("Application: %s", model.getUuid().toString()));

        Calendar lastUpdateTime = model.getLastUpdateTime();
        lastUpdateTime = lastUpdateTime == null ? Calendar.getInstance()
                                               : lastUpdateTime;
        entry.setUpdated(lastUpdateTime.getTime());

    }

    /*
     * (non-Javadoc)
     * @see
     * org.familysearch.gal.shared.builder.DefaultFeedBuilder#doCreateEntryLinks(org
     * .apache.abdera.model.Entry, java.lang.Object, javax.ws.rs.core.Request)
     */
    @Override
    protected void doCreateEntryLinks(Entry entry, Application model, Request request) {
        // Link to batch resource
        Variant primaryVariant = GALMediaTypes.primaryVariant(request,
                                                              "application");
        String applicationURL = linkBuilderFactory.newBuilder()
            .path(ApplicationEndpointsImpl.class)
            .path(ApplicationEndpoints.class, "read")
            .build(model.getUuid().toString());

        // Link to partner (TBD)

        entry.setContent(new IRI(applicationURL), primaryVariant.getMediaType()
            .toString());

    }

    /*
     * (non-Javadoc)
     * @see
     * org.familysearch.gal.shared.builder.DefaultFeedBuilder#doCreateEntrySummary
     * (org.apache.abdera.model.Entry, java.lang.Object, java.util.Map)
     */
    @Override
    protected void doCreateEntrySummary(Entry entry, Application model, Map<String, Object> searchOptions) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(SummaryEntryConstants.APPLICATION_NAME.key(), model.getAppName());
        map.put(SummaryEntryConstants.APPLICATION_STATUS.key(),
                model.getAppStatus());
        map.put(SummaryEntryConstants.APPLICATION_PLATFORM.key(), model.getPlatform());
        map.put(SummaryEntryConstants.APPLICATION_VERSION.key(), model.getAppVersion());
        map.put(SummaryEntryConstants.APPLICATION_DOWNLOAD_LINK.key(), model.getDownloadLink());
        map.put(SummaryEntryConstants.APPLICATION_POPULARITY.key(), model.getPopularity());
        map.put(SummaryEntryConstants.APPLICATION_AVERAGE_RATING.key(), model.getAverageRating());
        map.put(SummaryEntryConstants.APPLICATION_RATING_COUNT.key(), model.getRatingCount());

        for (ApplicationLocale locale : model.getLocales()) {
            Map<String, String> localMap = new HashMap<String, String>();
            localMap.put(SummaryEntryConstants.APPLICATION_LOCALE.key(), locale.getLocale().getLanguage());
            localMap.put(SummaryEntryConstants.APPLICATION_LOCALE_DESCRIPTION.key(), locale.getDescription());
            localMap.put(SummaryEntryConstants.APPLICATION_LOCALE_TITLE.key(), locale.getTitle());
            localMap.put(SummaryEntryConstants.APPLICATION_SUMMARY.key(), locale.getAppSummary());

            map.put("Application Locale", localMap);
        }
        addSummary(entry, map);
    }
}
