package com.flashcardapp.flashcard.repositories;


import java.sql.SQLException;
import java.sql.ResultSet;
import com.flashcardapp.flashcard.ent.Flashcardset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FlashcardRepository {

    //Used to encrypt password. Automagically provided by Spring @Autowired
    @Autowired
    private JdbcTemplate jdbc;

    public String makeRandomId() {
        String new_id = "";
        String valid_char[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".split("");

        System.out.println("HERE");
        do {
            System.out.println("nHERE");
            for (int i = 0; i < 15; i++) {
                new_id += valid_char[(int) Math.floor(Math.random() * valid_char.length)];
            }
        } while (this.findOne(new_id) != null);

        return new_id;
    }

    public boolean delete(String del_id) {
        if (this.findOne(del_id) == null) {
            return false;
        }
        else {
            this.jdbc.update("DELETE FROM flashcardset WHERE id = ?", del_id);
            return true;
        }
    }

    //Returns Flashcardset on successful save,
    //and null if a Flashcardset already exists with that id in database.
    public Flashcardset save(Flashcardset Flashcardset) {

        //Checks to make sure that Flashcardset id is unique.
        if (this.findOne(Flashcardset.getId()) != null) {
            return null;
        }
        else {
            this.jdbc.update("INSERT INTO flashcardset (id, name, flashcards, owner) VALUES (?, ?, ?, ?)",
                        Flashcardset.getId(), Flashcardset.getName(), Flashcardset.getFlashcardsStr(), Flashcardset.getOwner());
            return Flashcardset;
        }
    }

    public void updateFlashcardset(Flashcardset Flashcardset) {
            this.jdbc.update("UPDATE flashcardset SET name = ?, flashcards = ? WHERE id = ?", Flashcardset.getName(), Flashcardset.getFlashcardsStr(), Flashcardset.getId());
    }

    public Flashcardset findOne(String id) {
        try {
            return this.jdbc.queryForObject("SELECT id, name, flashcards, owner FROM Flashcardset WHERE id = ?", this::mapRowToFlashcardset, id);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No result found for that query.");
            return null;
        }

    }

    private Flashcardset mapRowToFlashcardset(ResultSet rs, int rowNum) throws SQLException {
        if (!rs.isBeforeFirst()) {
            return new Flashcardset(rs.getString("id"), rs.getString("name"), rs.getString("flashcards"), rs.getString("owner"));
        }
        else {
            System.out.println("no results found.");
            return null;
        }
    }


}
