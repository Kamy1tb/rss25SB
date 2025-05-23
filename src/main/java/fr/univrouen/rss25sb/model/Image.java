package fr.univrouen.rss25sb.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;

/**
 * Entity class for image
 * Corresponds to the image element in the XSD schema
 */
@Entity
@Table(name = "images")
@XmlAccessorType(XmlAccessType.FIELD)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Long id;

    @XmlAttribute(name = "type", required = true)
    @Column(nullable = false)
    private String type;

    @XmlAttribute(name = "href", required = true)
    @Column(nullable = false)
    private String href;

    @XmlAttribute(name = "alt", required = true)
    @Column(nullable = false)
    private String alt;

    @XmlAttribute(name = "length")
    private Integer length;

    @OneToOne(mappedBy = "image")
    @XmlTransient
    private Item item;

    // Default constructor required by JPA
    public Image() {
    }

    // Constructor with parameters
    public Image(String type, String href, String alt, Integer length) {
        this.type = type;
        this.href = href;
        this.alt = alt;
        this.length = length;
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

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}