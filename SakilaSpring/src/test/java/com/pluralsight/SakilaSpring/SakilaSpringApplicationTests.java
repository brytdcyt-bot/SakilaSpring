package com.pluralsight.SakilaSpring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that the Spring Boot application loads correctly at a framework level.
 * These tests should not hit external systems; they ensure that the context,
 * configuration, and bean wiring are valid.
 */
@SpringBootTest
class SakilaSpringApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    @DisplayName("Application context should load without errors")
    void contextLoads() {
        // If the application fails to start, this test will fail before reaching assertions.
        assertThat(applicationContext)
                .as("Spring ApplicationContext should be initialized")
                .isNotNull();
    }

    @Test
    @DisplayName("Main application class should be registered as a Spring bean")
    void mainApplicationBeanExists() {
        boolean containsMainBean =
                applicationContext.containsBeanDefinition("sakilaSpringApplication");

        assertThat(containsMainBean)
                .as("Main @SpringBootApplication class should be registered in context")
                .isTrue();
    }

}