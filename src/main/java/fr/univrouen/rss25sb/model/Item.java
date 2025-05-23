package fr.univrouen.rss25sb.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class for RSS item
 * Corresponds to the item element in the XSD schema
 */
@Entity
@Table(name = "items")
@XmlRootElement(name = "item", namespace = "http://univ.fr/rss25")
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Long id;

    @XmlElement(namespace = "http://univ.fr/rss25")
    @Column(nullable = false)
    private String guid;

    @XmlElement(namespace = "http://univ.fr/rss25")
    @Column(nullable = false, length = 128)
    private String title;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @XmlElement(name = "category", namespace = "http://univ.fr/rss25")
    private List<Category> categories = new ArrayList<>();

    @XmlElement(name = "published", namespace = "http://univ.fr/rss25")
    @Column(nullable = false)
    private String published;

    @XmlElement(name = "updated", namespace = "http://univ.fr/rss25")
    @Column
    private String updated;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    @XmlElement(name = "image", namespace = "http://univ.fr/rss25")
    private Image image;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "content_id")
    @XmlElement(name = "content", namespace = "http://univ.fr/rss25")
    private Content content;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @XmlElement(name = "author", namespace = "http://univ.fr/rss25")
    private List<Author> authors = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @XmlElement(name = "contributor", namespace = "http://univ.fr/rss25")
    private List<Author> contributors = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "feed_id")
    @XmlTransient
    private Feed feed;

    // Default constructor required by JPA
    public Item() {
        // Constructor is empty, but setUpdated will handle setting published if needed
    }

    // Constructor with parameters
    public Item(String guid, String title, String published) {
        this.guid = guid;
        this.title = title;
        this.published = published;
    }

    // Helper methods to maintain bidirectional relationships
    public void addCategory(Category category) {
        categories.add(category);
        category.setItem(this);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
        category.setItem(null);
    }

    public void setImage(Image image) {
        if (image == null) {
            if (this.image != null) {
                this.image.setItem(null);
            }
        } else {
            image.setItem(this);
        }
        this.image = image;
    }

    public void setContent(Content content) {
        if (content == null) {
            if (this.content != null) {
                this.content.setItem(null);
            }
        } else {
            content.setItem(this);
        }
        this.content = content;
    }

    public void addAuthor(Author author) {
        authors.add(author);
        author.setItem(this);
        author.setContributor(false);
    }

    public void removeAuthor(Author author) {
        authors.remove(author);
        author.setItem(null);
    }

    public void addContributor(Author contributor) {
        contributors.add(contributor);
        contributor.setItem(this);
        contributor.setContributor(true);
    }

    public void removeContributor(Author contributor) {
        contributors.remove(contributor);
        contributor.setItem(null);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        // If published is being set to null but updated is not null, use updated as published
        if (published == null && this.updated != null) {
            this.published = this.updated;
        } else {
            this.published = published;
        }
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
        // If published is null but updated is not, use updated as published
        if (this.published == null && updated != null) {
            this.published = updated;
        }
    }

    public Image getImage() {
        return image;
    }

    public Content getContent() {
        return content;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Author> getContributors() {
        return contributors;
    }

    public void setContributors(List<Author> contributors) {
        this.contributors = contributors;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    @Override
    public String toString() {
        return "Article : " + title + "\n(" + guid + ") Le = " + published;
    }
}
