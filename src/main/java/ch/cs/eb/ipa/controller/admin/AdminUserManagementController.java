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

/**
 * author: Elijon Berisha
 * date: 14.03.2022
 * class: AdminUserManagementController.java
 */

// THIS IS THE CENTRAL CONTROLLER FOR ADMINISTRATIVE TASKS
@Controller
public class AdminUserManagementController {
    // USER REPOSITORY IS INJECTED; NO CONSTRUCTOR NEEDED
    @Autowired
    CUserRepository userRepository;
    // AUTHORITY REPOSITORY IS INJECTED; NO CONSTRUCTOR NEEDED
    @Autowired
    UAuthorityRepository authorityRepository;

    // MAPPING FOR THE CENTRAL USER MANAGEMENT TEMPLATE
    @RequestMapping("/user_management")
    public String userManagement(Model model) {
        // USERNAME FETCHER IS INSTANTIATED
        UsernameFetcher usernameFetcher = new UsernameFetcher();
        // LIST OF ALL USERS IS FETCHED
        List<CUser> userList = userRepository.fetchAllUsers();
        // LIST FOR THE INACTIVE USERS IS FETCHED
        List<CUser> inactiveUsers = new ArrayList<>();

        // IF USER IS ADMIN, THE ADMIN PROPERTY IS SET IN THE MODEL
        if (userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())).getUser_authority().getRole().equals(UserAuthority.ADMIN)) {
            model.addAttribute("admin", "no relevance");
        }
        // CURRENTLY LOGGED IN USER IS PASSED TO THE MODEL
        model.addAttribute("self", userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())));
        // SEARCH CRITERIA CONTAINER IS PASSED TO THE MODEL
        model.addAttribute("criteria", new SearchCriteriaContainer());

        // INACTIVE USERS ARE SORTED FROM THE USER LIST
        for (CUser c : userList) {
            // IF USER HAS THE 'INACTIVE' ROLE -> USER IS ADDED TO THE INACTIVE USERS LIST
            if (c.getUser_authority().getRole().equals(UserAuthority.INACTIVE)) {
                inactiveUsers.add(c);
            }
        }

        // LIST OF INACTIVE USERS IS PASSED TO THE MODEL
        model.addAttribute("users", inactiveUsers);

        // IF THERE ARE NO INACTIVE USERS, MESSAGE CONTENT IS PREPARED AND PASSED TO THE MODEL
        if (inactiveUsers.isEmpty()) {
            model.addAttribute("message", "There are no inactive users.");
        }

        return "user_management";
    }

    // MAPPING TO ACTIVATE A USER; PATH VARIABLE ID IS REQUIRED
    @GetMapping("activate_profile/{id}")
    public String activateProfile(@PathVariable("id") int id, Model model) {
        // IF USER DOES NOT EXIST IN THE DB -> PROCESS IS INVALIDATED
        if (userRepository.getByCtsId(id) == null) {
            return "redirect:/user_management";
        }
        // USERNAME FETCHER IS INSTANTIATED
        UsernameFetcher usernameFetcher = new UsernameFetcher();

        // LIST OF INACTIVE USERS IS INSTANTIATED
        List<CUser> inactiveUsers = new ArrayList<>();

        // IF USER IS ADMIN, THE ADMIN PROPERTY IN THE MODEL WILL BE SET
        if (userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())).getUser_authority().getRole().equals(UserAuthority.ADMIN)) {
            model.addAttribute("admin", "no relevance");
        }
        // CURRENTLY LOGGED IN USER IS PASSED TO THE MODEL
        model.addAttribute("self", userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())));
        // SEARCH CRITERIA CONTAINER IS PASSED TO THE MODEL
        model.addAttribute("criteria", new SearchCriteriaContainer());

        // IF THE REQUESTED USER IS INACTIVE, THE USER WILL BE ACTIVATED
        if (userRepository.getByCtsId(id).getUser_authority().getRole().equals(UserAuthority.INACTIVE)) {
            // INACTIVE USER WILL BE FETCHED FROM THE DB
            CUser inactiveUser = userRepository.getByCtsId(id);
            // EMPLOYEE AUTHORITY IS SET FOR THE INACTIVE USER
            inactiveUser.setUser_authority(authorityRepository.getByAuthorityEnum(UserAuthority.EMPLOYEE));
            // THE NOW ACTIVE USER WILL BE UPDATED IN THE DB
            userRepository.updateUser(inactiveUser, inactiveUser.getId());
            // SUCCESS MESSAGE IS PREPARED AND PASSED TO THE MODEL
            model.addAttribute("activation", "User " + inactiveUser.getCts_id() + " has been activated!");
        } else {
            model.addAttribute("activation", "User is already activated.");
        }

        // ALL USERS ARE FETCHED FROM THE DB
        List<CUser> userList = userRepository.fetchAllUsers();
        // LIST OF INACTIVE USERS IS PREPARED FOR TEMPLATE RENDERING
        for (CUser c : userList) {
            if (c.getUser_authority().getRole().equals(UserAuthority.INACTIVE)) {
                inactiveUsers.add(c);
            }
        }
        // LIST OF INACTIVE USERS IS PASSED TO THE MODEL
        model.addAttribute("users", inactiveUsers);

        // IF THERE ARE NO INACTIVE USERS, MESSAGE WILL BE PREPARED ACCORDINGLY
        if (inactiveUsers.isEmpty()) {
            model.addAttribute("message", "There are no inactive users.");
            return "user_management";
        }

        return "user_management";
    }

    // MAPPING FOR SEARCHING USER
    @PostMapping("/search_user_admin")
    public String searchUserAdmin(Model model, @ModelAttribute("criteria") SearchCriteriaContainer criteria) {
        // USERNAME FETCHER IS INSTANTIATED; WILL BE USED TO FETCH CURRENT USER BY CTS_ID
        UsernameFetcher usernameFetcher = new UsernameFetcher();
        // ALL USERS ARE FETCHED FROM THE DB
        List<CUser> userList = userRepository.fetchAllUsers();
        // LIST OF INACTIVE USERS IS INSTANTIATED
        List<CUser> inactiveUsers = new ArrayList<>();
        // LIST FOR SEARCH RESULTS IS INSTANTIATED
        List<CUser> searchResults = new ArrayList<>();

        // SEARCH CRITERIA IS FETCHED AND SET TO LOWER CASE
        String searchTerm = criteria.getSearchTerm().toLowerCase();

        // IF USER IS ADMIN, THE ADMIN PROPERTY WILL BE SET IN THE MODEL
        if (userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())).getUser_authority().getRole().equals(UserAuthority.ADMIN)) {
            model.addAttribute("admin", "no relevance");
        }
        // CURRENTYLY LOGGED IN USER IS PASSED TO THE MODEL
        model.addAttribute("self", userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())));
        // SEARCH CRITERIA CONTAINER IS PREPARED AND PASSED TO THE MODEL
        model.addAttribute("criteria", new SearchCriteriaContainer());

        // INACTIVE USERS ARE FILTERED FROM THE USER LIST AND ADDED TO THE LIST OF INACTIVE USERS
        for (CUser c : userList) {
            if (c.getUser_authority().getRole().equals(UserAuthority.INACTIVE)) {
                inactiveUsers.add(c);
            }
        }
        // LIST OF INACTIVE USERS IS PASSED TO THE MODEL
        model.addAttribute("users", inactiveUsers);

        // IF THERE ARE NO INACTIVE USERS, MESSAGE WILL BE PREPARED ACCORDINGLY
        if (inactiveUsers.isEmpty()) {
            model.addAttribute("message", "There are no inactive users.");
        }

        // IF THERE ARE NO USERS IN THE DB, PROCESS WILL BE INVALIDATED
        if (userList == null || userList.isEmpty()) {
            return "redirect:/user_management";
        }
        // IF THE SEARCH TERM IS EMPTY, PROCESS WILL BE INVALIDATED
        if (searchTerm.trim().equals("")) {
            return "redirect:/user_management";
        }

        // LOOPS THROUGH ALL USERS IN DB
        for (CUser c : userList) {
            // A USER CANNOT SEARCH HIMSELF, THIS WOULD NOT MAKE SENSE
            if (c.getCts_id() != userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())).getCts_id()) {
                // CHECKS IF THE CTS-ID, FIRST NAME, LAST NAME AND COGNIZANT MAIL CONTAINS THE SEARCH TERM; IF YES -> USER WILL BE ADDED TO SEARCH RESULT LIST
                if (String.valueOf(c.getCts_id()).toLowerCase().contains(searchTerm) || String.valueOf(c.getEmail()).toLowerCase().contains(searchTerm) || String.valueOf(c.getLastname()).toLowerCase().contains(searchTerm) || String.valueOf(c.getPrename()).toLowerCase().contains(searchTerm)) {
                    searchResults.add(c);
                }
            }
        }

        // SEARCH RESULT MESSAGE WILL BE PREPARED ACCORDINGLY IF THERE ARE NO RESULTS
        if (searchResults.isEmpty()) {
            model.addAttribute("result", "Your search returned no results. Please reconsider your search criteria.");
        }

        // SEARCH RESULT LIST IS PASSED TO THE MODEL
        model.addAttribute("searchResults", searchResults);

        return "user_management";
    }
}
