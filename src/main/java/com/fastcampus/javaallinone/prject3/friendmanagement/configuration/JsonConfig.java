package com.fastcampus.javaallinone.prject3.friendmanagement.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;

@Configuration
public class JsonConfig {

    @Bean
    public MappingJackson2CborHttpMessageConverter mappingJackson2CborHttpMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2CborHttpMessageConverter converter = new MappingJackson2CborHttpMessageConverter();
        converter.setObjectMapper(objectMapper);

        return converter;
    }

    @Bean
}
