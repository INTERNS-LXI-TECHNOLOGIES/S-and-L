package com.byta.aayos.web.rest;

import com.byta.aayos.HealthInsuranceApp;

import com.byta.aayos.domain.InsurerInvoice;
import com.byta.aayos.repository.InsurerInvoiceRepository;
import com.byta.aayos.service.InsurerInvoiceService;
import com.byta.aayos.service.dto.InsurerInvoiceDTO;
import com.byta.aayos.service.mapper.InsurerInvoiceMapper;
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
 * Test class for the InsurerInvoiceResource REST controller.
 *
 * @see InsurerInvoiceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HealthInsuranceApp.class)
public class InsurerInvoiceResourceIntTest {

    private static final Long DEFAULT_INVOICE_NUMBER = 1L;
    private static final Long UPDATED_INVOICE_NUMBER = 2L;

    private static final LocalDate DEFAULT_INVOICE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INVOICE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private InsurerInvoiceRepository insurerInvoiceRepository;

    @Autowired
    private InsurerInvoiceMapper insurerInvoiceMapper;

    @Autowired
    private InsurerInvoiceService insurerInvoiceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInsurerInvoiceMockMvc;

    private InsurerInvoice insurerInvoice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InsurerInvoiceResource insurerInvoiceResource = new InsurerInvoiceResource(insurerInvoiceService);
        this.restInsurerInvoiceMockMvc = MockMvcBuilders.standaloneSetup(insurerInvoiceResource)
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
    public static InsurerInvoice createEntity(EntityManager em) {
        InsurerInvoice insurerInvoice = new InsurerInvoice()
            .invoiceNumber(DEFAULT_INVOICE_NUMBER)
            .invoiceDate(DEFAULT_INVOICE_DATE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return insurerInvoice;
    }

    @Before
    public void initTest() {
        insurerInvoice = createEntity(em);
    }

    @Test
    @Transactional
    public void createInsurerInvoice() throws Exception {
        int databaseSizeBeforeCreate = insurerInvoiceRepository.findAll().size();

        // Create the InsurerInvoice
        InsurerInvoiceDTO insurerInvoiceDTO = insurerInvoiceMapper.toDto(insurerInvoice);
        restInsurerInvoiceMockMvc.perform(post("/api/insurer-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insurerInvoiceDTO)))
            .andExpect(status().isCreated());

        // Validate the InsurerInvoice in the database
        List<InsurerInvoice> insurerInvoiceList = insurerInvoiceRepository.findAll();
        assertThat(insurerInvoiceList).hasSize(databaseSizeBeforeCreate + 1);
        InsurerInvoice testInsurerInvoice = insurerInvoiceList.get(insurerInvoiceList.size() - 1);
        assertThat(testInsurerInvoice.getInvoiceNumber()).isEqualTo(DEFAULT_INVOICE_NUMBER);
        assertThat(testInsurerInvoice.getInvoiceDate()).isEqualTo(DEFAULT_INVOICE_DATE);
        assertThat(testInsurerInvoice.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testInsurerInvoice.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createInsurerInvoiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = insurerInvoiceRepository.findAll().size();

        // Create the InsurerInvoice with an existing ID
        insurerInvoice.setId(1L);
        InsurerInvoiceDTO insurerInvoiceDTO = insurerInvoiceMapper.toDto(insurerInvoice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInsurerInvoiceMockMvc.perform(post("/api/insurer-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insurerInvoiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InsurerInvoice in the database
        List<InsurerInvoice> insurerInvoiceList = insurerInvoiceRepository.findAll();
        assertThat(insurerInvoiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInsurerInvoices() throws Exception {
        // Initialize the database
        insurerInvoiceRepository.saveAndFlush(insurerInvoice);

        // Get all the insurerInvoiceList
        restInsurerInvoiceMockMvc.perform(get("/api/insurer-invoices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insurerInvoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceNumber").value(hasItem(DEFAULT_INVOICE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void getInsurerInvoice() throws Exception {
        // Initialize the database
        insurerInvoiceRepository.saveAndFlush(insurerInvoice);

        // Get the insurerInvoice
        restInsurerInvoiceMockMvc.perform(get("/api/insurer-invoices/{id}", insurerInvoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(insurerInvoice.getId().intValue()))
            .andExpect(jsonPath("$.invoiceNumber").value(DEFAULT_INVOICE_NUMBER.intValue()))
            .andExpect(jsonPath("$.invoiceDate").value(DEFAULT_INVOICE_DATE.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInsurerInvoice() throws Exception {
        // Get the insurerInvoice
        restInsurerInvoiceMockMvc.perform(get("/api/insurer-invoices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInsurerInvoice() throws Exception {
        // Initialize the database
        insurerInvoiceRepository.saveAndFlush(insurerInvoice);
        int databaseSizeBeforeUpdate = insurerInvoiceRepository.findAll().size();

        // Update the insurerInvoice
        InsurerInvoice updatedInsurerInvoice = insurerInvoiceRepository.findOne(insurerInvoice.getId());
        // Disconnect from session so that the updates on updatedInsurerInvoice are not directly saved in db
        em.detach(updatedInsurerInvoice);
        updatedInsurerInvoice
            .invoiceNumber(UPDATED_INVOICE_NUMBER)
            .invoiceDate(UPDATED_INVOICE_DATE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        InsurerInvoiceDTO insurerInvoiceDTO = insurerInvoiceMapper.toDto(updatedInsurerInvoice);

        restInsurerInvoiceMockMvc.perform(put("/api/insurer-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insurerInvoiceDTO)))
            .andExpect(status().isOk());

        // Validate the InsurerInvoice in the database
        List<InsurerInvoice> insurerInvoiceList = insurerInvoiceRepository.findAll();
        assertThat(insurerInvoiceList).hasSize(databaseSizeBeforeUpdate);
        InsurerInvoice testInsurerInvoice = insurerInvoiceList.get(insurerInvoiceList.size() - 1);
        assertThat(testInsurerInvoice.getInvoiceNumber()).isEqualTo(UPDATED_INVOICE_NUMBER);
        assertThat(testInsurerInvoice.getInvoiceDate()).isEqualTo(UPDATED_INVOICE_DATE);
        assertThat(testInsurerInvoice.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testInsurerInvoice.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingInsurerInvoice() throws Exception {
        int databaseSizeBeforeUpdate = insurerInvoiceRepository.findAll().size();

        // Create the InsurerInvoice
        InsurerInvoiceDTO insurerInvoiceDTO = insurerInvoiceMapper.toDto(insurerInvoice);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInsurerInvoiceMockMvc.perform(put("/api/insurer-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insurerInvoiceDTO)))
            .andExpect(status().isCreated());

        // Validate the InsurerInvoice in the database
        List<InsurerInvoice> insurerInvoiceList = insurerInvoiceRepository.findAll();
        assertThat(insurerInvoiceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInsurerInvoice() throws Exception {
        // Initialize the database
        insurerInvoiceRepository.saveAndFlush(insurerInvoice);
        int databaseSizeBeforeDelete = insurerInvoiceRepository.findAll().size();

        // Get the insurerInvoice
        restInsurerInvoiceMockMvc.perform(delete("/api/insurer-invoices/{id}", insurerInvoice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InsurerInvoice> insurerInvoiceList = insurerInvoiceRepository.findAll();
        assertThat(insurerInvoiceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InsurerInvoice.class);
        InsurerInvoice insurerInvoice1 = new InsurerInvoice();
        insurerInvoice1.setId(1L);
        InsurerInvoice insurerInvoice2 = new InsurerInvoice();
        insurerInvoice2.setId(insurerInvoice1.getId());
        assertThat(insurerInvoice1).isEqualTo(insurerInvoice2);
        insurerInvoice2.setId(2L);
        assertThat(insurerInvoice1).isNotEqualTo(insurerInvoice2);
        insurerInvoice1.setId(null);
        assertThat(insurerInvoice1).isNotEqualTo(insurerInvoice2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InsurerInvoiceDTO.class);
        InsurerInvoiceDTO insurerInvoiceDTO1 = new InsurerInvoiceDTO();
        insurerInvoiceDTO1.setId(1L);
        InsurerInvoiceDTO insurerInvoiceDTO2 = new InsurerInvoiceDTO();
        assertThat(insurerInvoiceDTO1).isNotEqualTo(insurerInvoiceDTO2);
        insurerInvoiceDTO2.setId(insurerInvoiceDTO1.getId());
        assertThat(insurerInvoiceDTO1).isEqualTo(insurerInvoiceDTO2);
        insurerInvoiceDTO2.setId(2L);
        assertThat(insurerInvoiceDTO1).isNotEqualTo(insurerInvoiceDTO2);
        insurerInvoiceDTO1.setId(null);
        assertThat(insurerInvoiceDTO1).isNotEqualTo(insurerInvoiceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(insurerInvoiceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(insurerInvoiceMapper.fromId(null)).isNull();
    }
}
