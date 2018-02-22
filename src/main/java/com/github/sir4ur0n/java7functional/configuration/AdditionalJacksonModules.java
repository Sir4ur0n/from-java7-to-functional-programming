package com.github.sir4ur0n.java7functional.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vavr.collection.List;
import io.vavr.jackson.datatype.VavrModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AdditionalJacksonModules {

  @Bean
  public Module vavr() {
    return new VavrModule();
  }

  @Bean
  public Module java8Time() {
    return new JavaTimeModule();
  }

  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();

    restTemplate.setMessageConverters(List.ofAll(restTemplate.getMessageConverters())
        .map(converter -> converter instanceof MappingJackson2HttpMessageConverter
            ? mappingJackson2HttpMessageConverter()
            : converter
        ).toJavaList());

    return restTemplate;
  }

  @Bean
  public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
    MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(vavr());
    objectMapper.registerModule(java8Time());
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    jsonConverter.setObjectMapper(objectMapper);
    return jsonConverter;
  }
}
