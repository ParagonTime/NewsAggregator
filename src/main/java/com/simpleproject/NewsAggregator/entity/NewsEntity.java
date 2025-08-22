package com.simpleproject.NewsAggregator.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Entity
@Table(name = "news")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String title;
    
    private String link;
    
    @Column(length = 500)
    private String description;
    
    private String category;
    private String pubDate;
    
    @Column(length = 500)
    private String media;
}
