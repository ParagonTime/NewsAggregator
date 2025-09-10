package com.simpleproject.NewsAggregator.client;

import com.simpleproject.NewsAggregator.dto.SourceDto;
import okhttp3.mockwebserver.MockResponse;
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
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        restTemplateClient = new RestTemplateClient();

        testSource = new SourceDto(1L, "Test name", "test.com",
                mockWebServer.url("/rss").toString(), true);
    }

    @AfterEach
    void tesrDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void test1() {
        String expectedContent = "<rss><channel><title>Test News</title></channel></rss>";
        mockWebServer.enqueue(new MockResponse()
                .setBody(expectedContent)
                .addHeader("Content-Type", "application/xml"));

        String result = restTemplateClient.fetchSource(testSource);

        assertNotNull(result);
        assertEquals(expectedContent, result);

        assertEquals(1, mockWebServer.getRequestCount());
    }

    @Test
    void test2() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500)
                .setBody("Internal Server Error"));

        assertThrows(RuntimeException.class, () -> {
            restTemplateClient.fetchSource(testSource);
        });

        assertEquals(1, mockWebServer.getRequestCount());
    }

    @Test
    void test3() {
        mockWebServer.enqueue(new MockResponse()
                .setBody("")
                .addHeader("Content-Type", "text/plain"));

        assertThrows(RuntimeException.class, () -> {
            String result = restTemplateClient.fetchSource(testSource);
            assertTrue(result == null || result.isEmpty());
        });




        assertEquals(1, mockWebServer.getRequestCount());
    }

    @Test
    void test4() {
        mockWebServer.enqueue(new MockResponse()
                .setBody("Delayed response")
                .setBodyDelay(2, java.util.concurrent.TimeUnit.SECONDS));

        assertDoesNotThrow(() -> {
            String result = restTemplateClient.fetchSource(testSource);
            assertTrue(result == null || result.contains("Delayed response"));
        });

        assertEquals(1, mockWebServer.getRequestCount());
    }
}