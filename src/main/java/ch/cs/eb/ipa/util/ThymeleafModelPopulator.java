package ch.cs.eb.ipa.util;

import ch.cs.eb.ipa.entity.*;
import ch.cs.eb.ipa.model.FormContainerUser;
import ch.cs.eb.ipa.model.SearchCriteriaContainer;
import ch.cs.eb.ipa.model.UserAuthority;
import ch.cs.eb.ipa.repository.CUserRepository;
import ch.cs.eb.ipa.repository.UAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ThymeleafModelPopulator {
    @Autowired
    CUserRepository userRepository;

    @Autowired
    UAuthorityRepository authorityRepository;

    public boolean isUserAdmin(CUser user) {
        if (user.getUser_authority().getRole().equals(UserAuthority.ADMIN)) {
            return true;
        } else {
            return false;
        }
    }

    public Model generateEditProfileModel(Model model, CUser user) {
        if (isUserAdmin(user)) {
            model.addAttribute("admin", "no relevance");
        }

        if (user.getDegreed() != null) {
            model.addAttribute("degreed", user.getDegreed());
        } else {
            model.addAttribute("degreed", new Degreed());
        }
        if (user.getOreilly() != null) {
            model.addAttribute("oreilly", user.getOreilly());
        } else {
            model.addAttribute("oreilly", new OReilly());
        }
        if (user.getYoutube() != null) {
            model.addAttribute("youtube", user.getYoutube());
        } else {
            model.addAttribute("youtube", new Youtube());
        }
        if (user.getUdemy() != null) {
            model.addAttribute("udemy", user.getUdemy());
        } else {
            model.addAttribute("udemy", new Udemy());
        }
        if (user.getW3schools() != null) {
            model.addAttribute("w3schools", user.getW3schools());
        } else {
            model.addAttribute("w3schools", new W3schools());
        }
        if (user.getTed() != null) {
            model.addAttribute("ted", user.getTed());
        } else {
            model.addAttribute("ted", new Ted());
        }

        model.addAttribute("self", user);

        return model;
    }

    public Model generateHomeModel(Model model, CUser user) {
        List<CUser> userList = userRepository.fetchAllUsers();
        Collections.shuffle(userList);
        if (userList.size() > 10) {
            userList.stream().limit(10).collect(Collectors.toList());
        }

        if (isUserAdmin(user)) {
            model.addAttribute("admin", "no relevance");
        }

        model.addAttribute("self", user);
        model.addAttribute("criteria", new SearchCriteriaContainer());
        model.addAttribute("users", userList);

        if (userList.isEmpty()) {
            model.addAttribute("message", "No people have been found.");
        }
        return model;
    }

    public Model generateViewProfileModel(Model model, CUser user, CUser targetUser, List<String> udemyList, List<String> tedList, List<String> w3schoolsList) {
        if (isUserAdmin(user)) {
            model.addAttribute("admin", "no relevance");
        }

        model.addAttribute("self", user);
        model.addAttribute("user", targetUser);
        model.addAttribute("role", StringUtils.capitalize(targetUser.getUser_authority().getRole().name()));
        model.addAttribute("udemyList", udemyList);
        model.addAttribute("tedList", tedList);
        model.addAttribute("w3schoolsList", w3schoolsList);

        return model;
    }

    public Model generateErrMessageModel(Model model, CUser user) {
        if (isUserAdmin(user)) {
            model.addAttribute("admin", "no relevance");
        }
        model.addAttribute("self", "no relevance");
        return model;
    }

    public Model generateChangePwdModel(Model model, CUser user) {
        if (isUserAdmin(user)) {
            model.addAttribute("admin", "no relevance");
        }
        model.addAttribute("user", new FormContainerUser());
        model.addAttribute("self", user);
        return model;
    }

    public Model generateUserManagementModel() {

    }
}
