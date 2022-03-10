package ch.cs.eb.ipa.entity;

import javax.persistence.*;

@Entity
@NamedQuery(name = "Degreed.findAll", query = "SELECT d FROM Degreed d")
@Table(name = "degreed")
public class Degreed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private int id;

    @Column(name = "article_link")
    private String articleLink;

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

    @Override
    public String toString() {
        return "Degreed{" +
                "id=" + id +
                ", articleLink='" + articleLink + '\'' +
                '}';
    }
}
