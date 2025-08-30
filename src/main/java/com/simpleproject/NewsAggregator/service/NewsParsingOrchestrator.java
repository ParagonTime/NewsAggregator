package com.simpleproject.NewsAggregator.service;

import com.simpleproject.NewsAggregator.dto.NewsDto;
import com.simpleproject.NewsAggregator.dto.SourceDto;
import com.simpleproject.NewsAggregator.entity.NewsEntity;
import com.simpleproject.NewsAggregator.repository.NewsRepository;
import com.simpleproject.NewsAggregator.repository.SourceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsParsingOrchestrator {

    private final HttpFetcher fetcherService;
    private final SourceRepository sourceRepository;
    private final NewsRepository newsRepository;

    public void parseAllActiveSources() {
        log.info("Вызван оркестратор");

        List<NewsDto> resultNewsList =  new ArrayList<>();

        List<SourceDto> sources = sourceRepository.findAllActiveSources().stream()
                .map(SourceDto::toDto)
                .toList();
        try {
            List<NewsDto> listNews = fetcherService.getNewsFromSources(sources);
            log.info("Найдено {} новостей", listNews.size());
            resultNewsList.addAll(listNews);

        } catch (Exception e) {
            log.error("ошибка в NewsParsingOrchestrator");
            e.getStackTrace();
        }
        List<NewsEntity> newsEntityList = resultNewsList.stream()
                .map(NewsDto::toEntity)
                .toList();
        saveFromList(newsEntityList);
    }

    private void saveFromList(List<NewsEntity> newsList) {
        int newNewsCount = 0;
        for (NewsEntity news : newsList) {
            if (newsRepository.findByLink(news.getLink()) == null) {
                newsRepository.save(news);
                newNewsCount++;
                log.info("Новость внесена в базу: {}", news.getTitle());
            }
        }
        log.info("{} новых новостей добавлено в базу", newNewsCount);
    }
}


