package com.simpleproject.NewsAggregator.service;

import com.simpleproject.NewsAggregator.repository.SourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SourcesService {

    private final SourceRepository sourceRepository;

    public List<String> getResources() {
        List<String> sourcesList = sourceRepository.getAllSources();
        if (sourcesList != null && !sourcesList.isEmpty()) {
            return sourcesList;
        }
        return null;
    }

    public List<String> getCategories() {
        List<String> categoriesList = sourceRepository.getCategories();
        if (categoriesList != null && !categoriesList.isEmpty()) {
            return categoriesList;
        }
        return null;
    }
}
