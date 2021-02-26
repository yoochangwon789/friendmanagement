package com.fastcampus.javaallinone.prject3.friendmanagement.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.predicate;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class HelloWorldControllerTest {

    @Autowired
    private HelloWorldController helloWorldController;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(helloWorldController)
                .alwaysDo(print())
                .build();
    }

    @Test
    void helloWorld() throws Exception {
        // perform 을 통해 실제 http 동작을 실행
        mockMvc.perform(MockMvcRequestBuilders.get("/api/helloWorld"))
                // 구체적인 test perform 의 결과를 도출 하기 위한 작업)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("HelloWorld"));
    }

    @Test
    void helloException() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/helloException"))
                .andExpect(status().isInternalServerError());
    }
}