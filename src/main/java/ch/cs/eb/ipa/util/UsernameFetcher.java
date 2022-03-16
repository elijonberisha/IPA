package ch.cs.eb.ipa.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * author: Elijon Berisha
 * date: 14.03.2022
 * class: UsernameFetcher.java
 */

// CLASS IS USED TO GET INFORMATION ON THE CURRENTLY LOGGED IN USER
public class UsernameFetcher {
    public String getUsername() {
        // GETS THE SPRING SECURITY AUTHENTICATION INSTANCE FROM CONTEXT HOLDER, WHICH STORES AUTHENTICATION DATA
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // IF THE AUTHENTICATION IS NULL OR USER IS ANONYMOUS, NO USERNAME IS RETURNED
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "";
        } else {
            // USERNAME IS SET AND RETURNED FOR USAGE
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username;

            if (principal instanceof UserDetails) {
                username = ((UserDetails)principal).getUsername();
            } else {
                username = principal.toString();
            }

            return username;
        }
    }
}
