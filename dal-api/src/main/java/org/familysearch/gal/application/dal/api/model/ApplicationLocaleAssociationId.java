package org.familysearch.gal.application.dal.api.model;

import java.io.Serializable;
import java.util.Locale;

public class ApplicationLocaleAssociationId implements Serializable {

    /**
     * Change this if you change the class
     */
    private static final long serialVersionUID = 1L;

    private long applicationId;
    
    private Locale locale;
    

    public long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(long applicationId) {
        this.applicationId = applicationId;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (applicationId ^ (applicationId >>> 32));
        result = prime * result + ((locale == null) ? 0 : locale.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ApplicationLocaleAssociationId other = (ApplicationLocaleAssociationId) obj;
        if (applicationId != other.applicationId)
            return false;
        if (locale == null) {
            if (other.locale != null)
                return false;
        }
        else if (!locale.equals(other.locale))
            return false;
        return true;
    }


 
}
