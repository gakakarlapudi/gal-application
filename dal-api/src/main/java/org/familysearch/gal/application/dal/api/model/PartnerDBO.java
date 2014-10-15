/**
 * Copyright © 2010 Intellectual Reserve, Inc. All Rights reserved.
 */

package org.familysearch.gal.application.dal.api.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
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
@Table(name = "partner", uniqueConstraints = @UniqueConstraint(columnNames = { "uuid", "email" }))
public class PartnerDBO extends AbstractEntity<PartnerDBO> {

    private static final long serialVersionUID = 873245192978870789L;

    private static final ModelValidator<PartnerDBO> validator = new GenericModelValidator<PartnerDBO>();

    private UUID uuid = UUID.randomUUID();
    private Long id;
    private String name;
    private String email;
    private String website;
    private String partnerType;
    private Calendar creationTime = Calendar.getInstance();
    private Calendar lastUpdateTime = Calendar.getInstance();
    
    List<ApplicationDBO> applications = new ArrayList<ApplicationDBO>();

    /**
     * Default constructor
     */
    public PartnerDBO() {

    }

    /**
     * Parametrized Constructor
     */
    public PartnerDBO(PartnerDBO other) {
        this.uuid = other.getUuid();
        this.name = other.getName();
        this.email = other.getEmail();
        this.website = other.getWebsite();
        this.partnerType = other.getPartnerType();
        this.creationTime = other.getCreationTime();
        this.lastUpdateTime = other.getLastUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    protected ModelValidator<PartnerDBO> validator() {
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
    @Column(name = "partner_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partner_seq")
    @org.hibernate.annotations.GenericGenerator(name = "partner_seq", strategy = "org.hibernate.id.enhanced.TableGenerator",
                    parameters = {
                                  @org.hibernate.annotations.Parameter(name = "segment_value", value = "partner_seq"),
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
    
    @OneToMany(mappedBy = "partner", fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE })
    public List<ApplicationDBO> getApplications() {
        return applications;
    }

    public void setApplications(List<ApplicationDBO> applications) {
        this.applications = applications;
    }

    @NotNull
    @Length(max = 128)
    @Column(name = "name", length = 128, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(max = 128)
    @Column(name = "email", length = 128, nullable = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Length(max = 45)
    @Column(name = "website", length = 45, nullable = true)
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Length(max = 35)
    @Column(name = "partner_type", length = 35, nullable = true)
    public String getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(String partnerType) {
        this.partnerType = partnerType;
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
        if (!(obj instanceof PartnerDBO)) {
            return false;
        }
        PartnerDBO other = (PartnerDBO) obj;
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
