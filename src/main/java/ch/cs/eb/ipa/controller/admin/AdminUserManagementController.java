package ch.cs.eb.ipa.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminUserManagementController {
    @RequestMapping("/user_management")
    public String userManagement() {
        return "user_management";
    }
}
