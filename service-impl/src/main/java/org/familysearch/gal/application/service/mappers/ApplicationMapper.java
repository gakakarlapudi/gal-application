package org.familysearch.gal.application.service.mappers;

import org.familysearch.gal.application.dal.api.model.ApplicationDBO;
import org.familysearch.gal.application.dal.api.model.ApplicationLocaleDBO;
import org.familysearch.gal.application.dal.api.model.PartnerDBO;
import org.familysearch.gal.application.service.api.model.Application;
import org.familysearch.gal.application.service.api.model.ApplicationLocale;
import org.familysearch.gal.application.service.api.model.Partner;
import org.familysearch.gal.shared.mapper.Mapper;
import org.familysearch.gal.shared.mapper.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper which converts the Application Model to DBO and vice versa
 */
@Component
public class ApplicationMapper extends AbstractServiceMapper<Application, ApplicationDBO> implements
                ServiceMapper<Application, ApplicationDBO> {

    @Autowired
    private ServiceMapper<ApplicationLocale, ApplicationLocaleDBO> applicationLocaleMapper;


    public ServiceMapper<ApplicationLocale, ApplicationLocaleDBO> getApplicationLocaleMapper() {
        return applicationLocaleMapper;
    }

    public void setApplicationLocaleMapper(ServiceMapper<ApplicationLocale, ApplicationLocaleDBO> applicationLocaleMapper) {
        this.applicationLocaleMapper = applicationLocaleMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Application toModel(ApplicationDBO applicationDBO) {
        Mapper<ApplicationDBO, Application> mapperFactory = MapperFactory.instance(ApplicationDBO.class, Application.class);
        Application application = mapperFactory.from(applicationDBO);
        if(application != null) {
            application.setUuid(applicationDBO.getUuid());
            application.setLocales(applicationLocaleMapper.toModel(applicationDBO.getLocales()));
            application.setPartnerId(applicationDBO.getPartner().getUuid());
        }
        return application;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApplicationDBO toDBO(Application application) {
        if(application == null) {
            return null;
        }
        ApplicationDBO applicationDBO = new ApplicationDBO();
        
        Mapper<Application, ApplicationDBO> mapperFactory = MapperFactory.instance(Application.class, ApplicationDBO.class);
        applicationDBO = mapperFactory.from(application);
        applicationDBO.setLocales(applicationLocaleMapper.toDBO(application.getLocales()));
        return applicationDBO;
    }
}
