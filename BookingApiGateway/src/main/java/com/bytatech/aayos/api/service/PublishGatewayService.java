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
package com.bytatech.aayos.api.service;

//import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
//import org.springframework.util.MimeTypeUtils;

import com.bytatech.aayos.api.model.GatewayBooking;
import com.bytatech.aayos.api.stream.GatewayStream;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO Provide a detailed description here 
 * @author sanilkumar
 * , 
 */

@Slf4j
@Service
public class PublishGatewayService {

private final GatewayStream gatewayStream;
	
	public PublishGatewayService(GatewayStream gatewayStream){
		this.gatewayStream=gatewayStream;
	}
	
	public void sendMessage(GatewayBooking booking) {
		
		log.info("sendMessage() in PublishGatewayService is called {} ", booking);
		
		//MessageChannel messageChannel = bookingStream.writeMessage();
		gatewayStream.writeMessage().send(MessageBuilder.withPayload(booking).setHeader("type", "patient registered").build());
		log.info("sendMessage() in PublishGatewayService is ended {} ", booking);
	}
}
