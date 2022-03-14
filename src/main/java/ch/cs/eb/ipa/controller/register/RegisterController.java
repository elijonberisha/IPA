package ch.cs.eb.ipa.controller.register;

import ch.cs.eb.ipa.entity.CUser;
import ch.cs.eb.ipa.model.FormContainerUser;
import ch.cs.eb.ipa.model.UserAuthority;
import ch.cs.eb.ipa.repository.CUserRepository;
import ch.cs.eb.ipa.repository.UAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegisterController {
    @Autowired
    CUserRepository userRepository;

    @Autowired
    UAuthorityRepository authorityRepository;

    @RequestMapping("/register")
    public String register(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("user", new FormContainerUser());
            return "register";
        }
        return "redirect:/home";
    }

    @PostMapping("/register")
    public String registerPost(Model model, @ModelAttribute("user") FormContainerUser user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        model.addAttribute("user", new FormContainerUser());

        if (!user.getCts_id().trim().matches("^[0-9]{6}$")) {
            model.addAttribute("message", "Please enter a valid CTS-ID.");
            return "register";
        }

        if (!user.getPrename().trim().matches("[a-zA-Z]{2,255}$")) {
            model.addAttribute("message", "First name must be 2 to 255 characters long. Only Latin characters are accepted.");
            return "register";
        }

        if (!user.getLastname().trim().matches("[a-zA-Z]{2,255}$")) {
            model.addAttribute("message", "Last name must be 2 to 255 characters long. Only Latin characters are accepted.");
            return "register";
        }

        if (!user.getEmail().trim().toLowerCase().matches("^[A-Za-z]{1,}\\.[A-Za-z]{1,}@cognizant\\.com$") || user.getEmail().trim().length() < 15 || user.getEmail().trim().length() > 255) {
            model.addAttribute("message", "Following format is accepted: <name>.<name>@cognizant.com. Length must be between 15 and 255 characters long.");
            return "register";
        }

        if (!user.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            model.addAttribute("message", "Password must contain a minimum of eight characters, at least one upper case letter, one lower case letter, one number and one special character.");
            return "register";
        } else if (!user.getPassword().equals(user.getRepeat_password())) {
            model.addAttribute("message", "Passwords do not match.");
            return "register";
        }

        if (userRepository.fetchAllUsers() != null && !userRepository.fetchAllUsers().isEmpty()) {
            if (userRepository.getByCtsId(Integer.parseInt(user.getCts_id())) != null) {
                if (userRepository.getByCtsId(Integer.parseInt(user.getCts_id())).getCts_id() == Integer.parseInt(user.getCts_id())) {
                    model.addAttribute("message", "User is already taken.");
                    return "register";
                }
            }
            if (userRepository.getByEmail(user.getEmail()) != null) {
                if (userRepository.getByEmail(user.getEmail()).getEmail().equals(user.getEmail())) {
                    model.addAttribute("message", "User is already taken.");
                    return "register";
                }
            }
        }

        CUser endUser = user.generateCUser();
        endUser.setPassword(encoder.encode(endUser.getPassword()));
        endUser.setUser_authority(authorityRepository.getByAuthorityEnum(UserAuthority.INACTIVE));

        userRepository.createUser(endUser);

        model.addAttribute("registration_success", "Registration successful! You can now log in.");
        return "login";
    }
}
