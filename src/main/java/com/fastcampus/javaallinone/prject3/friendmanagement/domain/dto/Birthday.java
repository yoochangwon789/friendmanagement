package com.fastcampus.javaallinone.prject3.friendmanagement.domain.dto;

import lombok.AllArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
public class Birthday {

    private int yearOfBirthday;

    private int monthOfBirthday;

    private int dayOfBirthday;
}
