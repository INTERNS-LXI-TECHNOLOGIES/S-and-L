package com.byta.aayos.web.rest;

import com.byta.aayos.HealthInsuranceApp;

import com.byta.aayos.domain.Insurer;
import com.byta.aayos.repository.InsurerRepository;
import com.byta.aayos.service.InsurerService;
import com.byta.aayos.service.dto.InsurerDTO;
import com.byta.aayos.service.mapper.InsurerMapper;
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
 * Test class for the InsurerResource REST controller.
 *
 * @see InsurerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HealthInsuranceApp.class)
public class InsurerResourceIntTest {

    private static final String DEFAULT_INSURAR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_INSURAR_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_CONTACT_NUMBER = 1L;
    private static final Long UPDATED_CONTACT_NUMBER = 2L;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private InsurerRepository insurerRepository;

    @Autowired
    private InsurerMapper insurerMapper;

    @Autowired
    private InsurerService insurerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInsurerMockMvc;

    private Insurer insurer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InsurerResource insurerResource = new InsurerResource(insurerService);
        this.restInsurerMockMvc = MockMvcBuilders.standaloneSetup(insurerResource)
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
    public static Insurer createEntity(EntityManager em) {
        Insurer insurer = new Insurer()
            .insurarName(DEFAULT_INSURAR_NAME)
            .contactNumber(DEFAULT_CONTACT_NUMBER)
            .address(DEFAULT_ADDRESS);
        return insurer;
    }

    @Before
    public void initTest() {
        insurer = createEntity(em);
    }

    @Test
    @Transactional
    public void createInsurer() throws Exception {
        int databaseSizeBeforeCreate = insurerRepository.findAll().size();

        // Create the Insurer
        InsurerDTO insurerDTO = insurerMapper.toDto(insurer);
        restInsurerMockMvc.perform(post("/api/insurers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insurerDTO)))
            .andExpect(status().isCreated());

        // Validate the Insurer in the database
        List<Insurer> insurerList = insurerRepository.findAll();
        assertThat(insurerList).hasSize(databaseSizeBeforeCreate + 1);
        Insurer testInsurer = insurerList.get(insurerList.size() - 1);
        assertThat(testInsurer.getInsurarName()).isEqualTo(DEFAULT_INSURAR_NAME);
        assertThat(testInsurer.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testInsurer.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void createInsurerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = insurerRepository.findAll().size();

        // Create the Insurer with an existing ID
        insurer.setId(1L);
        InsurerDTO insurerDTO = insurerMapper.toDto(insurer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInsurerMockMvc.perform(post("/api/insurers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insurerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Insurer in the database
        List<Insurer> insurerList = insurerRepository.findAll();
        assertThat(insurerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInsurers() throws Exception {
        // Initialize the database
        insurerRepository.saveAndFlush(insurer);

        // Get all the insurerList
        restInsurerMockMvc.perform(get("/api/insurers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insurer.getId().intValue())))
            .andExpect(jsonPath("$.[*].insurarName").value(hasItem(DEFAULT_INSURAR_NAME.toString())))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())));
    }

    @Test
    @Transactional
    public void getInsurer() throws Exception {
        // Initialize the database
        insurerRepository.saveAndFlush(insurer);

        // Get the insurer
        restInsurerMockMvc.perform(get("/api/insurers/{id}", insurer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(insurer.getId().intValue()))
            .andExpect(jsonPath("$.insurarName").value(DEFAULT_INSURAR_NAME.toString()))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER.intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInsurer() throws Exception {
        // Get the insurer
        restInsurerMockMvc.perform(get("/api/insurers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInsurer() throws Exception {
        // Initialize the database
        insurerRepository.saveAndFlush(insurer);
        int databaseSizeBeforeUpdate = insurerRepository.findAll().size();

        // Update the insurer
        Insurer updatedInsurer = insurerRepository.findOne(insurer.getId());
        // Disconnect from session so that the updates on updatedInsurer are not directly saved in db
        em.detach(updatedInsurer);
        updatedInsurer
            .insurarName(UPDATED_INSURAR_NAME)
            .contactNumber(UPDATED_CONTACT_NUMBER)
            .address(UPDATED_ADDRESS);
        InsurerDTO insurerDTO = insurerMapper.toDto(updatedInsurer);

        restInsurerMockMvc.perform(put("/api/insurers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insurerDTO)))
            .andExpect(status().isOk());

        // Validate the Insurer in the database
        List<Insurer> insurerList = insurerRepository.findAll();
        assertThat(insurerList).hasSize(databaseSizeBeforeUpdate);
        Insurer testInsurer = insurerList.get(insurerList.size() - 1);
        assertThat(testInsurer.getInsurarName()).isEqualTo(UPDATED_INSURAR_NAME);
        assertThat(testInsurer.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testInsurer.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingInsurer() throws Exception {
        int databaseSizeBeforeUpdate = insurerRepository.findAll().size();

        // Create the Insurer
        InsurerDTO insurerDTO = insurerMapper.toDto(insurer);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInsurerMockMvc.perform(put("/api/insurers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insurerDTO)))
            .andExpect(status().isCreated());

        // Validate the Insurer in the database
        List<Insurer> insurerList = insurerRepository.findAll();
        assertThat(insurerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInsurer() throws Exception {
        // Initialize the database
        insurerRepository.saveAndFlush(insurer);
        int databaseSizeBeforeDelete = insurerRepository.findAll().size();

        // Get the insurer
        restInsurerMockMvc.perform(delete("/api/insurers/{id}", insurer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Insurer> insurerList = insurerRepository.findAll();
        assertThat(insurerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Insurer.class);
        Insurer insurer1 = new Insurer();
        insurer1.setId(1L);
        Insurer insurer2 = new Insurer();
        insurer2.setId(insurer1.getId());
        assertThat(insurer1).isEqualTo(insurer2);
        insurer2.setId(2L);
        assertThat(insurer1).isNotEqualTo(insurer2);
        insurer1.setId(null);
        assertThat(insurer1).isNotEqualTo(insurer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InsurerDTO.class);
        InsurerDTO insurerDTO1 = new InsurerDTO();
        insurerDTO1.setId(1L);
        InsurerDTO insurerDTO2 = new InsurerDTO();
        assertThat(insurerDTO1).isNotEqualTo(insurerDTO2);
        insurerDTO2.setId(insurerDTO1.getId());
        assertThat(insurerDTO1).isEqualTo(insurerDTO2);
        insurerDTO2.setId(2L);
        assertThat(insurerDTO1).isNotEqualTo(insurerDTO2);
        insurerDTO1.setId(null);
        assertThat(insurerDTO1).isNotEqualTo(insurerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(insurerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(insurerMapper.fromId(null)).isNull();
    }
}
