package com.simpleproject.NewsAggregator.repository;

import com.simpleproject.NewsAggregator.entity.SourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SourceRepository extends JpaRepository<SourceEntity, Long> {
    @Query(value = "select * from source where is_active = true", nativeQuery = true)
    List<SourceEntity> findAllActiveSources();

    @Query(value = "select name from source where is_active = true", nativeQuery = true)
    List<String> getAllSources();

    @Query(value = "select distinct category from source", nativeQuery = true)
    List<String> getCategories();
}
