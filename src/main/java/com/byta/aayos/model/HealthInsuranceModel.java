package com.byta.aayos.model;

import java.time.LocalDate;

import com.byta.aayos.domain.InsuranceCategory;
import com.byta.aayos.domain.Insurer;
import com.byta.aayos.domain.Relationship;



public class HealthInsuranceModel {

	private Long id;
	private Long patientId;
	private LocalDate createdDate;
	private Long insuranceNumber;
	private LocalDate expiryDate;
	private String coverager;
	private InsuranceCategory category;
	private Relationship status;
	private Insurer insurer;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
	public LocalDate getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}
	public Long getInsuranceNumber() {
		return insuranceNumber;
	}
	public void setInsuranceNumber(Long insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}
	public LocalDate getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getCoverager() {
		return coverager;
	}
	public void setCoverager(String coverager) {
		this.coverager = coverager;
	}
	public InsuranceCategory getCategory() {
		return category;
	}
	public void setCategory(InsuranceCategory category) {
		this.category = category;
	}
	public Relationship getStatus() {
		return status;
	}
	public void setStatus(Relationship status) {
		this.status = status;
	}
	public Insurer getInsurer() {
		return insurer;
	}
	public void setInsurer(Insurer insurer) {
		this.insurer = insurer;
	}
	
}
