package ch.cs.eb.ipa.entity;

import javax.persistence.*;

/**
 * author: Elijon Berisha
 * date: 10.03.2022
 * class: Degreed.java
 */

@Entity
@NamedQuery(name = "Degreed.findAll", query = "SELECT d FROM Degreed d")
@Table(name = "degreed")
public class Degreed {
    // UNIQUE ID IS GENERATED FOR ENTITY
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private int id;

    // STORES THE DEGREED ARTICLE LINK IN THE "article_link" COLUMN
    @Column(name = "article_link")
    private String articleLink;

    // CONTAINS USER THAT HAS BEEN ASSIGNED THIS ENTITY; ONLY ONE USER CAN BE ASSIGNED
    @OneToOne(mappedBy = "degreed")
    private CUser user;

    public Degreed() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArticleLink() {
        return articleLink;
    }

    public void setArticleLink(String articleLink) {
        this.articleLink = articleLink;
    }

    public CUser getUser() {
        return user;
    }

    public void setUser(CUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Degreed{" +
                "id=" + id +
                ", articleLink='" + articleLink + '\'' +
                ", user=" + user +
                '}';
    }
}
