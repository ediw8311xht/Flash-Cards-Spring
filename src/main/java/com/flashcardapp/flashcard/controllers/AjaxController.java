package com.flashcardapp.flashcard.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Flashcardset fcs = this.flashcardRepo.findOne(id);

        //Check that owner of set sent request.
        if (!fcs.getOwner().equals(auth.getName())) {
            return null;
        }
        else {
            fcs.setFlashcardsFromStr(flashcards);
            this.flashcardRepo.updateFlashcardset(fcs);
            return fcs;
        }
    }

    @RequestMapping(value = "/Flashcardset/addCard", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Flashcardset addCardFlashcardset(@RequestParam("id") String id, @RequestParam("front") String frontF, @RequestParam("back") String backF) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Flashcardset fcs = this.flashcardRepo.findOne(id);

        //Check that owner of set sent request.
        if (!fcs.getOwner().equals(auth.getName())) {
            return null;
        }
        else {
            fcs.addToFlashcards(frontF + "\\" + backF);
            this.flashcardRepo.updateFlashcardset(fcs);
            return fcs;
        }
    }

}
