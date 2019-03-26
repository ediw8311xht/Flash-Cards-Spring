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

    @Autowired
    private JdbcTemplate jdbc;

    public FlashcardRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public String makeRandomId() {
        String new_id = "";
        String valid_char[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".split("");

        System.out.println("HERE");
        do {
            System.out.println("nHERE");
            for (int i = 0; i < 14; i++) {
                new_id += valid_char[(int) Math.floor(Math.random() * valid_char.length)];
            }
        } while (this.findOne(new_id) != null);

        return new_id;
    }

    //Returns Flashcardset on successful save,
    //and null if a Flashcardset already exists with that id in database.
    public Flashcardset save(Flashcardset Flashcardset) {

        //Checks to make sure that Flashcardset id is unique.
        if (this.findOne(Flashcardset.getId()) != null) {
            return null;
        }
        else {
            jdbc.update("INSERT INTO Flashcardset (id, name, flashcards) VALUES (?, ?, ?)",
                        Flashcardset.getId(), Flashcardset.getName(), Flashcardset.getFlashcardsStr());
            return Flashcardset;
        }
    }

    public void updateFlashcardset(Flashcardset Flashcardset) {
            jdbc.update("UPDATE Flashcardset SET name = ?, flashcards = ? WHERE id = ?", Flashcardset.getName(), Flashcardset.getFlashcardsStr(), Flashcardset.getId());
    }

    public Flashcardset findOne(String id) {
        try {
            System.out.println(id);
            return jdbc.queryForObject("SELECT id, name, flashcards FROM Flashcardset WHERE id = ?", this::mapRowToFlashcardset, id);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No result found for that query.");
            return null;
        }

    }

    private Flashcardset mapRowToFlashcardset(ResultSet rs, int rowNum) throws SQLException {
        if (!rs.isBeforeFirst()) {
            System.out.println("HERE");
            return new Flashcardset(rs.getString("id"), rs.getString("name"), rs.getString("flashcards"));
        }
        else {
            System.out.println("no results found.");
            return null;
        }
    }


}
