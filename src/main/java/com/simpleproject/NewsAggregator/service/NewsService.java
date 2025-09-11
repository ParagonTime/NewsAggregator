package com.simpleproject.NewsAggregator.service;

import com.simpleproject.NewsAggregator.dto.NewsDto;
import com.simpleproject.NewsAggregator.entity.NewsEntity;
import com.simpleproject.NewsAggregator.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    public List<NewsDto> getNews() {
        List<NewsEntity> requestNews = newsRepository.getNewsLimit25();
        if (requestNews == null || requestNews.isEmpty()) {
            throw new NoSuchElementException("No news avaliable");
        }
        return requestNews.stream().map(NewsEntity::toDto).toList();
    }

    public NewsDto findNewsById(Long id) {
        // проверка для throw new IllegalArgumentException();
        if (id > 1_000_000) {
            throw new IllegalArgumentException("News with id:" + id + " not found");
        }
        Optional<NewsEntity> transfer = newsRepository.findById(id);
        if (transfer != null && transfer.isPresent()) {
            return transfer.get().toDto();
        }
        throw new NoSuchElementException("News with id:" + id + " not found");
    }
}
