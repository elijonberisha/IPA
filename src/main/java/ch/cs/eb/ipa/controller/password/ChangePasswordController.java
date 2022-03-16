package ch.cs.eb.ipa.controller.password;

import ch.cs.eb.ipa.entity.CUser;
import ch.cs.eb.ipa.model.FormContainerUser;
import ch.cs.eb.ipa.model.UserAuthority;
import ch.cs.eb.ipa.repository.CUserRepository;
import ch.cs.eb.ipa.util.UsernameFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * author: Elijon Berisha
 * date: 15.03.2022
 * class: ChangePasswordController.java
 */

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

    @PostMapping("/change_password")
    public String changePassword(Model model, @ModelAttribute("user") FormContainerUser user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String username = new UsernameFetcher().getUsername();
        CUser currentUser = userRepository.getByCtsId(Integer.parseInt(username));

        if (currentUser.getUser_authority().getRole().equals(UserAuthority.ADMIN)) {
            model.addAttribute("admin", "no relevance");
        }

        model.addAttribute("user", new FormContainerUser());
        model.addAttribute("self", currentUser);

        if (!encoder.matches(user.getPassword(), currentUser.getPassword())) {
            model.addAttribute("message", "The current password does not match with your input.");
            return "change_password";
        }

        if (!user.getNew_password().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            model.addAttribute("message", "New password must contain a minimum of eight characters, at least one upper case letter, one lower case letter, one number and one special character.");
            return "change_password";
        } else if (!user.getNew_password().equals(user.getRepeat_password())) {
            model.addAttribute("message", "Your new passwords do not match.");
            return "change_password";
        } else if (encoder.matches(user.getNew_password(), currentUser.getPassword())) {
            model.addAttribute("message", "Your new password is identical to your old password. Please reconsider your input.");
            return "change_password";
        }

        currentUser.setPassword(encoder.encode(user.getNew_password()));
        userRepository.updateUser(currentUser, currentUser.getId());
        model.addAttribute("message_success", "Your password has been successfully changed!");

        return "change_password";
    }
}
