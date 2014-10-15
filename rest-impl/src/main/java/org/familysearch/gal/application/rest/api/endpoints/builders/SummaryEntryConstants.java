package org.familysearch.gal.application.rest.api.endpoints.builders;

/**
 * Enumerated list of Summary attributes names.
 */
public enum SummaryEntryConstants {

    //Application Summary Constants
    APPLICATION_NAME("appName"),
    APPLICATION_STATUS("appStatus"),
    APPLICATION_VERSION("appVersion"),
    APPLICATION_DOWNLOAD_LINK("downloadLink"),
    APPLICATION_PLATFORM("platform"),
    APPLICATION_AVERAGE_RATING("averageRating"),
    APPLICATION_RATING_COUNT("ratingCount"),
    APPLICATION_POPULARITY("popularity"),
    APPLICATION_LOCALE("locale"),
    APPLICATION_LOCALE_TITLE("title"),
    APPLICATION_LOCALE_DESCRIPTION("description"),
    APPLICATION_SUMMARY("appSummary");
    
    String attribute;

    private SummaryEntryConstants(String attribute) {
        this.attribute = attribute;
    }

    public String key() {
        return attribute;
    }
}
