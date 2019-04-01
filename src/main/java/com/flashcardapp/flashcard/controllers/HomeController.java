package com.flashcardapp.flashcard.controllers;

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

    @GetMapping("")
    public String FlashcardsetHome(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("logged_in", (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)));
        return "Home";
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

    @GetMapping("/getFlashcardsetEdit")
    public String getFlashcardsetEdit(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("logged_in", (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)));
        return "GetSetEdit";
    }

}
