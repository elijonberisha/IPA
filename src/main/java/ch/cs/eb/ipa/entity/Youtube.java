package ch.cs.eb.ipa.entity;

import javax.persistence.*;

/**
 * author: Elijon Berisha
 * date: 10.03.2022
 * class: YouTube.java
 */

@Entity
@NamedQuery(name = "Youtube.findAll", query = "SELECT y FROM Youtube y")
@Table(name = "youtube")
public class Youtube {
    // UNIQUE ID IS GENERATED FOR ENTITY
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private int id;

    // STORES THE YOUTUBE TUTORIAL LINK IN THE "tutorial_link" COLUMN
    @Column(name = "tutorial_link")
    private String tutorialLink;

    // CONTAINS USER THAT HAS BEEN ASSIGNED THIS ENTITY; ONLY ONE USER CAN BE ASSIGNED
    @OneToOne(mappedBy = "youtube")
    private CUser user;

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

    public CUser getUser() {
        return user;
    }

    public void setUser(CUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Youtube{" +
                "id=" + id +
                ", tutorialLink='" + tutorialLink + '\'' +
                ", user=" + user +
                '}';
    }
}
