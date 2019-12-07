package com.mca.nick.service.impl;

import com.mca.nick.service.ClientConfigurationService;
import com.mca.nick.domain.ClientConfiguration;
import com.mca.nick.repository.ClientConfigurationRepository;
import com.mca.nick.service.dto.ClientConfigurationDTO;
import com.mca.nick.service.mapper.ClientConfigurationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ClientConfiguration}.
 */
@Service
@Transactional
public class ClientConfigurationServiceImpl implements ClientConfigurationService {

    private final Logger log = LoggerFactory.getLogger(ClientConfigurationServiceImpl.class);

    private final ClientConfigurationRepository clientConfigurationRepository;

    private final ClientConfigurationMapper clientConfigurationMapper;

    public ClientConfigurationServiceImpl(ClientConfigurationRepository clientConfigurationRepository, ClientConfigurationMapper clientConfigurationMapper) {
        this.clientConfigurationRepository = clientConfigurationRepository;
        this.clientConfigurationMapper = clientConfigurationMapper;
    }

    /**
     * Save a clientConfiguration.
     *
     * @param clientConfigurationDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ClientConfigurationDTO save(ClientConfigurationDTO clientConfigurationDTO) {
        log.debug("Request to save ClientConfiguration : {}", clientConfigurationDTO);
        ClientConfiguration clientConfiguration = clientConfigurationMapper.toEntity(clientConfigurationDTO);
        clientConfiguration = clientConfigurationRepository.save(clientConfiguration);
        return clientConfigurationMapper.toDto(clientConfiguration);
    }

    /**
     * Get all the clientConfigurations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClientConfigurationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClientConfigurations");
        return clientConfigurationRepository.findAll(pageable)
            .map(clientConfigurationMapper::toDto);
    }


    /**
     * Get one clientConfiguration by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClientConfigurationDTO> findOne(Long id) {
        log.debug("Request to get ClientConfiguration : {}", id);
        return clientConfigurationRepository.findById(id)
            .map(clientConfigurationMapper::toDto);
    }

    /**
     * Delete the clientConfiguration by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClientConfiguration : {}", id);
        clientConfigurationRepository.deleteById(id);
    }
}
