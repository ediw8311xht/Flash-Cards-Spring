package com.flashcardapp.flashcard.controllers;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.flashcardapp.flashcard.repositories.FlashcardRepository;
import com.flashcardapp.flashcard.ent.Flashcardset;

@Controller
@RequestMapping("/flashcard")
public class FlashcardsetController {

    private FlashcardRepository flashcardRepo;

    public FlashcardsetController(FlashcardRepository flashcardRepo) {
        this.flashcardRepo = flashcardRepo;
    }

    @PostMapping("/makeNewFlashcardset")
    public ModelAndView makeFlashcardset(@RequestParam("name") String name) {
        String id = this.flashcardRepo.makeRandomId();
        Flashcardset new_fs = new Flashcardset(id, name);
        this.flashcardRepo.save(new_fs);
        return new ModelAndView("redirect:/flashcard/Flashcardset?id=" + id);
    }

    @GetMapping("/Flashcardset")
    public String getFlashcardset(@RequestParam("id") String id, Model model) {
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
        Flashcardset fcs = this.flashcardRepo.findOne(id);
        if (fcs != null) {
            model.addAttribute("flashcardset", fcs);
            return "SetEdit";
        }
        else {
            return "SetNotFound";
        }
    }

}
