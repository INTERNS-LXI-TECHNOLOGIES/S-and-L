package com.byta.aayos.web.rest;

import com.byta.aayos.HealthInsuranceApp;

import com.byta.aayos.domain.HealthInsurance;
import com.byta.aayos.repository.HealthInsuranceRepository;
import com.byta.aayos.service.HealthInsuranceService;
import com.byta.aayos.service.dto.HealthInsuranceDTO;
import com.byta.aayos.service.mapper.HealthInsuranceMapper;
import com.byta.aayos.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.byta.aayos.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the HealthInsuranceResource REST controller.
 *
 * @see HealthInsuranceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HealthInsuranceApp.class)
public class HealthInsuranceResourceIntTest {

    private static final Long DEFAULT_PATIENT_ID = 1L;
    private static final Long UPDATED_PATIENT_ID = 2L;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_INSURANCE_NUMBER = 1L;
    private static final Long UPDATED_INSURANCE_NUMBER = 2L;

    private static final LocalDate DEFAULT_EXPIRY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPIRY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COVERAGER = "AAAAAAAAAA";
    private static final String UPDATED_COVERAGER = "BBBBBBBBBB";

    @Autowired
    private HealthInsuranceRepository healthInsuranceRepository;

    @Autowired
    private HealthInsuranceMapper healthInsuranceMapper;

    @Autowired
    private HealthInsuranceService healthInsuranceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHealthInsuranceMockMvc;

    private HealthInsurance healthInsurance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HealthInsuranceResource healthInsuranceResource = new HealthInsuranceResource(healthInsuranceService);
        this.restHealthInsuranceMockMvc = MockMvcBuilders.standaloneSetup(healthInsuranceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HealthInsurance createEntity(EntityManager em) {
        HealthInsurance healthInsurance = new HealthInsurance()
            .patientId(DEFAULT_PATIENT_ID)
            .createdDate(DEFAULT_CREATED_DATE)
            .insuranceNumber(DEFAULT_INSURANCE_NUMBER)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .coverager(DEFAULT_COVERAGER);
        return healthInsurance;
    }

    @Before
    public void initTest() {
        healthInsurance = createEntity(em);
    }

    @Test
    @Transactional
    public void createHealthInsurance() throws Exception {
        int databaseSizeBeforeCreate = healthInsuranceRepository.findAll().size();

        // Create the HealthInsurance
        HealthInsuranceDTO healthInsuranceDTO = healthInsuranceMapper.toDto(healthInsurance);
        restHealthInsuranceMockMvc.perform(post("/api/health-insurances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthInsuranceDTO)))
            .andExpect(status().isCreated());

        // Validate the HealthInsurance in the database
        List<HealthInsurance> healthInsuranceList = healthInsuranceRepository.findAll();
        assertThat(healthInsuranceList).hasSize(databaseSizeBeforeCreate + 1);
        HealthInsurance testHealthInsurance = healthInsuranceList.get(healthInsuranceList.size() - 1);
        assertThat(testHealthInsurance.getPatientId()).isEqualTo(DEFAULT_PATIENT_ID);
        assertThat(testHealthInsurance.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testHealthInsurance.getInsuranceNumber()).isEqualTo(DEFAULT_INSURANCE_NUMBER);
        assertThat(testHealthInsurance.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testHealthInsurance.getCoverager()).isEqualTo(DEFAULT_COVERAGER);
    }

    @Test
    @Transactional
    public void createHealthInsuranceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = healthInsuranceRepository.findAll().size();

        // Create the HealthInsurance with an existing ID
        healthInsurance.setId(1L);
        HealthInsuranceDTO healthInsuranceDTO = healthInsuranceMapper.toDto(healthInsurance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHealthInsuranceMockMvc.perform(post("/api/health-insurances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthInsuranceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HealthInsurance in the database
        List<HealthInsurance> healthInsuranceList = healthInsuranceRepository.findAll();
        assertThat(healthInsuranceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHealthInsurances() throws Exception {
        // Initialize the database
        healthInsuranceRepository.saveAndFlush(healthInsurance);

        // Get all the healthInsuranceList
        restHealthInsuranceMockMvc.perform(get("/api/health-insurances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(healthInsurance.getId().intValue())))
            .andExpect(jsonPath("$.[*].patientId").value(hasItem(DEFAULT_PATIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].insuranceNumber").value(hasItem(DEFAULT_INSURANCE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].coverager").value(hasItem(DEFAULT_COVERAGER.toString())));
    }

    @Test
    @Transactional
    public void getHealthInsurance() throws Exception {
        // Initialize the database
        healthInsuranceRepository.saveAndFlush(healthInsurance);

        // Get the healthInsurance
        restHealthInsuranceMockMvc.perform(get("/api/health-insurances/{id}", healthInsurance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(healthInsurance.getId().intValue()))
            .andExpect(jsonPath("$.patientId").value(DEFAULT_PATIENT_ID.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.insuranceNumber").value(DEFAULT_INSURANCE_NUMBER.intValue()))
            .andExpect(jsonPath("$.expiryDate").value(DEFAULT_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.coverager").value(DEFAULT_COVERAGER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHealthInsurance() throws Exception {
        // Get the healthInsurance
        restHealthInsuranceMockMvc.perform(get("/api/health-insurances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHealthInsurance() throws Exception {
        // Initialize the database
        healthInsuranceRepository.saveAndFlush(healthInsurance);
        int databaseSizeBeforeUpdate = healthInsuranceRepository.findAll().size();

        // Update the healthInsurance
        HealthInsurance updatedHealthInsurance = healthInsuranceRepository.findOne(healthInsurance.getId());
        // Disconnect from session so that the updates on updatedHealthInsurance are not directly saved in db
        em.detach(updatedHealthInsurance);
        updatedHealthInsurance
            .patientId(UPDATED_PATIENT_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .insuranceNumber(UPDATED_INSURANCE_NUMBER)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .coverager(UPDATED_COVERAGER);
        HealthInsuranceDTO healthInsuranceDTO = healthInsuranceMapper.toDto(updatedHealthInsurance);

        restHealthInsuranceMockMvc.perform(put("/api/health-insurances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthInsuranceDTO)))
            .andExpect(status().isOk());

        // Validate the HealthInsurance in the database
        List<HealthInsurance> healthInsuranceList = healthInsuranceRepository.findAll();
        assertThat(healthInsuranceList).hasSize(databaseSizeBeforeUpdate);
        HealthInsurance testHealthInsurance = healthInsuranceList.get(healthInsuranceList.size() - 1);
        assertThat(testHealthInsurance.getPatientId()).isEqualTo(UPDATED_PATIENT_ID);
        assertThat(testHealthInsurance.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testHealthInsurance.getInsuranceNumber()).isEqualTo(UPDATED_INSURANCE_NUMBER);
        assertThat(testHealthInsurance.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testHealthInsurance.getCoverager()).isEqualTo(UPDATED_COVERAGER);
    }

    @Test
    @Transactional
    public void updateNonExistingHealthInsurance() throws Exception {
        int databaseSizeBeforeUpdate = healthInsuranceRepository.findAll().size();

        // Create the HealthInsurance
        HealthInsuranceDTO healthInsuranceDTO = healthInsuranceMapper.toDto(healthInsurance);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHealthInsuranceMockMvc.perform(put("/api/health-insurances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthInsuranceDTO)))
            .andExpect(status().isCreated());

        // Validate the HealthInsurance in the database
        List<HealthInsurance> healthInsuranceList = healthInsuranceRepository.findAll();
        assertThat(healthInsuranceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHealthInsurance() throws Exception {
        // Initialize the database
        healthInsuranceRepository.saveAndFlush(healthInsurance);
        int databaseSizeBeforeDelete = healthInsuranceRepository.findAll().size();

        // Get the healthInsurance
        restHealthInsuranceMockMvc.perform(delete("/api/health-insurances/{id}", healthInsurance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HealthInsurance> healthInsuranceList = healthInsuranceRepository.findAll();
        assertThat(healthInsuranceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HealthInsurance.class);
        HealthInsurance healthInsurance1 = new HealthInsurance();
        healthInsurance1.setId(1L);
        HealthInsurance healthInsurance2 = new HealthInsurance();
        healthInsurance2.setId(healthInsurance1.getId());
        assertThat(healthInsurance1).isEqualTo(healthInsurance2);
        healthInsurance2.setId(2L);
        assertThat(healthInsurance1).isNotEqualTo(healthInsurance2);
        healthInsurance1.setId(null);
        assertThat(healthInsurance1).isNotEqualTo(healthInsurance2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HealthInsuranceDTO.class);
        HealthInsuranceDTO healthInsuranceDTO1 = new HealthInsuranceDTO();
        healthInsuranceDTO1.setId(1L);
        HealthInsuranceDTO healthInsuranceDTO2 = new HealthInsuranceDTO();
        assertThat(healthInsuranceDTO1).isNotEqualTo(healthInsuranceDTO2);
        healthInsuranceDTO2.setId(healthInsuranceDTO1.getId());
        assertThat(healthInsuranceDTO1).isEqualTo(healthInsuranceDTO2);
        healthInsuranceDTO2.setId(2L);
        assertThat(healthInsuranceDTO1).isNotEqualTo(healthInsuranceDTO2);
        healthInsuranceDTO1.setId(null);
        assertThat(healthInsuranceDTO1).isNotEqualTo(healthInsuranceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(healthInsuranceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(healthInsuranceMapper.fromId(null)).isNull();
    }
}
