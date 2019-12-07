package com.mca.nick.repository;

import com.mca.nick.domain.ClientConfiguration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ClientConfiguration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientConfigurationRepository extends JpaRepository<ClientConfiguration, Long>, JpaSpecificationExecutor<ClientConfiguration> {

}
