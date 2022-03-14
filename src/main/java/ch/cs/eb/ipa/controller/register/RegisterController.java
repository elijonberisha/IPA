package ch.cs.eb.ipa.controller.register;

import ch.cs.eb.ipa.model.FormContainerUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegisterController {
    @RequestMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new FormContainerUser());
        return "register";
    }
}
