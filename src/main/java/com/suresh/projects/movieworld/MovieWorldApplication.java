package com.suresh.projects.movieworld;

import java.time.LocalDate;

import javax.jms.Session;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.DispatcherServlet;

import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
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
@EnableJms
public class MovieWorldApplication extends SpringBootServletInitializer {

	@Autowired private AwsPropertiesHelper awsPropertiesHelper;

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

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
    	DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(SQSConnectionFactory.builder()
	            .withRegion(Region.getRegion(Regions.US_EAST_2))
	            .withAWSCredentialsProvider(awsPropertiesHelper.getAwsCredentials())
	            .build());
        factory.setDestinationResolver(new DynamicDestinationResolver());//Using the destination resolver as dynamic since we want to refer the AWS SQS by their names.
        factory.setConcurrency("3-10");//3 listeners created initially and scales up to 10.
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);//This will make spring acknowledge to delete the message after our method is done.
        return factory;
    }

    @Bean
    public JmsTemplate defaultJmsTemplate() {
        return new JmsTemplate(SQSConnectionFactory.builder()
										            .withRegion(Region.getRegion(Regions.US_EAST_2))
										            .withAWSCredentialsProvider(awsPropertiesHelper.getAwsCredentials())
										            .build());
    }
	
//    @Bean
//    public DispatcherServlet dispatcherServlet() {
//        return new DispatcherServlet();
//    }
//
//    @Bean
//    public ServletRegistrationBean dispatcherServletRegistration(){
//        ServletRegistrationBean registrationBean = new ServletRegistrationBean(dispatcherServlet());
//        registrationBean.addUrlMappings("*/*");
//        registrationBean.setName(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);
//        return registrationBean;
//    }
    
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MovieWorldApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(MovieWorldApplication.class, args);
	}
}
