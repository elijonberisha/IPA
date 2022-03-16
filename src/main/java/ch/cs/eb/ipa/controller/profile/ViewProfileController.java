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

@Controller
public class ViewProfileController {
    @Autowired
    CUserRepository userRepository;

    @GetMapping("/view_profile/{id}")
    public String viewProfile(@PathVariable("id") int id, Model model) {
        CUser currentUser = userRepository.getByCtsId(Integer.parseInt(new UsernameFetcher().getUsername()));

        List<String> udemyList = new ArrayList<>();
        List<String> tedList = new ArrayList<>();
        List<String> w3schoolsList = new ArrayList<>();

        if (currentUser.getUser_authority().getRole().equals(UserAuthority.ADMIN)) {
            model.addAttribute("admin", "no relevance");
        }

        if (userRepository.getByCtsId(id) == null) {
            return "redirect:/home";
        }

        CUser targetUser = userRepository.getByCtsId(id);

        model.addAttribute("self", currentUser);
        model.addAttribute("user", targetUser);
        model.addAttribute("role", StringUtils.capitalize(targetUser.getUser_authority().getRole().name()));

        if (targetUser.getYoutube() != null) {
            if (targetUser.getYoutube().getTutorialLink() != null && !targetUser.getYoutube().getTutorialLink().trim().equals("")) {
                model.addAttribute("youtube", targetUser.getYoutube().getTutorialLink());
            }
        }
        if (targetUser.getOreilly() != null) {
            if (targetUser.getOreilly().getBookLink() != null && !targetUser.getOreilly().getBookLink().trim().equals("")) {
                model.addAttribute("oreilly", targetUser.getOreilly().getBookLink());
            }
        }
        if (targetUser.getDegreed() != null) {
            if (targetUser.getDegreed().getArticleLink() != null && !targetUser.getDegreed().getArticleLink().trim().equals("")) {
                model.addAttribute("degreed", targetUser.getDegreed().getArticleLink());
            }
        }

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

        model.addAttribute("udemyList", udemyList);
        model.addAttribute("tedList", tedList);
        model.addAttribute("w3schoolsList", w3schoolsList);

        return "view_profile";
    }
}
