package com.simpleproject.NewsAggregator.service;

import com.simpleproject.NewsAggregator.dto.NewsDto;
import com.simpleproject.NewsAggregator.entity.NewsEntity;
import com.simpleproject.NewsAggregator.repository.NewsRepository;
import com.simpleproject.NewsAggregator.repository.SourceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsParsingOrchestratorTest {

    @Mock
    private HttpFetcher fetcherService;

    @Mock
    private SourceRepository sourceRepository;

    @Mock
    private NewsRepository newsRepository;

    @InjectMocks
    private NewsParsingOrchestrator orchestrator;

    @Test
    void parseAllActiveSources_ShouldProcessNews_WhenSourcesAndNewsExist() {
        NewsDto newsDto = new NewsDto(1L, "Title", "link", "desc", "cat", "date", "media");
        NewsEntity newsEntity = NewsDto.toEntity(newsDto);

        when(sourceRepository.findAllActiveSources()).thenReturn(List.of());
        when(fetcherService.getNewsFromSources(anyList())).thenReturn(List.of(newsDto));
        when(newsRepository.findByLink("link")).thenReturn(null);

        orchestrator.parseAllActiveSources();

        verify(sourceRepository).findAllActiveSources();
        verify(fetcherService).getNewsFromSources(anyList());
        verify(newsRepository).findByLink("link");
        verify(newsRepository).save(newsEntity);
    }

    @Test
    void parseAllActiveSources_ShouldHandleException_WhenFetcherThrowsException() {
        when(sourceRepository.findAllActiveSources()).thenReturn(List.of());
        when(fetcherService.getNewsFromSources(anyList())).thenThrow(new RuntimeException("Error"));

        orchestrator.parseAllActiveSources();

        verify(sourceRepository).findAllActiveSources();
        verify(fetcherService).getNewsFromSources(anyList());
        verify(newsRepository, never()).findByLink(anyString());
        verify(newsRepository, never()).save(any());
    }

    @Test
    void parseAllActiveSources_ShouldNotSaveDuplicateNews_WhenNewsAlreadyExists() {
        NewsDto newsDto = new NewsDto(1L, "Title", "link", "desc", "cat", "date", "media");
        NewsEntity existingEntity = new NewsEntity();

        when(sourceRepository.findAllActiveSources()).thenReturn(List.of());
        when(fetcherService.getNewsFromSources(anyList())).thenReturn(List.of(newsDto));
        when(newsRepository.findByLink("link")).thenReturn(existingEntity);

        orchestrator.parseAllActiveSources();

        verify(sourceRepository).findAllActiveSources();
        verify(fetcherService).getNewsFromSources(anyList());
        verify(newsRepository).findByLink("link");
        verify(newsRepository, never()).save(any());
    }

    @Test
    void parseAllActiveSources_ShouldNotProcess_WhenNoActiveSources() {
        when(sourceRepository.findAllActiveSources()).thenReturn(List.of());

        orchestrator.parseAllActiveSources();

        verify(sourceRepository).findAllActiveSources();
        verify(fetcherService).getNewsFromSources(List.of());
        verify(newsRepository, never()).findByLink(anyString());
        verify(newsRepository, never()).save(any());
    }

    @Test
    void parseAllActiveSources_ShouldNotSave_WhenNoNewNews() {
        when(sourceRepository.findAllActiveSources()).thenReturn(List.of());
        when(fetcherService.getNewsFromSources(anyList())).thenReturn(List.of());

        orchestrator.parseAllActiveSources();

        verify(sourceRepository).findAllActiveSources();
        verify(fetcherService).getNewsFromSources(anyList());
        verify(newsRepository, never()).findByLink(anyString());
        verify(newsRepository, never()).save(any());
    }
}