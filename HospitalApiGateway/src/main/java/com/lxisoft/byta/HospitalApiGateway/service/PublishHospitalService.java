/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lxisoft.byta.HospitalApiGateway.service;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import com.lxisoft.byta.HospitalApiGateway.model.Hospital;

import com.lxisoft.byta.HospitalApiGateway.stream.HospitalStream;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO Provide a detailed description here 
 * @author Sarangi Balu
 * , 
 */

@Slf4j
@Service
public class PublishHospitalService 
{
 private final HospitalStream hospitalStream;
 
 public PublishHospitalService(HospitalStream hospitalStream)
 {
	 this.hospitalStream=hospitalStream;
 }
 
 
 public void sendChannel(final Hospital hospital)
 {
 	MessageChannel messageChannel = hospitalStream.setMsgChannel();
 	
 	messageChannel.send(MessageBuilder.withPayload(hospital)
			.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build()
			) ;
 	
 	log.info("Sending channels in PublishHospitalService   {}",hospital.getName());
 }
 
 
 
}
