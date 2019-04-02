package com.flashcardapp.flashcard.ent;

import java.util.Arrays;
import java.util.ArrayList;


public class User {

    private String username;
    private String password;
    private ArrayList<String> flashsets = new ArrayList<String>();
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

    public User(String username, String password, String flsets, boolean enabled) {
        this.username = username;
        this.password = password;
        setFlashsetsFromStr(flsets);
        this.enabled = enabled;
    }

    //-------------------------------------------------------------------------------------------//
    //-----------------------------------------------------------------GETTERS-------------------//
    //-------------------------------------------------------------------------------------------//

    public String getFlashsetsStr() {

        if (this.flashsets.size() == 0) {
            return "";
        }
        else if (this.flashsets.size() == 1) {
            System.out.println("S");
            System.out.println(this.flashsets);
            System.out.println("END");
            return this.flashsets.get(0);
        }
        else {
            return String.join(",", this.flashsets);
        }

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

    public void setFlashsetsFromStr(String flsets) {

        //Makes sure that it does not set empty value
        if (!flsets.equals("")) {
            this.flashsets = new ArrayList<String>(Arrays.asList(flsets.split(",")));
        }
        
    }

    public void addFlashset(String flashset) {
        this.flashsets.add(flashset);
    }

    public void removeFlashset(int position) {
        this.flashsets.remove(position);
    }


}
