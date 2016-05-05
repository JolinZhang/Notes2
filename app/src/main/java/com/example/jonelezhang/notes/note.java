package com.example.jonelezhang.notes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;

public class note extends AppCompatActivity {
    TextView title;
    TextView content;
    TextView time;
    Button delete;
    ImageView photo;

    String temp1;
    String temp;

    File dir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        title = (TextView) findViewById(R.id.title);
        content = (TextView)findViewById(R.id.content);
        time = (TextView) findViewById(R.id.time);
        //get content
        Intent intent = getIntent();
        temp1  = intent.getStringExtra("title");
        temp = intent.getStringExtra("content");
        title.setText(temp1);
        content.setText(temp);
        //time
        dir = getFilesDir();
        Date lastModDate = new Date(dir.lastModified());
        time.setText(lastModDate.toString());
        //get photo
//        Context context = getApplicationContext();
//        try {
//            File f=new File("notes_photo", temp1+".jpg");
//            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
//            photo = (ImageView) findViewById(R.id.photo);
//            photo.setImageBitmap(b);
//        }
//        catch (FileNotFoundException e)
//        {
//            e.printStackTrace();
//        }printStackTrace
        //delete diary
        delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File file = new File(dir, temp1);
                boolean deleted = file.delete();
                startActivity(new Intent(note.this, MainActivity.class));

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
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
