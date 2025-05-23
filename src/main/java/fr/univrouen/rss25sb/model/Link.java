package fr.univrouen.rss25sb.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;

/**
 * Entity class for link
 * Corresponds to the link element in the XSD schema
 */
@Entity
@Table(name = "links")
@XmlAccessorType(XmlAccessType.FIELD)
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Long id;

    @XmlAttribute(name = "rel", required = true)
    @Column(nullable = false)
    private String rel;

    @XmlAttribute(name = "type", required = true)
    @Column(nullable = false)
    private String type;

    @XmlAttribute(name = "href", required = true)
    @Column(nullable = false)
    private String href;

    @ManyToOne
    @JoinColumn(name = "feed_id")
    @XmlTransient
    private Feed feed;

    // Default constructor required by JPA
    public Link() {
    }

    // Constructor with parameters
    public Link(String rel, String type, String href) {
        this.rel = rel;
        this.type = type;
        this.href = href;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }
}