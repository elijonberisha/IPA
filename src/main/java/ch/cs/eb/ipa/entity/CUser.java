package ch.cs.eb.ipa.entity;

import javax.persistence.*;

/**
 * author: Elijon Berisha
 * date: 10.03.2022
 * class: CUser.java
 */

@Entity
@NamedQuery(name = "CUser.findAll", query = "SELECT c FROM CUser c")
@Table(name = "c_user")
public class CUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private int id;

    @Column(name = "cts_id")
    private int cts_id;

    @Column(name = "prename")
    private String prename;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authority_IDFK", referencedColumnName = "ID")
    private UAuthority user_authority;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "youtube_IDFK", referencedColumnName = "ID")
    private Youtube youtube;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "udemy_IDFK", referencedColumnName = "ID")
    private Udemy udemy;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "oreilly_IDFK", referencedColumnName = "ID")
    private OReilly oreilly;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "w3schools_IDFK", referencedColumnName = "ID")
    private W3schools w3schools;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ted_IDFK", referencedColumnName = "ID")
    private Ted ted;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "degreed_IDFK", referencedColumnName = "ID")
    private Degreed degreed;

    public CUser() {
    }

    public CUser(int cts_id, String prename, String lastname, String password, String email, UAuthority user_authority) {
        this.cts_id = cts_id;
        this.prename = prename;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.user_authority = user_authority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCts_id() {
        return cts_id;
    }

    public void setCts_id(int cts_id) {
        this.cts_id = cts_id;
    }

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UAuthority getUser_authority() {
        return user_authority;
    }

    public void setUser_authority(UAuthority user_authority) {
        this.user_authority = user_authority;
    }

    public Youtube getYoutube() {
        return youtube;
    }

    public void setYoutube(Youtube youtube) {
        this.youtube = youtube;
    }

    public Udemy getUdemy() {
        return udemy;
    }

    public void setUdemy(Udemy udemy) {
        this.udemy = udemy;
    }

    public OReilly getOreilly() {
        return oreilly;
    }

    public void setOreilly(OReilly oreilly) {
        this.oreilly = oreilly;
    }

    public W3schools getW3schools() {
        return w3schools;
    }

    public void setW3schools(W3schools w3schools) {
        this.w3schools = w3schools;
    }

    public Ted getTed() {
        return ted;
    }

    public void setTed(Ted ted) {
        this.ted = ted;
    }

    public Degreed getDegreed() {
        return degreed;
    }

    public void setDegreed(Degreed degreed) {
        this.degreed = degreed;
    }

    @Override
    public String toString() {
        return "CUser{" +
                "id=" + id +
                ", cts_id=" + cts_id +
                ", prename='" + prename + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", user_authority=" + user_authority +
                ", youtube=" + youtube +
                ", udemy=" + udemy +
                ", oreilly=" + oreilly +
                ", w3schools=" + w3schools +
                ", ted=" + ted +
                ", degreed=" + degreed +
                '}';
    }
}
