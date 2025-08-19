package com.simpleproject.NewsAggregator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AggregatorController {

    @GetMapping("/api/news")
    public String getAllNews() {
        return "all news coming soon";
    }
}
