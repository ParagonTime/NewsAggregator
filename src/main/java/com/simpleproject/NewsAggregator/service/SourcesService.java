package com.simpleproject.NewsAggregator.service;

import com.simpleproject.NewsAggregator.repository.SourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SourcesService {

    private final SourceRepository sourceRepository;

    @Cacheable("sourses")
    public List<String> getResources() {
        List<String> sourcesList = sourceRepository.getAllSources();
        if (sourcesList == null || sourcesList.isEmpty()) {
            throw new NoSuchElementException("No sources available");
        }
        return sourcesList;
    }

    @Cacheable("categories")
    public List<String> getCategories() {
        List<String> categoriesList = sourceRepository.getCategories();
        if (categoriesList == null || categoriesList.isEmpty()) {
            throw new NoSuchElementException("No categories available");
        }
        return categoriesList;
    }
}
