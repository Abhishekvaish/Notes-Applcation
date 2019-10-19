package com.example.notes;

import java.util.Date;

public class Note {
    private String filename , date ,encodedname;

    public Note(String filename, String date ,String encodedname) {
        this.filename = filename;
        this.date = date;
        this.encodedname = encodedname;

    }

    public String getFilename() {
        return filename;
    }

    public String getEncodedname(){return encodedname;}

    public String getDate() {
        return date;
    }
}
