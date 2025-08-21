package com.simpleproject.NewsAggregator.controller;

import com.simpleproject.NewsAggregator.dto.NewsDto;
import com.simpleproject.NewsAggregator.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AggregatorController {

    private final NewsService newsService;

    @GetMapping("/api/v1/news")
    public List<NewsDto> getAllNews() {

        return newsService.getNews();
    }

    @GetMapping("/api/vi/news/{id}")
    public ResponseEntity<NewsDto> getNewsById(@PathVariable Long id) {

        return newsService.findById(id);
    }

    @GetMapping("api/v1/categories")
    public List<String> getCategories() {

        return List.of("category1");
    }

    @GetMapping("api/v1/sourses")
    public List<String> getSourses() {

        return List.of("url");
    }
}