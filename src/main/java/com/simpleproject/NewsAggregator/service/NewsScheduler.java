package com.simpleproject.NewsAggregator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsScheduler {

    private final NewsParsingOrchestrator orchestrator;

    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void scheduledParsing() { // spring call this fu
        orchestrator.parseAllActiveSources();
    }
}
