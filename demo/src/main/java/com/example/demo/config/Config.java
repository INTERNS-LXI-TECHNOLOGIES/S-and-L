package com.example.demo.config;

import org.springframework.cloud.stream.annotation.EnableBinding;

import com.example.demo.stream.PatientStream;


@EnableBinding(PatientStream.class)
public class Config
{

}
