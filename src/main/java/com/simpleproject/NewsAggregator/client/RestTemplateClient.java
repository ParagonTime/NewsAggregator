package com.simpleproject.NewsAggregator.client;

import com.simpleproject.NewsAggregator.dto.SourceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestTemplateClient {

    public String fetchSource(SourceDto source) {

        log.info("Запрос к: {}", source.urlToFetch());

        HttpHeaders headers = new HttpHeaders(); // не лучше ли вынести в конструктор?
        headers.set("User-Agent", "NewsSercher00/1.0");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate(); // не лучше ли вынести в конструктор?
        ResponseEntity<String> response = restTemplate.exchange(
                source.urlToFetch(),
                HttpMethod.GET,
                entity,
                String.class
        );

        log.info("Status code: {}", response.getStatusCode());

        return response.getBody().toString();
    }
}
