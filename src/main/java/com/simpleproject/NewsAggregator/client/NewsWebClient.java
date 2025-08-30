package com.simpleproject.NewsAggregator.client;

import com.simpleproject.NewsAggregator.dto.SourceDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class NewsWebClient {

    private final WebClient webClient;

    public NewsWebClient(WebClient.Builder webClientBuilder) {
        webClient = webClientBuilder
                .defaultHeader("User-Agent", "NewsSercher00/1.0")
                .build();
    }

    public String fetchSource(SourceDto source) {
        log.info("web client запрос к: {}", source.urlToFetch());

        return webClient.get()
                .uri(source.urlToFetch())
                .header("User-Agent", "NewsSercher00/1.0")
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> log.info("200 OK {}", source.urlToFetch()))
                .doOnError(error -> log.error("Error: {}", error.getMessage()))
                .block();
    }
}
