package fr.univrouen.rss25sb.service;

import fr.univrouen.rss25sb.service.FeedConverterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FeedConverterServiceTest {

    @Autowired
    private FeedConverterService feedConverterService;

    @Test
    public void testLeMondeFeedConversion() {
        try {
            // Test conversion of Le Monde feed
            String result = feedConverterService.convertFeed("Le Monde - International");

            // Verify that the result is not null
            assertNotNull(result);

            // Verify that the result is valid XML
            assertTrue(result.startsWith("<?xml"));

            // Parse the XML to check specific fields
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(result)));

            // Check the copyright field
            NodeList copyrightNodes = doc.getElementsByTagNameNS("http://univ.fr/rss25", "copyright");
            if (copyrightNodes.getLength() > 0) {
                String copyright = copyrightNodes.item(0).getTextContent();
                System.out.println("[DEBUG_LOG] Copyright: " + copyright);
                System.out.println("[DEBUG_LOG] Copyright length: " + copyright.length());

                // Verify that the copyright field is not longer than 128 characters
                assertTrue(copyright.length() <= 128, "Copyright field exceeds 128 characters: " + copyright.length());
            }

            // Check the GUID field
            NodeList itemNodes = doc.getElementsByTagNameNS("http://univ.fr/rss25", "item");
            for (int i = 0; i < itemNodes.getLength(); i++) {
                NodeList guidNodes = ((org.w3c.dom.Element) itemNodes.item(i)).getElementsByTagNameNS("http://univ.fr/rss25", "guid");
                if (guidNodes.getLength() > 0) {
                    String guid = guidNodes.item(0).getTextContent();
                    System.out.println("[DEBUG_LOG] GUID: " + guid);

                    // Verify that the GUID matches the UUID pattern
                    assertTrue(guid.matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"), 
                               "GUID does not match UUID pattern: " + guid);
                }
            }

            // Check the author name field
            for (int i = 0; i < itemNodes.getLength(); i++) {
                NodeList authorNodes = ((org.w3c.dom.Element) itemNodes.item(i)).getElementsByTagNameNS("http://univ.fr/rss25", "author");
                for (int j = 0; j < authorNodes.getLength(); j++) {
                    NodeList nameNodes = ((org.w3c.dom.Element) authorNodes.item(j)).getElementsByTagNameNS("http://univ.fr/rss25", "name");
                    if (nameNodes.getLength() > 0) {
                        String name = nameNodes.item(0).getTextContent();
                        System.out.println("[DEBUG_LOG] Author name: " + name);
                        System.out.println("[DEBUG_LOG] Author name length: " + name.length());

                        // Verify that the author name field is not longer than 64 characters
                        assertTrue(name.length() <= 64, "Author name field exceeds 64 characters: " + name.length());
                    }
                }
            }

            // Print debug information
            System.out.println("[DEBUG_LOG] Converted XML: " + result.substring(0, Math.min(500, result.length())) + "...");

            // Success if no exception is thrown
            assertTrue(true);
        } catch (Exception e) {
            // If an exception is thrown, the test fails
            System.out.println("[DEBUG_LOG] Exception: " + e.getMessage());
            fail("Exception thrown during feed conversion: " + e.getMessage());
        }
    }

    @Test
    public void testFonctionPubliqueFeedConversion() {
        try {
            // Test conversion of Fonction Publique feed
            String result = feedConverterService.convertFeed("Fonction Publique - Actualités");

            // Verify that the result is not null
            assertNotNull(result);

            // Verify that the result is valid XML
            assertTrue(result.startsWith("<?xml"));

            // Parse the XML to check specific fields
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(result)));

            // Check the pubDate field format
            NodeList pubDateNodes = doc.getElementsByTagNameNS("http://univ.fr/rss25", "pubDate");
            if (pubDateNodes.getLength() > 0) {
                String pubDate = pubDateNodes.item(0).getTextContent();
                System.out.println("[DEBUG_LOG] PubDate: " + pubDate);

                // Verify that the pubDate matches the required pattern
                String pattern = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(Z|[+-]\\d{2}:\\d{2})";
                assertTrue(Pattern.matches(pattern, pubDate), 
                           "PubDate does not match required pattern: " + pubDate);
            }

            // Check published/updated dates in items
            NodeList itemNodes = doc.getElementsByTagNameNS("http://univ.fr/rss25", "item");
            for (int i = 0; i < itemNodes.getLength(); i++) {
                NodeList publishedNodes = ((org.w3c.dom.Element) itemNodes.item(i)).getElementsByTagNameNS("http://univ.fr/rss25", "published");
                NodeList updatedNodes = ((org.w3c.dom.Element) itemNodes.item(i)).getElementsByTagNameNS("http://univ.fr/rss25", "updated");

                if (publishedNodes.getLength() > 0) {
                    String published = publishedNodes.item(0).getTextContent();
                    System.out.println("[DEBUG_LOG] Published date: " + published);

                    // Verify that the published date matches the required pattern
                    String pattern = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(Z|[+-]\\d{2}:\\d{2})";
                    assertTrue(Pattern.matches(pattern, published), 
                               "Published date does not match required pattern: " + published);
                }

                if (updatedNodes.getLength() > 0) {
                    String updated = updatedNodes.item(0).getTextContent();
                    System.out.println("[DEBUG_LOG] Updated date: " + updated);

                    // Verify that the updated date matches the required pattern
                    String pattern = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(Z|[+-]\\d{2}:\\d{2})";
                    assertTrue(Pattern.matches(pattern, updated), 
                               "Updated date does not match required pattern: " + updated);
                }
            }

            // Print debug information
            System.out.println("[DEBUG_LOG] Converted XML: " + result.substring(0, Math.min(500, result.length())) + "...");

            // Success if no exception is thrown
            assertTrue(true);
        } catch (Exception e) {
            // If an exception is thrown, the test fails
            System.out.println("[DEBUG_LOG] Exception: " + e.getMessage());
            fail("Exception thrown during feed conversion: " + e.getMessage());
        }
    }
}
