package ch.cs.eb.ipa.controller.profile;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewProfileController {
    @RequestMapping("/view_profile")
    public String viewProfile() {
        return "view_profile";
    }
}
