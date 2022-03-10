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
