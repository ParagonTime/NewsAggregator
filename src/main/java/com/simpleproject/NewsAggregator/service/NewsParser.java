package com.simpleproject.NewsAggregator.service;

import com.simpleproject.NewsAggregator.dto.NewsDto;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class NewsParser {

    public List<NewsDto> parseXML(String xmlContent) {
        Random random = new Random();
        List<NewsDto> newsList = new ArrayList<>();

        try {
            // Пытаемся распарсить с автоматическим определением кодировки
            return parseWithEncodingDetection(xmlContent, random);
        } catch (Exception e) {
            log.error("Основной парсинг не удался, пробуем fallback: {}", e.getMessage());
            // Если не получилось, пробуем альтернативные кодировки
            return parseWithFallbackEncodings(xmlContent, random);
        }
    }

    private List<NewsDto> parseWithEncodingDetection(String xmlContent, Random random) throws Exception {
        String cleanedXml = xmlContent.replace("\uFEFF", ""); // удаляем BOM
        String encoding = detectEncoding(xmlContent);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        InputStream inputStream = new ByteArrayInputStream(cleanedXml.getBytes(encoding));
        Document document = builder.parse(inputStream);
        document.getDocumentElement().normalize();

        return parseDocument(document, random);
    }

    private List<NewsDto> parseWithFallbackEncodings(String xmlContent, Random random) {
        String[] encodings = {"UTF-8", "ISO-8859-1", "Windows-1251", "Windows-1252", "CP1251"};

        for (String encoding : encodings) {
            try {
                String cleanedXml = xmlContent.replace("\uFEFF", "");
                InputStream inputStream = new ByteArrayInputStream(cleanedXml.getBytes(encoding));

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(inputStream);
                document.getDocumentElement().normalize();

                return parseDocument(document, random);
            } catch (Exception e) {
                log.error("Не удалось с кодировкой {}", (encoding + ": " + e.getMessage()));
            }
        }
        throw new RuntimeException("Не удалось распарсить XML ни с одной кодировкой");
    }

    private String detectEncoding(String xmlContent) {
        // Пытаемся найти encoding в XML декларации
        Pattern pattern = Pattern.compile("encoding=[\"']([^\"']+)[\"']");
        Matcher matcher = pattern.matcher(xmlContent.substring(0, Math.min(100, xmlContent.length())));
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "UTF-8"; // по умолчанию
    }

    private List<NewsDto> parseDocument(Document document, Random random) {
        List<NewsDto> newsList = new ArrayList<>();
        NodeList items = document.getElementsByTagName("item");

        for (int i = 0; i < items.getLength(); i++) {
            Element item = (Element) items.item(i);

            String title = truncateText(getElementText(item, "title"));
            String link = getElementText(item, "link");
            String description = truncateText(getElementText(item, "description"));
            String category = getCategory(item);
            String pubDate = getElementText(item, "pubDate");
            String media = truncateText(getMediaUrl(item));

            NewsDto newsDto = new NewsDto(
                    Math.abs(random.nextLong()), title, link, description, category, pubDate, media
            );
            newsList.add(newsDto);
        }
        return newsList;
    }

    private String truncateText(String text) {
        int MAX_LENGTH = 500;
        if (text == null || text.length() <= MAX_LENGTH) {
            return text;
        }
        return text.substring(0, MAX_LENGTH - 3) + "...";
    }

    private String getElementText(Element element, String tagName) {
        NodeList nodes = element.getElementsByTagName(tagName);
        if (nodes.getLength() > 0) {
            String text = nodes.item(0).getTextContent();
            if ("description".equals(tagName)) {
                text = cleanDescription(text);
            }
            return cleanText(text);
        }
        return "";
    }

    private String cleanDescription(String description) {
        // Удаляем все HTML теги
        String cleaned = description.replaceAll("<[^>]*>", "");
        // Удаляем ссылки (http, https, www)
        cleaned = cleaned.replaceAll("https?://\\S+\\s?", "");
        cleaned = cleaned.replaceAll("www\\.\\S+\\s?", "");
        // Удаляем лишние пробелы
        cleaned = cleaned.replaceAll("\\s+", " ").trim();
        return cleaned;
    }

    private String cleanText(String text) {
        return text.replace("â\u0080\u0099", "'")
                .replace("â\u0080\u0098", "'")
                .replace("â€™", "'")
                .replace("â€“", "-")
                .replace("â€”", "—")
                .replace("â€œ", "\"")
                .replace("â€", "\"")
                .replace("Ã©", "é")
                .replace("Ã¶", "ö")
                .replace("Ã¼", "ü")
                .replace("Ã¤", "ä")
                .replace("Ã¶", "ö")
                .replace("ÃŸ", "ß")
                .replace("Ã†", "Æ")
                .replace("Ã°", "ð")
                .trim();
    }

    private String getCategory(Element item) {
        NodeList categories = item.getElementsByTagName("category");
        if (categories.getLength() > 0) {
            String category = categories.item(0).getTextContent();
            return cleanText(category);
        }
        return "";
    }

    private String getMediaUrl(Element item) {
        NodeList mediaContents = item.getElementsByTagName("media:content");
        if (mediaContents.getLength() > 0) {
            Element media = (Element) mediaContents.item(0);
            return media.getAttribute("url");
        }

        NodeList enclosures = item.getElementsByTagName("enclosure");
        if (enclosures.getLength() > 0) {
            Element enclosure = (Element) enclosures.item(0);
            return enclosure.getAttribute("url");
        }

        return "";
    }
}