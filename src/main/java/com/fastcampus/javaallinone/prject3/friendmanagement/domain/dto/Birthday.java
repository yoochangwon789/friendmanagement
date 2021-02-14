package com.fastcampus.javaallinone.prject3.friendmanagement.domain.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Birthday {

    private int yearOfBirthday;

    private int monthOfBirthday;

    private int dayOfBirthday;
}
