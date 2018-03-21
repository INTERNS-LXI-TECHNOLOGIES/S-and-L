package com.byta.aayos.web.rest;

import com.byta.aayos.HealthInsuranceApp;

import com.byta.aayos.domain.InsuranceCategory;
import com.byta.aayos.repository.InsuranceCategoryRepository;
import com.byta.aayos.service.InsuranceCategoryService;
import com.byta.aayos.service.dto.InsuranceCategoryDTO;
import com.byta.aayos.service.mapper.InsuranceCategoryMapper;
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
 * Test class for the InsuranceCategoryResource REST controller.
 *
 * @see InsuranceCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HealthInsuranceApp.class)
public class InsuranceCategoryResourceIntTest {

    private static final String DEFAULT_CATEGORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_NAME = "BBBBBBBBBB";

    @Autowired
    private InsuranceCategoryRepository insuranceCategoryRepository;

    @Autowired
    private InsuranceCategoryMapper insuranceCategoryMapper;

    @Autowired
    private InsuranceCategoryService insuranceCategoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInsuranceCategoryMockMvc;

    private InsuranceCategory insuranceCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InsuranceCategoryResource insuranceCategoryResource = new InsuranceCategoryResource(insuranceCategoryService);
        this.restInsuranceCategoryMockMvc = MockMvcBuilders.standaloneSetup(insuranceCategoryResource)
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
    public static InsuranceCategory createEntity(EntityManager em) {
        InsuranceCategory insuranceCategory = new InsuranceCategory()
            .categoryName(DEFAULT_CATEGORY_NAME);
        return insuranceCategory;
    }

    @Before
    public void initTest() {
        insuranceCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createInsuranceCategory() throws Exception {
        int databaseSizeBeforeCreate = insuranceCategoryRepository.findAll().size();

        // Create the InsuranceCategory
        InsuranceCategoryDTO insuranceCategoryDTO = insuranceCategoryMapper.toDto(insuranceCategory);
        restInsuranceCategoryMockMvc.perform(post("/api/insurance-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the InsuranceCategory in the database
        List<InsuranceCategory> insuranceCategoryList = insuranceCategoryRepository.findAll();
        assertThat(insuranceCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        InsuranceCategory testInsuranceCategory = insuranceCategoryList.get(insuranceCategoryList.size() - 1);
        assertThat(testInsuranceCategory.getCategoryName()).isEqualTo(DEFAULT_CATEGORY_NAME);
    }

    @Test
    @Transactional
    public void createInsuranceCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = insuranceCategoryRepository.findAll().size();

        // Create the InsuranceCategory with an existing ID
        insuranceCategory.setId(1L);
        InsuranceCategoryDTO insuranceCategoryDTO = insuranceCategoryMapper.toDto(insuranceCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInsuranceCategoryMockMvc.perform(post("/api/insurance-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InsuranceCategory in the database
        List<InsuranceCategory> insuranceCategoryList = insuranceCategoryRepository.findAll();
        assertThat(insuranceCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInsuranceCategories() throws Exception {
        // Initialize the database
        insuranceCategoryRepository.saveAndFlush(insuranceCategory);

        // Get all the insuranceCategoryList
        restInsuranceCategoryMockMvc.perform(get("/api/insurance-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insuranceCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryName").value(hasItem(DEFAULT_CATEGORY_NAME.toString())));
    }

    @Test
    @Transactional
    public void getInsuranceCategory() throws Exception {
        // Initialize the database
        insuranceCategoryRepository.saveAndFlush(insuranceCategory);

        // Get the insuranceCategory
        restInsuranceCategoryMockMvc.perform(get("/api/insurance-categories/{id}", insuranceCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(insuranceCategory.getId().intValue()))
            .andExpect(jsonPath("$.categoryName").value(DEFAULT_CATEGORY_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInsuranceCategory() throws Exception {
        // Get the insuranceCategory
        restInsuranceCategoryMockMvc.perform(get("/api/insurance-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInsuranceCategory() throws Exception {
        // Initialize the database
        insuranceCategoryRepository.saveAndFlush(insuranceCategory);
        int databaseSizeBeforeUpdate = insuranceCategoryRepository.findAll().size();

        // Update the insuranceCategory
        InsuranceCategory updatedInsuranceCategory = insuranceCategoryRepository.findOne(insuranceCategory.getId());
        // Disconnect from session so that the updates on updatedInsuranceCategory are not directly saved in db
        em.detach(updatedInsuranceCategory);
        updatedInsuranceCategory
            .categoryName(UPDATED_CATEGORY_NAME);
        InsuranceCategoryDTO insuranceCategoryDTO = insuranceCategoryMapper.toDto(updatedInsuranceCategory);

        restInsuranceCategoryMockMvc.perform(put("/api/insurance-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the InsuranceCategory in the database
        List<InsuranceCategory> insuranceCategoryList = insuranceCategoryRepository.findAll();
        assertThat(insuranceCategoryList).hasSize(databaseSizeBeforeUpdate);
        InsuranceCategory testInsuranceCategory = insuranceCategoryList.get(insuranceCategoryList.size() - 1);
        assertThat(testInsuranceCategory.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingInsuranceCategory() throws Exception {
        int databaseSizeBeforeUpdate = insuranceCategoryRepository.findAll().size();

        // Create the InsuranceCategory
        InsuranceCategoryDTO insuranceCategoryDTO = insuranceCategoryMapper.toDto(insuranceCategory);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInsuranceCategoryMockMvc.perform(put("/api/insurance-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the InsuranceCategory in the database
        List<InsuranceCategory> insuranceCategoryList = insuranceCategoryRepository.findAll();
        assertThat(insuranceCategoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInsuranceCategory() throws Exception {
        // Initialize the database
        insuranceCategoryRepository.saveAndFlush(insuranceCategory);
        int databaseSizeBeforeDelete = insuranceCategoryRepository.findAll().size();

        // Get the insuranceCategory
        restInsuranceCategoryMockMvc.perform(delete("/api/insurance-categories/{id}", insuranceCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InsuranceCategory> insuranceCategoryList = insuranceCategoryRepository.findAll();
        assertThat(insuranceCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InsuranceCategory.class);
        InsuranceCategory insuranceCategory1 = new InsuranceCategory();
        insuranceCategory1.setId(1L);
        InsuranceCategory insuranceCategory2 = new InsuranceCategory();
        insuranceCategory2.setId(insuranceCategory1.getId());
        assertThat(insuranceCategory1).isEqualTo(insuranceCategory2);
        insuranceCategory2.setId(2L);
        assertThat(insuranceCategory1).isNotEqualTo(insuranceCategory2);
        insuranceCategory1.setId(null);
        assertThat(insuranceCategory1).isNotEqualTo(insuranceCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InsuranceCategoryDTO.class);
        InsuranceCategoryDTO insuranceCategoryDTO1 = new InsuranceCategoryDTO();
        insuranceCategoryDTO1.setId(1L);
        InsuranceCategoryDTO insuranceCategoryDTO2 = new InsuranceCategoryDTO();
        assertThat(insuranceCategoryDTO1).isNotEqualTo(insuranceCategoryDTO2);
        insuranceCategoryDTO2.setId(insuranceCategoryDTO1.getId());
        assertThat(insuranceCategoryDTO1).isEqualTo(insuranceCategoryDTO2);
        insuranceCategoryDTO2.setId(2L);
        assertThat(insuranceCategoryDTO1).isNotEqualTo(insuranceCategoryDTO2);
        insuranceCategoryDTO1.setId(null);
        assertThat(insuranceCategoryDTO1).isNotEqualTo(insuranceCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(insuranceCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(insuranceCategoryMapper.fromId(null)).isNull();
    }
}
