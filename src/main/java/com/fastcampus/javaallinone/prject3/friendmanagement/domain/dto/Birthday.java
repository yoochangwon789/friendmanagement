package com.fastcampus.javaallinone.prject3.friendmanagement.domain.dto;

import javax.persistence.Embeddable;

@Embeddable
public class Birthday {

    private int yearOfBirthday;

    private int monthOfBirthday;

    private int dayOfBirthday;
}
