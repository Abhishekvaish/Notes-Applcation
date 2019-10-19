package com.example.notes;

import android.app.Application;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

public class ApplicationData extends Application {
    public  static ArrayList<Note> dataList ;  // to store all file names
    final String AllFiles ="com.example.notes.data";

    @Override
    public void onCreate() {
        super.onCreate();
        dataList= new ArrayList<Note>(0);
        loadData();
    }

    public void loadData() {
        File file = getApplicationContext().getFileStreamPath(AllFiles); //opening all files list
        if(file.exists())
        {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(AllFiles)));
                String string;
                while ((string=br.readLine()) != null)
                {
                    StringTokenizer tokenizer = new StringTokenizer(string,",");
                    String filename = tokenizer.nextToken();
                    String date = tokenizer.nextToken()+","+tokenizer.nextToken();
                    String encodedname = tokenizer.nextToken();
                    dataList.add(new Note(filename,date,encodedname));
                }
            }
            catch (IOException e)
            {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
