package com.fastcampus.javaallinone.prject3.friendmanagement.configuration;

import com.fastcampus.javaallinone.prject3.friendmanagement.configuration.serializer.BirthdaySerializer;
import com.fastcampus.javaallinone.prject3.friendmanagement.domain.dto.Birthday;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class JsonConfig {

    // 내가 만든 ObjectMapper 를 주입 하기 위한 작업
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);

        return converter;
    }

    // 커스텀 마이징 하는 곳
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // 내가 만든 Serializer 를 등록할 수 있다.
        objectMapper.registerModule(new BirthdayModule());

        return objectMapper;
    }
    
    // module 을 Serializer 를 맵핑 하는 작업
    static class BirthdayModule extends SimpleModule {
        BirthdayModule() {
            super();
            // 나는 Birthday 를 가져와 Birthday 를 Serializer 을 하겠다 라는 의미
            addSerializer(Birthday.class, new BirthdaySerializer());
        }
    }

    /*
    * 1. 내가 만든 Serializer 을 BirthdayModule 주입해준다
    * 2. BirthdayModule 를 objectMapper 에 넣어준 후
    * 3. MappingJackson2HttpMessageConverter 에 넣어주는 형식이다
    */
}
