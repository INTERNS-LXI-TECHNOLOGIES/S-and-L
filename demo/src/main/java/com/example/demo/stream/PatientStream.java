package com.example.demo.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface PatientStream 
{
 String MSGTOPIC1="messages-in",MSGTOPIC2="messages-out"; 
 
 @Input(MSGTOPIC1)
 SubscribableChannel getChannel();
 
 @Output(MSGTOPIC2)
 MessageChannel  setMsgChannel();
 
}
