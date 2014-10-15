package org.familysearch.gal.application.service.mappers;

import java.util.*;

import org.springframework.stereotype.Component;

/**
 * Abstract implementation for the {@link org.familysearch.idx.message.service.mapper.ServiceMapper}
 */
@Component
public abstract class AbstractServiceMapper<S, D> implements ServiceMapper<S, D> {

    /**
     * {@inheritDoc}
     */
    public abstract S toModel(D d);

    /**
     * {@inheritDoc}
     */
    public abstract D toDBO(S s);

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<S> toModel(Collection<D> dCollection) {
        Set<S> sSet = new LinkedHashSet<S>();
        if (dCollection != null) {
            for (D d : dCollection) {
                sSet.add(toModel(d));
            }
        }
        return sSet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<D> toDBO(Collection<S> sCollection) {
        Set<D> dSet = new HashSet<D>();
        if (sCollection != null) {
            for (S s : sCollection) {
                dSet.add(toDBO(s));
            }
        }
        return dSet;
    }
}
