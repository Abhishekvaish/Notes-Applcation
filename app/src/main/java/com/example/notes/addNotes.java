package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.UUID;


public class addNotes extends AppCompatActivity {
    EditText edData;
    final String AllFiles ="com.example.notes.data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("New Note");

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        edData = findViewById(R.id.editText);
        if (getIntent().getBooleanExtra("FromList",false))
        {
            try
            {
                Note o = ApplicationData.dataList.get(getIntent().getIntExtra("i",0));
                BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(o.getEncodedname()  )));
                String oneLine;
                edData.setText("");
                while( (oneLine = br.readLine()) != null)
                    edData.setText(edData.getText()+oneLine+"\n");
                br.close();
            }
            catch (IOException e)
            {
                Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
                edData.setText("ERROR 404 FIlE NOT FOUND "+e.getMessage());
            }

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.clearall:
                edData.setText("");
                break;
            case R.id.delete:
                if(getIntent().getBooleanExtra("FromList",false))
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setTitle("Delete current File ");
                    dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteFile();
                            finish();
                        }
                    });
                    dialog.setNegativeButton("No",null);
                    dialog.show();

                }


                break;
            case R.id.save:
                if(edData.getText().toString().trim().isEmpty())
                    Toast.makeText(this, "Note Cannot be Empty", Toast.LENGTH_SHORT).show();
                else {
                    if (getIntent().getBooleanExtra("FromList", false)) {
                        saveFile(getIntent().getIntExtra("i", 0));
                    } else {
                        String encodedname = UUID.randomUUID().toString();

                        ApplicationData.dataList.add(new Note(get_Name(),DateFormat.getDateInstance().format(new Date()),encodedname )); //FileName
                        saveAllFile();
                        //Toast.makeText(this, ApplicationData.dataList.get(ApplicationData.dataList.size()-1).getEncodedname(), Toast.LENGTH_SHORT).show();
                        saveFile(ApplicationData.dataList.size() - 1);
                    }
                    finish();
                    break;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveFile(int i) {
        Note o = ApplicationData.dataList.get(i);
        String encodedname = o.getEncodedname();             // File Name
        try
        {
            OutputStreamWriter opw = new OutputStreamWriter(openFileOutput(encodedname,MODE_PRIVATE));
            opw.write(edData.getText().toString().trim());
            opw.flush();
            opw.close();
            MainActivity.actionBar.setTitle(" Notes("+ApplicationData.dataList.size()+")");
            Toast.makeText(this, "File Saved", Toast.LENGTH_SHORT).show();
        }
        catch (IOException e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public String get_Name()
    {
       String Name="";
       int i = 0 , n= edData.getText().toString().trim().length();
       while (i<n && i<25 && edData.getText().toString().trim().charAt(i) !='\n')
       {
           Name+=edData.getText().toString().trim().charAt(i);
           i++;
       }
       if(i==25)
           Name+="...";
       return Name ;
    }

    public void saveAllFile()
    {
        try
        {
            OutputStreamWriter opw = new OutputStreamWriter(openFileOutput(AllFiles,MODE_PRIVATE));
            for (int i=0;i<ApplicationData.dataList.size();i++)
            {
                opw.write(ApplicationData.dataList.get(i).getFilename()+","+ApplicationData.dataList.get(i).getDate()+","+ApplicationData.dataList.get(i).getEncodedname()+ "\n");
            }
            opw.flush();
            opw.close();

            MainActivity.recyclerView.getAdapter().notifyDataSetChanged();

        }
        catch (IOException e)
        {
            Toast.makeText(this, e.getMessage() ,Toast.LENGTH_SHORT).show();
        }

    }

    public void deleteFile()
    {
        Note o = ApplicationData.dataList.get( getIntent().getIntExtra("i",0));
        ApplicationData.dataList.remove( o ) ;
        MainActivity.recyclerView.getAdapter().notifyDataSetChanged();
        MainActivity.actionBar.setTitle(" Notes("+ApplicationData.dataList.size()+")");
        saveAllFile();
        deleteFile(o.getEncodedname());
        Snackbar.make(MainActivity.recyclerView, "File Deleted", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }


}
