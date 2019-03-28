package com.flashcardapp.flashcard.controllers;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.flashcardapp.flashcard.repositories.FlashcardRepository;
import com.flashcardapp.flashcard.ent.Flashcardset;

@Controller
@RequestMapping("/flashcard/ajax")
public class AjaxController {

    private FlashcardRepository flashcardRepo;

    public AjaxController(FlashcardRepository flashcardRepo) {
        this.flashcardRepo = flashcardRepo;
    }

    @RequestMapping(value = "/Flashcardset", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Flashcardset getFlashcardset(@RequestParam("id") String id) {
        Flashcardset fcs = this.flashcardRepo.findOne(id);
        return fcs;
    }

    @RequestMapping(value = "/Flashcardset/updateCards", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Flashcardset updateCardsFlashcardset(@RequestParam("id") String id, @RequestParam("flashcards") String flashcards) {
        Flashcardset fcs = this.flashcardRepo.findOne(id);
        fcs.setFlashcardsFromStr(flashcards);
        this.flashcardRepo.updateFlashcardset(fcs);
        return fcs;
    }

    @RequestMapping(value = "/Flashcardset/addCard", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Flashcardset addCardFlashcardset(@RequestParam("id") String id, @RequestParam("front") String frontF, @RequestParam("back") String backF) {
        System.out.println(id);
        System.out.println(frontF);
        System.out.println(backF);
        Flashcardset fcs = this.flashcardRepo.findOne(id);
        fcs.addToFlashcards(frontF + "\\" + backF);
        this.flashcardRepo.updateFlashcardset(fcs);
        System.out.println("flashcards");
        System.out.println(fcs.getFlashcardsStr());
        return fcs;
    }

}
