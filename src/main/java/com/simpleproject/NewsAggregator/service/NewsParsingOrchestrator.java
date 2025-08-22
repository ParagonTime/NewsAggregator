package com.simpleproject.NewsAggregator.service;

import com.simpleproject.NewsAggregator.dto.NewsDto;
import com.simpleproject.NewsAggregator.dto.SourceDto;
import com.simpleproject.NewsAggregator.entity.NewsEntity;
import com.simpleproject.NewsAggregator.repository.NewsRepository;
import com.simpleproject.NewsAggregator.repository.SourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsParsingOrchestrator {

    private final HttpFetcher fetcherService;
    private final SourceRepository sourceRepository;
    private final NewsRepository newsRepository;



    public void parseAllActiveSources() {
        System.out.println("вызван оркестратор");

        List<NewsDto> resultNewsList =  new ArrayList<>();

        List<SourceDto> sources = sourceRepository.findAllActiveSources().stream()
                .map(SourceDto::toDto)
                .toList();
        // System.out.println(sources.stream().toList());

        try {
//            for (SourceDto source : sources) {
//                List<NewsDto> listNews = fetcherService.getNewsFromSource(source);
//                System.out.println(source.name() + " " + listNews.size() + " новостей");
//                resultNewsList.addAll(listNews);
//            }
            List<NewsDto> listNews = fetcherService.getNewsFromSources(sources);
            System.out.println("Найдено " + listNews.size() + " новостей");
            resultNewsList.addAll(listNews);

        } catch (Exception e) {
            System.out.println("ошибка в NewsParsingOrchestrator");
            e.getStackTrace();
        }

        //System.out.println(resultNewsList.stream().toList());
        // преобразовываем dto в entity
        // здесь нужно отправлять эти новости в базу данных таблицы news
        List<NewsEntity> newsEntityList = resultNewsList.stream()
                .map(NewsDto::toEntity)
                .toList();

        saveFromList(newsEntityList);

    }

    private void saveFromList(List<NewsEntity> newsList) {
        for (NewsEntity news : newsList) {
            if (newsRepository.findByLink(news.getLink()) == null) {
                newsRepository.save(news);
                System.out.println("Новость: " + news.getTitle() + " внесена в базу");
            }
        }
    }

}


