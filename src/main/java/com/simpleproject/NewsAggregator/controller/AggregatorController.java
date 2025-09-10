package com.simpleproject.NewsAggregator.controller;

import com.simpleproject.NewsAggregator.dto.NewsDto;
import com.simpleproject.NewsAggregator.service.NewsService;
import com.simpleproject.NewsAggregator.service.SourcesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "News Aggregator", description = "API для получения новостей")
public class AggregatorController {

    private final NewsService newsService;
    private final SourcesService sourcesService;

    @Operation(summary = "Получить новости", description = "Возвращает список из 25 последний новостей")
    @ApiResponse(responseCode = "200", description = "Новости получены")
    @GetMapping("/api/v1/news")
    public List<NewsDto> getNews() {
        return newsService.getNews();
    }

    @Operation(summary = "Получить новость по ID", description = "Возвращает новость по указанному ID")
    @ApiResponse(responseCode = "200", description = "Новость найдена")
    @ApiResponse(responseCode = "404", description = "Новость не найдена")
    @GetMapping("/api/v1/news/{id}")
    public NewsDto getNewsById(
            @PathVariable
            @Min(value = 1, message = "Индекс должен быть больше 0")
            Long id) {
        return newsService.findNewsById(id);
    }

    @Operation(summary = "Получить категории новостей", description = "Возвращает список категорий новостей")
    @ApiResponse(responseCode = "200", description = "Категории получены")
    @GetMapping("api/v1/categories")
    public List<String> getCategories() {
        return sourcesService.getCategories();
    }

    @Operation(summary = "Получить источники новостей", description = "Возвращает список источников новостей")
    @ApiResponse(responseCode = "200", description = "Источники получены")
    @GetMapping("api/v1/sources")
    public List<String> getSources() {

        return sourcesService.getResources();
    }
}