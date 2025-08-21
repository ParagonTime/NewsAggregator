package com.simpleproject.NewsAggregator.service;

import com.simpleproject.NewsAggregator.dto.NewsDto;
import com.simpleproject.NewsAggregator.dto.SourceDto;
import com.simpleproject.NewsAggregator.repository.SourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsParsingOrchestrator {

    private final HttpFetcher fetcherService;
    private final SourceRepository repository;



    public void parseAllActiveSources() {
        System.out.println("вызван оркестратор");

        List<NewsDto> resultNewsList =  new ArrayList<>();

        List<SourceDto> sources = repository.findAll().stream()
                .map(SourceDto::toDto)
                .toList();
        System.out.println(sources.stream().toList());

        try {
            for (SourceDto source : sources) {
                List<NewsDto> listNews = fetcherService.getNewsFromSource(source);
                System.out.println(source.name() + " " + listNews.size() + " новостей");
                resultNewsList.addAll(listNews);
            }
        } catch (Exception e) {
            System.out.println("ошибка в NewsParsingOrchestrator");
            e.getStackTrace();
        }

        System.out.println(resultNewsList.stream().toList());
        // здесь нужно отправлять эти новости в базу данных таблицы news
    }
}
