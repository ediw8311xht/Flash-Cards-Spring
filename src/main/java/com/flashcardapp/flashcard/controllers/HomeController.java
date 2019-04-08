package com.flashcardapp.flashcard.controllers;

import com.flashcardapp.flashcard.ent.User;
import com.flashcardapp.flashcard.ent.Flashcardset;
import java.util.ArrayList;
import com.flashcardapp.flashcard.repositories.FlashcardRepository;
import com.flashcardapp.flashcard.repositories.UserRepository;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/flashcard")
public class HomeController {

    private UserRepository userRepo;
    private FlashcardRepository flashRepo;

    public HomeController(UserRepository userRepo, FlashcardRepository flashRepo) {
        this.userRepo = userRepo;
        this.flashRepo = flashRepo;
    }

    @GetMapping("")
    public String FlashcardsetHome(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("logged_in", (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)));

        //"featured_set_user" is a user that is specifically made to only store featured flashcardsets.
        //This user should not create flashcardsets, should only copy sets from other users. 
        User feat_user =  userRepo.findOne("featured_set_user");
        if (feat_user == null) {
            System.out.println("Featured user does not exist. You should probably create one.");
            return "Home";
        }
        else {
            ArrayList<String> ft_sets = feat_user.getFlashsets();

            ArrayList<Flashcardset> featured_sets = new ArrayList<Flashcardset>();
            for (int i = 0; i < ft_sets.size(); i++) {
                featured_sets.add(this.flashRepo.findOne(ft_sets.get(i)));
            }
            model.addAttribute("featured_flashsets", featured_sets);
            return "Home";
        }
    }

    @GetMapping("/makeNewFlashcardset")
    public String FlashcardsetNew(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("logged_in", auth.isAuthenticated());
        return "NewSet";
    }

    @GetMapping("/getFlashcardset")
    public String getFlashcardset(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("logged_in", (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)));
        return "GetSet";
    }

}
