package ch.cs.eb.ipa.controller.login;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * author: Elijon Berisha
 * date: 14.03.2022
 * class: LoginController.java
 */

// SERVES AS A ROUTING STATION FOR THE LOGIN MAPPING
@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login() {
        // GETS CURRENT AUTHENTICATION; IF AUTHENTICATION IS NOT OF TYPE ANONYMOUS OR NOT EXISTENT, THE USER CAN ACCESS THE REGISTER TEMPLATE
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        // IF USER IS LOGGED IN, HE IS REDIRECTED TO THE /home MAPPING
        return "redirect:/home";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        // USER SESSION IS INVALIDATED
        session.invalidate();
        return "redirect:/login";
    }

    @RequestMapping("/logout")
    public String logoutGet(HttpSession session) {
        // USER SESSION IS INVALIDATED
        session.invalidate();
        return "redirect:/login";
    }

    @RequestMapping("/expired")
    public String expired() {
        return "redirect:/home";
    }
}
