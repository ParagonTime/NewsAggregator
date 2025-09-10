package com.simpleproject.NewsAggregator.dto;

import com.simpleproject.NewsAggregator.entity.SourceEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO источник новостей")
public record SourceDto(
        @Schema(description = "ID источника", example = "1")
        @NotNull
        Long id,
        @Schema(description = "Название источника", example = "The Guardian")
        @NotNull
        String name,
        @Schema(description = "main URL источника", example = "https://www.theguardian.com/")
        String urlMain,
        @Schema(description = "URL для сбора новостей", example = "https://www.theguardian.com/world/rss")
        @NotNull
        String urlToFetch,
        @Schema(description = "Статус сбора новостей с источника", example = "true")
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
