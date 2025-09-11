package com.simpleproject.NewsAggregator.service;

import com.simpleproject.NewsAggregator.repository.SourceRepository;
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
class SourcesServiceTest {

    @Mock
    private SourceRepository sourceRepository;

    @InjectMocks
    private SourcesService sourcesService;

    @Test
    void getResources_ShouldReturnSourcesList_WhenRepositoryHasData() {
        List<String> expectedSources = List.of("BBC", "CNN", "Reuters");
        when(sourceRepository.getAllSources()).thenReturn(expectedSources);

        List<String> result = sourcesService.getResources();

        assertNotNull(result);
        assertEquals(expectedSources, result);
        verify(sourceRepository, times(1)).getAllSources();
    }

    @Test
    void getResources_ShouldReturnNull_WhenRepositoryReturnsEmptyList() {
        when(sourceRepository.getAllSources()).thenReturn(List.of());
        assertThrows(NoSuchElementException.class,
                () -> sourcesService.getResources());

        verify(sourceRepository, times(1)).getAllSources();
    }

    @Test
    void getResources_ShouldReturnNull_WhenRepositoryReturnsNull() {
        when(sourceRepository.getAllSources()).thenReturn(null);

        assertThrows(NoSuchElementException.class, () -> sourcesService.getResources());

        verify(sourceRepository, times(1)).getAllSources();
    }

    @Test
    void getCategories_ShouldReturnCategoriesList_WhenRepositoryHasData() {
        List<String> expectedCategories = List.of("Technology", "Sports", "Politics");
        when(sourceRepository.getCategories()).thenReturn(expectedCategories);

        List<String> result = sourcesService.getCategories();

        assertNotNull(result);
        assertEquals(expectedCategories, result);
        verify(sourceRepository, times(1)).getCategories();
    }

    @Test
    void getCategories_ShouldReturnNull_WhenRepositoryReturnsEmptyList() {
        when(sourceRepository.getCategories()).thenReturn(List.of());

        assertThrows(NoSuchElementException.class, () -> sourcesService.getCategories());

        verify(sourceRepository, times(1)).getCategories();
    }

    @Test
    void getCategories_ShouldReturnNull_WhenRepositoryReturnsNull() {
        when(sourceRepository.getCategories()).thenReturn(null);

        assertThrows(NoSuchElementException.class, () -> sourcesService.getCategories());

        verify(sourceRepository, times(1)).getCategories();
    }
}