package com.simpleproject.NewsAggregator.service;

import com.simpleproject.NewsAggregator.client.NewsWebClient;
import com.simpleproject.NewsAggregator.client.RestTemplateClient;
import com.simpleproject.NewsAggregator.dto.NewsDto;
import com.simpleproject.NewsAggregator.dto.SourceDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class HttpFetcher {

//    private RestTemplateClient client;
//    private NewsWebClient webClient;
    private NewsWebClient client;
    private NewsParser parser;

    public List<NewsDto> getNewsFromSource(SourceDto source){

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

        
//        String responceWeb = webClient.fetchSource(source);
//        System.out.println(responceWeb.equals(responce));

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
