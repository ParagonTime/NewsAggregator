package com.simpleproject.NewsAggregator.client;

import com.simpleproject.NewsAggregator.dto.SourceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class WebClient {



    public String fetchSource(SourceDto source) {

        System.out.println("Запрос к: " + source.urlToFetch());

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "NewsSercher00/1.0");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                source.urlToFetch(),
                HttpMethod.GET,
                entity,
                String.class
        );


        System.out.println("Status code: " + response.getStatusCode());
        //System.out.println("body: " + response.getBody());

        return response.getBody().toString();
    }
}
