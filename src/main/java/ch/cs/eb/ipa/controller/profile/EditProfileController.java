package ch.cs.eb.ipa.controller.profile;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EditProfileController {
    @RequestMapping("/edit_profile")
    public String editProfile() {
        return "edit_profile";
    }
}
