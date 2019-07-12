package com.mca.mca7.web.rest;

import com.mca.mca7.McaApp;
import com.mca.mca7.domain.Calibration;
import com.mca.mca7.repository.CalibrationRepository;
import com.mca.mca7.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mca.mca7.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link CalibrationResource} REST controller.
 */
@SpringBootTest(classes = McaApp.class)
public class CalibrationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REGION_FILE = "AAAAAAAAAA";
    private static final String UPDATED_REGION_FILE = "BBBBBBBBBB";

    @Autowired
    private CalibrationRepository calibrationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCalibrationMockMvc;

    private Calibration calibration;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CalibrationResource calibrationResource = new CalibrationResource(calibrationRepository);
        this.restCalibrationMockMvc = MockMvcBuilders.standaloneSetup(calibrationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Calibration createEntity(EntityManager em) {
        Calibration calibration = new Calibration()
            .name(DEFAULT_NAME)
            .regionFile(DEFAULT_REGION_FILE);
        return calibration;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Calibration createUpdatedEntity(EntityManager em) {
        Calibration calibration = new Calibration()
            .name(UPDATED_NAME)
            .regionFile(UPDATED_REGION_FILE);
        return calibration;
    }

    @BeforeEach
    public void initTest() {
        calibration = createEntity(em);
    }

    @Test
    @Transactional
    public void createCalibration() throws Exception {
        int databaseSizeBeforeCreate = calibrationRepository.findAll().size();

        // Create the Calibration
        restCalibrationMockMvc.perform(post("/api/calibrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calibration)))
            .andExpect(status().isCreated());

        // Validate the Calibration in the database
        List<Calibration> calibrationList = calibrationRepository.findAll();
        assertThat(calibrationList).hasSize(databaseSizeBeforeCreate + 1);
        Calibration testCalibration = calibrationList.get(calibrationList.size() - 1);
        assertThat(testCalibration.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCalibration.getRegionFile()).isEqualTo(DEFAULT_REGION_FILE);
    }

    @Test
    @Transactional
    public void createCalibrationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = calibrationRepository.findAll().size();

        // Create the Calibration with an existing ID
        calibration.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalibrationMockMvc.perform(post("/api/calibrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calibration)))
            .andExpect(status().isBadRequest());

        // Validate the Calibration in the database
        List<Calibration> calibrationList = calibrationRepository.findAll();
        assertThat(calibrationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCalibrations() throws Exception {
        // Initialize the database
        calibrationRepository.saveAndFlush(calibration);

        // Get all the calibrationList
        restCalibrationMockMvc.perform(get("/api/calibrations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calibration.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].regionFile").value(hasItem(DEFAULT_REGION_FILE.toString())));
    }
    
    @Test
    @Transactional
    public void getCalibration() throws Exception {
        // Initialize the database
        calibrationRepository.saveAndFlush(calibration);

        // Get the calibration
        restCalibrationMockMvc.perform(get("/api/calibrations/{id}", calibration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(calibration.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.regionFile").value(DEFAULT_REGION_FILE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCalibration() throws Exception {
        // Get the calibration
        restCalibrationMockMvc.perform(get("/api/calibrations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCalibration() throws Exception {
        // Initialize the database
        calibrationRepository.saveAndFlush(calibration);

        int databaseSizeBeforeUpdate = calibrationRepository.findAll().size();

        // Update the calibration
        Calibration updatedCalibration = calibrationRepository.findById(calibration.getId()).get();
        // Disconnect from session so that the updates on updatedCalibration are not directly saved in db
        em.detach(updatedCalibration);
        updatedCalibration
            .name(UPDATED_NAME)
            .regionFile(UPDATED_REGION_FILE);

        restCalibrationMockMvc.perform(put("/api/calibrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCalibration)))
            .andExpect(status().isOk());

        // Validate the Calibration in the database
        List<Calibration> calibrationList = calibrationRepository.findAll();
        assertThat(calibrationList).hasSize(databaseSizeBeforeUpdate);
        Calibration testCalibration = calibrationList.get(calibrationList.size() - 1);
        assertThat(testCalibration.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCalibration.getRegionFile()).isEqualTo(UPDATED_REGION_FILE);
    }

    @Test
    @Transactional
    public void updateNonExistingCalibration() throws Exception {
        int databaseSizeBeforeUpdate = calibrationRepository.findAll().size();

        // Create the Calibration

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalibrationMockMvc.perform(put("/api/calibrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calibration)))
            .andExpect(status().isBadRequest());

        // Validate the Calibration in the database
        List<Calibration> calibrationList = calibrationRepository.findAll();
        assertThat(calibrationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCalibration() throws Exception {
        // Initialize the database
        calibrationRepository.saveAndFlush(calibration);

        int databaseSizeBeforeDelete = calibrationRepository.findAll().size();

        // Delete the calibration
        restCalibrationMockMvc.perform(delete("/api/calibrations/{id}", calibration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Calibration> calibrationList = calibrationRepository.findAll();
        assertThat(calibrationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Calibration.class);
        Calibration calibration1 = new Calibration();
        calibration1.setId(1L);
        Calibration calibration2 = new Calibration();
        calibration2.setId(calibration1.getId());
        assertThat(calibration1).isEqualTo(calibration2);
        calibration2.setId(2L);
        assertThat(calibration1).isNotEqualTo(calibration2);
        calibration1.setId(null);
        assertThat(calibration1).isNotEqualTo(calibration2);
    }
}
