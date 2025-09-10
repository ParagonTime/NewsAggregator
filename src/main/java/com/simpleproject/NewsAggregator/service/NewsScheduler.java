package com.simpleproject.NewsAggregator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsScheduler {

    private final NewsParsingOrchestrator orchestrator;

    @Scheduled(fixedRate = 15 * 60 * 1000)
    public void scheduledParsing() { // spring call this fu
        try {
            orchestrator.parseAllActiveSources();
        } catch (Exception e) {
            log.error("Ошибка в Scheduler: {}", e.getMessage());
        }
    }
}
