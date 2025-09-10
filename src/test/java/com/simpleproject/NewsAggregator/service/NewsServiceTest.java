package com.simpleproject.NewsAggregator.service;

import com.simpleproject.NewsAggregator.dto.NewsDto;
import com.simpleproject.NewsAggregator.entity.NewsEntity;
import com.simpleproject.NewsAggregator.repository.NewsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {

    @Mock
    private NewsRepository newsRepository;

    @InjectMocks
    private NewsService newsService;

    @Test
    void getNews_ShouldReturnNewsList_WhenRepositoryHasData() {
        NewsEntity entity = new NewsEntity();
        when(newsRepository.getNewsLimit25()).thenReturn(List.of(entity));

        List<NewsDto> result = newsService.getNews();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(newsRepository).getNewsLimit25();
    }

    @Test
    void getNews_ShouldReturnNull_WhenRepositoryReturnsEmptyList() {
        when(newsRepository.getNewsLimit25()).thenReturn(List.of());

        List<NewsDto> result = newsService.getNews();

        assertNull(result);
        verify(newsRepository).getNewsLimit25();
    }

    @Test
    void getNews_ShouldReturnNull_WhenRepositoryReturnsNull() {
        when(newsRepository.getNewsLimit25()).thenReturn(null);

        List<NewsDto> result = newsService.getNews();

        assertNull(result);
        verify(newsRepository).getNewsLimit25();
    }

    @Test
    void findNewsById_ShouldReturnNews_WhenNewsExists() {
        NewsEntity entity = new NewsEntity();
        when(newsRepository.findById(1L)).thenReturn(Optional.of(entity));

        NewsDto result = newsService.findNewsById(1L);

        assertNotNull(result);
        verify(newsRepository).findById(1L);
    }

    @Test
    void findNewsById_ShouldReturnNull_WhenNewsNotFound() {
        when(newsRepository.findById(1L)).thenReturn(Optional.empty());

        NewsDto result = newsService.findNewsById(1L);

        assertNull(result);
        verify(newsRepository).findById(1L);
    }

    @Test
    void findNewsById_ShouldReturnNull_WhenRepositoryReturnsNull() {
        when(newsRepository.findById(1L)).thenReturn(null);

        NewsDto result = newsService.findNewsById(1L);

        assertNull(result);
        verify(newsRepository).findById(1L);
    }
}