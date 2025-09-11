package com.simpleproject.NewsAggregator.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class RateLimitConfig implements WebMvcConfigurer {

    private final static int LIMIT = 100;
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public Bucket resolveBucket(String ip) {
        return buckets.computeIfAbsent(ip, key -> {
            Refill refill = Refill.intervally(LIMIT, Duration.ofMinutes(1));
            Bandwidth limit = Bandwidth.classic(LIMIT, refill);
            return Bucket.builder().addLimit(limit).build();
        });
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RateLimitInterceptor(this));
    }
}
