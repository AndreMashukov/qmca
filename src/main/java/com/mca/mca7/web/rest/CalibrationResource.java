package com.mca.mca7.web.rest;

import com.mca.mca7.domain.Calibration;
import com.mca.mca7.repository.CalibrationRepository;
import com.mca.mca7.web.rest.errors.BadRequestAlertException;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mca.mca7.domain.Calibration}.
 */
@RestController
@RequestMapping("/api")
public class CalibrationResource {

    private final Logger log = LoggerFactory.getLogger(CalibrationResource.class);

    private static final String ENTITY_NAME = "calibration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CalibrationRepository calibrationRepository;

    public CalibrationResource(CalibrationRepository calibrationRepository) {
        this.calibrationRepository = calibrationRepository;
    }

    /**
     * {@code POST  /calibrations} : Create a new calibration.
     *
     * @param calibration the calibration to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new calibration, or with status {@code 400 (Bad Request)} if the calibration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/calibrations")
    public ResponseEntity<Calibration> createCalibration(@RequestBody Calibration calibration) throws URISyntaxException {
        log.debug("REST request to save Calibration : {}", calibration);
        if (calibration.getId() != null) {
            throw new BadRequestAlertException("A new calibration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Calibration result = calibrationRepository.save(calibration);
        return ResponseEntity.created(new URI("/api/calibrations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /calibrations} : Updates an existing calibration.
     *
     * @param calibration the calibration to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calibration,
     * or with status {@code 400 (Bad Request)} if the calibration is not valid,
     * or with status {@code 500 (Internal Server Error)} if the calibration couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/calibrations")
    public ResponseEntity<Calibration> updateCalibration(@RequestBody Calibration calibration) throws URISyntaxException {
        log.debug("REST request to update Calibration : {}", calibration);
        if (calibration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Calibration result = calibrationRepository.save(calibration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, calibration.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /calibrations} : get all the calibrations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of calibrations in body.
     */
    @GetMapping("/calibrations")
    public ResponseEntity<List<Calibration>> getAllCalibrations(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Calibrations");
        Page<Calibration> page = calibrationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /calibrations/:id} : get the "id" calibration.
     *
     * @param id the id of the calibration to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the calibration, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/calibrations/{id}")
    public ResponseEntity<Calibration> getCalibration(@PathVariable Long id) {
        log.debug("REST request to get Calibration : {}", id);
        Optional<Calibration> calibration = calibrationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(calibration);
    }

    /**
     * {@code DELETE  /calibrations/:id} : delete the "id" calibration.
     *
     * @param id the id of the calibration to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/calibrations/{id}")
    public ResponseEntity<Void> deleteCalibration(@PathVariable Long id) {
        log.debug("REST request to delete Calibration : {}", id);
        calibrationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
