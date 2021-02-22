package com.fastcampus.javaallinone.prject3.friendmanagement.service;

import com.fastcampus.javaallinone.prject3.friendmanagement.domain.Person;
import com.fastcampus.javaallinone.prject3.friendmanagement.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// spring contact loading 이 없다면 테스트 코드를 보다 더 효율적이고 빠르게 사용할 수 있다.
// SpringBootTest 를 사용하지 않고 Mock 라이브러리 테스트로 구동 시킬 예정

@SpringBootTest
class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

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