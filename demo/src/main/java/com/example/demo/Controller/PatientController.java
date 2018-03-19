package com.example.demo.Controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Patient;
import com.example.demo.service.MessageListener;
import com.example.demo.service.PublishPatientService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
public class PatientController 
{
  
  MessageListener messageListener;
  
  PublishPatientService publishPatientService;
  
  public PatientController(PublishPatientService publishPatientService,MessageListener messageListener)
  {
	  this.publishPatientService=publishPatientService;
	  this.messageListener=messageListener;
  }
  
  @RequestMapping("/getMessage")
  public void getMessage(@RequestParam("name") String name)
  {
	  Patient patient=Patient.builder().name(name).build();
	  publishPatientService.sendChannel(patient);
	  
	  log.info("getMessage: {} in PatientController", name);
  }
  
  
  
  
}
