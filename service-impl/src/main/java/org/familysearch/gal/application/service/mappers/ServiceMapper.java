package org.familysearch.gal.application.service.mappers;

import java.util.Collection;
import java.util.Set;

/**
 * Mapper to map the properties from S to D
 * 
 * Where
 * the S represents the Service layer model class and
 * the D represents the Database model 
 * 
 */
public interface ServiceMapper<S, D> {

    /**
     * Maps the Database model to service model
     * 
     * @param d Database layer
     * @return service model
     */
    S toModel(D d);

    /**
     * Maps the service model to Database model
     * 
     * @param s Service model
     * @return Representation
     */
    D toDBO(S s);

    /**
     * Maps the collection of Database model to service model list
     * 
     * @param dCollection collection of Database model
     * @return set of service model
     */
    Set<S> toModel(Collection<D> dCollection);

    /**
     * Maps the collection of service model to Database model
     * 
     * @param sCollection collection of service model
     * @return set of Database models
     */
    Set<D> toDBO(Collection<S> sCollection);
}
