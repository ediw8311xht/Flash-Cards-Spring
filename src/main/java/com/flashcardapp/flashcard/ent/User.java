package com.flashcardapp.flashcard.ent;

import java.util.Arrays;
import java.util.ArrayList;


public class User {

    private String username;
    private String password;
    private ArrayList<String> flashsets;
    private boolean enabled;

    //-------------------------------------------------------------------------------------------//
    //-----------------------------------------------------------------CONSTRUCTORS--------------//
    //-------------------------------------------------------------------------------------------//

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.flashsets = new ArrayList<String>();
        this.enabled = true;
    }

    public User(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.flashsets = new ArrayList<String>();
        this.enabled = enabled;
    }

    public User(String username, String password, String flashsets, boolean enabled) {
        this.username = username;
        this.password = password;
        setFlashsetsFromStr(flashsets);
        this.enabled = enabled;
    }

    //-------------------------------------------------------------------------------------------//
    //-----------------------------------------------------------------GETTERS-------------------//
    //-------------------------------------------------------------------------------------------//

    public String getFlashsetsStr() {
        return String.join(",", this.flashsets);
    }

    public ArrayList<String> getFlashsets() {
        return this.flashsets;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    //-------------------------------------------------------------------------------------------//
    //-----------------------------------------------------------------SETTERS/ETC---------------//
    //-------------------------------------------------------------------------------------------//

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setFlashsetsFromStr(String flashsets) {
        if (flashsets == "") {
            this.flashsets = new ArrayList<String>();
        }
        else {
            this.flashsets = new ArrayList<String>(Arrays.asList(flashsets.split(",")));
        }
    }

    public void addFlashset(String flashset) {
        this.flashsets.add(flashset);
    }

    public void removeFlashset(int position) {
        this.flashsets.remove(position);
    }


}
