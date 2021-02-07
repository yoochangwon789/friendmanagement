package com.fastcampus.javaallinone.prject3.friendmanagement.repository;

import com.fastcampus.javaallinone.prject3.friendmanagement.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void crud() {
        Person person = new Person();

        personRepository.save(person);

        System.out.println(personRepository.findAll());
    }
}