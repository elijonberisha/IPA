package ch.cs.eb.ipa.entity;

import javax.persistence.*;

@Entity
@NamedQuery(name = "W3schools.findAll", query = "SELECT w FROM W3schools w")
@Table(name = "w3schools")
public class W3schools {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private int id;

    @Column(name = "article1_link")
    private String article1Link;

    @Column(name = "article2_link")
    private String article2Link;

    @Column(name = "article3_link")
    private String article3Link;

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

    @Override
    public String toString() {
        return "W3schools{" +
                "id=" + id +
                ", article1Link='" + article1Link + '\'' +
                ", article2Link='" + article2Link + '\'' +
                ", article3Link='" + article3Link + '\'' +
                '}';
    }
}
