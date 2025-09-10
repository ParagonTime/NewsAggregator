package com.simpleproject.NewsAggregator.dto;

import com.simpleproject.NewsAggregator.entity.NewsEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO новость")
public record NewsDto(
        @Schema(description = "ID новости", example = "1")
        @NotNull
        Long id,
        @Schema(description = "Заголовок новости", example = "Новость номер 1")
        @NotNull
        String title,
        @Schema(description = "URL ссылка на новость", example = "https://example.com/news/1")
        @NotNull
        String link,
        @Schema(description = "Описание новости", example = "Краткое описание новости")
        String description,
        @Schema(description = "Категория новости", example = "world")
        String category,
        @Schema(description = "Дата публикации", example = "2024-01-15T10:30:00Z")
        @NotNull(message = "отсутствует дата публикации")
        String pubDate,
        @Schema(description = "Медиа при наличии", example = "https://example.com/image.jpg")
        String media
) {

    public static NewsEntity toEntity(NewsDto newsDto) {
        NewsEntity entity = new NewsEntity();
        entity.setTitle(newsDto.title);
        entity.setLink(newsDto.link);
        entity.setDescription(newsDto.description);
        entity.setCategory(newsDto.category);
        entity.setPubDate(newsDto.pubDate);
        entity.setMedia(newsDto.media);
        return entity;
    }

    @Override
    public String toString() {
        return "NewsDto{" +
                "id=" + id +
                ", title=" + title + '\n' +
                ", link=" + link + '\n' +
                ", description=" + description + '\n' +
                ", category=" + category + '\n' +
                ", pubDate=" + pubDate + '\n' +
                ", media=" + media + '\n' +
                '}';
    }
}
