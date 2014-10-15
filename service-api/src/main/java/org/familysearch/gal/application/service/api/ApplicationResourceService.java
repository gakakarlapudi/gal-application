package org.familysearch.gal.application.service.api;

import java.util.List;
import java.util.UUID;

import org.familysearch.gal.application.service.api.model.Application;
import org.familysearch.gal.shared.model.Page;

/**
 * Resource Services for Application
 */
public interface ApplicationResourceService {
    
    /**
     * Return list of all application resources
     * 
     * @return list of all application resources
     */
    List<Application> findAll(Page pageObject);

    /**
     * Retrieve the application which is identified by the unique application identifier
     * 
     * @param applictaionId unique application identifier
     * @return {@link org.familysearch.gal.application.service.api.model.Application} corresponding to given application id.
     */
	Application getApplication(UUID applictaionId);

    /**
     * Responsible to create the application from the given {@link org.familysearch.gal.application.service.api.model.Application}
     * 
     * @param application application to be created
     * @return Created {@link org.familysearch.gal.application.service.api.model.Application}
     */
	Application createApplication(Application application);    

    
    /**
     * Responsible to update the application with new details in the application
     *
     * @param application application to be updated
     * @return TRUE on successful update of application
     */
    Boolean updateApplication(UUID applictaionId, Application application);

    /**
     * Responsible to delete applications based on the given application identifier
     * 
     * @param applictaionId unique application identifier
     * @return TRUE of delete successful otherwise FALSE
     */
    boolean deleteApplication(UUID applictaionId);

}

