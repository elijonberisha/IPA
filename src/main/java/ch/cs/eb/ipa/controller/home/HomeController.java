package ch.cs.eb.ipa.controller.home;

import ch.cs.eb.ipa.entity.CUser;
import ch.cs.eb.ipa.model.SearchCriteriaContainer;
import ch.cs.eb.ipa.model.UserAuthority;
import ch.cs.eb.ipa.repository.CUserRepository;
import ch.cs.eb.ipa.util.UsernameFetcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    @Autowired
    CUserRepository userRepository;

    @RequestMapping("/home")
    public String landingPage(Model model) {
        UsernameFetcher usernameFetcher = new UsernameFetcher();

        List<CUser> userList = userRepository.fetchAllUsers();
        Collections.shuffle(userList);
        if (userList.size() > 10) {
            userList.stream().limit(10).collect(Collectors.toList());
        }

        if (userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())).getUser_authority().getRole().equals(UserAuthority.ADMIN)) {
            model.addAttribute("admin", "no relevance");
        }

        model.addAttribute("self", userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername())));
        model.addAttribute("criteria", new SearchCriteriaContainer());
        model.addAttribute("users", userList);

        if (userList == null || userList.isEmpty()) {
            model.addAttribute("message", "No people have been found.");
            return "home";
        }

        return "home";
    }
}
