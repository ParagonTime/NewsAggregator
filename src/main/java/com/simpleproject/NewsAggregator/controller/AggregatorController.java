package com.simpleproject.NewsAggregator.controller;

import com.simpleproject.NewsAggregator.dto.NewsDto;
import com.simpleproject.NewsAggregator.service.NewsService;
import com.simpleproject.NewsAggregator.service.SourcesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AggregatorController {

    private final NewsService newsService;
    private final SourcesService sourcesService;

    @GetMapping("/api/v1/news")
    public List<NewsDto> getAllNews() {
        return newsService.getNews();
    }

    @GetMapping("/api/v1/news/{id}")
    public NewsDto getNewsById(@PathVariable Long id) {
        return newsService.findById(id);
    }

    @GetMapping("api/v1/categories")
    public List<String> getCategories() {
        return sourcesService.getAllCategories();
    }

    @GetMapping("api/v1/sources")
    public List<String> getSources() {
        return sourcesService.getAllResources();
    }
}