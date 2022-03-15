package ch.cs.eb.ipa.controller.password;

import ch.cs.eb.ipa.model.FormContainerUser;
import ch.cs.eb.ipa.model.UserAuthority;
import ch.cs.eb.ipa.repository.CUserRepository;
import ch.cs.eb.ipa.util.UsernameFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChangePasswordController {
    @Autowired
    CUserRepository userRepository;

    @RequestMapping("/change_password")
    public String changePassword(Model model) {
        UsernameFetcher usernameFetcher = new UsernameFetcher();

        if (userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())).getUser_authority().getRole().equals(UserAuthority.ADMIN)) {
            model.addAttribute("admin", "no relevance");
        }

        model.addAttribute("user", new FormContainerUser());
        model.addAttribute("self", userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())));
        
        return "change_password";
    }
}
