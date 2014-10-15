package org.familysearch.gal.application.rest.api.endpoints.builders;

import java.util.List;
import java.util.Map;

import org.familysearch.gal.application.service.api.model.Application;
import org.familysearch.gal.shared.builder.FeedOptions;
import org.springframework.stereotype.Component;

/**
 * Application Feed options to build Atom Feed
 * 
 * @see FeedOptions
 * @author gakakarlapudi
 * 
 */
@Component
public class ApplicationFeedOptions extends FeedOptions<Application> {

    @Override
    public Map<String, Object> getSearchOptions() {
        return searchOptions;
    }

    /*
     * (non-Javadoc)
     * @see org.familysearch.idx.api.rest.common.FeedOptions#setSearchOptions(java.util.Map)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void setSearchOptions(Map<String, Object> searchOptions) {

        for (Map.Entry<String, Object> entry : searchOptions.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value != null) {
                if (value instanceof List && !((List<Object>) value).isEmpty())
                    this.searchOptions.put(key, value);
                else if (value instanceof String) {
                    if (value.toString() != "")
                        this.searchOptions.put(key, value);
                }
                else
                    this.searchOptions.put(key, value);
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see org.familysearch.idx.api.rest.common.FeedOptions#getSortOptions()
     */
    @Override
    public List<String> getSortOptions() {
        return sortOptions;
    }

    /*
     * (non-Javadoc)
     * @see org.familysearch.idx.api.rest.common.FeedOptions#setSortOptions(java.util.List)
     */
    @Override
    public void setSortOptions(List<String> sortOptions) {
        this.sortOptions = sortOptions;
    }
}
