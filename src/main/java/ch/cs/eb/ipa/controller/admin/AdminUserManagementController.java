package ch.cs.eb.ipa.controller.admin;

import ch.cs.eb.ipa.entity.CUser;
import ch.cs.eb.ipa.model.SearchCriteriaContainer;
import ch.cs.eb.ipa.model.UserAuthority;
import ch.cs.eb.ipa.repository.CUserRepository;
import ch.cs.eb.ipa.repository.UAuthorityRepository;
import ch.cs.eb.ipa.util.UsernameFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminUserManagementController {
    @Autowired
    CUserRepository userRepository;

    @Autowired
    UAuthorityRepository authorityRepository;

    @RequestMapping("/user_management")
    public String userManagement(Model model) {
        UsernameFetcher usernameFetcher = new UsernameFetcher();

        List<CUser> userList = userRepository.fetchAllUsers();
        List<CUser> inactiveUsers = new ArrayList<>();

        if (userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())).getUser_authority().getRole().equals(UserAuthority.ADMIN)) {
            model.addAttribute("admin", "no relevance");
        }
        model.addAttribute("self", userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())));
        model.addAttribute("criteria", new SearchCriteriaContainer());

        for (CUser c : userList) {
            if (c.getUser_authority().getRole().equals(UserAuthority.INACTIVE)) {
                inactiveUsers.add(c);
            }
        }

        model.addAttribute("users", inactiveUsers);

        if (inactiveUsers.isEmpty()) {
            model.addAttribute("message", "There are no inactive users.");
            return "user_management";
        }

        return "user_management";
    }

    @GetMapping("activate_profile/{id}")
    public String activateProfile(@PathVariable("id") int id, Model model) {
        if (userRepository.getByCtsId(id) == null) {
            return "redirect:/user_management";
        }

        UsernameFetcher usernameFetcher = new UsernameFetcher();

        List<CUser> userList = userRepository.fetchAllUsers();
        List<CUser> inactiveUsers = new ArrayList<>();

        if (userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())).getUser_authority().getRole().equals(UserAuthority.ADMIN)) {
            model.addAttribute("admin", "no relevance");
        }
        model.addAttribute("self", userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())));
        model.addAttribute("criteria", new SearchCriteriaContainer());

        if (userRepository.getByCtsId(id).getUser_authority().getRole().equals(UserAuthority.INACTIVE)) {
            CUser inactiveUser = userRepository.getByCtsId(id);
            inactiveUser.setUser_authority(authorityRepository.getByAuthorityEnum(UserAuthority.EMPLOYEE));
            userRepository.updateUser(inactiveUser, inactiveUser.getId());
            model.addAttribute("activation", "User " + inactiveUser.getCts_id() + " has been activated!");
        } else {
            model.addAttribute("activation", "User is already activated.");
        }

        for (CUser c : userList) {
            if (c.getUser_authority().getRole().equals(UserAuthority.INACTIVE)) {
                inactiveUsers.add(c);
            }
        }

        model.addAttribute("users", inactiveUsers);

        if (inactiveUsers.isEmpty()) {
            model.addAttribute("message", "There are no inactive users.");
            return "user_management";
        }

        return "user_management";
    }
}
