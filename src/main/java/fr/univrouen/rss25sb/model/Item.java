package fr.univrouen.rss25sb.model;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Item {
    @XmlAttribute
    private String guid;
    @XmlElement
    private String title;
    @XmlElement
    private String published;
    public Item(String guid, String title, String published) {
        super();
        this.guid = guid;
        this.title = title;
        this.published = published;
    }
    public Item() {
    }
    @Override
    public String toString() {
        return ("Article : " + title + "\n(" + guid
                + ") Le = " + published );
    }
}