package com.flashcardapp.flashcard.controllers;


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

    private UserRepository userRepo;

    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/me")
    public String MyProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("logged_in", (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)));
        User user_n = userRepo.findOne(auth.getName());
        model.addAttribute("username", user_n.getUsername());
        model.addAttribute("flashsets", user_n.getFlashsetsStr());
        return "User";

    }

    @GetMapping("")
    public String User(@RequestParam("username") String username, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("logged_in", (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)));
        System.out.println("USERNAME " + username);
        User user_n = userRepo.findOne(username);
        if (user_n == null) {
            return "UserNotFound";
        }
        else {
            model.addAttribute("username", user_n.getUsername());
            model.addAttribute("flashsets", user_n.getFlashsetsStr());
            return "User";
        }
    }

    @GetMapping("/register")
    public String UserRegister() {
        return "Register";
    }

    @PostMapping("/register")
    public ModelAndView PostUserRegister(@RequestParam("username") String username, @RequestParam("password") String password ) {
        System.out.println("HHHbbbHHHbbbHHHaaaHHHaaa - - - - - . 0");
        User new_user = new User(username, password);
        userRepo.save(new_user);
        return new ModelAndView("redirect:/flashcard/User?username=" + username);
    }

    @GetMapping("/login")
    public String UserLogin() {
        return "Login";
    }
}
