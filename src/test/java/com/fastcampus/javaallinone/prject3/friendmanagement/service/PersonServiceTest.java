package com.fastcampus.javaallinone.prject3.friendmanagement.service;

import com.fastcampus.javaallinone.prject3.friendmanagement.domain.Person;
import com.fastcampus.javaallinone.prject3.friendmanagement.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Test
    void getPeopleExcludeBlocks() {
        List<Person> result = personService.getPeopleExcludeBlocks();

        result.forEach(System.out::println);

        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0).getName()).isEqualTo("martin");
        assertThat(result.get(1).getName()).isEqualTo("david");
        assertThat(result.get(2).getName()).isEqualTo("benny");
    }

    @Test
    void getPeopleByName() {
        List<Person> result = personService.getPeopleByName("martin");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("martin");
    }

    @Test
    void getPerson() {
        Person person = personService.getPerson(3L);

        assertThat(person.getName()).isEqualTo("dennis");
    }
}