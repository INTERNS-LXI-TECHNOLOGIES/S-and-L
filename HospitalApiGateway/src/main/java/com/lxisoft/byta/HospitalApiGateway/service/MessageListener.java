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

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.lxisoft.byta.HospitalApiGateway.model.Hospital;
import com.lxisoft.byta.HospitalApiGateway.stream.HospitalStream;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO Provide a detailed description here 
 * @author Sarangi Balu
 * , 
 */
@Component
@Slf4j
public class MessageListener 
{
	@StreamListener(HospitalStream.MSGTOPIC1)
	public void listenToMsg(@Payload Hospital hospital)
	{
		System.out.println("done...........");
		
		log.info("listenToMsg: {} in MessageListener Hospital", hospital.getName());
	
	}
	
}
