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
package com.bytatech.aayos.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bytatech.aayos.api.model.GatewayBooking;
import com.bytatech.aayos.api.service.GatewayMessageListener;
import com.bytatech.aayos.api.service.PublishGatewayService;

import lombok.extern.slf4j.Slf4j;


/**
 * TODO Provide a detailed description here 
 * @author sanilkumar
 * , 
 */

@RestController
@Slf4j
public class GatewayController {

	GatewayMessageListener messageListener;
	
	PublishGatewayService publishGatewayService;
	
	public GatewayController (PublishGatewayService publishGatewayService, GatewayMessageListener messageListener) {
		
		this.publishGatewayService=publishGatewayService;
		this.messageListener=messageListener;
	}
	
	@RequestMapping("/getGatewayBooking")
    public void getMessage(@RequestParam("doctorName") String doctorName,@RequestParam("patientName") String patientName) {
		
		log.info("getMessage() in GatewayController is called ");
        GatewayBooking booking = GatewayBooking.builder().doctorName(doctorName).build();
        booking.setPatientName(patientName);
        
		publishGatewayService.sendMessage(booking);
		log.info("getMessage() in GatewayController is ended ");
		
	}
}
