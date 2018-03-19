package com.example.demo.service;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import com.example.demo.model.Patient;
import com.example.demo.stream.PatientStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PublishPatientService 
{
   
	private final PatientStream patientstream;
    
    public PublishPatientService(PatientStream patientstream)
    {
    	this.patientstream=patientstream;
    }
    
    public void sendChannel(final Patient patient)
    {
    	MessageChannel messageChannel = patientstream.setMsgChannel();
    	
    	messageChannel.send(MessageBuilder.withPayload(patient)
			.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build()
			) ;
    	
    	log.info("Sending channels in PublishPatientService  {}",patient.getName());
    }
    
    
    
    
    
    
    
}
