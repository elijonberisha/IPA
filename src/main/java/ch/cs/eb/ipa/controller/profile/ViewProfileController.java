package ch.cs.eb.ipa.controller.profile;

import ch.cs.eb.ipa.entity.CUser;
import ch.cs.eb.ipa.model.UserAuthority;
import ch.cs.eb.ipa.repository.CUserRepository;
import ch.cs.eb.ipa.util.UsernameFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

/**
 * author: Elijon Berisha
 * date: 15.03.2022
 * class: ViewProfileController.java
 */

// CONTROLLER FOR THE view_profile TEMPLATE
@Controller
public class ViewProfileController {
    // USER REPOSITORY IS INJECTED; NO CONSTRUCTOR NEEDED
    @Autowired
    CUserRepository userRepository;

    // MAIN MAPPING OF THE VIEW PROFILE FUNCTIONALITY; PATH VARIABLE IS ACCEPTED
    @GetMapping("/view_profile/{id}")
    public String viewProfile(@PathVariable("id") int id, Model model) {
        // CURRENTLY LOGGED IN USER IS FETCHED FROM THE DB
        CUser currentUser = userRepository.getByCtsId(Integer.parseInt(new UsernameFetcher().getUsername()));

        // LIST OF UDEMY LINKS IS INSTANTIATED
        List<String> udemyList = new ArrayList<>();
        // LIST OF TED-TALK LINKS IS INSTANTIATED
        List<String> tedList = new ArrayList<>();
        // LIST OF W3SCHOOLS LINKS IS INSTANTIATED
        List<String> w3schoolsList = new ArrayList<>();

        // IF USER IS ADMIN, THE ADMIN PROPERTY WILL BE SET IN THE MODEL
        if (currentUser.getUser_authority().getRole().equals(UserAuthority.ADMIN)) {
            model.addAttribute("admin", "no relevance");
        }

        // IF THE REQUESTED USER DOES NOT EXISTS, PROCESS WILL BE INVALIDATED
        if (userRepository.getByCtsId(id) == null) {
            return "redirect:/home";
        }

        // TARGET USER IS FETCHED FROM THE DB
        CUser targetUser = userRepository.getByCtsId(id);

        // CURRENTLY LOGGED IN USER IS PASSED TO THE MODEL
        model.addAttribute("self", currentUser);
        // TARGET USER IS PASSED TO THE MODEL
        model.addAttribute("user", targetUser);
        // USER ROLE IS PASSED TO THE MODEL
        model.addAttribute("role", StringUtils.capitalize(targetUser.getUser_authority().getRole().name()));

        // IF YOUTUBE ENTITY IS LINKED TO USER AND LINK IS NOT EMPTY -> LINK WILL BE PASSED TO MODEL
        if (targetUser.getYoutube() != null) {
            if (targetUser.getYoutube().getTutorialLink() != null && !targetUser.getYoutube().getTutorialLink().trim().equals("")) {
                model.addAttribute("youtube", targetUser.getYoutube().getTutorialLink());
            }
        }
        // IF OREILLY ENTITY IS LINKED TO USER AND LINK IS NOT EMPTY -> LINK WILL BE PASSED TO MODEL
        if (targetUser.getOreilly() != null) {
            if (targetUser.getOreilly().getBookLink() != null && !targetUser.getOreilly().getBookLink().trim().equals("")) {
                model.addAttribute("oreilly", targetUser.getOreilly().getBookLink());
            }
        }
        // IF DEGREED ENTITY IS LINKED TO USER AND LINK IS NOT EMPTY -> LINK WILL BE PASSED TO MODEL
        if (targetUser.getDegreed() != null) {
            if (targetUser.getDegreed().getArticleLink() != null && !targetUser.getDegreed().getArticleLink().trim().equals("")) {
                model.addAttribute("degreed", targetUser.getDegreed().getArticleLink());
            }
        }

        // IF ENTITY IS LINKED TO USER AND LINK IS NOT EMPTY -> LINK WILL BE PASSED TO MODEL
        if (targetUser.getTed() != null) {
            if (targetUser.getTed().getTalk1Link() != null && !targetUser.getTed().getTalk1Link().trim().equals("")) {
                tedList.add(targetUser.getTed().getTalk1Link());
            }
            if (targetUser.getTed().getTalk2Link() != null && !targetUser.getTed().getTalk2Link().trim().equals("")) {
                tedList.add(targetUser.getTed().getTalk2Link());
            }
            if (targetUser.getTed().getTalk3Link() != null && !targetUser.getTed().getTalk3Link().trim().equals("")) {
                tedList.add(targetUser.getTed().getTalk3Link());
            }
        }

        // IF ENTITY IS LINKED TO USER AND LINK IS NOT EMPTY -> LINK WILL BE PASSED TO MODEL
        if (targetUser.getUdemy() != null) {
            if (targetUser.getUdemy().getTraining1Link() != null && !targetUser.getUdemy().getTraining1Link().trim().equals("")) {
                udemyList.add(targetUser.getTed().getTalk1Link());
            }
            if (targetUser.getUdemy().getTraining2Link() != null && !targetUser.getUdemy().getTraining2Link().trim().equals("")) {
                udemyList.add(targetUser.getUdemy().getTraining2Link());
            }
            if (targetUser.getUdemy().getTraining3Link() != null && !targetUser.getUdemy().getTraining3Link().trim().equals("")) {
                udemyList.add(targetUser.getUdemy().getTraining3Link());
            }
        }

        // IF ENTITY IS LINKED TO USER AND LINK IS NOT EMPTY -> LINK WILL BE PASSED TO MODEL
        if (targetUser.getW3schools() != null) {
            if (targetUser.getW3schools().getArticle1Link() != null && !targetUser.getW3schools().getArticle1Link().trim().equals("")) {
                w3schoolsList.add(targetUser.getW3schools().getArticle1Link());
            }
            if (targetUser.getW3schools().getArticle2Link() != null && !targetUser.getW3schools().getArticle2Link().trim().equals("")) {
                w3schoolsList.add(targetUser.getW3schools().getArticle2Link());
            }
            if (targetUser.getW3schools().getArticle3Link() != null && !targetUser.getW3schools().getArticle3Link().trim().equals("")) {
                w3schoolsList.add(targetUser.getW3schools().getArticle3Link());
            }
        }

        // LIST OF UDEMY LINKS IS PASSED TO THE MODEL
        model.addAttribute("udemyList", udemyList);
        // LIST OF TED TALK LINKS IS PASSED TO THE MODEL
        model.addAttribute("tedList", tedList);
        // LIST OF W3SCHOOLS LINKS IS PASSED TO THE MODEL
        model.addAttribute("w3schoolsList", w3schoolsList);

        return "view_profile";
    }
}
