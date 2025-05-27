package fr.univrouen.rss25sb.service;

import org.springframework.stereotype.Service;

/**
 * Service for converting external RSS feeds to RSS25 format
 */
@Service
public class FeedConverterService {

    /**
     * Convert a feed from a source to RSS25 format
     * @param source the source to convert from
     * @return the converted feed in RSS25 format
     */
    public String convertFeed(String source) {
        // This is a stub implementation for testing
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><feed xmlns=\"http://univ.fr/rss25\" lang=\"fr\" version=\"25\"><title>Test Feed</title><pubDate>2023-01-01T00:00:00Z</pubDate><copyright>© 2023 Test</copyright><link rel=\"self\" type=\"application/xml\" href=\"http://example.com\"/><item><guid>12345678-1234-1234-1234-123456789012</guid><title>Test Item</title><category term=\"Test\"/><published>2023-01-01T00:00:00Z</published><content type=\"html\">Test content</content><author><name>Test Author</name></author></item></feed>";
    }
}