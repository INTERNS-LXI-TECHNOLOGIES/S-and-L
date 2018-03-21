package com.byta.aayos.web.rest;

import com.byta.aayos.HealthInsuranceApp;

import com.byta.aayos.domain.CoveredActivity;
import com.byta.aayos.repository.CoveredActivityRepository;
import com.byta.aayos.service.CoveredActivityService;
import com.byta.aayos.service.dto.CoveredActivityDTO;
import com.byta.aayos.service.mapper.CoveredActivityMapper;
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
 * Test class for the CoveredActivityResource REST controller.
 *
 * @see CoveredActivityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HealthInsuranceApp.class)
public class CoveredActivityResourceIntTest {

    private static final String DEFAULT_ACTIVITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_ACTIVITY_PRICE = 1D;
    private static final Double UPDATED_ACTIVITY_PRICE = 2D;

    @Autowired
    private CoveredActivityRepository coveredActivityRepository;

    @Autowired
    private CoveredActivityMapper coveredActivityMapper;

    @Autowired
    private CoveredActivityService coveredActivityService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCoveredActivityMockMvc;

    private CoveredActivity coveredActivity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CoveredActivityResource coveredActivityResource = new CoveredActivityResource(coveredActivityService);
        this.restCoveredActivityMockMvc = MockMvcBuilders.standaloneSetup(coveredActivityResource)
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
    public static CoveredActivity createEntity(EntityManager em) {
        CoveredActivity coveredActivity = new CoveredActivity()
            .activityName(DEFAULT_ACTIVITY_NAME)
            .activityPrice(DEFAULT_ACTIVITY_PRICE);
        return coveredActivity;
    }

    @Before
    public void initTest() {
        coveredActivity = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoveredActivity() throws Exception {
        int databaseSizeBeforeCreate = coveredActivityRepository.findAll().size();

        // Create the CoveredActivity
        CoveredActivityDTO coveredActivityDTO = coveredActivityMapper.toDto(coveredActivity);
        restCoveredActivityMockMvc.perform(post("/api/covered-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coveredActivityDTO)))
            .andExpect(status().isCreated());

        // Validate the CoveredActivity in the database
        List<CoveredActivity> coveredActivityList = coveredActivityRepository.findAll();
        assertThat(coveredActivityList).hasSize(databaseSizeBeforeCreate + 1);
        CoveredActivity testCoveredActivity = coveredActivityList.get(coveredActivityList.size() - 1);
        assertThat(testCoveredActivity.getActivityName()).isEqualTo(DEFAULT_ACTIVITY_NAME);
        assertThat(testCoveredActivity.getActivityPrice()).isEqualTo(DEFAULT_ACTIVITY_PRICE);
    }

    @Test
    @Transactional
    public void createCoveredActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coveredActivityRepository.findAll().size();

        // Create the CoveredActivity with an existing ID
        coveredActivity.setId(1L);
        CoveredActivityDTO coveredActivityDTO = coveredActivityMapper.toDto(coveredActivity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoveredActivityMockMvc.perform(post("/api/covered-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coveredActivityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CoveredActivity in the database
        List<CoveredActivity> coveredActivityList = coveredActivityRepository.findAll();
        assertThat(coveredActivityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCoveredActivities() throws Exception {
        // Initialize the database
        coveredActivityRepository.saveAndFlush(coveredActivity);

        // Get all the coveredActivityList
        restCoveredActivityMockMvc.perform(get("/api/covered-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coveredActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].activityName").value(hasItem(DEFAULT_ACTIVITY_NAME.toString())))
            .andExpect(jsonPath("$.[*].activityPrice").value(hasItem(DEFAULT_ACTIVITY_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    public void getCoveredActivity() throws Exception {
        // Initialize the database
        coveredActivityRepository.saveAndFlush(coveredActivity);

        // Get the coveredActivity
        restCoveredActivityMockMvc.perform(get("/api/covered-activities/{id}", coveredActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coveredActivity.getId().intValue()))
            .andExpect(jsonPath("$.activityName").value(DEFAULT_ACTIVITY_NAME.toString()))
            .andExpect(jsonPath("$.activityPrice").value(DEFAULT_ACTIVITY_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCoveredActivity() throws Exception {
        // Get the coveredActivity
        restCoveredActivityMockMvc.perform(get("/api/covered-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoveredActivity() throws Exception {
        // Initialize the database
        coveredActivityRepository.saveAndFlush(coveredActivity);
        int databaseSizeBeforeUpdate = coveredActivityRepository.findAll().size();

        // Update the coveredActivity
        CoveredActivity updatedCoveredActivity = coveredActivityRepository.findOne(coveredActivity.getId());
        // Disconnect from session so that the updates on updatedCoveredActivity are not directly saved in db
        em.detach(updatedCoveredActivity);
        updatedCoveredActivity
            .activityName(UPDATED_ACTIVITY_NAME)
            .activityPrice(UPDATED_ACTIVITY_PRICE);
        CoveredActivityDTO coveredActivityDTO = coveredActivityMapper.toDto(updatedCoveredActivity);

        restCoveredActivityMockMvc.perform(put("/api/covered-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coveredActivityDTO)))
            .andExpect(status().isOk());

        // Validate the CoveredActivity in the database
        List<CoveredActivity> coveredActivityList = coveredActivityRepository.findAll();
        assertThat(coveredActivityList).hasSize(databaseSizeBeforeUpdate);
        CoveredActivity testCoveredActivity = coveredActivityList.get(coveredActivityList.size() - 1);
        assertThat(testCoveredActivity.getActivityName()).isEqualTo(UPDATED_ACTIVITY_NAME);
        assertThat(testCoveredActivity.getActivityPrice()).isEqualTo(UPDATED_ACTIVITY_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingCoveredActivity() throws Exception {
        int databaseSizeBeforeUpdate = coveredActivityRepository.findAll().size();

        // Create the CoveredActivity
        CoveredActivityDTO coveredActivityDTO = coveredActivityMapper.toDto(coveredActivity);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCoveredActivityMockMvc.perform(put("/api/covered-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coveredActivityDTO)))
            .andExpect(status().isCreated());

        // Validate the CoveredActivity in the database
        List<CoveredActivity> coveredActivityList = coveredActivityRepository.findAll();
        assertThat(coveredActivityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCoveredActivity() throws Exception {
        // Initialize the database
        coveredActivityRepository.saveAndFlush(coveredActivity);
        int databaseSizeBeforeDelete = coveredActivityRepository.findAll().size();

        // Get the coveredActivity
        restCoveredActivityMockMvc.perform(delete("/api/covered-activities/{id}", coveredActivity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CoveredActivity> coveredActivityList = coveredActivityRepository.findAll();
        assertThat(coveredActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoveredActivity.class);
        CoveredActivity coveredActivity1 = new CoveredActivity();
        coveredActivity1.setId(1L);
        CoveredActivity coveredActivity2 = new CoveredActivity();
        coveredActivity2.setId(coveredActivity1.getId());
        assertThat(coveredActivity1).isEqualTo(coveredActivity2);
        coveredActivity2.setId(2L);
        assertThat(coveredActivity1).isNotEqualTo(coveredActivity2);
        coveredActivity1.setId(null);
        assertThat(coveredActivity1).isNotEqualTo(coveredActivity2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoveredActivityDTO.class);
        CoveredActivityDTO coveredActivityDTO1 = new CoveredActivityDTO();
        coveredActivityDTO1.setId(1L);
        CoveredActivityDTO coveredActivityDTO2 = new CoveredActivityDTO();
        assertThat(coveredActivityDTO1).isNotEqualTo(coveredActivityDTO2);
        coveredActivityDTO2.setId(coveredActivityDTO1.getId());
        assertThat(coveredActivityDTO1).isEqualTo(coveredActivityDTO2);
        coveredActivityDTO2.setId(2L);
        assertThat(coveredActivityDTO1).isNotEqualTo(coveredActivityDTO2);
        coveredActivityDTO1.setId(null);
        assertThat(coveredActivityDTO1).isNotEqualTo(coveredActivityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(coveredActivityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(coveredActivityMapper.fromId(null)).isNull();
    }
}
