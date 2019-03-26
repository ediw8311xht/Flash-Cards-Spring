package com.flashcardapp.flashcard.ent;


import java.util.Arrays;
import java.util.ArrayList;

public class Flashcardset {

    private String id;

    private String name;

    //Flashcards are in format of String
    //with front and back seperated by a backslash (\)
    //i.e. "2 + 2\4"
    private ArrayList<String> flashcards = new ArrayList<String>();


    //-------------------------------------------------------------------------------------------//
    //-----------------------------------------------------------------CONSTRUCTORS--------------//
    //-------------------------------------------------------------------------------------------//

    //Id should not be picked by user.
    //Should be determined by repository to ensure uniqueness
    public Flashcardset(String id) {
        this.id = id;
        this.name = "untitled";
    }

    public Flashcardset(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Flashcardset(String id, String name, String flashcards) {
        this.id = id;
        this.name = name;
        setFlashcardsFromStr(flashcards);
    }


    //-------------------------------------------------------------------------------------------//
    //-----------------------------------------------------------------GETTERS-------------------//
    //-------------------------------------------------------------------------------------------//
    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<String> getFlashcards() {
        return this.flashcards;
    }

    //Returns flashcards as string,
    //flashcards seperated by commas,
    //and front and back of flashcards seperated by a backflash (\)
    //i.e. "2 + 2\4,9+9\18,22999*111000\who knows"
    public String getFlashcardsStr() {
        if (this.flashcards.size() <= 0) {
            return "";
        }
        else {
            return String.join("\\", this.flashcards);
        }
    }


    //-------------------------------------------------------------------------------------------//
    //-----------------------------------------------------------------SETTERS/ETC---------------//
    //-------------------------------------------------------------------------------------------//

    public void setName(String name) {
        this.name = name;
    }

    public void setFlashcards(ArrayList<String> flashcards) {
        this.flashcards = flashcards;
    }

    public void setFlashcardsFromStr(String flashset) {
        //Stupid convoluted mess is because split returns Array and needs to be ArrayList.
        this.flashcards = new ArrayList<String>(Arrays.asList(flashset.split(",")));
    }

    public void addToFlashcards(String flashcard) {
        this.flashcards.add(flashcard);
    }

    public void removeFlashcard(int position) {
        this.flashcards.remove(position);
    }
}
