package com.simpleproject.NewsAggregator.repository;

import com.simpleproject.NewsAggregator.entity.SourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceRepository extends JpaRepository<SourceEntity, Long> {
}
