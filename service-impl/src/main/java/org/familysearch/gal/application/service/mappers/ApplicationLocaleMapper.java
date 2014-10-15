package org.familysearch.gal.application.service.mappers;

import org.familysearch.gal.application.dal.api.model.ApplicationLocaleDBO;
import org.familysearch.gal.application.service.api.model.ApplicationLocale;
import org.familysearch.gal.shared.mapper.Mapper;
import org.familysearch.gal.shared.mapper.MapperFactory;
import org.springframework.stereotype.Component;

/**
 * Mapper for {@link org.familysearch.gal.application.dal.api.model.ApplicationLocaleDBO} to model and vice-versa
 */
@Component
public class ApplicationLocaleMapper extends AbstractServiceMapper<ApplicationLocale, ApplicationLocaleDBO> implements
                ServiceMapper<ApplicationLocale, ApplicationLocaleDBO> {

    @Override
    public ApplicationLocale toModel(ApplicationLocaleDBO applicationLocaleDBO) {
        Mapper<ApplicationLocaleDBO, ApplicationLocale> mapperFactory = MapperFactory.instance(ApplicationLocaleDBO.class,
                                                                                               ApplicationLocale.class);
        ApplicationLocale applicationLocale = mapperFactory.from(applicationLocaleDBO);
        applicationLocale.setDescription(new String(applicationLocaleDBO.getDescriptionData()));
        return applicationLocale;
    }

    @Override
    public ApplicationLocaleDBO toDBO(ApplicationLocale applicationLocale) {
        ApplicationLocaleDBO applicationLocaleDBO = null;
        if (applicationLocale != null) {
            applicationLocaleDBO = new ApplicationLocaleDBO();
            applicationLocaleDBO.setAppSummary(applicationLocale.getAppSummary());
            applicationLocaleDBO.setTitle(applicationLocale.getTitle());
            applicationLocaleDBO.setDescriptionData(applicationLocale.getDescription().getBytes());
            applicationLocaleDBO.setLocale(applicationLocale.getLocale());
        }
        return applicationLocaleDBO;
    }
}
