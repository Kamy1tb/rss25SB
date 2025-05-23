package fr.univrouen.rss25sb.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;

/**
 * Entity class for content
 * Corresponds to the content element in the XSD schema
 */
@Entity
@Table(name = "contents")
@XmlAccessorType(XmlAccessType.FIELD)
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Long id;

    @XmlAttribute(name = "type", required = true)
    @Column(nullable = false)
    private String type;

    @XmlAttribute(name = "src")
    private String src;

    @XmlValue
    @Column(columnDefinition = "TEXT")
    private String value;

    @OneToOne(mappedBy = "content")
    @XmlTransient
    private Item item;

    // Default constructor required by JPA
    public Content() {
    }

    // Constructor with parameters
    public Content(String type, String src, String value) {
        this.type = type;
        this.src = src;
        this.value = value;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}