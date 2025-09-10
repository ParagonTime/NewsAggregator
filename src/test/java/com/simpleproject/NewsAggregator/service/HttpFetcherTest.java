package com.simpleproject.NewsAggregator.service;

import com.simpleproject.NewsAggregator.client.RestTemplateClient;
import com.simpleproject.NewsAggregator.dto.NewsDto;
import com.simpleproject.NewsAggregator.dto.SourceDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HttpFetcherTest {

    @Mock
    private RestTemplateClient client;

    @Mock
    private NewsParser parser;

    @InjectMocks
    private HttpFetcher httpFetcher;

    // Тестовые данные
    private final SourceDto testSource = new SourceDto(
            1L,
            "Test Source",
            "https://test.com",
            "https://test.com/rss",
            true
    );

    private final NewsDto testNews1 = new NewsDto(
            1L,
            "Test News 1",
            "https://test.com/news1",
            "Description 1",
            "Technology",
            "2024-01-15T10:00:00Z",
            "https://test.com/image1.jpg"
    );

    private final NewsDto testNews2 = new NewsDto(
            2L,
            "Test News 2",
            "https://test.com/news2",
            "Description 2",
            "Sports",
            "2024-01-15T12:00:00Z",
            null
    );

    @Test
    void getNewsFromSource_ShouldReturnNewsList_WhenClientAndParserWorkCorrectly() {
        String xmlResponse = "<rss><channel><item>...</item></channel></rss>";
        List<NewsDto> expectedNews = List.of(testNews1, testNews2);

        when(client.fetchSource(testSource)).thenReturn(xmlResponse);
        when(parser.parseXML(xmlResponse)).thenReturn(expectedNews);

        List<NewsDto> result = httpFetcher.getNewsFromSource(testSource);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedNews, result);

        verify(client, times(1)).fetchSource(testSource);
        verify(parser, times(1)).parseXML(xmlResponse);
        verifyNoMoreInteractions(client, parser);
    }

    @Test
    void getNewsFromSource_ShouldReturnEmptyList_WhenClientThrowsException() {
        when(client.fetchSource(testSource))
                .thenThrow(new RuntimeException("Network error"));

        List<NewsDto> result = httpFetcher.getNewsFromSource(testSource);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(client, times(1)).fetchSource(testSource);
        verify(parser, never()).parseXML(anyString());
    }

    @Test
    void getNewsFromSource_ShouldReturnEmptyList_WhenClientReturnsNull() {
        when(client.fetchSource(testSource)).thenReturn(null);

        List<NewsDto> result = httpFetcher.getNewsFromSource(testSource);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(client, times(1)).fetchSource(testSource);
        verify(parser, never()).parseXML(anyString());
    }

    @Test
    void getNewsFromSource_ShouldReturnEmptyList_WhenParserThrowsException() {

        String xmlResponse = "<rss><channel><item>...</item></channel></rss>";

        when(client.fetchSource(testSource)).thenReturn(xmlResponse);
        when(parser.parseXML(xmlResponse))
                .thenThrow(new RuntimeException("Parse error"));

        List<NewsDto> result = httpFetcher.getNewsFromSource(testSource);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(client, times(1)).fetchSource(testSource);
        verify(parser, times(1)).parseXML(xmlResponse);
    }

    @Test
    void getNewsFromSource_ShouldReturnEmptyList_WhenParserReturnsNull() {
        String xmlResponse = "<rss><channel><item>...</item></channel></rss>";

        when(client.fetchSource(testSource)).thenReturn(xmlResponse);
        when(parser.parseXML(xmlResponse)).thenReturn(null);

        List<NewsDto> result = httpFetcher.getNewsFromSource(testSource);

        assertNull(result);

        verify(client, times(1)).fetchSource(testSource);
        verify(parser, times(1)).parseXML(xmlResponse);
    }

    @Test
    void getNewsFromSources_ShouldReturnCombinedNews_FromMultipleSources() {
        SourceDto source1 = new SourceDto(1L, "Source 1", "url1", "rss1", true);
        SourceDto source2 = new SourceDto(2L, "Source 2", "url2", "rss2", true);

        List<SourceDto> sources = List.of(source1, source2);

        String xmlResponse1 = "<rss>Source1</rss>";
        String xmlResponse2 = "<rss>Source2</rss>";

        List<NewsDto> newsFromSource1 = List.of(testNews1);
        List<NewsDto> newsFromSource2 = List.of(testNews2);

        when(client.fetchSource(source1)).thenReturn(xmlResponse1);
        when(client.fetchSource(source2)).thenReturn(xmlResponse2);

        when(parser.parseXML(xmlResponse1)).thenReturn(newsFromSource1);
        when(parser.parseXML(xmlResponse2)).thenReturn(newsFromSource2);

        List<NewsDto> result = httpFetcher.getNewsFromSources(sources);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(testNews1));
        assertTrue(result.contains(testNews2));

        verify(client, times(1)).fetchSource(source1);
        verify(client, times(1)).fetchSource(source2);
        verify(parser, times(1)).parseXML(xmlResponse1);
        verify(parser, times(1)).parseXML(xmlResponse2);
    }

    @Test
    void getNewsFromSources_ShouldReturnEmptyList_WhenSourcesListIsEmpty() {

        List<SourceDto> emptySources = List.of();

        List<NewsDto> result = httpFetcher.getNewsFromSources(emptySources);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(client, never()).fetchSource(any());
        verify(parser, never()).parseXML(anyString());
    }

    @Test
    void getNewsFromSources_ShouldReturnNewsFromWorkingSource_WhenOneSourceFails() {
        SourceDto workingSource = new SourceDto(1L, "Working", "url1", "rss1", true);
        SourceDto failingSource = new SourceDto(2L, "Failing", "url2", "rss2", true);

        List<SourceDto> sources = List.of(failingSource, workingSource);

        String xmlResponse = "<rss>Working</rss>";
        List<NewsDto> newsFromWorkingSource = List.of(testNews1);

        when(client.fetchSource(failingSource))
                .thenThrow(new RuntimeException("Source unavailable"));

        when(client.fetchSource(workingSource)).thenReturn(xmlResponse);
        when(parser.parseXML(xmlResponse)).thenReturn(newsFromWorkingSource);

        List<NewsDto> result = httpFetcher.getNewsFromSources(sources);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testNews1, result.getFirst());

        verify(client, times(1)).fetchSource(failingSource);
        verify(client, times(1)).fetchSource(workingSource);
        verify(parser, times(1)).parseXML(xmlResponse);
    }

    @Test
    void getNewsFromSources_ShouldProcessAllSources_EvenWithErrors() {
        SourceDto source1 = new SourceDto(1L, "Source1", "url1", "rss1", true);
        SourceDto source2 = new SourceDto(2L, "Source2", "url2", "rss2", true);
        SourceDto source3 = new SourceDto(3L, "Source3", "url3", "rss3", true);

        List<SourceDto> sources = List.of(source1, source2, source3);

        when(client.fetchSource(source1))
                .thenThrow(new RuntimeException("Error"));

        String xmlResponse2 = "<rss>Source2</rss>";
        when(client.fetchSource(source2)).thenReturn(xmlResponse2);
        when(parser.parseXML(xmlResponse2))
                .thenThrow(new RuntimeException("Parse error"));

        String xmlResponse3 = "<rss>Source3</rss>";
        List<NewsDto> newsFromSource3 = List.of(testNews1);
        when(client.fetchSource(source3)).thenReturn(xmlResponse3);
        when(parser.parseXML(xmlResponse3)).thenReturn(newsFromSource3);

        List<NewsDto> result = httpFetcher.getNewsFromSources(sources);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testNews1, result.getFirst());

        verify(client, times(1)).fetchSource(source1);
        verify(client, times(1)).fetchSource(source2);
        verify(client, times(1)).fetchSource(source3);
        verify(parser, times(1)).parseXML(xmlResponse2);
        verify(parser, times(1)).parseXML(xmlResponse3);
    }
}