package ch.cs.eb.ipa.entity;

import javax.persistence.*;

@Entity
@NamedQuery(name = "OReilly.findAll", query = "SELECT o FROM OReilly o")
@Table(name = "oreilly")
public class OReilly {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private int id;

    @Column(name = "book_link")
    private String bookLink;

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

    @Override
    public String toString() {
        return "OReilly{" +
                "id=" + id +
                ", bookLink='" + bookLink + '\'' +
                '}';
    }
}
