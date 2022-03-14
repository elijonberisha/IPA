package ch.cs.eb.ipa.controller.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        return "redirect:/home";
    }

    @RequestMapping("/403")
    public String accessDenied() {
        return "error/403";
    }
}
