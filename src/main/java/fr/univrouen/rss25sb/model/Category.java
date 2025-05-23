package fr.univrouen.rss25sb.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;

/**
 * Entity class for category
 * Corresponds to the category element in the XSD schema
 */
@Entity
@Table(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Long id;

    @XmlAttribute(name = "term", required = true)
    @Column(nullable = false)
    private String term;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @XmlTransient
    private Item item;

    // Default constructor required by JPA
    public Category() {
    }

    // Constructor with parameters
    public Category(String term) {
        this.term = term;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}