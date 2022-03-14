package ch.cs.eb.ipa.config;

import org.springframework.security.core.GrantedAuthority;

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
