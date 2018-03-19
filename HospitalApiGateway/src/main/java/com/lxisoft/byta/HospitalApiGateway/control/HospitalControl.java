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
package com.lxisoft.byta.HospitalApiGateway.control;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.lxisoft.byta.HospitalApiGateway.model.Hospital;
import com.lxisoft.byta.HospitalApiGateway.service.MessageListener;
import com.lxisoft.byta.HospitalApiGateway.service.PublishHospitalService;

import lombok.extern.slf4j.Slf4j;



/**
 * TODO Provide a detailed description here 
 * @author Sarangi Balu
 * , 
 */

@Slf4j
@RestController
public class HospitalControl 

{
	MessageListener messageListener;
	  
	PublishHospitalService publishHospitalService;
	  
	  public HospitalControl(PublishHospitalService publishHospitalService,MessageListener messageListener)
	  {
		  this.publishHospitalService=publishHospitalService;
		  this.messageListener=messageListener;
	  }
	  
	  @RequestMapping("/getMessage")
	  public void getMessage(@RequestParam("name") String name)
	  {
		  Hospital hospital=Hospital.builder().name(name).build();
		  publishHospitalService.sendChannel(hospital);
		  
		  log.info("getMessage: {} in HospitalControl", name);
	  }
	  

}
