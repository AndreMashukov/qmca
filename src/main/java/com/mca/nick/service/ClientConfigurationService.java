package com.mca.nick.service;

import com.mca.nick.service.dto.ClientConfigurationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mca.nick.domain.ClientConfiguration}.
 */
public interface ClientConfigurationService {

    /**
     * Save a clientConfiguration.
     *
     * @param clientConfigurationDTO the entity to save.
     * @return the persisted entity.
     */
    ClientConfigurationDTO save(ClientConfigurationDTO clientConfigurationDTO);

    /**
     * Get all the clientConfigurations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClientConfigurationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" clientConfiguration.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClientConfigurationDTO> findOne(Long id);

    /**
     * Delete the "id" clientConfiguration.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
