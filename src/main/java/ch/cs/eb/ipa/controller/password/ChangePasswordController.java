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
    // USER REPOSITORY IS INJECTED; NO NEED FOR CONSTRUCTOR
    @Autowired
    CUserRepository userRepository;

    @RequestMapping("/change_password")
    public String changePassword(Model model) {
        // USERNAME FETCHER IS INSTANTIATED SO WE CAN HAVE ACCESS TO THE USERNAME OF THE CURRENT USER
        UsernameFetcher usernameFetcher = new UsernameFetcher();

        // IF USER IS ADMIN, THE ADMIN PROPERTY WILL BE SET IN THE MODEL
        if (userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())).getUser_authority().getRole().equals(UserAuthority.ADMIN)) {
            model.addAttribute("admin", "no relevance");
        }

        // EMPTY NON-ENTITY FormContainerUser INSTANCE WILL BE PASSED TO THE MODEL
        model.addAttribute("user", new FormContainerUser());
        // CURRENT USER WILL BE PASSED TO THE MODEL
        model.addAttribute("self", userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())));

        return "change_password";
    }

    @PostMapping("/change_password")
    public String changePassword(Model model, @ModelAttribute("user") FormContainerUser user) {
        // BCRYPT PASSWORD ENCODER IS INSTANTIATED; USED FOR PASSWORD ENCRYPTION AND DECRYPTION
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // USERNAME OF CURRENTLY LOGGED IN USER IS FETCHED
        String username = new UsernameFetcher().getUsername();
        // CURRENT USER IS FETCHED
        CUser currentUser = userRepository.getByCtsId(Integer.parseInt(username));

        // IF USER IS ADMIN, THE ADMIN PROPERTY WILL BE SET IN THE MODEL
        if (currentUser.getUser_authority().getRole().equals(UserAuthority.ADMIN)) {
            model.addAttribute("admin", "no relevance");
        }

        // EMPTY NON-ENTITY FormContainerUser INSTANCE WILL BE PASSED TO THE MODEL
        model.addAttribute("user", new FormContainerUser());
        // CURRENT USER WILL BE PASSED TO THE MODEL
        model.addAttribute("self", currentUser);

        // IF CURRENT PASSWORD INPUT DOES NOT MATCH WITH THE CURRENT PASSWORD -> PROCESS IS INVALIDATED
        if (!encoder.matches(user.getPassword(), currentUser.getPassword())) {
            model.addAttribute("message", "The current password does not match with your input.");
            return "change_password";
        }

        // PASSWORD VALIDATION VIA REGEX; 8 CHARACTERS IN LENGTH, 1 UPPER CASE LETTER, 1 LOWER CASE LETTER, 1 NUMBER, 1 SPECIAL CHARACTER (@, $, !, %, *, ?, &)
        if (!user.getNew_password().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            model.addAttribute("message", "New password must contain a minimum of eight characters, at least one upper case letter, one lower case letter, one number and one special character.");
            return "change_password";
        // IF NEW PASSWORD DOES NOT MATCH WITH THE PASSWORD CONFIRMATION -> PROCESS IS INVALIDATED
        } else if (!user.getNew_password().equals(user.getRepeat_password())) {
            model.addAttribute("message", "Your new passwords do not match.");
            return "change_password";
        // IF THE NEW PASSWORD IS THE SAME AS THE OLD PASSWORD -> PROCESS IS INVALIDATED
        } else if (encoder.matches(user.getNew_password(), currentUser.getPassword())) {
            model.addAttribute("message", "Your new password is identical to your old password. Please reconsider your input.");
            return "change_password";
        }

        // ENCODED PASSWORD IS SET FOR THE ENTITY OF THE CURRENT USER
        currentUser.setPassword(encoder.encode(user.getNew_password()));
        // CURRENT USER IS UPDATED IN THE DB
        userRepository.updateUser(currentUser, currentUser.getId());
        // SUCCESS MESSAGE IS PREPARED
        model.addAttribute("message_success", "Your password has been successfully changed!");

        return "change_password";
    }
}
