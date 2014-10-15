/**
 * Copyright © 2010 Intellectual Reserve, Inc. All Rights reserved.
 */

package org.familysearch.gal.application.dal.api.model;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.familysearch.gal.application.dal.api.base.AbstractEntity;
import org.familysearch.gal.application.dal.api.util.GenericModelValidator;
import org.familysearch.gal.application.dal.api.util.ModelValidator;
import org.hibernate.validator.constraints.Length;

/**
 * Represents application entry
 * 
 * @author gakakarlapudi
 * 
 */
@Entity
@Table(name = "application", uniqueConstraints = @UniqueConstraint(columnNames = { "uuid", "application_id" }))
public class ApplicationDBO extends AbstractEntity<ApplicationDBO> {

    private static final long serialVersionUID = 873245192978870789L;

    private static final ModelValidator<ApplicationDBO> validator = new GenericModelValidator<ApplicationDBO>();

    private UUID uuid = UUID.randomUUID();
    private Long id;
    private PartnerDBO partner;
    private String appStatus;
    private String appName;
    private String appVersion;
    private String downloadLink;
    private String platform;
    private Double averageRating;
    private Integer ratingCount;
    private Integer popularity;
    private Calendar creationTime = Calendar.getInstance();
    private Calendar lastUpdateTime = Calendar.getInstance();
    private Long version = Long.valueOf(0);

    Set<ApplicationLocaleDBO> locales = new HashSet<ApplicationLocaleDBO>();

    /**
     * Default constructor
     */
    public ApplicationDBO() {

    }

    /**
     * Parametrized Constructor
     */
    public ApplicationDBO(ApplicationDBO other) {
        this.uuid = other.getUuid();
        this.id = other.getId();
        this.partner = other.getPartner();
        this.appStatus = other.getAppStatus();
        this.appName = other.getAppName();
        this.appVersion = other.getAppVersion();
        this.downloadLink = other.getDownloadLink();
        this.platform = other.getPlatform();
        this.averageRating = other.getAverageRating();
        this.ratingCount = other.getRatingCount();
        this.popularity = other.getPopularity();
        this.creationTime = other.getCreationTime();
        this.lastUpdateTime = other.getLastUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    protected ModelValidator<ApplicationDBO> validator() {
        return validator;
    }

    /**
     * Gets uuid.
     * 
     * @return Value of uuid.
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Sets new uuid.
     * 
     * @param uuid New value of uuid.
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * @return the productId
     */
    @Id
    @Column(name = "application_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "application_seq")
    @org.hibernate.annotations.GenericGenerator(name = "application_seq", strategy = "org.hibernate.id.enhanced.TableGenerator",
                    parameters = {
                                  @org.hibernate.annotations.Parameter(name = "segment_value", value = "application_seq"),
                                  @org.hibernate.annotations.Parameter(name = "initial_value", value = "50"),
                                  @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                                  @org.hibernate.annotations.Parameter(name = "optimizer", value = "none") })
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false, referencedColumnName = "partner_id")
    public PartnerDBO getPartner() {
        return partner;
    }

    public void setPartner(PartnerDBO partner) {
        this.partner = partner;
    }

    @Length(max = 45)
    @Column(length = 45, nullable = true)
    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

    @Length(max = 255)
    @Column(length = 255, nullable = false)
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Length(max = 45)
    @Column(length = 45, nullable = true)
    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    @Length(max = 512)
    @Column(length = 512, nullable = true)
    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    @Length(max = 45)
    @Column(length = 45, nullable = true)
    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    /**
     * Return the database version for the application.
     * 
     * @return the version
     */
    @Version
    public Long getVersion() {
        return version;
    }

    /**
     * Set the database version for the application.
     * 
     * @param version the version to set
     */
    @SuppressWarnings("unused")
    private void setVersion(Long version) { // NOSONAR
        this.version = version; // NOSONAR
    }

    @OneToMany(mappedBy = "application", fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE })
    public Set<ApplicationLocaleDBO> getLocales() {
        return locales;
    }

    public void setLocales(Set<ApplicationLocaleDBO> locales) {
        this.locales = locales;
    }

    /**
     * @return the creationTime
     */
    @NotNull
    @Column(name = "creationTime", insertable = true, nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Calendar getCreationTime() {
        return creationTime;
    }

    /**
     * @param creationTime
     *            the creationTime to set
     */
    public void setCreationTime(Calendar creationTime) {
        this.creationTime = creationTime;
    }

    @NotNull
    @Column(name = "lastUpdateTime", insertable = true, nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Calendar getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Calendar lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 0;
        result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
        if (!(obj instanceof ApplicationDBO)) {
            return false;
        }
        ApplicationDBO other = (ApplicationDBO) obj;
        if (uuid == null) {
            if (other.getUuid() != null) {
                return false;
            }
        }
        else if (!uuid.equals(other.getUuid())) {
            return false;
        }
        return true;
    }

}
