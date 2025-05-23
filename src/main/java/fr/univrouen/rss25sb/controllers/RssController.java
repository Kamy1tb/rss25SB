package fr.univrouen.rss25sb.controllers;

import fr.univrouen.rss25sb.model.Feed;
import fr.univrouen.rss25sb.model.Item;
import fr.univrouen.rss25sb.service.FeedService;
import fr.univrouen.rss25sb.service.ItemService;
import fr.univrouen.rss25sb.util.XmlValidator;
import fr.univrouen.rss25sb.util.XsltTransformer;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controller for RSS-related endpoints
 */
@Controller
@RequestMapping("/rss25SB")
public class RssController {

    private final FeedService feedService;
    private final ItemService itemService;
    private final XmlValidator xmlValidator;
    private final XsltTransformer xsltTransformer;

    @Autowired
    public RssController(FeedService feedService, ItemService itemService, XmlValidator xmlValidator, XsltTransformer xsltTransformer) {
        this.feedService = feedService;
        this.itemService = itemService;
        this.xmlValidator = xmlValidator;
        this.xsltTransformer = xsltTransformer;
    }

    /**
     * Get a list of all feeds in XML format
     * @return XML representation of all feeds
     */
    @GetMapping(value = "/resume/xml", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public String getItemsXml() {
        try {
            List<Feed> feeds = feedService.findAll();

            // Create a wrapper XML element to contain all feeds
            StringBuilder xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            xml.append("<feeds xmlns=\"http://univ.fr/rss25\">\n");

            for (Feed feed : feeds) {
                // Marshal each feed to XML using JAXB
                JAXBContext context = JAXBContext.newInstance(Feed.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true); // Don't include XML declaration

                StringWriter writer = new StringWriter();
                marshaller.marshal(feed, writer);

                // Append the feed XML to the result
                xml.append(writer.toString()).append("\n");
            }

            xml.append("</feeds>");
            return xml.toString();
        } catch (Exception e) {
            return createErrorXml("ERROR", "Failed to retrieve feeds: " + e.getMessage());
        }
    }

    /**
     * Get a list of all items in HTML format
     * @return HTML representation of all items
     */
    @GetMapping(value = "/resume/html", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getItemsHtml() {
        try {
            String xml = getItemsXml();
            return xsltTransformer.transformItemList(xml);
        } catch (TransformerException | IOException e) {
            return "<html><body><h1>Error</h1><p>Failed to transform items to HTML: " + e.getMessage() + "</p></body></html>";
        }
    }

    /**
     * Get a specific item by ID in XML format
     * @param id the ID of the item to retrieve
     * @return XML representation of the item
     */
    @GetMapping(value = "/resume/xml/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public String getItemXml(@PathVariable Long id) {
        try {
            Optional<Item> optionalItem = itemService.findById(id);

            if (optionalItem.isPresent()) {
                Item item = optionalItem.get();
                JAXBContext context = JAXBContext.newInstance(Item.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                StringWriter writer = new StringWriter();
                marshaller.marshal(item, writer);
                return writer.toString();
            } else {
                return createErrorXml(id.toString(), "ERROR");
            }
        } catch (JAXBException e) {
            return createErrorXml(id.toString(), "ERROR");
        }
    }

    /**
     * Get a specific item by ID in HTML format
     * @param id the ID of the item to retrieve
     * @return HTML representation of the item
     */
    @GetMapping(value = "/html/{id}", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getItemHtml(@PathVariable Long id) {
        try {
            Optional<Item> optionalItem = itemService.findById(id);

            if (optionalItem.isPresent()) {
                Item item = optionalItem.get();
                JAXBContext context = JAXBContext.newInstance(Item.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                StringWriter writer = new StringWriter();
                marshaller.marshal(item, writer);

                return xsltTransformer.transformItemDetail(writer.toString());
            } else {
                // Create error XML
                String errorXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<error>\n" +
                        "  <id>" + id + "</id>\n" +
                        "  <status>ERROR</status>\n" +
                        "</error>";

                return xsltTransformer.transformItemDetail(errorXml);
            }
        } catch (JAXBException | TransformerException | IOException e) {
            return "<html><body><h1>Error</h1><p>Failed to retrieve or transform item: " + e.getMessage() + "</p></body></html>";
        }
    }

    /**
     * Get a specific item by GUID in HTML format
     * @param guid the GUID of the item to retrieve
     * @return HTML representation of the item
     */
    @GetMapping(value = "/html/guid/{guid}", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getItemHtmlByGuid(@PathVariable String guid) {
        try {
            Optional<Item> optionalItem = itemService.findByGuid(guid);

            if (optionalItem.isPresent()) {
                Item item = optionalItem.get();
                JAXBContext context = JAXBContext.newInstance(Item.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                StringWriter writer = new StringWriter();
                marshaller.marshal(item, writer);

                return xsltTransformer.transformItemDetail(writer.toString());
            } else {
                // Create error XML
                String errorXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<error>\n" +
                        "  <guid>" + guid + "</guid>\n" +
                        "  <status>ERROR</status>\n" +
                        "</error>";

                return xsltTransformer.transformItemDetail(errorXml);
            }
        } catch (JAXBException | TransformerException | IOException e) {
            return "<html><body><h1>Error</h1><p>Failed to retrieve or transform item: " + e.getMessage() + "</p></body></html>";
        }
    }

    /**
     * Add a new feed
     * @param xml the XML representation of the feed to add
     * @return XML response indicating success or failure
     */
    @PostMapping(value = "/insert", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public String addFeed(@RequestBody String xml) {
        try {
            // Validate XML against XSD schema
            XmlValidator.ValidationResult validationResult = xmlValidator.validate(xml);
            if (!validationResult.isValid()) {
                return createErrorXml("ERROR", validationResult.getErrorMessage());
            }

            // Unmarshal XML to Feed object
            JAXBContext context = JAXBContext.newInstance(Feed.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Feed feed = (Feed) unmarshaller.unmarshal(new StringReader(xml));

            // Check if feed with same title and pubDate already exists
            if (feedService.existsByTitleAndPubDate(feed.getTitle(), feed.getPubDate())) {
                return createErrorXml("ERROR", "Feed with same title and publication date already exists");
            }

            // Save feed
            Feed savedFeed = feedService.save(feed);

            // Get ID of last inserted item
            Long lastItemId = null;
            if (savedFeed.getItems() != null && !savedFeed.getItems().isEmpty()) {
                lastItemId = savedFeed.getItems().get(savedFeed.getItems().size() - 1).getId();
            }

            // Create success response
            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<result>\n" +
                    "  <id>" + lastItemId + "</id>\n" +
                    "  <status>INSERTED</status>\n" +
                    "</result>";
        } catch (Exception e) {
            return createErrorXml("ERROR", e.getMessage());
        }
    }

    /**
     * Delete an item by ID
     * @param id the ID of the item to delete
     * @return XML response indicating success or failure
     */
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public String deleteItem(@PathVariable Long id) {
        try {
            Optional<Item> optionalItem = itemService.findById(id);

            if (optionalItem.isPresent()) {
                Item item = optionalItem.get();
                Feed feed = item.getFeed();

                // Delete item
                itemService.deleteById(id);

                // Check if feed is empty and delete it if necessary
                if (feed != null && (feed.getItems() == null || feed.getItems().isEmpty())) {
                    feedService.deleteById(feed.getId());
                }

                // Create success response
                return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<result>\n" +
                        "  <id>" + id + "</id>\n" +
                        "  <status>DELETED</status>\n" +
                        "</result>";
            } else {
                return createErrorXml("ERROR", "Item with ID " + id + " not found");
            }
        } catch (Exception e) {
            return createErrorXml("ERROR", e.getMessage());
        }
    }

    /**
     * Create an error XML response
     * @param status the error status
     * @param message the error message
     * @return XML representation of the error
     */
    private String createErrorXml(String status, String message) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<error>\n" +
                "  <status>" + status + "</status>\n" +
                "  <message>" + message + "</message>\n" +
                "</error>";
    }
}
