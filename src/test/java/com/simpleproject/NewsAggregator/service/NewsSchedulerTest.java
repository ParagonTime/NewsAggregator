package com.simpleproject.NewsAggregator.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsSchedulerTest {

    @Mock
    private NewsParsingOrchestrator orchestrator;

    @InjectMocks
    private NewsScheduler newsScheduler;

    @Test
    void scheduledParsing_ShouldCallOrchestrator() {
        newsScheduler.scheduledParsing();
        verify(orchestrator, times(1)).parseAllActiveSources();
    }

    @Test
    void scheduledParsing_ShouldHandleException_WhenOrchestratorFails() {
        doThrow(new RuntimeException("Error")).when(orchestrator).parseAllActiveSources();
        newsScheduler.scheduledParsing();
        verify(orchestrator, times(1)).parseAllActiveSources();
    }
}