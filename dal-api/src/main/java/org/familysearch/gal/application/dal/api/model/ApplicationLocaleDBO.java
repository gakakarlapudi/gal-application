/**
 * Copyright © 2010 Intellectual Reserve, Inc. All Rights reserved.
 */

package org.familysearch.gal.application.dal.api.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Locale;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * Represents application entry
 * 
 * @author gakakarlapudi
 * 
 */
@Entity
@Table(name = "application_locale", uniqueConstraints = @UniqueConstraint(columnNames = { "application_id", "locale" }))
@IdClass(ApplicationLocaleAssociationId.class)
public class ApplicationLocaleDBO implements Serializable {

    private static final long serialVersionUID = 873245192978870789L;

    private Long applicationId;
    private ApplicationDBO application;
    private Locale locale;
    private String title;
    private byte[] descriptionData;
    private String appSummary;

    /**
     * Default constructor
     */
    public ApplicationLocaleDBO() {

    }

    /**
     * Parametrized Constructor
     */
    public ApplicationLocaleDBO(ApplicationLocaleDBO other) {
        this.applicationId = other.getApplicationId();
        this.application = other.getApplication();
        this.locale = other.getLocale();
        this.title = other.getTitle();
        this.descriptionData = other.getDescriptionData();
        this.appSummary = other.getAppSummary();
    }

    /**
     * @return the applicationId
     */
    @Id
    @Column(name = "application_id")
    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * @return application
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", updatable = false, insertable = false, referencedColumnName = "application_id")
    public ApplicationDBO getApplication() {
        return application;
    }

    public void setApplication(ApplicationDBO application) {
        this.application = application;
    }

    @Id
    @Length(max = 45)
    @Column(name = "locale", length = 45, nullable = false)
    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Length(max = 256)
    @Column(name = "title", length = 256, nullable = true)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Length(max = 512)
    @Column(name = "appSummary", length = 512, nullable = true)
    public String getAppSummary() {
        return appSummary;
    }

    public void setAppSummary(String appSummary) {
        this.appSummary = appSummary;
    }

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "description", length = 16000000)
    public byte[] getDescriptionData() {
        return (descriptionData == null) ? null : Arrays.copyOf(descriptionData, descriptionData.length);
    }

    public void setDescriptionData(byte[] descriptionData) {
        this.descriptionData = (descriptionData == null) ? null : Arrays.copyOf(descriptionData, descriptionData.length);
    }
    

    @Override
    public String toString() {
        return "ApplicationLocaleDBO [applicationId=" + applicationId + ", application=" + application + ", locale="
               + locale + ", title=" + title + ", descriptionData=" + Arrays.toString(descriptionData)
               + ", appSummary=" + appSummary + "]";
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((application == null) ? 0 : application.hashCode());
        result = prime * result + ((locale == null) ? 0 : locale.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ApplicationLocaleDBO)) {
            return false;
        }
        ApplicationLocaleDBO other = (ApplicationLocaleDBO) obj;
        if (application == null) {
            if (other.getApplication() != null) {
                return false;
            }
        }
        else if (!application.equals(other.getApplication())) {
            return false;
        }
        if (locale == null) {
            if (other.getLocale() != null) {
                return false;
            }
        }
        else if (!locale.equals(other.getLocale())) {
            return false;
        }
        return true;
    }
}
