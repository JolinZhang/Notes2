package com.example.jonelezhang.notes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import java.io.File;
import java.io.FileInputStream;

public class MainActivity extends AppCompatActivity {
    Button write;

    ListView listSavedFiles;
    String[] savedFiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        write = (Button) findViewById(R.id.button);
        listSavedFiles = (ListView) findViewById(R.id.listNotes);

        //link to write activity
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, write.class));
            }
        });
        //list savedFiles;
        ShowSavedFiles();


        //link to  view items
        listSavedFiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = listSavedFiles.getItemAtPosition(position).toString();
                    try {
                        File dirFiles = getDir("notes", MODE_PRIVATE);
                        File path = new File(dirFiles,title);
                        FileInputStream fin = new FileInputStream(path);
                        int c;
                        String temp = "";

                        while ((c = fin.read()) != -1) {
                            temp = temp + Character.toString((char) c);
                        }

//                    Toast.makeText(getBaseContext(), temp, Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(MainActivity.this, note.class));
//                    //link to view activities
                        Intent i = new Intent(MainActivity.this, note.class);
                        i.putExtra("title", title);
                        i.putExtra("content", temp);
                        startActivity(i);

                    } catch (Exception e) {
                    }

                }
        });
    }
    public void ShowSavedFiles(){
        File dirFiles = getDir("notes",MODE_PRIVATE);
        savedFiles = dirFiles.list();
        ArrayAdapter adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, savedFiles);
        listSavedFiles.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
