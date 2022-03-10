package ch.cs.eb.ipa.entity;

import ch.cs.eb.ipa.model.UserAuthority;

import javax.persistence.*;

@Entity
@NamedQuery(name = "UAuthority.findAll", query = "SELECT u FROM UAuthority u")
@Table(name = "authority")
public class UAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private int id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserAuthority role;

    public UAuthority(UserAuthority role) {
        this.role = role;
    }

    public UAuthority() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserAuthority getRole() {
        return role;
    }

    public void setRole(UserAuthority role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UAuthority{" +
                "id=" + id +
                ", role=" + role +
                '}';
    }
}
