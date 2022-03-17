package ch.cs.eb.ipa.entity;

import javax.persistence.*;

/**
 * author: Elijon Berisha
 * date: 10.03.2022
 * class: W3schools.java
 */

@Entity
@NamedQuery(name = "W3schools.findAll", query = "SELECT w FROM W3schools w")
@Table(name = "w3schools")
public class W3schools {
    // UNIQUE ID IS GENERATED FOR ENTITY
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private int id;

    // STORES THE FIRST W3SCHOOLS ARTICLE LINK IN THE "article1_link" COLUMN
    @Column(name = "article1_link")
    private String article1Link;

    // STORES THE SECOND W3SCHOOLS ARTICLE LINK IN THE "article2_link" COLUMN
    @Column(name = "article2_link")
    private String article2Link;

    // STORES THE THIRD W3SCHOOLS ARTICLE LINK IN THE "article3_link" COLUMN
    @Column(name = "article3_link")
    private String article3Link;

    // CONTAINS USER THAT HAS BEEN ASSIGNED THIS ENTITY; ONLY ONE USER CAN BE ASSIGNED
    @OneToOne(mappedBy = "w3schools")
    private CUser user;

    public W3schools() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArticle1Link() {
        return article1Link;
    }

    public void setArticle1Link(String article1Link) {
        this.article1Link = article1Link;
    }

    public String getArticle2Link() {
        return article2Link;
    }

    public void setArticle2Link(String article2Link) {
        this.article2Link = article2Link;
    }

    public String getArticle3Link() {
        return article3Link;
    }

    public void setArticle3Link(String article3Link) {
        this.article3Link = article3Link;
    }

    public CUser getUser() {
        return user;
    }

    public void setUser(CUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "W3schools{" +
                "id=" + id +
                ", article1Link='" + article1Link + '\'' +
                ", article2Link='" + article2Link + '\'' +
                ", article3Link='" + article3Link + '\'' +
                ", user=" + user +
                '}';
    }
}
