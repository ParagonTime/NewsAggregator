package com.simpleproject.NewsAggregator.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "source")
@Setter@Getter
@NoArgsConstructor
public class SourceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String urlMain;
    private String urlToFetch;
    private boolean isActive;
    private String category;
    private String language;
}
