package com.simpleproject.NewsAggregator.dto;

import com.simpleproject.NewsAggregator.entity.SourceEntity;
import jakarta.validation.constraints.NotNull;


public record SourceDto(
        @NotNull
        Long id,
        @NotNull
        String name,
        String urlMain,
        @NotNull
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
