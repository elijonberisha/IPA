package ch.cs.eb.ipa.config;

import ch.cs.eb.ipa.entity.CUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * author: Elijon Berisha
 * date: 14.03.2022
 * class: CUserToUserDetailsMapper.java
 */

// MAPS CUser TO User
public class CUserToUserDetailsMapper {
    public static UserDetails toUserDetails(CUser employee) {
        User user = null;

        if (employee != null) {
            Collection<UserGrantedAuthority> authorities = new ArrayList<>();
            // USER AUTHORITY IS FETCHED AND INSERTED INTO AUTHORITIES LIST
            authorities.add(new UserGrantedAuthority(employee.getUser_authority().getRole().name()));

            // User IS CREATED WITH THE CUser DATA -> USERNAME IS SET TO cts_id AND AUTHORITY IS FETCHED
            user = new org.springframework.security.core.userdetails.User(String.valueOf(employee.getCts_id()),
                    employee.getPassword(),
                    authorities);
        }
        return user;
    }
}
