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

    //Repositories to allow querying of user and flashcard repositories.
    private FlashcardRepository flashcardRepo;
    private UserRepository userRepo;

    //Arguments get automatically wired into controller by spring.
    public FlashcardsetController(FlashcardRepository flashcardRepo, UserRepository userRepo) {
        this.flashcardRepo = flashcardRepo;
        this.userRepo = userRepo;
    }

    @PostMapping("/makeNewFlashcardset")
    public ModelAndView makeFlashcardset(@RequestParam("name") String name) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //Doesn't check that the user actually exists, because
        //the SecurityConfig.java file already checks that user is logged in for this url.
        User ln_user = this.userRepo.findOne(auth.getName());

        //Id being created from .makeRandomId() to ensure uniquness
        //and that it is 15 characters and random.
        String id = this.flashcardRepo.makeRandomId();

        //Creating actual flashcardset, note flashcards are left off as an argument
        //Since this is a new flashcardset, their are no flashcards so that property can stay empty.
        Flashcardset new_fs = new Flashcardset(id, name, ln_user.getUsername());

        //Adding id of the new flashcardset to the user that created it. Updating database also.
        ln_user.addFlashset(id);
        this.userRepo.updateUserDetails(ln_user);

        //Saving actual flashcardset to database.
        this.flashcardRepo.save(new_fs);

        //Post requests sent by html forms should always redirect.
        return new ModelAndView("redirect:/flashcard/Flashcardset?id=" + id);
    }

    @GetMapping("/Flashcardset")
    public String getFlashcardset(@RequestParam("id") String id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //This line checks if user is logged in or not, and adds that to model attributes
        //so that the tempalte of navigation bar can show login/register or myprofile depending on if user is logged in or not.
        //Since spring sets user instance to an anonymous user if not logged in, we also have to check if auth equals that either.
        model.addAttribute("logged_in", (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)));
        Flashcardset fcs = this.flashcardRepo.findOne(id);

        //Making sure flashcardset with that id actually exists.
        if (fcs != null) {
            //Adds flashcardset to model so that template can get properties of flashcardset.
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

        //Making sure flashcardset with that id actually exists.
        if (fcs == null) {
            return "SetNotFound";
        }
        //Makes sure that the current logged in user actually owns the flashcardset, returns NotOwner template otherwise.
        else if (!fcs.getOwner().equals(auth.getName())) {
            return "NotOwner";
        }
        else {
            //Adds flashcardset to model so that template can get properties of flashcardset.
            model.addAttribute("flashcardset", fcs);
            return "SetEdit";
        }
    }

}
