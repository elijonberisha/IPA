package ch.cs.eb.ipa.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminChangePasswordController {
    @RequestMapping("/change_password_admin")
    public String changePasswordAdmin() {
        return "change_password_admin";
    }
}
