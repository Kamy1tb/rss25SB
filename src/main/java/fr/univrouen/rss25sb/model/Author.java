package fr.univrouen.rss25sb.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;

/**
 * Entity class for author
 * Corresponds to the author element in the XSD schema
 */
@Entity
@Table(name = "authors")
@XmlAccessorType(XmlAccessType.FIELD)
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Long id;

    @XmlElement(namespace = "http://univ.fr/rss25")
    @Column(nullable = false, length = 64)
    private String name;

    @XmlElement(namespace = "http://univ.fr/rss25")
    @Column
    private String email;

    @XmlElement(namespace = "http://univ.fr/rss25")
    @Column
    private String uri;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @XmlTransient
    private Item item;

    @Column(name = "is_contributor")
    @XmlTransient
    private boolean isContributor = false;

    // Default constructor required by JPA
    public Author() {
    }

    // Constructor with parameters
    public Author(String name, String email, String uri, boolean isContributor) {
        this.name = name;
        this.email = email;
        this.uri = uri;
        this.isContributor = isContributor;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean isContributor() {
        return isContributor;
    }

    public void setContributor(boolean contributor) {
        isContributor = contributor;
    }
}