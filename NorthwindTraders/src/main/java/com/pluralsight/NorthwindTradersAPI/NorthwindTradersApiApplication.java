package com.pluralsight.NorthwindTradersAPI;

import com.pluralsight.NorthwindTradersAPI.ui.CredentialUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NorthwindTradersApiApplication {

    public static void main(String[] args) {

        // Prompt user via console UI
        CredentialUI.Credentials creds = CredentialUI.prompt();

        // Pass values into Spring Boot environment
        System.setProperty("dbUsername", creds.username());
        System.setProperty("dbPassword", creds.password());
        System.setProperty("spring.profiles.active", creds.profile());

        System.out.println("\nStarting API with profile: " + creds.profile() + "...");

        SpringApplication.run(NorthwindTradersApiApplication.class, args);
    }
}