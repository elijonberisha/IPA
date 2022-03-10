package ch.cs.eb.ipa.entity;

import javax.persistence.*;

@Entity
@NamedQuery(name = "Youtube.findAll", query = "SELECT y FROM Youtube y")
@Table(name = "youtube")
public class Youtube {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private int id;

    @Column(name = "tutorial_link")
    private String tutorialLink;

    public Youtube() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTutorialLink() {
        return tutorialLink;
    }

    public void setTutorialLink(String tutorialLink) {
        this.tutorialLink = tutorialLink;
    }

    @Override
    public String toString() {
        return "Youtube{" +
                "id=" + id +
                ", tutorialLink='" + tutorialLink + '\'' +
                '}';
    }
}
