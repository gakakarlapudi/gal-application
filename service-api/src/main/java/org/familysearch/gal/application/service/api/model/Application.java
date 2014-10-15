package org.familysearch.gal.application.service.api.model;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.familysearch.gal.application.dal.api.util.GenericModelValidator;
import org.familysearch.gal.application.dal.api.util.ModelValidator;

public class Application {

    private static final long serialVersionUID = 1L;

    private static final ModelValidator<Application> validator = new GenericModelValidator<Application>();

    @NotNull
    private UUID uuid;
    private UUID partnerId;
    @Size(max = 45)
    private String appStatus;
    @Size(max = 255)
    private String appName;
    @Size(max = 45)
    private String appVersion;
    @Size(max = 512)
    private String downloadLink;
    @Size(max = 45)
    private String platform;
    private double averageRating;
    private int ratingCount;
    private int popularity;
    private Calendar creationTime;
    private Calendar lastUpdateTime;

    Set<ApplicationLocale> locales = new HashSet<ApplicationLocale>();

    public Application() {
    }

    /**
     * Copy constructor.
     * 
     * @param that instance to copy
     */
    public Application(Application that) {
        this.uuid = that.getUuid();
        this.partnerId = that.getPartnerId();
        this.appStatus = that.getAppStatus();
        this.appName = that.getAppName();
        this.appVersion = that.getAppVersion();
        this.downloadLink = that.getDownloadLink();
        this.platform = that.getPlatform();
        this.averageRating = that.getAverageRating();
        this.ratingCount = that.getRatingCount();
        this.popularity = that.getPopularity();
        this.creationTime = that.getCreationTime();
        this.lastUpdateTime = that.getLastUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    protected ModelValidator<Application> validator() {
        return validator;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(UUID partnerId) {
        this.partnerId = partnerId;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public Calendar getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Calendar creationTime) {
        this.creationTime = creationTime;
    }

    public Calendar getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Calendar lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Set<ApplicationLocale> getLocales() {
        return locales;
    }

    public void setLocales(Set<ApplicationLocale> locales) {
        this.locales = locales;
    }

}
