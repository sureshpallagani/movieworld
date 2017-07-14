package com.suresh.projects.movieworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import com.suresh.projects.movieworld.util.CucumberGlobalContext;

@ContextConfiguration
@SpringBootTest(classes = MovieWorldApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application-test.properties")
public class MovieWorldApplicationTests {
	@Autowired protected TestRestTemplate restTemplate;
	@Autowired protected CucumberGlobalContext globalContext;
}
