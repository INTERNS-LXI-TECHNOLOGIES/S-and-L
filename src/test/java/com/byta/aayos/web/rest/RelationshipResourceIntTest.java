package com.byta.aayos.web.rest;

import com.byta.aayos.HealthInsuranceApp;

import com.byta.aayos.domain.Relationship;
import com.byta.aayos.repository.RelationshipRepository;
import com.byta.aayos.service.RelationshipService;
import com.byta.aayos.service.dto.RelationshipDTO;
import com.byta.aayos.service.mapper.RelationshipMapper;
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
 * Test class for the RelationshipResource REST controller.
 *
 * @see RelationshipResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HealthInsuranceApp.class)
public class RelationshipResourceIntTest {

    private static final String DEFAULT_RELATION = "AAAAAAAAAA";
    private static final String UPDATED_RELATION = "BBBBBBBBBB";

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private RelationshipMapper relationshipMapper;

    @Autowired
    private RelationshipService relationshipService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRelationshipMockMvc;

    private Relationship relationship;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RelationshipResource relationshipResource = new RelationshipResource(relationshipService);
        this.restRelationshipMockMvc = MockMvcBuilders.standaloneSetup(relationshipResource)
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
    public static Relationship createEntity(EntityManager em) {
        Relationship relationship = new Relationship()
            .relation(DEFAULT_RELATION);
        return relationship;
    }

    @Before
    public void initTest() {
        relationship = createEntity(em);
    }

    @Test
    @Transactional
    public void createRelationship() throws Exception {
        int databaseSizeBeforeCreate = relationshipRepository.findAll().size();

        // Create the Relationship
        RelationshipDTO relationshipDTO = relationshipMapper.toDto(relationship);
        restRelationshipMockMvc.perform(post("/api/relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipDTO)))
            .andExpect(status().isCreated());

        // Validate the Relationship in the database
        List<Relationship> relationshipList = relationshipRepository.findAll();
        assertThat(relationshipList).hasSize(databaseSizeBeforeCreate + 1);
        Relationship testRelationship = relationshipList.get(relationshipList.size() - 1);
        assertThat(testRelationship.getRelation()).isEqualTo(DEFAULT_RELATION);
    }

    @Test
    @Transactional
    public void createRelationshipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = relationshipRepository.findAll().size();

        // Create the Relationship with an existing ID
        relationship.setId(1L);
        RelationshipDTO relationshipDTO = relationshipMapper.toDto(relationship);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelationshipMockMvc.perform(post("/api/relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Relationship in the database
        List<Relationship> relationshipList = relationshipRepository.findAll();
        assertThat(relationshipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRelationships() throws Exception {
        // Initialize the database
        relationshipRepository.saveAndFlush(relationship);

        // Get all the relationshipList
        restRelationshipMockMvc.perform(get("/api/relationships?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relationship.getId().intValue())))
            .andExpect(jsonPath("$.[*].relation").value(hasItem(DEFAULT_RELATION.toString())));
    }

    @Test
    @Transactional
    public void getRelationship() throws Exception {
        // Initialize the database
        relationshipRepository.saveAndFlush(relationship);

        // Get the relationship
        restRelationshipMockMvc.perform(get("/api/relationships/{id}", relationship.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(relationship.getId().intValue()))
            .andExpect(jsonPath("$.relation").value(DEFAULT_RELATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRelationship() throws Exception {
        // Get the relationship
        restRelationshipMockMvc.perform(get("/api/relationships/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRelationship() throws Exception {
        // Initialize the database
        relationshipRepository.saveAndFlush(relationship);
        int databaseSizeBeforeUpdate = relationshipRepository.findAll().size();

        // Update the relationship
        Relationship updatedRelationship = relationshipRepository.findOne(relationship.getId());
        // Disconnect from session so that the updates on updatedRelationship are not directly saved in db
        em.detach(updatedRelationship);
        updatedRelationship
            .relation(UPDATED_RELATION);
        RelationshipDTO relationshipDTO = relationshipMapper.toDto(updatedRelationship);

        restRelationshipMockMvc.perform(put("/api/relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipDTO)))
            .andExpect(status().isOk());

        // Validate the Relationship in the database
        List<Relationship> relationshipList = relationshipRepository.findAll();
        assertThat(relationshipList).hasSize(databaseSizeBeforeUpdate);
        Relationship testRelationship = relationshipList.get(relationshipList.size() - 1);
        assertThat(testRelationship.getRelation()).isEqualTo(UPDATED_RELATION);
    }

    @Test
    @Transactional
    public void updateNonExistingRelationship() throws Exception {
        int databaseSizeBeforeUpdate = relationshipRepository.findAll().size();

        // Create the Relationship
        RelationshipDTO relationshipDTO = relationshipMapper.toDto(relationship);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRelationshipMockMvc.perform(put("/api/relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipDTO)))
            .andExpect(status().isCreated());

        // Validate the Relationship in the database
        List<Relationship> relationshipList = relationshipRepository.findAll();
        assertThat(relationshipList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRelationship() throws Exception {
        // Initialize the database
        relationshipRepository.saveAndFlush(relationship);
        int databaseSizeBeforeDelete = relationshipRepository.findAll().size();

        // Get the relationship
        restRelationshipMockMvc.perform(delete("/api/relationships/{id}", relationship.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Relationship> relationshipList = relationshipRepository.findAll();
        assertThat(relationshipList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Relationship.class);
        Relationship relationship1 = new Relationship();
        relationship1.setId(1L);
        Relationship relationship2 = new Relationship();
        relationship2.setId(relationship1.getId());
        assertThat(relationship1).isEqualTo(relationship2);
        relationship2.setId(2L);
        assertThat(relationship1).isNotEqualTo(relationship2);
        relationship1.setId(null);
        assertThat(relationship1).isNotEqualTo(relationship2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RelationshipDTO.class);
        RelationshipDTO relationshipDTO1 = new RelationshipDTO();
        relationshipDTO1.setId(1L);
        RelationshipDTO relationshipDTO2 = new RelationshipDTO();
        assertThat(relationshipDTO1).isNotEqualTo(relationshipDTO2);
        relationshipDTO2.setId(relationshipDTO1.getId());
        assertThat(relationshipDTO1).isEqualTo(relationshipDTO2);
        relationshipDTO2.setId(2L);
        assertThat(relationshipDTO1).isNotEqualTo(relationshipDTO2);
        relationshipDTO1.setId(null);
        assertThat(relationshipDTO1).isNotEqualTo(relationshipDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(relationshipMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(relationshipMapper.fromId(null)).isNull();
    }
}
