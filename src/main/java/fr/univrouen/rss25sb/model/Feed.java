package fr.univrouen.rss25sb.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class for RSS feed
 * Corresponds to the feed element in the XSD schema
 */
@Entity
@Table(name = "feeds")
@XmlRootElement(name = "feed", namespace = "http://univ.fr/rss25")
@XmlAccessorType(XmlAccessType.FIELD)
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Long id;

    @XmlElement(namespace = "http://univ.fr/rss25")
    @Column(nullable = false, length = 128)
    private String title;

    @XmlElement(namespace = "http://univ.fr/rss25")
    @Column(nullable = false)
    private String pubDate;

    @XmlElement(namespace = "http://univ.fr/rss25")
    @Column(nullable = false, length = 128)
    private String copyright;

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    @XmlElement(name = "link", namespace = "http://univ.fr/rss25")
    private List<Link> links = new ArrayList<>();

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    @XmlElement(name = "item", namespace = "http://univ.fr/rss25")
    private List<Item> items = new ArrayList<>();

    @XmlAttribute(name = "lang")
    @Column(length = 10)
    private String lang;

    @XmlAttribute(name = "version")
    @Column(nullable = false)
    private String version = "25";

    // Default constructor required by JPA
    public Feed() {
    }

    // Constructor with parameters
    public Feed(String title, String pubDate, String copyright, String lang) {
        this.title = title;
        this.pubDate = pubDate;
        this.copyright = copyright;
        this.lang = lang;
    }

    // Helper methods to maintain bidirectional relationships
    public void addLink(Link link) {
        links.add(link);
        link.setFeed(this);
    }

    public void removeLink(Link link) {
        links.remove(link);
        link.setFeed(null);
    }

    public void addItem(Item item) {
        items.add(item);
        item.setFeed(this);
    }

    public void removeItem(Item item) {
        items.remove(item);
        item.setFeed(null);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
