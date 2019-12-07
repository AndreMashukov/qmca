package com.mca.nick.web.rest;

import com.mca.nick.QmcaApp;
import com.mca.nick.domain.ClientConfiguration;
import com.mca.nick.repository.ClientConfigurationRepository;
import com.mca.nick.service.ClientConfigurationService;
import com.mca.nick.service.dto.ClientConfigurationDTO;
import com.mca.nick.service.mapper.ClientConfigurationMapper;
import com.mca.nick.web.rest.errors.ExceptionTranslator;
import com.mca.nick.service.dto.ClientConfigurationCriteria;
import com.mca.nick.service.ClientConfigurationQueryService;

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

import static com.mca.nick.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ClientConfigurationResource} REST controller.
 */
@SpringBootTest(classes = QmcaApp.class)
public class ClientConfigurationResourceIT {

    private static final Integer DEFAULT_CLIENT_ID = 1;
    private static final Integer UPDATED_CLIENT_ID = 2;

    private static final String DEFAULT_ATTACHMENT = "AAAAAAAAAA";
    private static final String UPDATED_ATTACHMENT = "BBBBBBBBBB";

    private static final String DEFAULT_PRE_TABLE_NOTIFICATION_TEMPLATE = "AAAAAAAAAA";
    private static final String UPDATED_PRE_TABLE_NOTIFICATION_TEMPLATE = "BBBBBBBBBB";

    private static final String DEFAULT_POST_TABLE_TEMPLATE = "AAAAAAAAAA";
    private static final String UPDATED_POST_TABLE_TEMPLATE = "BBBBBBBBBB";

    @Autowired
    private ClientConfigurationRepository clientConfigurationRepository;

    @Autowired
    private ClientConfigurationMapper clientConfigurationMapper;

    @Autowired
    private ClientConfigurationService clientConfigurationService;

    @Autowired
    private ClientConfigurationQueryService clientConfigurationQueryService;

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

    private MockMvc restClientConfigurationMockMvc;

    private ClientConfiguration clientConfiguration;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClientConfigurationResource clientConfigurationResource = new ClientConfigurationResource(clientConfigurationService, clientConfigurationQueryService);
        this.restClientConfigurationMockMvc = MockMvcBuilders.standaloneSetup(clientConfigurationResource)
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
    public static ClientConfiguration createEntity(EntityManager em) {
        ClientConfiguration clientConfiguration = new ClientConfiguration()
            .clientId(DEFAULT_CLIENT_ID)
            .attachment(DEFAULT_ATTACHMENT)
            .preTableNotificationTemplate(DEFAULT_PRE_TABLE_NOTIFICATION_TEMPLATE)
            .postTableTemplate(DEFAULT_POST_TABLE_TEMPLATE);
        return clientConfiguration;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientConfiguration createUpdatedEntity(EntityManager em) {
        ClientConfiguration clientConfiguration = new ClientConfiguration()
            .clientId(UPDATED_CLIENT_ID)
            .attachment(UPDATED_ATTACHMENT)
            .preTableNotificationTemplate(UPDATED_PRE_TABLE_NOTIFICATION_TEMPLATE)
            .postTableTemplate(UPDATED_POST_TABLE_TEMPLATE);
        return clientConfiguration;
    }

    @BeforeEach
    public void initTest() {
        clientConfiguration = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientConfiguration() throws Exception {
        int databaseSizeBeforeCreate = clientConfigurationRepository.findAll().size();

        // Create the ClientConfiguration
        ClientConfigurationDTO clientConfigurationDTO = clientConfigurationMapper.toDto(clientConfiguration);
        restClientConfigurationMockMvc.perform(post("/api/client-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientConfigurationDTO)))
            .andExpect(status().isCreated());

        // Validate the ClientConfiguration in the database
        List<ClientConfiguration> clientConfigurationList = clientConfigurationRepository.findAll();
        assertThat(clientConfigurationList).hasSize(databaseSizeBeforeCreate + 1);
        ClientConfiguration testClientConfiguration = clientConfigurationList.get(clientConfigurationList.size() - 1);
        assertThat(testClientConfiguration.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testClientConfiguration.getAttachment()).isEqualTo(DEFAULT_ATTACHMENT);
        assertThat(testClientConfiguration.getPreTableNotificationTemplate()).isEqualTo(DEFAULT_PRE_TABLE_NOTIFICATION_TEMPLATE);
        assertThat(testClientConfiguration.getPostTableTemplate()).isEqualTo(DEFAULT_POST_TABLE_TEMPLATE);
    }

    @Test
    @Transactional
    public void createClientConfigurationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientConfigurationRepository.findAll().size();

        // Create the ClientConfiguration with an existing ID
        clientConfiguration.setId(1L);
        ClientConfigurationDTO clientConfigurationDTO = clientConfigurationMapper.toDto(clientConfiguration);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientConfigurationMockMvc.perform(post("/api/client-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientConfigurationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClientConfiguration in the database
        List<ClientConfiguration> clientConfigurationList = clientConfigurationRepository.findAll();
        assertThat(clientConfigurationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPreTableNotificationTemplateIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientConfigurationRepository.findAll().size();
        // set the field null
        clientConfiguration.setPreTableNotificationTemplate(null);

        // Create the ClientConfiguration, which fails.
        ClientConfigurationDTO clientConfigurationDTO = clientConfigurationMapper.toDto(clientConfiguration);

        restClientConfigurationMockMvc.perform(post("/api/client-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<ClientConfiguration> clientConfigurationList = clientConfigurationRepository.findAll();
        assertThat(clientConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostTableTemplateIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientConfigurationRepository.findAll().size();
        // set the field null
        clientConfiguration.setPostTableTemplate(null);

        // Create the ClientConfiguration, which fails.
        ClientConfigurationDTO clientConfigurationDTO = clientConfigurationMapper.toDto(clientConfiguration);

        restClientConfigurationMockMvc.perform(post("/api/client-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<ClientConfiguration> clientConfigurationList = clientConfigurationRepository.findAll();
        assertThat(clientConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClientConfigurations() throws Exception {
        // Initialize the database
        clientConfigurationRepository.saveAndFlush(clientConfiguration);

        // Get all the clientConfigurationList
        restClientConfigurationMockMvc.perform(get("/api/client-configurations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID)))
            .andExpect(jsonPath("$.[*].attachment").value(hasItem(DEFAULT_ATTACHMENT.toString())))
            .andExpect(jsonPath("$.[*].preTableNotificationTemplate").value(hasItem(DEFAULT_PRE_TABLE_NOTIFICATION_TEMPLATE.toString())))
            .andExpect(jsonPath("$.[*].postTableTemplate").value(hasItem(DEFAULT_POST_TABLE_TEMPLATE.toString())));
    }
    
    @Test
    @Transactional
    public void getClientConfiguration() throws Exception {
        // Initialize the database
        clientConfigurationRepository.saveAndFlush(clientConfiguration);

        // Get the clientConfiguration
        restClientConfigurationMockMvc.perform(get("/api/client-configurations/{id}", clientConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientConfiguration.getId().intValue()))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID))
            .andExpect(jsonPath("$.attachment").value(DEFAULT_ATTACHMENT.toString()))
            .andExpect(jsonPath("$.preTableNotificationTemplate").value(DEFAULT_PRE_TABLE_NOTIFICATION_TEMPLATE.toString()))
            .andExpect(jsonPath("$.postTableTemplate").value(DEFAULT_POST_TABLE_TEMPLATE.toString()));
    }

    @Test
    @Transactional
    public void getAllClientConfigurationsByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        clientConfigurationRepository.saveAndFlush(clientConfiguration);

        // Get all the clientConfigurationList where clientId equals to DEFAULT_CLIENT_ID
        defaultClientConfigurationShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the clientConfigurationList where clientId equals to UPDATED_CLIENT_ID
        defaultClientConfigurationShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllClientConfigurationsByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        clientConfigurationRepository.saveAndFlush(clientConfiguration);

        // Get all the clientConfigurationList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultClientConfigurationShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the clientConfigurationList where clientId equals to UPDATED_CLIENT_ID
        defaultClientConfigurationShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllClientConfigurationsByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientConfigurationRepository.saveAndFlush(clientConfiguration);

        // Get all the clientConfigurationList where clientId is not null
        defaultClientConfigurationShouldBeFound("clientId.specified=true");

        // Get all the clientConfigurationList where clientId is null
        defaultClientConfigurationShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientConfigurationsByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientConfigurationRepository.saveAndFlush(clientConfiguration);

        // Get all the clientConfigurationList where clientId greater than or equals to DEFAULT_CLIENT_ID
        defaultClientConfigurationShouldBeFound("clientId.greaterOrEqualThan=" + DEFAULT_CLIENT_ID);

        // Get all the clientConfigurationList where clientId greater than or equals to UPDATED_CLIENT_ID
        defaultClientConfigurationShouldNotBeFound("clientId.greaterOrEqualThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllClientConfigurationsByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        clientConfigurationRepository.saveAndFlush(clientConfiguration);

        // Get all the clientConfigurationList where clientId less than or equals to DEFAULT_CLIENT_ID
        defaultClientConfigurationShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the clientConfigurationList where clientId less than or equals to UPDATED_CLIENT_ID
        defaultClientConfigurationShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllClientConfigurationsByAttachmentIsEqualToSomething() throws Exception {
        // Initialize the database
        clientConfigurationRepository.saveAndFlush(clientConfiguration);

        // Get all the clientConfigurationList where attachment equals to DEFAULT_ATTACHMENT
        defaultClientConfigurationShouldBeFound("attachment.equals=" + DEFAULT_ATTACHMENT);

        // Get all the clientConfigurationList where attachment equals to UPDATED_ATTACHMENT
        defaultClientConfigurationShouldNotBeFound("attachment.equals=" + UPDATED_ATTACHMENT);
    }

    @Test
    @Transactional
    public void getAllClientConfigurationsByAttachmentIsInShouldWork() throws Exception {
        // Initialize the database
        clientConfigurationRepository.saveAndFlush(clientConfiguration);

        // Get all the clientConfigurationList where attachment in DEFAULT_ATTACHMENT or UPDATED_ATTACHMENT
        defaultClientConfigurationShouldBeFound("attachment.in=" + DEFAULT_ATTACHMENT + "," + UPDATED_ATTACHMENT);

        // Get all the clientConfigurationList where attachment equals to UPDATED_ATTACHMENT
        defaultClientConfigurationShouldNotBeFound("attachment.in=" + UPDATED_ATTACHMENT);
    }

    @Test
    @Transactional
    public void getAllClientConfigurationsByAttachmentIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientConfigurationRepository.saveAndFlush(clientConfiguration);

        // Get all the clientConfigurationList where attachment is not null
        defaultClientConfigurationShouldBeFound("attachment.specified=true");

        // Get all the clientConfigurationList where attachment is null
        defaultClientConfigurationShouldNotBeFound("attachment.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientConfigurationsByPreTableNotificationTemplateIsEqualToSomething() throws Exception {
        // Initialize the database
        clientConfigurationRepository.saveAndFlush(clientConfiguration);

        // Get all the clientConfigurationList where preTableNotificationTemplate equals to DEFAULT_PRE_TABLE_NOTIFICATION_TEMPLATE
        defaultClientConfigurationShouldBeFound("preTableNotificationTemplate.equals=" + DEFAULT_PRE_TABLE_NOTIFICATION_TEMPLATE);

        // Get all the clientConfigurationList where preTableNotificationTemplate equals to UPDATED_PRE_TABLE_NOTIFICATION_TEMPLATE
        defaultClientConfigurationShouldNotBeFound("preTableNotificationTemplate.equals=" + UPDATED_PRE_TABLE_NOTIFICATION_TEMPLATE);
    }

    @Test
    @Transactional
    public void getAllClientConfigurationsByPreTableNotificationTemplateIsInShouldWork() throws Exception {
        // Initialize the database
        clientConfigurationRepository.saveAndFlush(clientConfiguration);

        // Get all the clientConfigurationList where preTableNotificationTemplate in DEFAULT_PRE_TABLE_NOTIFICATION_TEMPLATE or UPDATED_PRE_TABLE_NOTIFICATION_TEMPLATE
        defaultClientConfigurationShouldBeFound("preTableNotificationTemplate.in=" + DEFAULT_PRE_TABLE_NOTIFICATION_TEMPLATE + "," + UPDATED_PRE_TABLE_NOTIFICATION_TEMPLATE);

        // Get all the clientConfigurationList where preTableNotificationTemplate equals to UPDATED_PRE_TABLE_NOTIFICATION_TEMPLATE
        defaultClientConfigurationShouldNotBeFound("preTableNotificationTemplate.in=" + UPDATED_PRE_TABLE_NOTIFICATION_TEMPLATE);
    }

    @Test
    @Transactional
    public void getAllClientConfigurationsByPreTableNotificationTemplateIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientConfigurationRepository.saveAndFlush(clientConfiguration);

        // Get all the clientConfigurationList where preTableNotificationTemplate is not null
        defaultClientConfigurationShouldBeFound("preTableNotificationTemplate.specified=true");

        // Get all the clientConfigurationList where preTableNotificationTemplate is null
        defaultClientConfigurationShouldNotBeFound("preTableNotificationTemplate.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientConfigurationsByPostTableTemplateIsEqualToSomething() throws Exception {
        // Initialize the database
        clientConfigurationRepository.saveAndFlush(clientConfiguration);

        // Get all the clientConfigurationList where postTableTemplate equals to DEFAULT_POST_TABLE_TEMPLATE
        defaultClientConfigurationShouldBeFound("postTableTemplate.equals=" + DEFAULT_POST_TABLE_TEMPLATE);

        // Get all the clientConfigurationList where postTableTemplate equals to UPDATED_POST_TABLE_TEMPLATE
        defaultClientConfigurationShouldNotBeFound("postTableTemplate.equals=" + UPDATED_POST_TABLE_TEMPLATE);
    }

    @Test
    @Transactional
    public void getAllClientConfigurationsByPostTableTemplateIsInShouldWork() throws Exception {
        // Initialize the database
        clientConfigurationRepository.saveAndFlush(clientConfiguration);

        // Get all the clientConfigurationList where postTableTemplate in DEFAULT_POST_TABLE_TEMPLATE or UPDATED_POST_TABLE_TEMPLATE
        defaultClientConfigurationShouldBeFound("postTableTemplate.in=" + DEFAULT_POST_TABLE_TEMPLATE + "," + UPDATED_POST_TABLE_TEMPLATE);

        // Get all the clientConfigurationList where postTableTemplate equals to UPDATED_POST_TABLE_TEMPLATE
        defaultClientConfigurationShouldNotBeFound("postTableTemplate.in=" + UPDATED_POST_TABLE_TEMPLATE);
    }

    @Test
    @Transactional
    public void getAllClientConfigurationsByPostTableTemplateIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientConfigurationRepository.saveAndFlush(clientConfiguration);

        // Get all the clientConfigurationList where postTableTemplate is not null
        defaultClientConfigurationShouldBeFound("postTableTemplate.specified=true");

        // Get all the clientConfigurationList where postTableTemplate is null
        defaultClientConfigurationShouldNotBeFound("postTableTemplate.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClientConfigurationShouldBeFound(String filter) throws Exception {
        restClientConfigurationMockMvc.perform(get("/api/client-configurations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID)))
            .andExpect(jsonPath("$.[*].attachment").value(hasItem(DEFAULT_ATTACHMENT)))
            .andExpect(jsonPath("$.[*].preTableNotificationTemplate").value(hasItem(DEFAULT_PRE_TABLE_NOTIFICATION_TEMPLATE)))
            .andExpect(jsonPath("$.[*].postTableTemplate").value(hasItem(DEFAULT_POST_TABLE_TEMPLATE)));

        // Check, that the count call also returns 1
        restClientConfigurationMockMvc.perform(get("/api/client-configurations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClientConfigurationShouldNotBeFound(String filter) throws Exception {
        restClientConfigurationMockMvc.perform(get("/api/client-configurations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClientConfigurationMockMvc.perform(get("/api/client-configurations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingClientConfiguration() throws Exception {
        // Get the clientConfiguration
        restClientConfigurationMockMvc.perform(get("/api/client-configurations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientConfiguration() throws Exception {
        // Initialize the database
        clientConfigurationRepository.saveAndFlush(clientConfiguration);

        int databaseSizeBeforeUpdate = clientConfigurationRepository.findAll().size();

        // Update the clientConfiguration
        ClientConfiguration updatedClientConfiguration = clientConfigurationRepository.findById(clientConfiguration.getId()).get();
        // Disconnect from session so that the updates on updatedClientConfiguration are not directly saved in db
        em.detach(updatedClientConfiguration);
        updatedClientConfiguration
            .clientId(UPDATED_CLIENT_ID)
            .attachment(UPDATED_ATTACHMENT)
            .preTableNotificationTemplate(UPDATED_PRE_TABLE_NOTIFICATION_TEMPLATE)
            .postTableTemplate(UPDATED_POST_TABLE_TEMPLATE);
        ClientConfigurationDTO clientConfigurationDTO = clientConfigurationMapper.toDto(updatedClientConfiguration);

        restClientConfigurationMockMvc.perform(put("/api/client-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientConfigurationDTO)))
            .andExpect(status().isOk());

        // Validate the ClientConfiguration in the database
        List<ClientConfiguration> clientConfigurationList = clientConfigurationRepository.findAll();
        assertThat(clientConfigurationList).hasSize(databaseSizeBeforeUpdate);
        ClientConfiguration testClientConfiguration = clientConfigurationList.get(clientConfigurationList.size() - 1);
        assertThat(testClientConfiguration.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testClientConfiguration.getAttachment()).isEqualTo(UPDATED_ATTACHMENT);
        assertThat(testClientConfiguration.getPreTableNotificationTemplate()).isEqualTo(UPDATED_PRE_TABLE_NOTIFICATION_TEMPLATE);
        assertThat(testClientConfiguration.getPostTableTemplate()).isEqualTo(UPDATED_POST_TABLE_TEMPLATE);
    }

    @Test
    @Transactional
    public void updateNonExistingClientConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = clientConfigurationRepository.findAll().size();

        // Create the ClientConfiguration
        ClientConfigurationDTO clientConfigurationDTO = clientConfigurationMapper.toDto(clientConfiguration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientConfigurationMockMvc.perform(put("/api/client-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientConfigurationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClientConfiguration in the database
        List<ClientConfiguration> clientConfigurationList = clientConfigurationRepository.findAll();
        assertThat(clientConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClientConfiguration() throws Exception {
        // Initialize the database
        clientConfigurationRepository.saveAndFlush(clientConfiguration);

        int databaseSizeBeforeDelete = clientConfigurationRepository.findAll().size();

        // Delete the clientConfiguration
        restClientConfigurationMockMvc.perform(delete("/api/client-configurations/{id}", clientConfiguration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ClientConfiguration> clientConfigurationList = clientConfigurationRepository.findAll();
        assertThat(clientConfigurationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientConfiguration.class);
        ClientConfiguration clientConfiguration1 = new ClientConfiguration();
        clientConfiguration1.setId(1L);
        ClientConfiguration clientConfiguration2 = new ClientConfiguration();
        clientConfiguration2.setId(clientConfiguration1.getId());
        assertThat(clientConfiguration1).isEqualTo(clientConfiguration2);
        clientConfiguration2.setId(2L);
        assertThat(clientConfiguration1).isNotEqualTo(clientConfiguration2);
        clientConfiguration1.setId(null);
        assertThat(clientConfiguration1).isNotEqualTo(clientConfiguration2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientConfigurationDTO.class);
        ClientConfigurationDTO clientConfigurationDTO1 = new ClientConfigurationDTO();
        clientConfigurationDTO1.setId(1L);
        ClientConfigurationDTO clientConfigurationDTO2 = new ClientConfigurationDTO();
        assertThat(clientConfigurationDTO1).isNotEqualTo(clientConfigurationDTO2);
        clientConfigurationDTO2.setId(clientConfigurationDTO1.getId());
        assertThat(clientConfigurationDTO1).isEqualTo(clientConfigurationDTO2);
        clientConfigurationDTO2.setId(2L);
        assertThat(clientConfigurationDTO1).isNotEqualTo(clientConfigurationDTO2);
        clientConfigurationDTO1.setId(null);
        assertThat(clientConfigurationDTO1).isNotEqualTo(clientConfigurationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clientConfigurationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clientConfigurationMapper.fromId(null)).isNull();
    }
}
