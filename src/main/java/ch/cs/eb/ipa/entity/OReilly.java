package ch.cs.eb.ipa.entity;

import javax.persistence.*;

/**
 * author: Elijon Berisha
 * date: 10.03.2022
 * class: OReilly.java
 */

@Entity
@NamedQuery(name = "OReilly.findAll", query = "SELECT o FROM OReilly o")
@Table(name = "oreilly")
public class OReilly {
    // UNIQUE ID IS GENERATED FOR ENTITY
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private int id;

    // STORES THE OREILLY BOOK LINK IN THE "book_link" COLUMN
    @Column(name = "book_link")
    private String bookLink;

    // CONTAINS USER THAT HAS BEEN ASSIGNED THIS ENTITY; ONLY ONE USER CAN BE ASSIGNED
    @OneToOne(mappedBy = "oreilly")
    private CUser user;

    public OReilly() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookLink() {
        return bookLink;
    }

    public void setBookLink(String bookLink) {
        this.bookLink = bookLink;
    }

    public CUser getUser() {
        return user;
    }

    public void setUser(CUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "OReilly{" +
                "id=" + id +
                ", bookLink='" + bookLink + '\'' +
                ", user=" + user +
                '}';
    }
}