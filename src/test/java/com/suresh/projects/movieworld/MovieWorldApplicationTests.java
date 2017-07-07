package com.suresh.projects.movieworld;

import java.util.Arrays;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@ContextConfiguration
@SpringBootTest(classes = MovieWorldApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieWorldApplicationTests {

	@Bean
	public RestTemplateBuilder restTemplateBuilder() {
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    mapper.registerModule(new Jackson2HalModule());

	    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
	    converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
	    converter.setObjectMapper(mapper);

	  return new RestTemplateBuilder().additionalMessageConverters(Arrays.asList(converter));
	}
	
	 @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}