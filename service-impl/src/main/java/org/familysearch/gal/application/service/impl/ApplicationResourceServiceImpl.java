package org.familysearch.gal.application.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.familysearch.gal.application.dal.api.model.ApplicationDBO;
import org.familysearch.gal.application.dal.api.model.PartnerDBO;
import org.familysearch.gal.application.dal.api.repositories.ApplicationRepository;
import org.familysearch.gal.application.dal.api.repositories.PartnerRepository;
import org.familysearch.gal.application.service.api.ApplicationResourceService;
import org.familysearch.gal.application.service.api.model.Application;
import org.familysearch.gal.application.service.api.model.ApplicationLocale;
import org.familysearch.gal.application.service.api.model.Partner;
import org.familysearch.gal.application.service.mappers.ServiceMapper;
import org.familysearch.gal.shared.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation for {@link org.familysearch.gal.application.service.api.ApplicationResourceService}
 */
@Component
@Transactional("transactionManager")
public class ApplicationResourceServiceImpl implements ApplicationResourceService {

    /** Logger */
    private static final Logger LOGGER = Logger
        .getLogger(ApplicationResourceServiceImpl.class);

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private ServiceMapper<Application, ApplicationDBO> mapper;

    public ServiceMapper<Application, ApplicationDBO> getMapper() {
        return mapper;
    }

    public void setMapper(ServiceMapper<Application, ApplicationDBO> mapper) {
        this.mapper = mapper;
    }

    public ApplicationRepository getApplicationRepository() {
        return applicationRepository;
    }

    public void setApplicationRepository(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public PartnerRepository getPartnerRepository() {
        return partnerRepository;
    }

    public void setPartnerRepository(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    @Override
    public Application getApplication(UUID applictaionId) {
        ApplicationDBO applicationDBO = applicationRepository.findByUuid(applictaionId);
        Application application = mapper.toModel(applicationDBO);
        return application;
    }

    @Override
    public Application createApplication(Application application) {

        ApplicationDBO applicationDBO = mapper.toDBO(application);

        // Find the partner given in the request. If the partner doesn't exist create a default partner "FamilySearch"
        // and associate the application with it
        PartnerDBO partnerDBO = partnerRepository.findByUuid(application.getPartnerId());

        if (partnerDBO == null) {

            partnerDBO = new PartnerDBO();
            partnerDBO.setName("FamilySearch");
            partnerDBO.setEmail("xyz@familsearch.org");
            partnerDBO.setPartnerType("Volunteer");
            partnerDBO.setWebsite("https://familysearch.org/");

            partnerDBO = partnerRepository.save(partnerDBO);
        }
        applicationDBO.setPartner(partnerDBO);
        applicationDBO = applicationRepository.save(applicationDBO);

        LOGGER.info("New application created: " + applicationDBO);

        application = mapper.toModel(applicationDBO);
        application.setPartnerId(partnerDBO.getUuid());
        return application;
    }

    @Override
    public Boolean updateApplication(UUID applictaionId, Application application) {
        return true;
    }

    @Override
    public boolean deleteApplication(UUID applictaionId) {
        return true;
    }

    private Application createApplication() {
        Application application = new Application();
        application.setUuid(UUID.randomUUID());
        application.setAppName("Application 1");
        application.setAppStatus("Active");
        application.setAppVersion("beta");
        application.setAverageRating(3.5);
        application.setCreationTime(Calendar.getInstance());
        application.setLastUpdateTime(Calendar.getInstance());
        application.setDownloadLink("http://upload.wikimedia.org/wikipedia/commons/0/07/Honeycrisp-Apple.jpg");
        application.setPlatform("Windows");
        application.setPopularity(3);
        application.setRatingCount(5);

        // create partner
        Partner partner = new Partner();
        partner.setUuid(UUID.randomUUID());
        partner.setName("xyz");
        partner.setWebsite("google.com");
        partner.setEmail("xyz@gmail.com");
        partner.setPartnerType("Admin");
        partner.setCreationTime(Calendar.getInstance());
        partner.setLastUpdateTime(Calendar.getInstance());

        // application.setPartner(partner);

        // create locale
        ApplicationLocale locale = new ApplicationLocale();
        locale.setLocale(Locale.ENGLISH);
        locale.setDescription("Description of locale");
        locale.setTitle("Application");

        Set<ApplicationLocale> locales = new HashSet<ApplicationLocale>();
        locales.add(locale);

        application.setLocales(locales);

        return application;

    }

    @Override
    public List<Application> findAll(Page pageObject) {
        Sort orders = new Sort(Sort.Direction.ASC, pageObject.getSortColumn());
        Pageable pageSpecification = new PageRequest(pageObject.getPageNumber()-1, pageObject.getPageSize(), orders);
        org.springframework.data.domain.Page<ApplicationDBO> all = applicationRepository.findAll(pageSpecification);
        pageObject.setTotalRows((int) all.getTotalElements());
        List<Application> applications = new ArrayList<Application>(mapper.toModel(all.getContent()));
        return applications;
    }
}
