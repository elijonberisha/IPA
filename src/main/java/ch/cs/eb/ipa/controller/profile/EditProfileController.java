package ch.cs.eb.ipa.controller.profile;

import ch.cs.eb.ipa.entity.*;
import ch.cs.eb.ipa.model.UserAuthority;
import ch.cs.eb.ipa.repository.CUserRepository;
import ch.cs.eb.ipa.util.UsernameFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EditProfileController {
    @Autowired
    CUserRepository userRepository;

    @RequestMapping("/edit_profile")
    public String editProfile(Model model) {
        UsernameFetcher usernameFetcher = new UsernameFetcher();
        CUser user = userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername()));

        if (userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())).getUser_authority().getRole().equals(UserAuthority.ADMIN)) {
            model.addAttribute("admin", "no relevance");
        }

        model.addAttribute("self", user);
        model.addAttribute("degreed", new Degreed());
        model.addAttribute("oreilly", new OReilly());
        model.addAttribute("ted", new Ted());
        model.addAttribute("udemy", new Udemy());
        model.addAttribute("w3schools", new W3schools());
        model.addAttribute("youtube", new Youtube());

        return "edit_profile";
    }
}
