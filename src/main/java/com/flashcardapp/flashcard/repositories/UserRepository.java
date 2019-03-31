package com.flashcardapp.flashcard.repositories;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.flashcardapp.flashcard.ent.User;
import java.sql.SQLException;
import java.sql.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    //Used to encrypt password
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //Used for database querying
    @Autowired
    private JdbcTemplate jdbc;

    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    //Returns true on successful save,
    //and false if a User already exists with that username in database.
    public boolean save(User user) {

        //Checks to make sure that User id is unique.
        if (this.findOne(user.getUsername()) != null) {
            return false;
        }
        else {
            jdbc.update("INSERT INTO Users (username, password, flashsets, enabled) VALUES (?, ?, ?, ?)",
                        user.getUsername(), this.passwordEncoder.encode(user.getPassword()), user.getFlashsetsStr(), user.getEnabled());
            return true;
        }
    }

    public void updateUserDetails(User user) {
        jdbc.update("UPDATE Users SET flashsets = ?, enabled = ? WHERE username = ?", user.getFlashsetsStr(), user.getEnabled(), user.getUsername());
    }

    public void updateUserPassword(User user) {
        jdbc.update("UPDATE Users SET password = ? WHERE username = ?", this.passwordEncoder.encode(user.getPassword()), user.getUsername());
    }

    public User findOne(String username) {
        try {
            return jdbc.queryForObject("SELECT username, password, flashsets, enabled FROM Users WHERE username = ?", this::mapRowToUser, username);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No result found for that query.");
            return null;
        }

    }

    private User mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        if (!rs.isBeforeFirst()) {
            return new User(rs.getString("username"), rs.getString("password"), rs.getString("flashsets"), rs.getBoolean("enabled"));
        }
        else {
            System.out.println("no results found.");
            return null;
        }
    }


}
