package ch.cs.eb.ipa.controller.home;

import ch.cs.eb.ipa.entity.CUser;
import ch.cs.eb.ipa.model.SearchCriteriaContainer;
import ch.cs.eb.ipa.model.UserAuthority;
import ch.cs.eb.ipa.repository.CUserRepository;
import ch.cs.eb.ipa.util.UsernameFetcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author: Elijon Berisha
 * date: 14.03.2022
 * class: HomeController.java
 */

// MAIN CONTROLLER FOR HOME PAGE
@Controller
public class HomeController {
    // USER REPOSITORY IS INJECTED; NO NEED FOR CONSTRUCTOR
    @Autowired
    CUserRepository userRepository;

    // MAIN MAPPING; /home IS THE MAPPING FOR THE LANDINGPAGE
    @RequestMapping("/home")
    public String landingPage(Model model) {
        // USERNAME OF CURRENT USER IS FETCHED
        UsernameFetcher usernameFetcher = new UsernameFetcher();

        // ALL USERS ARE FETCHED FROM THE DB
        List<CUser> userList = userRepository.fetchAllUsers();
        // LIST IS RANDOMIZED
        Collections.shuffle(userList);
        // LIST SIZE IS LIMITED TO 10
        if (userList.size() > 10) {
            userList.stream().limit(10).collect(Collectors.toList());
        }

        // IF USER IS ADMIN, THE ADMIN PROPERTY IN THE MODEL WILL BE SET
        if (userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())).getUser_authority().getRole().equals(UserAuthority.ADMIN)) {
            model.addAttribute("admin", "no relevance");
        }

        // CURRENT USER IS PASSED TO THE MODEL AS A PROPERTY
        model.addAttribute("self", userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())));
        // SEARCH CRITERIA CONTAINER IS PASSED TO THE MODEL AS A PROPERTY
        model.addAttribute("criteria", new SearchCriteriaContainer());
        // USER LIST IS PASSED TO THE MODEL AS A PROPERTY
        model.addAttribute("users", userList);

        // ERROR MESSAGE IS ADDED TO MODEL IF THERE ARE NO USERS IN THE DB
        if (userList == null || userList.isEmpty()) {
            model.addAttribute("message", "No people have been found.");
            return "home";
        }

        return "home";
    }

    // POST MAPPING FOR THE SEARCH BAR IN THE home TEMPLATE
    @PostMapping("/search_user")
    public String searchUser(Model model, @ModelAttribute("criteria") SearchCriteriaContainer criteria) {
        // CURRENT USER IS FETCHED FROM THE DB USING THE USERNAME FETCHER AND USER REPOSITORY
        UsernameFetcher usernameFetcher = new UsernameFetcher();
        CUser currentUser = userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername()));

        // LIST THAT WILL CONTAIN THE SEARCH RESULTS IS INSTANTIATED
        List<CUser> searchResults = new ArrayList<>();
        // SEARCH TERM WILL BE CONVERTED TO LOWER CASE
        String searchTerm = criteria.getSearchTerm().toLowerCase();

        // LIST OF USERS FROM THE DB IS FETCHED
        List<CUser> userList = userRepository.fetchAllUsers();
        // LIST IS RANDOMIZED
        Collections.shuffle(userList);
        // LIST SIZE IS LIMITED TO 10
        if (userList.size() > 10) {
            userList.stream().limit(10).collect(Collectors.toList());
        }

        // IF USER IS ADMIN, THE ADMIN PROPERTY WILL BE SET IN THE MODEL
        if (userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())).getUser_authority().getRole().equals(UserAuthority.ADMIN)) {
            model.addAttribute("admin", "no relevance");
        }

        // IF THE USER LIST IS EMPTY, THERE IS NO POINT IN CONTINUING THE SEARCH PROCESS
        if (userList == null || userList.isEmpty()) {
            return "redirect:/home";
        }

        // IF THE SEARCH TERM IS EMPTY, THERE IS NO POINT IN CONTINUING THE SEARCH PROCESS
        if (searchTerm.trim().equals("")) {
            return "redirect:/home";
        }

        // CURRENT USER IS PASSED TO THE MODEL AS A PROPERTY
        model.addAttribute("self", currentUser);
        // USER LIST IS PASSED TO THE MODEL AS A PROPERTY
        model.addAttribute("users", userList);
        // SEARCH CRITERIA CONTAINER IS PASSED TO THE MODEL AS A PROPERTY
        model.addAttribute("criteria", new SearchCriteriaContainer());

        // LOOPS THROUGH ALL USERS IN THE USER LIST
        for (CUser c : userList) {
            // A USER CANNOT SEARCH HIMSELF, THIS WOULD NOT MAKE SENSE
            if (c.getCts_id() != currentUser.getCts_id()) {
                // CHECKS IF THE CTS-ID, FIRST NAME, LAST NAME AND COGNIZANT MAIL CONTAINS THE SEARCH TERM; IF YES -> USER WILL BE ADDED TO SEARCH RESULT LIST
                if (String.valueOf(c.getCts_id()).toLowerCase().contains(searchTerm) || String.valueOf(c.getEmail()).toLowerCase().contains(searchTerm) || String.valueOf(c.getLastname()).toLowerCase().contains(searchTerm) || String.valueOf(c.getPrename()).toLowerCase().contains(searchTerm)) {
                    searchResults.add(c);
                }
            }
        }

        // IF THERE ARE NO SEARCH RESULTS, USER WILL BE INFORMED ACCORDINGLY
        if (searchResults.isEmpty()) {
            model.addAttribute("result", "Your search returned no results. Please reconsider your search criteria.");
        }

        // SEARCH RESULT LIST IS RETURNED
        model.addAttribute("searchResults", searchResults);

        return "home";
    }
}
