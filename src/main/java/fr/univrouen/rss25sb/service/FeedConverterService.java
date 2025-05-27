package fr.univrouen.rss25sb.service;

import fr.univrouen.rss25sb.model.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Service for converting external RSS feeds to RSS25 format
 */
@Service
public class FeedConverterService {

    private final RestTemplate restTemplate;

    public FeedConverterService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Convert a feed from a source to RSS25 format
     * @param source the source to convert from
     * @return the converted feed in RSS25 format
     */
    public String convertFeed(String source) {
        try {
            // Determine the feed URL based on the source
            String feedUrl = getFeedUrl(source);

            // Fetch the feed
            String feedXml = fetchFeed(feedUrl);

            // Convert the feed to RSS25 format
            Feed feed = convertToRss25(feedXml, source);

            // Marshal the feed to XML
            return marshalToXml(feed);
        } catch (Exception e) {
            throw new RuntimeException("Error converting feed: " + e.getMessage(), e);
        }
    }

    /**
     * Get the feed URL for a source
     * @param source the source name
     * @return the feed URL
     */
    private String getFeedUrl(String source) {
        switch (source) {
            case "Le Monde - International":
                return "https://www.lemonde.fr/international/rss_full.xml";
            case "Fonction Publique - Actualités":
                return "https://www.fonction-publique.gouv.fr/flux-rss-actualites";
            default:
                throw new IllegalArgumentException("Unsupported source: " + source);
        }
    }

    /**
     * Fetch a feed from a URL
     * @param url the URL to fetch from
     * @return the feed XML
     */
    private String fetchFeed(String url) {
        return restTemplate.getForObject(url, String.class);
    }

    /**
     * Convert a feed to RSS25 format
     * @param feedXml the feed XML
     * @param sourceName the source name
     * @return the converted feed
     */
    private Feed convertToRss25(String feedXml, String sourceName) throws Exception {
        // Parse the XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(feedXml)));

        // Create a new feed
        Feed feed = new Feed();
        feed.setVersion("25");
        feed.setLang("fr");

        // Set feed title
        String title = getElementText(doc, "channel/title");
        String defaultTitle = "Flux converti depuis " + sourceName;
        // Truncate title if it exceeds 128 characters (string128Type limit in XSD)
        String truncatedTitle = truncateString(title != null ? title : defaultTitle, 128);
        feed.setTitle(truncatedTitle);

        // Set feed publication date
        String pubDate = getElementText(doc, "channel/pubDate");
        feed.setPubDate(pubDate != null ? formatDate(pubDate) : getCurrentDate());

        // Set feed copyright
        String copyright = getElementText(doc, "channel/copyright");
        String defaultCopyright = "© " + LocalDateTime.now().getYear() + " " + sourceName;
        // Truncate copyright if it exceeds 128 characters (string128Type limit in XSD)
        String truncatedCopyright = truncateString(copyright != null ? copyright : defaultCopyright, 128);
        feed.setCopyright(truncatedCopyright);

        // Add feed links
        Link selfLink = new Link("self", "application/xml", getFeedUrl(sourceName));
        feed.addLink(selfLink);

        String link = getElementText(doc, "channel/link");
        if (link != null) {
            Link alternateLink = new Link("alternate", "text/html", link);
            feed.addLink(alternateLink);
        }

        // Convert items
        NodeList itemNodes = doc.getElementsByTagName("item");
        for (int i = 0; i < Math.min(itemNodes.getLength(), 10); i++) {
            Element itemElement = (Element) itemNodes.item(i);
            Item item = convertItem(itemElement, sourceName);
            feed.addItem(item);
        }

        return feed;
    }

    /**
     * Convert an item to RSS25 format
     * @param itemElement the item element
     * @param sourceName the source name
     * @return the converted item
     */
    private Item convertItem(Element itemElement, String sourceName) {
        // Create a new item
        String guid = getElementText(itemElement, "guid");
        if (guid == null || !guid.matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}")) {
            guid = UUID.randomUUID().toString();
        }

        String title = getElementText(itemElement, "title");
        if (title == null) {
            title = "Article sans titre";
        }
        // Truncate title if it exceeds 128 characters (string128Type limit in XSD)
        title = truncateString(title, 128);

        String pubDate = getElementText(itemElement, "pubDate");
        String formattedDate = pubDate != null ? formatDate(pubDate) : getCurrentDate();

        Item item = new Item(guid, title, formattedDate);

        // Add category
        Category category = new Category(sourceName);
        item.addCategory(category);

        // Add content
        String description = getElementText(itemElement, "description");
        if (description == null) {
            description = "Pas de contenu disponible";
        }

        Content content = new Content("html", null, description);
        item.setContent(content);

        // Add image if available
        String enclosureUrl = getAttributeValue(itemElement, "enclosure", "url");
        if (enclosureUrl != null) {
            String enclosureType = getAttributeValue(itemElement, "enclosure", "type");
            String imageType = "PNG";
            if (enclosureType != null) {
                if (enclosureType.contains("jpeg") || enclosureType.contains("jpg")) {
                    imageType = "JPEG";
                } else if (enclosureType.contains("gif")) {
                    imageType = "GIF";
                } else if (enclosureType.contains("bmp")) {
                    imageType = "BMP";
                }
            }

            Image image = new Image(imageType, enclosureUrl, "Image de l'article", 0);
            item.setImage(image);
        }

        // Add author
        // Truncate author name if it exceeds 64 characters (string64Type limit in XSD)
        String authorName = truncateString("Auteur de " + sourceName, 64);
        Author author = new Author(authorName, null, null, false);
        item.addAuthor(author);

        return item;
    }

    /**
     * Get the text content of an element
     * @param parent the parent element
     * @param tagName the tag name
     * @return the text content or null if not found
     */
    private String getElementText(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes.getLength() > 0) {
            return nodes.item(0).getTextContent();
        }
        return null;
    }

    /**
     * Get the text content of an element
     * @param doc the document
     * @param path the path to the element
     * @return the text content or null if not found
     */
    private String getElementText(Document doc, String path) {
        String[] parts = path.split("/");
        Element current = doc.getDocumentElement();

        for (String part : parts) {
            NodeList nodes = current.getElementsByTagName(part);
            if (nodes.getLength() == 0) {
                return null;
            }
            current = (Element) nodes.item(0);
        }

        return current.getTextContent();
    }

    /**
     * Get the value of an attribute
     * @param parent the parent element
     * @param tagName the tag name
     * @param attributeName the attribute name
     * @return the attribute value or null if not found
     */
    private String getAttributeValue(Element parent, String tagName, String attributeName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes.getLength() > 0) {
            Element element = (Element) nodes.item(0);
            return element.getAttribute(attributeName);
        }
        return null;
    }

    /**
     * Format a date to ISO 8601 format without milliseconds
     * @param dateStr the date string
     * @return the formatted date
     */
    private String formatDate(String dateStr) {
        try {
            // Parse the date
            DateTimeFormatter inputFormatter = DateTimeFormatter.RFC_1123_DATE_TIME;
            ZonedDateTime dateTime = ZonedDateTime.parse(dateStr, inputFormatter);

            // Format the date with a custom pattern that matches the XSD schema
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[XXX]");
            return dateTime.format(outputFormatter);
        } catch (Exception e) {
            // If parsing fails, return current date
            return getCurrentDate();
        }
    }

    /**
     * Get the current date in ISO 8601 format without milliseconds
     * @return the current date
     */
    private String getCurrentDate() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Europe/Paris"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[XXX]");
        return now.format(formatter);
    }

    /**
     * Truncate a string to a maximum length
     * @param str the string to truncate
     * @param maxLength the maximum length
     * @return the truncated string
     */
    private String truncateString(String str, int maxLength) {
        if (str == null) {
            return null;
        }
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength);
    }

    /**
     * Marshal a feed to XML
     * @param feed the feed
     * @return the XML
     */
    private String marshalToXml(Feed feed) throws Exception {
        // Use JAXB to marshal the feed to XML
        jakarta.xml.bind.JAXBContext context = jakarta.xml.bind.JAXBContext.newInstance(Feed.class);
        jakarta.xml.bind.Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(jakarta.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);

        java.io.StringWriter writer = new java.io.StringWriter();
        marshaller.marshal(feed, writer);

        return writer.toString();
    }
}
