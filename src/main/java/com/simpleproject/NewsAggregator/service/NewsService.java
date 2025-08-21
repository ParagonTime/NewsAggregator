package com.simpleproject.NewsAggregator.service;

import com.simpleproject.NewsAggregator.dto.NewsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    public List<NewsDto> getNews() {
        return null;
    }

    public ResponseEntity<NewsDto> findById(Long id) {
        return null;
    }
}
