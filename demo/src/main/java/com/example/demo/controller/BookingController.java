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
package com.example.demo.controller;

//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import com.example.demo.model.Booking;
import com.example.demo.service.MessageListener;
import com.example.demo.service.PublishBookingService;

//import lombok.extern.slf4j.Slf4j;

/**
 * TODO Provide a detailed description here 
 * @author sanilkumar
 * , 
 */

@RestController
//@Slf4j
public class BookingController {

	MessageListener messageListener;
	
	PublishBookingService publishBookingService;
	
	public BookingController (PublishBookingService publishBookingService, MessageListener messageListener) {
		
		this.publishBookingService=publishBookingService;
		this.messageListener=messageListener;
	}
	
/*	@RequestMapping("/getBooking")
    public void getMessage(@RequestParam("doctorName") String doctorName,@RequestParam("patientName") String patientName) {
		
		log.info("getMessage() in BookingController is called ");
        Booking booking = Booking.builder().doctorName(doctorName).build();
        booking.setPatientName(patientName);
        
		publishBookingService.sendMessage(booking);
		log.info("getMessage() in BookingController is ended ");
		
	}*/
}
