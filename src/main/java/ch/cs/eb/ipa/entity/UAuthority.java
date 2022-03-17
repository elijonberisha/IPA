package ch.cs.eb.ipa.entity;

import ch.cs.eb.ipa.model.UserAuthority;

import javax.persistence.*;
import java.util.List;

/**
 * author: Elijon Berisha
 * date: 10.03.2022
 * class: UAuthority.java
 */

@Entity
// JPQL QUERY DECLARATION FOR LATER USAGE IN CUserRepository
@NamedQuery(name = "UAuthority.findAll", query = "SELECT u FROM UAuthority u")
@Table(name = "authority")
public class UAuthority {
    // UNIQUE ID IS GENERATED FOR ENTITY
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private int id;

    // ENUM IS STORED AS STRING IN DB; ROLE IS STORED IN "role" COLUMN
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserAuthority role;

    // CONTAINS USERS THAT HAVE BEEN ASSIGNED THIS AUTHORITY; CAN BE LINKED TO MULTIPLE USERS
    @OneToMany(targetEntity = CUser.class, mappedBy = "user_authority", fetch = FetchType.EAGER)
    List<CUser> userAuthority;

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

    public List<CUser> getUserAuthority() {
        return userAuthority;
    }

    public void setUserAuthority(List<CUser> userAuthority) {
        this.userAuthority = userAuthority;
    }

    @Override
    public String toString() {
        return "UAuthority{" +
                "id=" + id +
                ", role=" + role +
                ", userAuthority=" + userAuthority +
                '}';
    }
}
