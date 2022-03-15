package ch.cs.eb.ipa.controller.admin;

import ch.cs.eb.ipa.entity.CUser;
import ch.cs.eb.ipa.model.FormContainerUser;
import ch.cs.eb.ipa.model.UserAuthority;
import ch.cs.eb.ipa.repository.CUserRepository;
import ch.cs.eb.ipa.util.UsernameFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminChangePasswordController {
    @Autowired
    CUserRepository userRepository;

    @GetMapping("change_password_admin/{id}")
    public String changePasswordAdmin(Model model, @PathVariable("id") int id) {
        UsernameFetcher usernameFetcher = new UsernameFetcher();
        FormContainerUser user = new FormContainerUser();
        CUser currentUser = userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername()));

        if (currentUser.getUser_authority().getRole().equals(UserAuthority.ADMIN)) {
            model.addAttribute("admin", "no relevance");
        }

        if (userRepository.getByCtsId(id) == null) {
            return "redirect:/user_management";
        }

        user.setCts_id(String.valueOf(userRepository.getByCtsId(id).getCts_id()));

        model.addAttribute("user", user);
        model.addAttribute("self", currentUser);
        model.addAttribute("inactiveUser", userRepository.getByCtsId(id));

        return "change_password_admin";
    }

    @PostMapping("/change_password_admin/{id}")
    public String changePasswordAdmin(Model model, @ModelAttribute("user") FormContainerUser user, @PathVariable("id") int id) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String username = new UsernameFetcher().getUsername();
        CUser currentUser = userRepository.getByCtsId(Integer.parseInt(username));
        CUser end_user = userRepository.getByCtsId(id);

        model.addAttribute("user", user);
        model.addAttribute("self", currentUser);

        if (currentUser.getUser_authority().getRole().equals(UserAuthority.ADMIN)) {
            model.addAttribute("admin", "no relevance");
        }

        model.addAttribute("inactiveUser", end_user);

        if (!user.getNew_password().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            model.addAttribute("message", "New password must contain a minimum of eight characters, at least one upper case letter, one lower case letter, one number and one special character.");
            return "change_password_admin";
        } else if (!user.getNew_password().equals(user.getRepeat_password())) {
            model.addAttribute("message", "Passwords do not match.");
            return "change_password_admin";
        }

        end_user.setPassword(encoder.encode(user.getNew_password()));
        userRepository.updateUser(end_user, end_user.getId());

        model.addAttribute("message_success", "Password of User " + end_user.getCts_id() + " has been successfully changed!");

        return "change_password_admin";
    }
}
