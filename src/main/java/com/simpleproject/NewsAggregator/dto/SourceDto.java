package com.simpleproject.NewsAggregator.dto;

import com.simpleproject.NewsAggregator.entity.SourceEntity;

public record SourceDto(
        Long id,
        String name,
        String urlMain,
        String urlToFetch,
        boolean isActive
) {
    public SourceDto(SourceEntity source) {
        this(source.getId(),
                source.getName(),
                source.getUrlMain(),
                source.getUrlToFetch(),
                source.isActive());
    }

    public static SourceDto toDto(SourceEntity entity) {
        return new SourceDto(entity);
    }
}
