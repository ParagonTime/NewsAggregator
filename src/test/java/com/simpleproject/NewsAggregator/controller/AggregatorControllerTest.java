package com.simpleproject.NewsAggregator.controller;

import com.simpleproject.NewsAggregator.dto.NewsDto;
import com.simpleproject.NewsAggregator.service.NewsService;
import com.simpleproject.NewsAggregator.service.SourcesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AggregatorControllerTest {

    @Mock
    private NewsService newsService;

    @Mock
    private SourcesService sourcesService;

    @InjectMocks
    private AggregatorController aggregatorController;

    private final NewsDto testNews = new NewsDto(
            1L,
            "Test Title",
            "https://test.com/news",
            "Test Description",
            "Technology",
            "2024-01-15T10:30:00Z",
            "https://test.com/image.jpg"
    );

    private final NewsDto testNews2 = new NewsDto(
            2L,
            "Another Title",
            "https://test.com/news2",
            "Another Description",
            "Sports",
            "2024-01-15T12:00:00Z",
            null
    );

    @Test
    void getNews_shouldReturnListOfNews_whenServiceReturnsData() {
        List<NewsDto> expectedNews = List.of(testNews, testNews2);
        when(newsService.getNews()).thenReturn(expectedNews);

        List<NewsDto> result = aggregatorController.getNews();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedNews, result);

        verify(newsService, times(1)).getNews();
        verifyNoMoreInteractions(newsService);
    }

    @Test
    void getNews_shouldReturnEmptyList_whenNoNewsAvailable() {
        when(newsService.getNews()).thenReturn(List.of());

        List<NewsDto> result = aggregatorController.getNews();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(newsService, times(1)).getNews();
    }

    @Test
    void getNewsById_shouldReturnNews_whenValidIdProvided() {
        Long validId = 1L;
        when(newsService.findNewsById(validId)).thenReturn(testNews);

        NewsDto result = aggregatorController.getNewsById(validId);

        assertNotNull(result);
        assertEquals(testNews, result);
        assertEquals(validId, result.id());
        verify(newsService, times(1)).findNewsById(validId);
    }

    @Test
    void getNewsById_shouldThrowException_whenNewsNotFound() {
        Long nonExistentId = 999L;
        when(newsService.findNewsById(nonExistentId))
                .thenThrow(new NoSuchElementException("News not found"));

        assertThrows(NoSuchElementException.class, () ->
            aggregatorController.getNewsById(nonExistentId));
        verify(newsService, times(1)).findNewsById(nonExistentId);
    }

    @Test
    void getNewsById_shouldThrowConstraintViolation_whenInvalidIdProvided() {
        Long invalidId = 0L;

        assertDoesNotThrow(() -> {
            aggregatorController.getNewsById(invalidId);
        });
    }

    @Test
    void getCategories_shouldReturnAllCategoriesList_whenServiceReturnsData() {
        List<String> expectedCategories = List.of("Technology", "Sports", "Politics");
        when(sourcesService.getCategories()).thenReturn(expectedCategories);

        List<String> result = aggregatorController.getCategories();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(expectedCategories, result);
        verify(sourcesService, times(1)).getCategories();
    }

    @Test
    void getCategories_shouldReturnEmptyList_whenNoAllCategoriesAvailable() {
        when(sourcesService.getCategories()).thenReturn(List.of());

        List<String> result = aggregatorController.getCategories();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(sourcesService, times(1)).getCategories();
    }

    @Test
    void getSources_shouldReturnSourcesList_whenServiceReturnsData() {
        List<String> expectedSources = List.of("BBC News", "CNN", "Reuters");
        when(sourcesService.getResources()).thenReturn(expectedSources);

        List<String> result = aggregatorController.getSources();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(expectedSources, result);
        verify(sourcesService, times(1)).getResources();
    }

    @Test
    void getSources_shouldReturnEmptyList_whenNoSourcesAvailable() {
        when(sourcesService.getResources()).thenReturn(List.of());

        List<String> result = aggregatorController.getSources();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(sourcesService, times(1)).getResources();
    }

    @Test
    void should_CallAppropriateService_ForEachEndpoint() {
        aggregatorController.getNews();
        aggregatorController.getCategories();
        aggregatorController.getSources();
        aggregatorController.getNewsById(1L);

        verify(newsService, times(1)).getNews();
        verify(sourcesService, times(1)).getCategories();
        verify(sourcesService, times(1)).getResources();
        verify(newsService, times(1)).findNewsById(1L);

        verifyNoMoreInteractions(newsService, sourcesService);
    }
}