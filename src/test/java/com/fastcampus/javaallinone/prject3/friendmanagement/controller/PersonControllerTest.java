package com.fastcampus.javaallinone.prject3.friendmanagement.controller;

import com.fastcampus.javaallinone.prject3.friendmanagement.controller.dto.PersonDto;
import com.fastcampus.javaallinone.prject3.friendmanagement.domain.Person;
import com.fastcampus.javaallinone.prject3.friendmanagement.domain.dto.Birthday;
import com.fastcampus.javaallinone.prject3.friendmanagement.repository.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.NestedServletException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@Transactional
class PersonControllerTest {

    @Autowired
    private PersonController personController;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter messageConverter;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(personController).setMessageConverters().build();
    }

    @Test
    void getPerson() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person/1"))
                .andDo(print())
                .andExpect(status().isOk())
                // $ 객체를 의미하고 . 을 통해서 각 어트리뷰트를 가져올 수 있다 객체 타입으로 선언된 것은 $ 을 사용
                // 밑에 로직은 assertThat(result.getName()).isEqualTo("martin") 이랑 동일하고 밑에는 JsonPath 의 검증이라고 할 수 있다.
                .andExpect(jsonPath("$.name").value("martin"))
                .andExpect(jsonPath("$.hobby").isEmpty())
                .andExpect(jsonPath("$.address").isEmpty())
                .andExpect(jsonPath("$.birthday.yearOfBirthday").value(1991))
                .andExpect(jsonPath("$.birthday.monthOfBirthday").value(8))
                .andExpect(jsonPath("$.birthday.dayOfBirthday").value(15))
                .andExpect(jsonPath("$.job").isEmpty())
                .andExpect(jsonPath("$.phoneNumber").isEmpty())
                .andExpect(jsonPath("$.deleted").value(false))
                // exists 것은 값이 존재하느냐의 여부를 묻는다, isNumber 으로 지정해서 숫자의 값을 가지고 있느냐의 여부를 묻게한다
                .andExpect(jsonPath("$.age").isNumber())
                // 생일 하루가 잠재적인 오류를 발생할 수 있으므로 boolean 의 값을 가지냐 정도의 TDD 작성
                .andExpect(jsonPath("$.birthdayToday").isBoolean());
    }

    @Test
    void postPerson() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"martin2\",\n" +
                                "  \"age\": 20,\n" +
                                "  \"bloodType\": \"A\"\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void modifyPerson() throws Exception {
        PersonDto dto = PersonDto.of("martin", "programming", "판교", LocalDate.now(),
                "programmer", "010-1111-2222");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(dto)))
                .andDo(print())
                .andExpect(status().isOk());

        Person result = personRepository.findById(1L).get();

        // 모든 검증문을 한번에 확인 가능
        assertAll(
                () -> assertThat(result.getName()).isEqualTo("martin"),
                () -> assertThat(result.getHobby()).isEqualTo("programming"),
                () -> assertThat(result.getAddress()).isEqualTo("판교"),
                () -> assertThat(result.getBirthday()).isEqualTo(Birthday.of(LocalDate.now())),
                () -> assertThat(result.getJob()).isEqualTo("programmer"),
                () -> assertThat(result.getPhoneNumber()).isEqualTo("010-1111-2222")
        );
    }

    @Test
    void modifyPersonIfNameDifferent() throws Exception {
        PersonDto dto = PersonDto.of("james", "programming", "판교", LocalDate.now(),
                "programmer", "010-1111-2222");

        assertThrows(NestedServletException.class, () ->
                mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(dto)))
                .andDo(print())
                .andExpect(status().isOk()));
    }

    @Test
    void modifyName() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/person/1")
                .param("name", "martinModified"))
                .andDo(print())
                .andExpect(status().isOk());

        assertThat(personRepository.findById(1L).get().getName()).isEqualTo("martinModified");
    }

    @Test
    void deletePerson() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/person/1"))
                .andDo(print())
                .andExpect(status().isOk());

        assertTrue(personRepository.findPeopleDeleted().stream().anyMatch(person -> person.getId().equals(1L)));
    }

    // Json 형태의 값으로 변하는지 테스트 코드 작업
    @Test
    void checkJsonString() throws JsonProcessingException {
        PersonDto dto = new PersonDto();
        dto.setName("martin");
        dto.setBirthday(LocalDate.now());
        dto.setAddress("판교");

        System.out.println(">>>" + toJsonString(dto));
    }

    // Json 타입의 바디를 굳이 귀찮게 입력하지 않고도 personDto 를 Json 형태로 시리얼 라이즈 해준다.
    private String toJsonString(PersonDto personDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(personDto);
    }
}