package com.fastcampus.javaallinone.prject3.friendmanagement.controller.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PersonDto {

    private String name;

    private String hobby;

    private String address;

    private LocalDate birthday;

    private String job;

    private String phoneNumber;
}
