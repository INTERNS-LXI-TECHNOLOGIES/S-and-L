package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Patient
{
private String name;
private String address;
private Long age;


}
