package com.simpleproject.NewsAggregator.service;

import com.simpleproject.NewsAggregator.client.NewsWebClient;
import com.simpleproject.NewsAggregator.client.RestTemplateClient;
import com.simpleproject.NewsAggregator.dto.NewsDto;
import com.simpleproject.NewsAggregator.dto.SourceDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Validated
@AllArgsConstructor
public class HttpFetcher {

    private RestTemplateClient client;
//    private NewsWebClient client;
    private NewsParser parser;

    public List<NewsDto> getNewsFromSource(@Valid SourceDto source){
        List<NewsDto> newsList = List.of();
        String responce = null;
        try {
            responce = client.fetchSource(source);
        } catch (Exception e) {
            log.error("Ошибка при вызове client.fetchSource");;
        }
        try {
            assert responce != null;
            newsList = parser.parseXML(responce);
        } catch (Exception e) {
            log.error("ошибка при вызове parser.parseXML");
        }
        return newsList;
    }

    public List<NewsDto> getNewsFromSources(List<@Valid SourceDto> sources) {
        List<NewsDto> resultList = new ArrayList<>(List.of());
        for (SourceDto source : sources) {
            resultList.addAll(getNewsFromSource(source));
        }
        return resultList;
    }
}
