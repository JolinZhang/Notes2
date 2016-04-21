package com.example.jonelezhang.notes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;

public class write extends AppCompatActivity {
    EditText title;
    EditText content;
    Button reset;
    Button submit;

    String myFileName;
    String myFile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);
        // reset button
        reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText("");
                content.setText("");
            }
        });
        //save notes
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFileName = title.getText().toString()+".txt";
                myFile = content.getText().toString();
                try{
                    FileOutputStream fout = openFileOutput(myFileName, Context.MODE_PRIVATE);
                    fout.write(myFile.getBytes());
                    fout.close();
                    Toast.makeText(getBaseContext(), "file saved", Toast.LENGTH_SHORT).show();
                    //redirect to MainActivity.
                    startActivity(new Intent(write.this, MainActivity.class));
                }catch (Exception e){
                    e.printStackTrace();
                }



            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_write, menu);
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
