package ch.cs.eb.ipa.entity;

import javax.persistence.*;

/**
 * author: Elijon Berisha
 * date: 10.03.2022
 * class: Ted.java
 */

@Entity
@NamedQuery(name = "Ted.findAll", query = "SELECT t FROM Ted t")
@Table(name = "ted")
public class Ted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private int id;

    @Column(name = "talk1_link")
    private String talk1Link;

    @Column(name = "talk2_link")
    private String talk2Link;

    @Column(name = "talk3_link")
    private String talk3Link;

    @OneToOne(mappedBy = "ted")
    private CUser user;

    public Ted() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTalk1Link() {
        return talk1Link;
    }

    public void setTalk1Link(String talk1Link) {
        this.talk1Link = talk1Link;
    }

    public String getTalk2Link() {
        return talk2Link;
    }

    public void setTalk2Link(String talk2Link) {
        this.talk2Link = talk2Link;
    }

    public String getTalk3Link() {
        return talk3Link;
    }

    public void setTalk3Link(String talk3Link) {
        this.talk3Link = talk3Link;
    }

    public CUser getUser() {
        return user;
    }

    public void setUser(CUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Ted{" +
                "id=" + id +
                ", talk1Link='" + talk1Link + '\'' +
                ", talk2Link='" + talk2Link + '\'' +
                ", talk3Link='" + talk3Link + '\'' +
                ", user=" + user +
                '}';
    }
}
