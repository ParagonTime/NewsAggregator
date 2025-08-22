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
