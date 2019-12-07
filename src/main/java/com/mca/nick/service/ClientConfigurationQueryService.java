package com.mca.nick.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.mca.nick.domain.ClientConfiguration;
import com.mca.nick.domain.*; // for static metamodels
import com.mca.nick.repository.ClientConfigurationRepository;
import com.mca.nick.service.dto.ClientConfigurationCriteria;
import com.mca.nick.service.dto.ClientConfigurationDTO;
import com.mca.nick.service.mapper.ClientConfigurationMapper;

/**
 * Service for executing complex queries for {@link ClientConfiguration} entities in the database.
 * The main input is a {@link ClientConfigurationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClientConfigurationDTO} or a {@link Page} of {@link ClientConfigurationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClientConfigurationQueryService extends QueryService<ClientConfiguration> {

    private final Logger log = LoggerFactory.getLogger(ClientConfigurationQueryService.class);

    private final ClientConfigurationRepository clientConfigurationRepository;

    private final ClientConfigurationMapper clientConfigurationMapper;

    public ClientConfigurationQueryService(ClientConfigurationRepository clientConfigurationRepository, ClientConfigurationMapper clientConfigurationMapper) {
        this.clientConfigurationRepository = clientConfigurationRepository;
        this.clientConfigurationMapper = clientConfigurationMapper;
    }

    /**
     * Return a {@link List} of {@link ClientConfigurationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClientConfigurationDTO> findByCriteria(ClientConfigurationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ClientConfiguration> specification = createSpecification(criteria);
        return clientConfigurationMapper.toDto(clientConfigurationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClientConfigurationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClientConfigurationDTO> findByCriteria(ClientConfigurationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClientConfiguration> specification = createSpecification(criteria);
        return clientConfigurationRepository.findAll(specification, page)
            .map(clientConfigurationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClientConfigurationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ClientConfiguration> specification = createSpecification(criteria);
        return clientConfigurationRepository.count(specification);
    }

    /**
     * Function to convert ClientConfigurationCriteria to a {@link Specification}.
     */
    private Specification<ClientConfiguration> createSpecification(ClientConfigurationCriteria criteria) {
        Specification<ClientConfiguration> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ClientConfiguration_.id));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClientId(), ClientConfiguration_.clientId));
            }
            if (criteria.getAttachment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAttachment(), ClientConfiguration_.attachment));
            }
            if (criteria.getPreTableNotificationTemplate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPreTableNotificationTemplate(), ClientConfiguration_.preTableNotificationTemplate));
            }
            if (criteria.getPostTableTemplate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostTableTemplate(), ClientConfiguration_.postTableTemplate));
            }
        }
        return specification;
    }
}
