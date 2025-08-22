package com.simpleproject.NewsAggregator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "source")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SourceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String urlMain;
    private String urlToFetch;
    private boolean isActive;
    private String category;
    private String language;
}
