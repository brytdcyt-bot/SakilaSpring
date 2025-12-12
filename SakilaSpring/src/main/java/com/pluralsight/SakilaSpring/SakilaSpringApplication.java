package com.pluralsight.SakilaSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class SakilaSpringApplication {

    public static void main(String[] args) {

        // Validate startup arguments
        if (args.length != 2) {
            System.err.println("Error: Missing database credentials.");
            System.err.println("Usage: java -jar app.jar <username> <password>");
            System.exit(1);
        }

        String dbUser = args[0];
        String dbPass = args[1];

        // Set properties for Spring Boot (safer than System.setProperty)
        Map<String, Object> defaultProps = new HashMap<>();
        defaultProps.put("db.username", dbUser);
        defaultProps.put("db.password", dbPass);

        SpringApplication app = new SpringApplication(SakilaSpringApplication.class);
        app.setDefaultProperties(defaultProps);

        // Start the application
        app.run(args);
    }
}
