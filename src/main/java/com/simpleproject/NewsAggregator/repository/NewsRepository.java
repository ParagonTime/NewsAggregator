package com.simpleproject.NewsAggregator.repository;

import com.simpleproject.NewsAggregator.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Long> {

    @Query(value = "select * from news where link = :link", nativeQuery = true)
    NewsEntity findByLink(String link);
}
