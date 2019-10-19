package com.example.notes;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity implements ListAdapter.FileSelected {
    public static RecyclerView recyclerView;
    public static ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar =getSupportActionBar();
        actionBar.setIcon(R.drawable.assignment);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(" Notes("+ApplicationData.dataList.size()+")");


        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ListAdapter(this ,ApplicationData.dataList));
        recyclerView.setHasFixedSize(true);


    }


    public void newNotes(View view)
    {
        Intent intent =  new Intent(MainActivity.this,addNotes.class);
        startActivity(intent);
    }


    @Override
    public void OnFileSelected(Note o) {
        Intent intent =  new Intent(MainActivity.this,addNotes.class);
        intent.putExtra("FromList",true);
        intent.putExtra("i",ApplicationData.dataList.indexOf(o));
        startActivity(intent);
    }
}
