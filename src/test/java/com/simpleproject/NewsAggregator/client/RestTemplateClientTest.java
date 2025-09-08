package com.simpleproject.NewsAggregator.client;

import com.simpleproject.NewsAggregator.dto.SourceDto;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RestTemplateClientTest {

    private MockWebServer mockWebServer;
    private RestTemplateClient restTemplateClient;
    private SourceDto testSource;

    @BeforeEach
    void setUp() throws IOException {

    }

    @AfterEach
    void tesrDown() throws IOException {

    }

    @Test
    void test1() {

    }
}