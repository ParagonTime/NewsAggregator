package com.simpleproject.NewsAggregator.dto;

import com.simpleproject.NewsAggregator.entity.NewsEntity;

public record NewsDto(
        Long id,
        String title,
        String link,
        String description,
        String category,
        String pubDate,
        String media
) {

    public static NewsEntity toEntity(NewsDto newsDto) {
        return new NewsEntity(
                newsDto.id,
                newsDto.title,
                newsDto.link,
                newsDto.description,
                newsDto.category,
                newsDto.pubDate,
                newsDto.media
        );
    }

    @Override
    public String toString() {
        return "NewsDto{" +
                "id=" + id +
                ", title=" + title + '\n' +
                ", link=" + link + '\n' +
                //", description=" + description + '\n' +
                ", category=" + category + '\n' +
                ", pubDate=" + pubDate + '\n' +
                ", media=" + media + '\n' +
                '}';
    }
}
