package com.flashcardapp.flashcard.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.flashcardapp.flashcard.repositories.UserRepository;
import com.flashcardapp.flashcard.ent.User;
import com.flashcardapp.flashcard.repositories.FlashcardRepository;
import com.flashcardapp.flashcard.ent.Flashcardset;

@Controller
@RequestMapping("/flashcard")
public class FlashcardsetController {

    private FlashcardRepository flashcardRepo;
    private UserRepository userRepo;

    public FlashcardsetController(FlashcardRepository flashcardRepo, UserRepository userRepo) {
        this.flashcardRepo = flashcardRepo;
        this.userRepo = userRepo;
    }

    @PostMapping("/makeNewFlashcardset")
    public ModelAndView makeFlashcardset(@RequestParam("name") String name) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User ln_user = this.userRepo.findOne(auth.getName());

        String id = this.flashcardRepo.makeRandomId();
        Flashcardset new_fs = new Flashcardset(id, name, ln_user.getUsername());
        ln_user.addFlashset(id);
        this.userRepo.updateUserDetails(ln_user);
        this.flashcardRepo.save(new_fs);
        return new ModelAndView("redirect:/flashcard/Flashcardset?id=" + id);
    }

    @GetMapping("/Flashcardset")
    public String getFlashcardset(@RequestParam("id") String id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("logged_in", (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)));
        Flashcardset fcs = this.flashcardRepo.findOne(id);
        if (fcs != null) {
            model.addAttribute("flashcardset", fcs);
            return "Set";
        }
        else {
            return "SetNotFound";
        }
    }

    @GetMapping("/Flashcardset/edit")
    public String editFlashcardset(@RequestParam("id") String id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("logged_in", true);

        Flashcardset fcs = this.flashcardRepo.findOne(id);
        if (fcs == null) {
            return "SetNotFound";
        }
        else if (!fcs.getOwner().equals(auth.getName())) {
            return "NotOwner";
        }
        else {
            model.addAttribute("flashcardset", fcs);
            return "SetEdit";
        }
    }

}
