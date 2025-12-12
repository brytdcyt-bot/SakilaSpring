package com.pluralsight.NorthwindTradersAPI.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    /**
     * Simple greeting endpoint.
     *
     * @param country Optional query parameter, defaults to "World".
     * @return Greeting message.
     */
    @GetMapping("/")
    public String homePage(@RequestParam(name = "country", defaultValue = "World") String country) {
        // Use String.format for clearer concatenation
        return String.format("Hello %s", country);
    }
}