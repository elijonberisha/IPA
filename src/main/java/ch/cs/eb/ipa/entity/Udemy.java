package ch.cs.eb.ipa.entity;

import javax.persistence.*;

@Entity
@NamedQuery(name = "Udemy.findAll", query = "SELECT u FROM Udemy u")
@Table(name = "udemy")
public class Udemy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private int id;

    @Column(name = "training1_link")
    private String training1Link;

    @Column(name = "training2_link")
    private String training2Link;

    @Column(name = "training3_link")
    private String training3Link;

    @OneToOne(mappedBy = "udemy")
    private CUser user;

    public Udemy() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTraining1Link() {
        return training1Link;
    }

    public void setTraining1Link(String training1Link) {
        this.training1Link = training1Link;
    }

    public String getTraining2Link() {
        return training2Link;
    }

    public void setTraining2Link(String training2Link) {
        this.training2Link = training2Link;
    }

    public String getTraining3Link() {
        return training3Link;
    }

    public void setTraining3Link(String training3Link) {
        this.training3Link = training3Link;
    }

    public CUser getUser() {
        return user;
    }

    public void setUser(CUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Udemy{" +
                "id=" + id +
                ", training1Link='" + training1Link + '\'' +
                ", training2Link='" + training2Link + '\'' +
                ", training3Link='" + training3Link + '\'' +
                ", user=" + user +
                '}';
    }
}
