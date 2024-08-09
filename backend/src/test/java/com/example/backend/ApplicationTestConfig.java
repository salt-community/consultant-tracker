package com.example.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ApplicationTestConfig {

    @Value("${server.port}")
    protected int port;

    protected TestRestTemplate restTemplate = new TestRestTemplate();
}