package com.byta.aayos.web.rest;

import com.byta.aayos.HealthInsuranceApp;

import com.byta.aayos.domain.InsurerInvoiceStatus;
import com.byta.aayos.repository.InsurerInvoiceStatusRepository;
import com.byta.aayos.service.InsurerInvoiceStatusService;
import com.byta.aayos.service.dto.InsurerInvoiceStatusDTO;
import com.byta.aayos.service.mapper.InsurerInvoiceStatusMapper;
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
import java.util.List;

import static com.byta.aayos.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InsurerInvoiceStatusResource REST controller.
 *
 * @see InsurerInvoiceStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HealthInsuranceApp.class)
public class InsurerInvoiceStatusResourceIntTest {

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private InsurerInvoiceStatusRepository insurerInvoiceStatusRepository;

    @Autowired
    private InsurerInvoiceStatusMapper insurerInvoiceStatusMapper;

    @Autowired
    private InsurerInvoiceStatusService insurerInvoiceStatusService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInsurerInvoiceStatusMockMvc;

    private InsurerInvoiceStatus insurerInvoiceStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InsurerInvoiceStatusResource insurerInvoiceStatusResource = new InsurerInvoiceStatusResource(insurerInvoiceStatusService);
        this.restInsurerInvoiceStatusMockMvc = MockMvcBuilders.standaloneSetup(insurerInvoiceStatusResource)
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
    public static InsurerInvoiceStatus createEntity(EntityManager em) {
        InsurerInvoiceStatus insurerInvoiceStatus = new InsurerInvoiceStatus()
            .status(DEFAULT_STATUS);
        return insurerInvoiceStatus;
    }

    @Before
    public void initTest() {
        insurerInvoiceStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createInsurerInvoiceStatus() throws Exception {
        int databaseSizeBeforeCreate = insurerInvoiceStatusRepository.findAll().size();

        // Create the InsurerInvoiceStatus
        InsurerInvoiceStatusDTO insurerInvoiceStatusDTO = insurerInvoiceStatusMapper.toDto(insurerInvoiceStatus);
        restInsurerInvoiceStatusMockMvc.perform(post("/api/insurer-invoice-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insurerInvoiceStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the InsurerInvoiceStatus in the database
        List<InsurerInvoiceStatus> insurerInvoiceStatusList = insurerInvoiceStatusRepository.findAll();
        assertThat(insurerInvoiceStatusList).hasSize(databaseSizeBeforeCreate + 1);
        InsurerInvoiceStatus testInsurerInvoiceStatus = insurerInvoiceStatusList.get(insurerInvoiceStatusList.size() - 1);
        assertThat(testInsurerInvoiceStatus.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInsurerInvoiceStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = insurerInvoiceStatusRepository.findAll().size();

        // Create the InsurerInvoiceStatus with an existing ID
        insurerInvoiceStatus.setId(1L);
        InsurerInvoiceStatusDTO insurerInvoiceStatusDTO = insurerInvoiceStatusMapper.toDto(insurerInvoiceStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInsurerInvoiceStatusMockMvc.perform(post("/api/insurer-invoice-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insurerInvoiceStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InsurerInvoiceStatus in the database
        List<InsurerInvoiceStatus> insurerInvoiceStatusList = insurerInvoiceStatusRepository.findAll();
        assertThat(insurerInvoiceStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInsurerInvoiceStatuses() throws Exception {
        // Initialize the database
        insurerInvoiceStatusRepository.saveAndFlush(insurerInvoiceStatus);

        // Get all the insurerInvoiceStatusList
        restInsurerInvoiceStatusMockMvc.perform(get("/api/insurer-invoice-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insurerInvoiceStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getInsurerInvoiceStatus() throws Exception {
        // Initialize the database
        insurerInvoiceStatusRepository.saveAndFlush(insurerInvoiceStatus);

        // Get the insurerInvoiceStatus
        restInsurerInvoiceStatusMockMvc.perform(get("/api/insurer-invoice-statuses/{id}", insurerInvoiceStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(insurerInvoiceStatus.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInsurerInvoiceStatus() throws Exception {
        // Get the insurerInvoiceStatus
        restInsurerInvoiceStatusMockMvc.perform(get("/api/insurer-invoice-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInsurerInvoiceStatus() throws Exception {
        // Initialize the database
        insurerInvoiceStatusRepository.saveAndFlush(insurerInvoiceStatus);
        int databaseSizeBeforeUpdate = insurerInvoiceStatusRepository.findAll().size();

        // Update the insurerInvoiceStatus
        InsurerInvoiceStatus updatedInsurerInvoiceStatus = insurerInvoiceStatusRepository.findOne(insurerInvoiceStatus.getId());
        // Disconnect from session so that the updates on updatedInsurerInvoiceStatus are not directly saved in db
        em.detach(updatedInsurerInvoiceStatus);
        updatedInsurerInvoiceStatus
            .status(UPDATED_STATUS);
        InsurerInvoiceStatusDTO insurerInvoiceStatusDTO = insurerInvoiceStatusMapper.toDto(updatedInsurerInvoiceStatus);

        restInsurerInvoiceStatusMockMvc.perform(put("/api/insurer-invoice-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insurerInvoiceStatusDTO)))
            .andExpect(status().isOk());

        // Validate the InsurerInvoiceStatus in the database
        List<InsurerInvoiceStatus> insurerInvoiceStatusList = insurerInvoiceStatusRepository.findAll();
        assertThat(insurerInvoiceStatusList).hasSize(databaseSizeBeforeUpdate);
        InsurerInvoiceStatus testInsurerInvoiceStatus = insurerInvoiceStatusList.get(insurerInvoiceStatusList.size() - 1);
        assertThat(testInsurerInvoiceStatus.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingInsurerInvoiceStatus() throws Exception {
        int databaseSizeBeforeUpdate = insurerInvoiceStatusRepository.findAll().size();

        // Create the InsurerInvoiceStatus
        InsurerInvoiceStatusDTO insurerInvoiceStatusDTO = insurerInvoiceStatusMapper.toDto(insurerInvoiceStatus);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInsurerInvoiceStatusMockMvc.perform(put("/api/insurer-invoice-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insurerInvoiceStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the InsurerInvoiceStatus in the database
        List<InsurerInvoiceStatus> insurerInvoiceStatusList = insurerInvoiceStatusRepository.findAll();
        assertThat(insurerInvoiceStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInsurerInvoiceStatus() throws Exception {
        // Initialize the database
        insurerInvoiceStatusRepository.saveAndFlush(insurerInvoiceStatus);
        int databaseSizeBeforeDelete = insurerInvoiceStatusRepository.findAll().size();

        // Get the insurerInvoiceStatus
        restInsurerInvoiceStatusMockMvc.perform(delete("/api/insurer-invoice-statuses/{id}", insurerInvoiceStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InsurerInvoiceStatus> insurerInvoiceStatusList = insurerInvoiceStatusRepository.findAll();
        assertThat(insurerInvoiceStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InsurerInvoiceStatus.class);
        InsurerInvoiceStatus insurerInvoiceStatus1 = new InsurerInvoiceStatus();
        insurerInvoiceStatus1.setId(1L);
        InsurerInvoiceStatus insurerInvoiceStatus2 = new InsurerInvoiceStatus();
        insurerInvoiceStatus2.setId(insurerInvoiceStatus1.getId());
        assertThat(insurerInvoiceStatus1).isEqualTo(insurerInvoiceStatus2);
        insurerInvoiceStatus2.setId(2L);
        assertThat(insurerInvoiceStatus1).isNotEqualTo(insurerInvoiceStatus2);
        insurerInvoiceStatus1.setId(null);
        assertThat(insurerInvoiceStatus1).isNotEqualTo(insurerInvoiceStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InsurerInvoiceStatusDTO.class);
        InsurerInvoiceStatusDTO insurerInvoiceStatusDTO1 = new InsurerInvoiceStatusDTO();
        insurerInvoiceStatusDTO1.setId(1L);
        InsurerInvoiceStatusDTO insurerInvoiceStatusDTO2 = new InsurerInvoiceStatusDTO();
        assertThat(insurerInvoiceStatusDTO1).isNotEqualTo(insurerInvoiceStatusDTO2);
        insurerInvoiceStatusDTO2.setId(insurerInvoiceStatusDTO1.getId());
        assertThat(insurerInvoiceStatusDTO1).isEqualTo(insurerInvoiceStatusDTO2);
        insurerInvoiceStatusDTO2.setId(2L);
        assertThat(insurerInvoiceStatusDTO1).isNotEqualTo(insurerInvoiceStatusDTO2);
        insurerInvoiceStatusDTO1.setId(null);
        assertThat(insurerInvoiceStatusDTO1).isNotEqualTo(insurerInvoiceStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(insurerInvoiceStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(insurerInvoiceStatusMapper.fromId(null)).isNull();
    }
}
