package ch.cs.eb.ipa.controller.password;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChangePasswordController {

    @RequestMapping("/change_password")
    public String changePassword() {
        return "change_password";
    }
}
