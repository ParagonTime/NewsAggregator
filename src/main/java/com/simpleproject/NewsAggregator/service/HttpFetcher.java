package com.simpleproject.NewsAggregator.service;

import com.simpleproject.NewsAggregator.client.WebClient;
import com.simpleproject.NewsAggregator.dto.NewsDto;
import com.simpleproject.NewsAggregator.dto.SourceDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class HttpFetcher {

    private WebClient client;
    private NewsParser parser;

    public List<NewsDto> getNewsFromSource(SourceDto source){
        System.out.println("вызван HttpFetcherService");
        List<NewsDto> newsList = List.of();
        String responce = null;
        try {
            responce = client.fetchSource(source);
        } catch (Exception e) {
            System.out.println("Ошибка при вызове client.fetchSource(source)");;
        }
        try {
            assert responce != null;
            newsList = parser.parseXML(responce);
        } catch (Exception e) {
            System.out.println("ошибка при вызове parser.parseXML(responce.toString())");
        }

        return newsList;
    }

    public List<NewsDto> getNewsFromSources(List<SourceDto> sources) {
        List<NewsDto> resultList = new ArrayList<>(); // mb use linked list?
        for (SourceDto source : sources) {
            resultList.addAll(getNewsFromSource(source));
        }
        return resultList;
    }
}
