package org.familysearch.gal.application.dal.api.repositories;

import java.util.UUID;

import org.familysearch.gal.application.dal.api.model.PartnerDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to for {@link org.familysearch.gal.application.dal.api.model.PartnerDBO}
 */
@Repository
public interface PartnerRepository extends JpaRepository<PartnerDBO, Long>, JpaSpecificationExecutor,
                PagingAndSortingRepository<PartnerDBO, Long> {

    PartnerDBO findByUuid(UUID uuid);

}
