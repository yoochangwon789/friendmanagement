package com.fastcampus.javaallinone.prject3.friendmanagement.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor    // GeneratedValue 의 값을 생성자의 넣을 필요 없으므로 그 때 사용하는 어노탠션
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private int age;

    private String hobby;

    private String bloodType;

    private String address;

    private LocalDateTime birthday;

    private String job;

    @ToString.Exclude
    private String phoneNumber;
}
