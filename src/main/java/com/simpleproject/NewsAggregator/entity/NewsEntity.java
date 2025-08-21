package com.simpleproject.NewsAggregator.entity;

import com.simpleproject.NewsAggregator.dto.NewsDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "news")
@Setter@Getter
@NoArgsConstructor
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String link;
    private String description;
    private String category;
    private String pubDate;
    private String media;

    public NewsEntity(Long id, String title,
                      String link, String description,
                      String category, String pubDate,
                      String media) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.description = description;
        this.category = category;
        this.pubDate = pubDate;
        this.media = media;
    }
}
