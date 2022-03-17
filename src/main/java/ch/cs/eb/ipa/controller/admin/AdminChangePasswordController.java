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

/**
 * author: Elijon Berisha
 * date: 14.03.2022
 * class: AdminChangePasswordController.java
 */

@Controller
public class AdminChangePasswordController {
    // USER REPOSITORY IS INJECTED; NO NEED FOR CONSTRUCTOR
    @Autowired
    CUserRepository userRepository;

    @GetMapping("change_password_admin/{id}")
    public String changePasswordAdmin(Model model, @PathVariable("id") int id) {
        // USERNAME FETCHER IS INSTANTIATED
        UsernameFetcher usernameFetcher = new UsernameFetcher();
        // CONTAINER FOR THE INPUT DATA OF THE change_password FORM IS PREPARED
        FormContainerUser user = new FormContainerUser();
        // CURRENT USER IS FETCHED FORM THE DB
        CUser currentUser = userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername()));

        // IF USER IS ADMIN, THE ADMIN PROPERTY WILL BE SET IN THE MODEL
        if (currentUser.getUser_authority().getRole().equals(UserAuthority.ADMIN)) {
            model.addAttribute("admin", "no relevance");
        }

        // IF THE TARGET USER DOES NOT EXIST IN THE DB, THE USER IS REDIRECTED TO THE ADMIN PAGE
        if (userRepository.getByCtsId(id) == null) {
            return "redirect:/user_management";
        }

        // CTS ID OF THE FORM CONTAINER IS SET
        user.setCts_id(String.valueOf(userRepository.getByCtsId(id).getCts_id()));

        // EMPTY NON-ENTITY FORM CONTAINER USER INSTANCE IS PASSED TO THE MODEL
        model.addAttribute("user", user);
        // CURRENTLY LOGGED IN USER IS PASSED TO THE MODEL
        model.addAttribute("self", currentUser);
        // TARGET USER IS PASSED TO THE MODEL
        model.addAttribute("inactiveUser", userRepository.getByCtsId(id));

        return "change_password_admin";
    }

    @PostMapping("/change_password_admin/{id}")
    public String changePasswordAdmin(Model model, @ModelAttribute("user") FormContainerUser user, @PathVariable("id") int id) {
        // BCRYPT PASSWORD ENCODER IS INSTANTIATED; USED FOR PASSWORD ENCRYPTION AND DECRYPTION
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // USERNAME OF CURRENTLY LOGGED IN USER IS FETCHED
        String username = new UsernameFetcher().getUsername();
        // CURRENT USER IS FETCHED
        CUser currentUser = userRepository.getByCtsId(Integer.parseInt(username));
        // TARGET USER IS FETCHED
        CUser end_user = userRepository.getByCtsId(id);

        // FORM CONTAINER USER INSTANCE IS PASSED TO THE MODEL FOR TEMPLATE RENDERING
        model.addAttribute("user", user);
        // CURRENTLY LOGGED IN USER IS PASSED TO THE MODEL
        model.addAttribute("self", currentUser);

        // IF USER IS ADMIN, THE ADMIN PROPERTY WILL BE SET IN THE MODEL
        if (currentUser.getUser_authority().getRole().equals(UserAuthority.ADMIN)) {
            model.addAttribute("admin", "no relevance");
        }

        // INACTIVE USER IS PASSED TO THE MODEL; USER CAN BE ACTIVE AS WELL IN THIS INSTANCE
        model.addAttribute("inactiveUser", end_user);

        // PASSWORD VALIDATION VIA REGEX; 8 CHARACTERS IN LENGTH, 1 UPPER CASE LETTER, 1 LOWER CASE LETTER, 1 NUMBER, 1 SPECIAL CHARACTER (@, $, !, %, *, ?, &)
        if (!user.getNew_password().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            model.addAttribute("message", "New password must contain a minimum of eight characters, at least one upper case letter, one lower case letter, one number and one special character.");
            return "change_password_admin";
        // IF NEW PASSWORD DOES NOT MATCH WITH THE PASSWORD CONFIRMATION -> PROCESS IS INVALIDATED
        } else if (!user.getNew_password().equals(user.getRepeat_password())) {
            model.addAttribute("message", "Passwords do not match.");
            return "change_password_admin";
        }

        // PASSWORD OF TARGET USER IS ENCODED AND SET
        end_user.setPassword(encoder.encode(user.getNew_password()));
        // TARGET USER IS UPDATED IN THE DB
        userRepository.updateUser(end_user, end_user.getId());
        // SUCCESS MESSAGE IS PREPARED
        model.addAttribute("message_success", "Password of User " + end_user.getCts_id() + " has been successfully changed!");

        return "change_password_admin";
    }
}
