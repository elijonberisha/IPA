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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
        }

        return "user_management";
    }

    @GetMapping("activate_profile/{id}")
    public String activateProfile(@PathVariable("id") int id, Model model) {
        if (userRepository.getByCtsId(id) == null) {
            return "redirect:/user_management";
        }

        UsernameFetcher usernameFetcher = new UsernameFetcher();

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

        List<CUser> userList = userRepository.fetchAllUsers();
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

    @PostMapping("/search_user_admin")
    public String searchUserAdmin(Model model, @ModelAttribute("criteria") SearchCriteriaContainer criteria) {
        UsernameFetcher usernameFetcher = new UsernameFetcher();

        List<CUser> userList = userRepository.fetchAllUsers();
        List<CUser> inactiveUsers = new ArrayList<>();
        List<CUser> searchResults = new ArrayList<>();

        String searchTerm = criteria.getSearchTerm().toLowerCase();

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
        }

        if (userList == null || userList.isEmpty()) {
            return "redirect:/user_management";
        }

        if (searchTerm.trim().equals("")) {
            return "redirect:/user_management";
        }

        for (CUser c : userList) {
            if (c.getCts_id() != userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())).getCts_id()) {
                if (String.valueOf(c.getCts_id()).toLowerCase().contains(searchTerm) || String.valueOf(c.getEmail()).toLowerCase().contains(searchTerm) || String.valueOf(c.getLastname()).toLowerCase().contains(searchTerm) || String.valueOf(c.getPrename()).toLowerCase().contains(searchTerm)) {
                    searchResults.add(c);
                }
            }
        }

        if (searchResults.isEmpty()) {
            model.addAttribute("result", "Your search returned no results. Please reconsider your input");
        }

        model.addAttribute("searchResults", searchResults);

        return "user_management";
    }
}
