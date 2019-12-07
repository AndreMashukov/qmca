package com.mca.nick.web.rest;

import com.mca.nick.service.ClientConfigurationService;
import com.mca.nick.web.rest.errors.BadRequestAlertException;
import com.mca.nick.service.dto.ClientConfigurationDTO;
import com.mca.nick.service.dto.ClientConfigurationCriteria;
import com.mca.nick.service.ClientConfigurationQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mca.nick.domain.ClientConfiguration}.
 */
@RestController
@RequestMapping("/api")
public class ClientConfigurationResource {

    private final Logger log = LoggerFactory.getLogger(ClientConfigurationResource.class);

    private static final String ENTITY_NAME = "clientConfiguration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientConfigurationService clientConfigurationService;

    private final ClientConfigurationQueryService clientConfigurationQueryService;

    public ClientConfigurationResource(ClientConfigurationService clientConfigurationService, ClientConfigurationQueryService clientConfigurationQueryService) {
        this.clientConfigurationService = clientConfigurationService;
        this.clientConfigurationQueryService = clientConfigurationQueryService;
    }

    /**
     * {@code POST  /client-configurations} : Create a new clientConfiguration.
     *
     * @param clientConfigurationDTO the clientConfigurationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clientConfigurationDTO, or with status {@code 400 (Bad Request)} if the clientConfiguration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/client-configurations")
    public ResponseEntity<ClientConfigurationDTO> createClientConfiguration(@Valid @RequestBody ClientConfigurationDTO clientConfigurationDTO) throws URISyntaxException {
        log.debug("REST request to save ClientConfiguration : {}", clientConfigurationDTO);
        if (clientConfigurationDTO.getId() != null) {
            throw new BadRequestAlertException("A new clientConfiguration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientConfigurationDTO result = clientConfigurationService.save(clientConfigurationDTO);
        return ResponseEntity.created(new URI("/api/client-configurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /client-configurations} : Updates an existing clientConfiguration.
     *
     * @param clientConfigurationDTO the clientConfigurationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientConfigurationDTO,
     * or with status {@code 400 (Bad Request)} if the clientConfigurationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clientConfigurationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/client-configurations")
    public ResponseEntity<ClientConfigurationDTO> updateClientConfiguration(@Valid @RequestBody ClientConfigurationDTO clientConfigurationDTO) throws URISyntaxException {
        log.debug("REST request to update ClientConfiguration : {}", clientConfigurationDTO);
        if (clientConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClientConfigurationDTO result = clientConfigurationService.save(clientConfigurationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, clientConfigurationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /client-configurations} : get all the clientConfigurations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientConfigurations in body.
     */
    @GetMapping("/client-configurations")
    public ResponseEntity<List<ClientConfigurationDTO>> getAllClientConfigurations(ClientConfigurationCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ClientConfigurations by criteria: {}", criteria);
        Page<ClientConfigurationDTO> page = clientConfigurationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /client-configurations/count} : count all the clientConfigurations.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/client-configurations/count")
    public ResponseEntity<Long> countClientConfigurations(ClientConfigurationCriteria criteria) {
        log.debug("REST request to count ClientConfigurations by criteria: {}", criteria);
        return ResponseEntity.ok().body(clientConfigurationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /client-configurations/:id} : get the "id" clientConfiguration.
     *
     * @param id the id of the clientConfigurationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientConfigurationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/client-configurations/{id}")
    public ResponseEntity<ClientConfigurationDTO> getClientConfiguration(@PathVariable Long id) {
        log.debug("REST request to get ClientConfiguration : {}", id);
        Optional<ClientConfigurationDTO> clientConfigurationDTO = clientConfigurationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clientConfigurationDTO);
    }

    /**
     * {@code DELETE  /client-configurations/:id} : delete the "id" clientConfiguration.
     *
     * @param id the id of the clientConfigurationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/client-configurations/{id}")
    public ResponseEntity<Void> deleteClientConfiguration(@PathVariable Long id) {
        log.debug("REST request to delete ClientConfiguration : {}", id);
        clientConfigurationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
