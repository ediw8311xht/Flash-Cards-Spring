package com.flashcardapp.flashcard.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/flashcard")
public class HomeController {

    @GetMapping("")
    public String FlashcardsetHome() {
        return "Home";
    }

    @GetMapping("/makeNewFlashcardset")
    public String FlashcardsetNew() {
        return "NewSet";
    }

    @GetMapping("/getFlashcardset")
    public String getFlashcardset() {
        return "GetSet";
    }

    @GetMapping("/getFlashcardsetEdit")
    public String getFlashcardsetEdit() {
        return "GetSetEdit";
    }

}
