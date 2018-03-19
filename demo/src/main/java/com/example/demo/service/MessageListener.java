package com.example.demo.service;



import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.example.demo.model.Patient;
import com.example.demo.stream.PatientStream;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class MessageListener
{
	@StreamListener(PatientStream.MSGTOPIC1)
	public void listenToMsg(@Payload Patient patient)
	{
		System.out.println("done...........");
		
		log.info("listenToMsg: {} in MessageListener", patient.getName());
	
	}
	
	
	        //log.info("Received greetings: {}", greetings);

}
