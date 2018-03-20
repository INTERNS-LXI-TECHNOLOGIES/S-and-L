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
package com.example.demo.service;

import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import com.example.demo.model.Booking;
import com.example.demo.stream.BookingStream;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO Provide a detailed description here 
 * @author sanilkumar
 * , 
 */

@Slf4j
@Service
public class PublishBookingService {

	private final BookingStream bookingStream;
	
	public PublishBookingService(BookingStream bookingStream){
		this.bookingStream=bookingStream;
	}
	
/*	public void sendMessage(Booking booking) {
		
		log.info("sendMessage() in PublishBookingService is called {} ", booking);
		
		//MessageChannel messageChannel = bookingStream.writeMessage();
		bookingStream.writeMessage().send(MessageBuilder.withPayload(booking).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
		log.info("sendMessage() in PublishBookingService is ended {} ", booking);
	}*/
}