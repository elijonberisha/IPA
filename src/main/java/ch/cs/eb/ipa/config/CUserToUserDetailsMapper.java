package ch.cs.eb.ipa.config;

import ch.cs.eb.ipa.entity.CUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CUserToUserDetailsMapper {
    public static UserDetails toUserDetails(CUser employee) {
        User user = null;

        if (employee != null) {
            Collection<UserGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new UserGrantedAuthority(employee.getUser_authority().getRole().name()));

            user = new org.springframework.security.core.userdetails.User(String.valueOf(employee.getCts_id()),
                    employee.getPassword(),
                    authorities);
        }
        return user;
    }
}
