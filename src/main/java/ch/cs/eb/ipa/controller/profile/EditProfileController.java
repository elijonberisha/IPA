package ch.cs.eb.ipa.controller.profile;

import ch.cs.eb.ipa.entity.*;
import ch.cs.eb.ipa.model.UserAuthority;
import ch.cs.eb.ipa.repository.CUserRepository;
import ch.cs.eb.ipa.util.UrlChecker;
import ch.cs.eb.ipa.util.UsernameFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * author: Elijon Berisha
 * date: 15.03.2022
 * class: EditProfileController.java
 */

@Controller
public class EditProfileController {
    // USER REPOSITORY IS INJECTED; NO NEED FOR CONSTRUCTOR
    @Autowired
    CUserRepository userRepository;

    // MAPPING FOR edit_profile TEMPLATE
    @RequestMapping("/edit_profile")
    public String editProfile(Model model) {
        // USERNAME FETCHER IS PREPARED AND USED TO FETCH CURRENTLY LOGGED IN USER FROM DB
        UsernameFetcher usernameFetcher = new UsernameFetcher();
        CUser user = userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername()));

        // THYMELEAF ELEMENTS ARE PREPARED FOR THE TEMPLATE RENDERING
        generateThymeleafElements(model, user);

        return "edit_profile";
    }

    @PostMapping("/edit_profile")
    public String editProfile(Model model, @ModelAttribute("degreed") Degreed degreed, @ModelAttribute("oreilly") OReilly oreilly, @ModelAttribute("ted") Ted ted, @ModelAttribute("udemy") Udemy udemy, @ModelAttribute("w3schools") W3schools w3schools, @ModelAttribute("youtube") Youtube youtube) {
        // USERNAME CHECKER IS INSTANTIATED AND USED TO FETCH CURRENTLY LOGGED IN USER
        UsernameFetcher usernameFetcher = new UsernameFetcher();
        CUser user = userRepository.getByCtsId(Integer.parseInt(usernameFetcher.getUsername()));

        // URL CHECKER IS INSTANTIATED AND USED TO PERFORM A REGEX CHECK ON THE URL
        UrlChecker urlChecker = new UrlChecker();

        boolean inputExists = false;

        // THYMELEAF ELEMENTS ARE GENERATED FOR TEMPLATE RENDERING
        generateThymeleafElements(model, user);

        // CHECKS IF INPUT EXISTS; IF INPUT EXISTS AND IS VALID -> LINK IS UPDATED IN THE USER ENTITY
        if (degreed.getArticleLink() != null && !degreed.getArticleLink().trim().equals("") && degreed.getArticleLink().length() > 1) {
            inputExists = true;
            // IF THE INPUT URL DOES NOT THE REGEX PATTERN AND DOES NOT CONTAIN THE GIVEN DOMAIN NAME -> PROCESS IS INVALIDATED
            if (!urlChecker.isUrlRegex(degreed.getArticleLink()) || !degreed.getArticleLink().contains("degreed.com")) {
                model.addAttribute("message", "Your Degreed link is not a valid URL. Please reconsider your input.");
                return "edit_profile";
            } else if (user.getDegreed() != null) {
                user.getDegreed().setArticleLink(degreed.getArticleLink());
            } else if (user.getDegreed() == null) {
                user.setDegreed(degreed);
            }
        }

        // CHECKS IF INPUT EXISTS; IF INPUT EXISTS AND IS VALID -> LINK IS UPDATED IN THE USER ENTITY
        if (oreilly.getBookLink() != null && !oreilly.getBookLink().trim().equals("") && oreilly.getBookLink().length() > 1) {
            inputExists = true;
            // IF THE INPUT URL DOES NOT THE REGEX PATTERN AND DOES NOT CONTAIN THE GIVEN DOMAIN NAME -> PROCESS IS INVALIDATED
            if (!urlChecker.isUrlRegex(oreilly.getBookLink()) || !oreilly.getBookLink().contains("oreilly.com")) {
                model.addAttribute("message", "Your O'Reilly link is not a valid URL. Please reconsider your input.");
                return "edit_profile";
            } else if (user.getOreilly() != null) {
                user.getOreilly().setBookLink(oreilly.getBookLink());
            } else if (user.getOreilly() == null) {
                user.setOreilly(oreilly);
            }
        }

        // CHECKS IF INPUT EXISTS; IF INPUT EXISTS AND IS VALID -> LINK IS UPDATED IN THE USER ENTITY
        if (youtube.getTutorialLink() != null && !youtube.getTutorialLink().trim().equals("") && youtube.getTutorialLink().length() > 1) {
            inputExists = true;
            // IF THE INPUT URL DOES NOT THE REGEX PATTERN AND DOES NOT CONTAIN THE GIVEN DOMAIN NAME -> PROCESS IS INVALIDATED
            if (!urlChecker.isUrlRegex(youtube.getTutorialLink()) || !youtube.getTutorialLink().contains("youtube.com")) {
                model.addAttribute("message", "Your YouTube link is not a valid URL. Please reconsider your input.");
                return "edit_profile";
            } else if (user.getYoutube() != null) {
                user.getYoutube().setTutorialLink(youtube.getTutorialLink());
            } else if (user.getYoutube() == null) {
                user.setYoutube(youtube);
            }
        }

        // CHECKS IF INPUT EXISTS; IF INPUT EXISTS AND IS VALID -> LINK IS UPDATED IN THE USER ENTITY
        if (ted.getTalk1Link() != null && !ted.getTalk1Link().trim().equals("") && ted.getTalk1Link().length() > 1) {
            inputExists = true;
            // IF THE INPUT URL DOES NOT THE REGEX PATTERN AND DOES NOT CONTAIN THE GIVEN DOMAIN NAME -> PROCESS IS INVALIDATED
            if (!urlChecker.isUrlRegex(ted.getTalk1Link()) || !ted.getTalk1Link().contains("ted.com")) {
                model.addAttribute("message", "Your first TED link is not a valid URL. Please reconsider your input.");
                return "edit_profile";
            } else if (user.getTed() != null) {
                user.getTed().setTalk1Link(ted.getTalk1Link());
            } else if (user.getTed() == null) {
                user.setTed(ted);
            }
        }
        // CHECKS IF INPUT EXISTS; IF INPUT EXISTS AND IS VALID -> LINK IS UPDATED IN THE USER ENTITY
        if (ted.getTalk2Link() != null && !ted.getTalk2Link().trim().equals("") && ted.getTalk2Link().length() > 1) {
            inputExists = true;
            // IF THE INPUT URL DOES NOT THE REGEX PATTERN AND DOES NOT CONTAIN THE GIVEN DOMAIN NAME -> PROCESS IS INVALIDATED
            if (!urlChecker.isUrlRegex(ted.getTalk2Link()) || !ted.getTalk2Link().contains("ted.com")) {
                model.addAttribute("message", "Your second TED link is not a valid URL. Please reconsider your input.");
                return "edit_profile";
            } else if (user.getTed() != null) {
                user.getTed().setTalk2Link(ted.getTalk2Link());
            } else if (user.getTed() == null) {
                user.setTed(ted);
            }
        }
        // CHECKS IF INPUT EXISTS; IF INPUT EXISTS AND IS VALID -> LINK IS UPDATED IN THE USER ENTITY
        if (ted.getTalk3Link() != null && !ted.getTalk3Link().trim().equals("") && ted.getTalk3Link().length() > 1) {
            inputExists = true;
            // IF THE INPUT URL DOES NOT THE REGEX PATTERN AND DOES NOT CONTAIN THE GIVEN DOMAIN NAME -> PROCESS IS INVALIDATED
            if (!urlChecker.isUrlRegex(ted.getTalk3Link()) || !ted.getTalk3Link().contains("ted.com")) {
                model.addAttribute("message", "Your third TED link is not a valid URL. Please reconsider your input.");
                return "edit_profile";
            } else if (user.getTed() != null) {
                user.getTed().setTalk3Link(ted.getTalk3Link());
            } else if (user.getTed() == null) {
                user.setTed(ted);
            }
        }

        // CHECKS IF INPUT EXISTS; IF INPUT EXISTS AND IS VALID -> LINK IS UPDATED IN THE USER ENTITY
        if (w3schools.getArticle1Link() != null && !w3schools.getArticle1Link().trim().equals("") && w3schools.getArticle1Link().length() > 1) {
            inputExists = true;
            // IF THE INPUT URL DOES NOT THE REGEX PATTERN AND DOES NOT CONTAIN THE GIVEN DOMAIN NAME -> PROCESS IS INVALIDATED
            if (!urlChecker.isUrlRegex(w3schools.getArticle1Link()) || !w3schools.getArticle1Link().contains("w3schools.com")) {
                model.addAttribute("message", "Your first W3Schools link is not a valid URL. Please reconsider your input.");
                return "edit_profile";
            } else if (user.getW3schools() != null) {
                user.getW3schools().setArticle1Link(w3schools.getArticle1Link());
            } else if (user.getW3schools() == null) {
                user.setW3schools(w3schools);
            }
        }
        // CHECKS IF INPUT EXISTS; IF INPUT EXISTS AND IS VALID -> LINK IS UPDATED IN THE USER ENTITY
        if (w3schools.getArticle2Link() != null && !w3schools.getArticle2Link().trim().equals("") && w3schools.getArticle2Link().length() > 1) {
            inputExists = true;
            // IF THE INPUT URL DOES NOT THE REGEX PATTERN AND DOES NOT CONTAIN THE GIVEN DOMAIN NAME -> PROCESS IS INVALIDATED
            if (!urlChecker.isUrlRegex(w3schools.getArticle2Link()) || !w3schools.getArticle2Link().contains("w3schools.com")) {
                model.addAttribute("message", "Your second W3Schools link is not a valid URL. Please reconsider your input.");
                return "edit_profile";
            } else if (user.getW3schools() != null) {
                user.getW3schools().setArticle2Link(w3schools.getArticle2Link());
            } else if (user.getW3schools() == null) {
                user.setW3schools(w3schools);
            }
        }
        // CHECKS IF INPUT EXISTS; IF INPUT EXISTS AND IS VALID -> LINK IS UPDATED IN THE USER ENTITY
        if (w3schools.getArticle3Link() != null && !w3schools.getArticle3Link().trim().equals("") && w3schools.getArticle3Link().length() > 1) {
            inputExists = true;
            // IF THE INPUT URL DOES NOT THE REGEX PATTERN AND DOES NOT CONTAIN THE GIVEN DOMAIN NAME -> PROCESS IS INVALIDATED
            if (!urlChecker.isUrlRegex(w3schools.getArticle3Link()) || !w3schools.getArticle3Link().contains("w3schools.com")) {
                model.addAttribute("message", "Your third W3Schools link is not a valid URL. Please reconsider your input.");
                return "edit_profile";
            } else if (user.getW3schools() != null) {
                user.getW3schools().setArticle3Link(w3schools.getArticle3Link());
            } else if (user.getW3schools() == null) {
                user.setW3schools(w3schools);
            }
        }

        // CHECKS IF INPUT EXISTS; IF INPUT EXISTS AND IS VALID -> LINK IS UPDATED IN THE USER ENTITY
        if (udemy.getTraining1Link() != null && !udemy.getTraining1Link().trim().equals("") && udemy.getTraining1Link().length() > 1) {
            inputExists = true;
            // IF THE INPUT URL DOES NOT THE REGEX PATTERN AND DOES NOT CONTAIN THE GIVEN DOMAIN NAME -> PROCESS IS INVALIDATED
            if (!urlChecker.isUrlRegex(udemy.getTraining1Link()) || !udemy.getTraining1Link().contains("cognizant.udemy.com")) {
                model.addAttribute("message", "Your first Udemy link is not a valid URL. Please reconsider your input.");
                return "edit_profile";
            } else if (user.getUdemy() != null) {
                user.getUdemy().setTraining1Link(udemy.getTraining1Link());
            } else if (user.getUdemy() == null) {
                user.setUdemy(udemy);
            }
        }
        // CHECKS IF INPUT EXISTS; IF INPUT EXISTS AND IS VALID -> LINK IS UPDATED IN THE USER ENTITY
        if (udemy.getTraining2Link() != null && !udemy.getTraining2Link().trim().equals("") && udemy.getTraining2Link().length() > 1) {
            inputExists = true;
            // IF THE INPUT URL DOES NOT THE REGEX PATTERN AND DOES NOT CONTAIN THE GIVEN DOMAIN NAME -> PROCESS IS INVALIDATED
            if (!urlChecker.isUrlRegex(udemy.getTraining2Link()) || !udemy.getTraining2Link().contains("cognizant.udemy.com")) {
                model.addAttribute("message", "Your second Udemy link is not a valid URL. Please reconsider your input.");
                return "edit_profile";
            } else if (user.getUdemy() != null) {
                user.getUdemy().setTraining2Link(udemy.getTraining2Link());
            } else if (user.getUdemy() == null) {
                user.setUdemy(udemy);
            }
        }
        // CHECKS IF INPUT EXISTS; IF INPUT EXISTS AND IS VALID -> LINK IS UPDATED IN THE USER ENTITY
        if (udemy.getTraining3Link() != null && !udemy.getTraining3Link().trim().equals("") && udemy.getTraining3Link().length() > 1) {
            inputExists = true;
            // IF THE INPUT URL DOES NOT THE REGEX PATTERN AND DOES NOT CONTAIN THE GIVEN DOMAIN NAME -> PROCESS IS INVALIDATED
            if (!urlChecker.isUrlRegex(udemy.getTraining3Link()) || !udemy.getTraining3Link().contains("cognizant.udemy.com")) {
                model.addAttribute("message", "Your third Udemy link is not a valid URL. Please reconsider your input.");
                return "edit_profile";
            } else if (user.getUdemy() != null) {
                user.getUdemy().setTraining3Link(udemy.getTraining3Link());
            } else if (user.getUdemy() == null) {
                user.setUdemy(udemy);
            }
        }

        // IF ANY INPUT EXISTS; THE USER WILL BE UPDATED IN THE DB
        if (inputExists) {
            // USER IS UPDATED IN THE DB
            userRepository.updateUser(user, user.getId());
            model.addAttribute("message_success", "Your changes were successful!");
        }
        generateThymeleafElements(model, user);

        return "edit_profile";
    }

    public Model generateThymeleafElements(Model model, CUser user) {
        // IF USER IS ADMIN, THE ADMIN PROPERTY WILL BE SET IN THE MODEL
        if (user.getUser_authority().getRole().equals(UserAuthority.ADMIN)) {
            model.addAttribute("admin", "no relevance");
        }

        // CHECKS IF EACH ENTITY IS STORED IN THE USER ENTITY, IF YES -> ENTITY IS PASSED TO MODEL; IF NOT -> NEW ENTITY IS CREATED AND PASSED TO MODEL
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

        // CURRENTLY LOGGED IN USER IS PASSED TO MODEL
        model.addAttribute("self", user);

        return model;
    }
}
