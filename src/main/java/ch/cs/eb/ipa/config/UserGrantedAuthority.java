package ch.cs.eb.ipa.config;

import org.springframework.security.core.GrantedAuthority;

/**
 * author: Elijon Berisha
 * date: 14.03.2022
 * class: UserGrantedAuthority.java
 */

// CLASS IS USED TO SET THE AUTHORITY OF THE USER FOR THE APPLICATION USAGE
public class UserGrantedAuthority implements GrantedAuthority {
    private String authority;

    public UserGrantedAuthority(String authority) {
        this.authority = authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public String toString() {
        return "UserGrantedAuthority{" +
                "authority='" + authority + '\'' +
                '}';
    }
}
