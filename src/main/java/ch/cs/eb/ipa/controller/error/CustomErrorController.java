package ch.cs.eb.ipa.controller.error;

import ch.cs.eb.ipa.model.UserAuthority;
import ch.cs.eb.ipa.repository.CUserRepository;
import ch.cs.eb.ipa.util.UsernameFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * author: Elijon Berisha
 * date: 15.03.2022
 * class: CustomErrorController.java
 */

@Controller
public class CustomErrorController implements ErrorController {
    // USER REPOSITORY IS INSERTED; NO NEED FOR CONSTRUCTOR
    @Autowired
    CUserRepository userRepository;

    // ERRORS IN THE APPLICATION ARE MAPPED TO THIS MAPPING /error
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        // STATUS CODE OF REQUEST IS FETCHED
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        // IS STATUS DOES NOT EXIST; USER IS REDIRECTED BACK TO /home
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            // IF STATUS IS NOT FOUND, USER IS STILL RETURNED TO /home
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "redirect:/home";
            // IF USER IS UNAUTHORIZED, USER WILL BE REDIRECTED TO /403
            } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
                return "redirect:/403";
            }
        }

        return "redirect:/home";
    }

    // DESTINATION MAPPING IS USER IS UNAUTHORIZED
    @RequestMapping("/403")
    public String accessDenied(Model model) {
        // USERNAME IS FETCHED
        UsernameFetcher usernameFetcher = new UsernameFetcher();

        // IF USER IS AN ADMIN, THE ADMIN PROPERTY WILL BE SET IN THE MODEL
        if (userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())).getUser_authority().getRole().equals(UserAuthority.ADMIN)) {
            model.addAttribute("admin", "no relevance");
        }

        // CURRENT USER IS ADDED AS A PROPERTY TO THE MODEL
        model.addAttribute("self", userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())));
        return "error/403";
    }

    // DESTINATION IF TEMPLATE CANNOT BE FOUND
    @RequestMapping("/404")
    public String notFound(Model model) {
        // USERNAME IS FETCHED
        UsernameFetcher usernameFetcher = new UsernameFetcher();

        // IF USER IS AN ADMIN, THE ADMIN PROPERTY WILL BE SET IN THE MODEL
        if (userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())).getUser_authority().getRole().equals(UserAuthority.ADMIN)) {
            model.addAttribute("admin", "no relevance");
        }

        // CURRENT USER IS ADDED AS A PROPERTY TO THE MODEL
        model.addAttribute("self", userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())));
        return "error/404";
    }
}
