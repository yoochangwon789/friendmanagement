package com.fastcampus.javaallinone.prject3.friendmanagement.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;

@Configuration
public class JsonConfig {

    // 내가 만든 ObjectMapper 를 주입 하기 위한 작업
    @Bean
    public MappingJackson2CborHttpMessageConverter mappingJackson2CborHttpMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2CborHttpMessageConverter converter = new MappingJackson2CborHttpMessageConverter();
        converter.setObjectMapper(objectMapper);

        return converter;
    }

    // 커스텀 마이징 하는 곳
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // 내가 만든 Serializer 를 등록할 수 있다.
        objectMapper.registerModule();

        return objectMapper;
    }
}
