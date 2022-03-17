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

/**
 * author: Elijon Berisha
 * date: 14.03.2022
 * class: RegisterController.java
 * purpose: REGISTRATION VALIDATION
 */

@Controller
public class RegisterController {
    // USER REPOSITORY IS INJECTED; NO NEED FOR CONSTRUCTOR
    @Autowired
    CUserRepository userRepository;
    // AUTHORITY REPOSITORY IS INJECTED; NO NEED FOR CONSTRUCTOR
    @Autowired
    UAuthorityRepository authorityRepository;

    @RequestMapping("/register")
    public String register(Model model) {
        // GETS CURRENT AUTHENTICATION; IF AUTHENTICATION IS NOT OF TYPE ANONYMOUS OR NOT EXISTENT, THE USER CAN ACCESS THE REGISTER TEMPLATE
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("user", new FormContainerUser());
            return "register";
        }
        // IF USER IS LOGGED IN, HE IS REDIRECTED TO THE /home MAPPING
        return "redirect:/home";
    }

    @PostMapping("/register")
    public String registerPost(Model model, @ModelAttribute("user") FormContainerUser user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        model.addAttribute("user", new FormContainerUser());

        // CTS ID INPUT VALIDATION VIA REGEX; ONLY NUMBERS ALLOWED; MUST BE EXACTLY 6 DIGITS IN LENGTH
        if (!user.getCts_id().trim().matches("^[0-9]{6}$")) {
            model.addAttribute("message", "Please enter a valid CTS-ID. The CTS-ID is your unique 6 digit employee ID.");
            return "register";
        }

        // FIRST NAME VALIDATION VIA REGEX; EITHER UPPER OR LOWER CASE LETTERS; LENGTH MUST BE 2 TO 255 CHARACTERS
        if (!user.getPrename().trim().matches("[a-zA-Z]{2,255}$")) {
            model.addAttribute("message", "Only Latin characters are accepted. Minimum of 2 characters required.");
            return "register";
        }

        // LAST NAME VALIDATION VIA REGEX; EITHER UPPER OR LOWER CASE LETTERS; LENGTH MUST BE 2 TO 255 CHARACTERS
        if (!user.getLastname().trim().matches("[a-zA-Z]{2,255}$")) {
            model.addAttribute("message", "Only Latin characters are accepted. Minimum of 2 characters required.");
            return "register";
        }

        // COGNIZANT MAIL VALIDATION VIA REGEX; MUST FOLLOW THIS FORMAT -> <name>.<name>@cognizant.com; LENGTH MUST BE AT LEAST 17 CHARACTERS
        if (!user.getEmail().trim().toLowerCase().matches("^[A-Za-z]{1,}\\.[A-Za-z]{1,}@cognizant\\.com$") || user.getEmail().trim().length() < 17 || user.getEmail().trim().length() > 255) {
            model.addAttribute("message", "Following format is accepted: <name>.<name>@cognizant.com. Minimum of 17 characters required.");
            return "register";
        }

        // PASSWORD VALIDATION VIA REGEX; 8 CHARACTERS IN LENGTH, 1 UPPER CASE LETTER, 1 LOWER CASE LETTER, 1 NUMBER, 1 SPECIAL CHARACTER (@, $, !, %, *, ?, &)
        if (!user.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            model.addAttribute("message", "Password must contain a minimum of eight characters, at least one upper case letter, one lower case letter, one number and one special character.");
            return "register";
        // PASSWORD CONFIRMATION MUST MATCH WITH PASSWORD INPUT
        } else if (!user.getPassword().equals(user.getRepeat_password())) {
            model.addAttribute("message", "Passwords do not match.");
            return "register";
        }

        // CHECKS IF USER IS ALREADY TAKEN
        if (userRepository.fetchAllUsers() != null && !userRepository.fetchAllUsers().isEmpty()) {
            if (userRepository.getByCtsId(Integer.parseInt(user.getCts_id())) != null) {
                // IF USER EXISTS IN DB WITH THE SAME CTS-ID INPUT; THE REGISTRATION PROCESS IS INVALIDATED
                if (userRepository.getByCtsId(Integer.parseInt(user.getCts_id())).getCts_id() == Integer.parseInt(user.getCts_id())) {
                    model.addAttribute("message", "User is already taken.");
                    return "register";
                }
            }
            // IF USER EXISTS IN DB WITH THE SAME COGNIZANT MAIL INPUT; THE REGISTRATION PROCESS IS INVALIDATED
            if (userRepository.getByEmail(user.getEmail()) != null) {
                if (userRepository.getByEmail(user.getEmail()).getEmail().equals(user.getEmail())) {
                    model.addAttribute("message", "User is already taken.");
                    return "register";
                }
            }
        }

        // CUser INSTANCE IS GENERATED
        CUser endUser = user.generateCUser();
        // PASSWORD IS ENCODED AND SET IN THE INSTANCE
        endUser.setPassword(encoder.encode(endUser.getPassword()));
        // DEFAULT INACTIVE ROLE IS GIVEN TO THE NEW USER
        endUser.setUser_authority(authorityRepository.getByAuthorityEnum(UserAuthority.INACTIVE));

        // USER IS CREATED AND INSERTED INTO THE DB
        userRepository.createUser(endUser);

        model.addAttribute("registration_success", "Registration successful! You can now log in.");
        return "login";
    }
}
