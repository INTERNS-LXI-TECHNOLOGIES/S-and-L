
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
package com.byta.aayos.listner;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.byta.aayos.web.rest.HealthInsuranceResource;
import com.byta.aayos.web.stream.*;
import com.esotericsoftware.minlog.Log;
import com.byta.aayos.domain.HealthInsurance;
import com.byta.aayos.repository.HealthInsuranceRepository;
import com.byta.aayos.service.HealthInsuranceService;
import com.byta.aayos.service.dto.HealthInsuranceDTO;

import lombok.extern.slf4j.Slf4j;

/**
* TODO Provide a detailed description here 
* @author HP
* jeseeljamal, jeseel.j@lxisoft.com
*/
@Slf4j
@Component
public class HealthInsurerListner {

	@Autowired
	HealthInsuranceService h;

		@StreamListener(HealthInsuranceStream.HEALTH_INSURANCE)
		public void listenToMessages(@Payload HealthInsuranceDTO healthInsurance){
		
		Log.info("listener captured +++++++++++++++++++++++++++++ "+healthInsurance);
	LocalDate localDate=LocalDate.of(2025, 10, 10) ;
			//Log.info(healthInsurance);
		healthInsurance.setExpiryDate(localDate);
h.save(healthInsurance);
Log.info("listener captured +++++++++++++++++++++++++++++ "+healthInsurance);


		}
	}
