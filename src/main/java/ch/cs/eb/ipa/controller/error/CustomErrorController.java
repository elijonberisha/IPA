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

@Controller
public class CustomErrorController implements ErrorController {
    @Autowired
    CUserRepository userRepository;

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "redirect:/404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "redirect:/500";
            } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
                return "redirect:/403";
            }
        }

        return "redirect:/home";
    }

    @RequestMapping("/403")
    public String accessDenied(Model model) {
        UsernameFetcher usernameFetcher = new UsernameFetcher();

        if (userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())).getUser_authority().getRole().equals(UserAuthority.ADMIN)) {
            model.addAttribute("admin", "no relevance");
        }

        model.addAttribute("self", userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())));
        return "error/403";
    }

    @RequestMapping("/404")
    public String notFound(Model model) {
        UsernameFetcher usernameFetcher = new UsernameFetcher();

        if (userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())).getUser_authority().getRole().equals(UserAuthority.ADMIN)) {
            model.addAttribute("admin", "no relevance");
        }

        model.addAttribute("self", userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())));
        return "error/404";
    }
}
