package com.simpleproject.NewsAggregator.service;

import com.simpleproject.NewsAggregator.dto.NewsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class NewsService {

    public String[] getNews() {
        return new String[] {"dddd"};
    }

    public ResponseEntity<NewsDto> findById(Long id) {
        return null;
    }
}
