package com.pluralsight.SakilaSpring.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String homePage(
            @RequestParam(defaultValue = "World") String name,
            @RequestParam(defaultValue = "Red") String favoriteColor
    ) {
        return String.format("Hello %s! Your favorite color is %s.", name, favoriteColor);
    }

    @GetMapping("eric")
    public String eric() {
        return "Eric magic!";
    }

    @GetMapping("andy")
    public String andy() {
        return "Andy magic!";
    }
}