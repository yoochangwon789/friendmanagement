package com.fastcampus.javaallinone.prject3.friendmanagement.service;

import com.fastcampus.javaallinone.prject3.friendmanagement.domain.Person;
import com.fastcampus.javaallinone.prject3.friendmanagement.repository.PersonRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

// spring contact loading 이 없다면 테스트 코드를 보다 더 효율적이고 빠르게 사용할 수 있다.
// SpringBootTest 를 사용하지 않고 Mock 라이브러리 테스트로 구동 시킬 예정.

// Mockito 를 사용해서 기능을 수행시키 겠다는 의미.
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    // InjectMocks 는 테스트의 대상인 클래스의 붙여주면 된다
    @InjectMocks
    private PersonService personService;

    // 테스트 대상인 클래스의 오토와이어드는 @Mock 로 대체하면 된다.
    // @Mock 로 선언한 PersonRepository 는 @InjectMocks 의 PersonService 의 주입을 시켜준다.
    @Mock
    private PersonRepository personRepository;

    // Mock 로 선언된 클래스는 빈 껍때기 이므로 Test 코드에서 직접 구현을 시켜줘야 한다
    @Test
    void getPeopleByName() {
        // Mock 테스트는 실제로 구현된 객체를 사용하는 것이 아니라 단순히 메소드 시그니처 만을 동일한 가짜 객체를 이용해서 사용함
        when(personRepository.findByName("martin"))
                .thenReturn(Lists.newArrayList(new Person("martin")));

        List<Person> result = personService.getPeopleByName("martin");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("martin");
    }

    @Test
    void getPerson() {
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        Person person = personService.getPerson(1L);

        assertThat(person.getName()).isEqualTo("martin");
    }
}