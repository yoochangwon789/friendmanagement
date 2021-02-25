package com.fastcampus.javaallinone.prject3.friendmanagement.service;

import com.fastcampus.javaallinone.prject3.friendmanagement.controller.dto.PersonDto;
import com.fastcampus.javaallinone.prject3.friendmanagement.domain.Person;
import com.fastcampus.javaallinone.prject3.friendmanagement.domain.dto.Birthday;
import com.fastcampus.javaallinone.prject3.friendmanagement.repository.PersonRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    /*
    * 만약 SpringBootTest 로 돌렸으면 DB에 따로 지정된 객체를 만들어 거기에 맞는 테스트 코드를 맞춰야 하지만
    * Mock 를 사용하면 가차 객체를 바로 테스트 코드에 구현하여 테스트 코드를 보다 더 효율적이게 사용할 수 있다
    */

    @Test
    void getPerson() {
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        Person person = personService.getPerson(1L);

        assertThat(person.getName()).isEqualTo("martin");
    }

    @Test
    void getPersonIfNotFound() {
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        Person person = personService.getPerson(1L);

        assertThat(person).isNull();
    }

    @Test
    void put() {
        /*
        * Mock 의 대한 Test 코드를 지정해 주지 않아도 테스트 코드가 통가된다. 이유는 Mockito 리턴 값이 있거나 exception 이 발생하거나
        * 그런 행위들의 역할이 없으면 굳이 지정을 하지 않아도 테스트 코드가 통과된다.
        * */

        personService.put(mockPersonDto());

        // void 타입의 메서드를 테스트 하기 위해서 Mockito 의 verify 라는 것을 사용한다.
        // 우리가 지정하는 Mock 액션에 대해서 검증을 지원한다.
        // personRepository 의 체크를 하고 save 메서드가 실행이 되었는지 그리고 파라미터 값이 지정한 값이냐 확인 하는 것.
        // times -> 몇번 실행 되었는지 never -> 실행이 한번도 되지 않았는지 확인 하는 것
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    void modifyIfPersonNotFound() {
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> personService.modify(1L, mockPersonDto()));
    }

    @Test
    void modifyIfNameIsDifferent() {
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("tony")));

        assertThrows(RuntimeException.class, () -> personService.modify(1L, mockPersonDto()));
    }

    @Test
    void modify() {
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        personService.modify(1L, mockPersonDto());

//        verify(personRepository, times(1)).save(any(Person.class));
        verify(personRepository, times(1)).save(argThat(new IsPersonWillBeUpdated()));
    }

    @Test
    void modifyByNameIfPersonNotFound() {
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> personService.modify(1L, "daniel"));
    }

    @Test
    void modifyByName() {
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        personService.modify(1L, "daniel");

        verify(personRepository, times(1)).save(argThat(new IsNameWillBeUpdated()));
    }

    @Test
    void deleteIfPersonNotFound() {
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> personService.delete(1L));
    }

    @Test
    void delete() {
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        personService.delete(1L);
    }

    private PersonDto mockPersonDto() {
        return PersonDto.of("martin", "programming", "판교", LocalDate.now(),
                "programmer", "010-1111-2222");
    }

    //중요 로직을 지웠을 경우 테스트 코드가 검증을 못하고 통과시키는 경우를 대비해 현업에서 쓰이는 테스트 코드 구현 추가
    private static class IsPersonWillBeUpdated implements ArgumentMatcher<Person> {

        @Override
         public boolean matches(Person person) {
             return equals(person.getName(), "martin")
                     && equals(person.getHobby(), "programming")
                     && equals(person.getAddress(), "판교")
                     && equals(person.getBirthday(), Birthday.of(LocalDate.now()))
                     && equals(person.getJob(), "programmer")
                     && equals(person.getPhoneNumber(), "010-1111-2222");
         }

         private boolean equals(Object actual, Object expected) {
            return expected.equals(actual);
         }
     }

     private static class IsNameWillBeUpdated implements ArgumentMatcher<Person> {

         @Override
         public boolean matches(Person person) {
             return person.getName().equals("daniel");
         }
     }
}