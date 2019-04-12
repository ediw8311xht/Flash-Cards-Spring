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

    //This is the username of the owner of flashcardset.
    private String owner;
    //-------------------------------------------------------------------------------------------//
    //-----------------------------------------------------------------CONSTRUCTORS--------------//
    //-------------------------------------------------------------------------------------------//

    //Id should not be picked by user.
    //Should be determined by repository to ensure uniqueness
    public Flashcardset(String id, String owner) {
        this.id = id;
        this.name = "untitled";
        this.owner = owner;
    }

    public Flashcardset(String id, String name, String owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    public Flashcardset(String id, String name, String flashcards, String owner) {
        this.id = id;
        this.name = name;
        setFlashcardsFromStr(flashcards);
        this.owner = owner;
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
        System.out.println("SIZE SIZE SIZE");
        System.out.println(this.flashcards.size());
        if (this.flashcards.size() <= 0) {
            return "";
        }
        else if (this.flashcards.size() == 1) {
            return this.flashcards.get(0);
        }

        else {
            return String.join(",", this.flashcards);
        }
    }

    public String getOwner() {
        return this.owner;
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

        //If statement ensures that arraylist does not add an empty value if String is empty.
        if (!flashset.equals("")) {
            //Stupid convoluted line mess is because split returns Array and needs to be ArrayList.
            this.flashcards = new ArrayList<String>(Arrays.asList(flashset.split(",")));
        }

    }

    public void addToFlashcards(String flashcard) {
        this.flashcards.add(flashcard);
    }

    public void removeFlashcard(int position) {
        this.flashcards.remove(position);
    }
}
