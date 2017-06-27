package com.suresh.projects.movieworld;

import java.time.LocalDate;
import java.util.concurrent.Executor;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "com.suresh.projects.movieworld")
@PropertySource("classpath:application.properties")
@EnableSwagger2
@EnableAsync
public class MovieWorldApplication extends SpringBootServletInitializer {

	/**
	 * Properties configuration to read from applicaiton.properties
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	/**
	 * Message Converters configuration for jackson to fail on any input unknown properties.
	 */
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		jsonConverter.setObjectMapper(objectMapper);
		return jsonConverter;
	}
	/**
	 * Swagger2 configuration for API Documentation.
	 */
	@Bean
	public Docket moviesApi() {
		return new Docket(DocumentationType.SWAGGER_2)
					.select().apis(RequestHandlerSelectors.any())
					.paths(PathSelectors.any())
					.build()
					.directModelSubstitute(LocalDate.class, String.class)
					.genericModelSubstitutes(ResponseEntity.class);
	}
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
	/**
	 * By default a SimpleAsyncTaskExecutor is used to run Async tasks. Below is the customized.
	 * @return
	 */
//    @Bean(name="taskExecutor")
//    public Executor asyncExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(2);
//        executor.setMaxPoolSize(2);
//        executor.setQueueCapacity(500);
//        executor.setThreadNamePrefix("MovieWorldDataSetUp-");
//        executor.initialize();
//        return executor;
//    }

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MovieWorldApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(MovieWorldApplication.class, args);
	}
}
