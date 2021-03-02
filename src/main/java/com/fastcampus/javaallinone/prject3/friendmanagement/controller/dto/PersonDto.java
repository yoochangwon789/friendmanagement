package com.fastcampus.javaallinone.prject3.friendmanagement.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class PersonDto {

    @NotEmpty
    private String name;

    private String hobby;

    private String address;

    private LocalDate birthday;

    private String job;

    private String phoneNumber;
}
