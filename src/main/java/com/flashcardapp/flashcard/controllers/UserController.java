package com.flashcardapp.flashcard.controllers;


import com.flashcardapp.flashcard.repositories.FlashcardRepository;
import com.flashcardapp.flashcard.ent.Flashcardset;
import java.util.ArrayList;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import com.flashcardapp.flashcard.ent.User;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.flashcardapp.flashcard.repositories.UserRepository;

@Controller
@RequestMapping("/flashcard/User")
public class UserController {

    //Repositories to allow querying of user and flashcard repositories.
    private UserRepository userRepo;
    private FlashcardRepository flashcardRepo;

    //Arguments get automatically wired into controller by spring.
    public UserController(UserRepository userRepo, FlashcardRepository flashcardRepo) {
        this.userRepo = userRepo;
        this.flashcardRepo = flashcardRepo;
    }

    //-----------------------------------
    //Profile of a User specified by username
    //-----------------------------------
    @GetMapping("")
    public String User(@RequestParam("username") String username, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("logged_in", (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)));
        User user_n = userRepo.findOne(username);
        if (user_n == null) {
            return "UserNotFound";
        }
        else {
            model.addAttribute("username", user_n.getUsername());
            model.addAttribute("flashsets", user_n.getFlashsets());
            model.addAttribute("is_owner", false);
            return "User";
        }
    }

    //-----------------------------------
    //Profile of a User currently logged in
    //-----------------------------------
    @GetMapping("/me")
    public String MyProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //Check if logged in. If not, redirects to login page.
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            model.addAttribute("logged_in", false);
            return "redirect:/flashcard/User/login";
        }

        model.addAttribute("logged_in", true);

        User user_n = userRepo.findOne(auth.getName());
        model.addAttribute("username", user_n.getUsername());
        model.addAttribute("flashsets", user_n.getFlashsets());
        model.addAttribute("is_owner", true);
        return "User";

    }

    @GetMapping("/register")
    public String UserRegister() {
        return "Register";
    }

    @PostMapping("/register")
    public ModelAndView PostUserRegister(@RequestParam("username") String username, @RequestParam("password") String password ) {
        User new_user = new User(username, password);
        userRepo.save(new_user);
        return new ModelAndView("redirect:/flashcard/User?username=" + username);
    }

    @GetMapping("/login")
    public String UserLogin() {
        return "Login";
    }

    @GetMapping("/myFlashsets")
    public String getFlashcardsetEdit(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("logged_in", (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)));
        User ln_user = userRepo.findOne(auth.getName());
        ArrayList<Flashcardset> inftc = new ArrayList<Flashcardset>();
        ArrayList<String> ftc = ln_user.getFlashsets();
        for (String c : ftc) {
            inftc.add(this.flashcardRepo.findOne(c));
        }
        model.addAttribute("flashsets", inftc);
        return "MyFlashsets";
    }

}
