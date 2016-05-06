package com.example.jonelezhang.notes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.preference.DialogPreference;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class write extends AppCompatActivity {
    EditText title;
    EditText content;
    Button reset;
    Button submit;
    Button photo;
    ImageView viewImage;

    String myFileName;
    String myFile;
    Bitmap thumbnail;
    String label="0";



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
        // choose photo
        photo = (Button) findViewById( R.id.photo);
        //save the photo
        viewImage = (ImageView) findViewById(R.id.viewImage);
        photo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectImage();
                    }
                }
        );
        //save button
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFileName = title.getText().toString();
                myFile = content.getText().toString();
                try{
                    Context context = getApplicationContext();
                    //save the text
                    File notes = getDir("notes", Context.MODE_PRIVATE);
                    File myNotes = new File(notes,myFileName);
                    FileOutputStream fout = new FileOutputStream(myNotes);
                    fout.write(myFile.getBytes());
                    fout.close();
                    //save the picture
                    if(label.equals("1")) {
                        File notes_photo = context.getDir("notes_photo", Context.MODE_PRIVATE);
                        File myNotes_photo = new File(notes_photo, myFileName + ".jpg");
                        FileOutputStream fo = new FileOutputStream(myNotes_photo);
                        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, fo);
                        fo.close();
                        label="0";
                    }
                    Toast.makeText(getBaseContext(), "file saved", Toast.LENGTH_SHORT).show();
                    //redirect to MainActivity.
                    startActivity(new Intent(write.this, MainActivity.class));
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });



    }
    //AlertDialog choose photo
    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(write.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 1);
                } else if (items[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode== RESULT_OK){
            if(requestCode == 1){
                thumbnail = (Bitmap) data.getExtras().get("data");
                viewImage.setImageBitmap(thumbnail);
                label ="1";
            }else if(requestCode == 2){
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath=c.getString(columnIndex);
                c.close();
                Bitmap resized =(BitmapFactory.decodeFile(picturePath));
                thumbnail = Bitmap.createScaledBitmap(
                        resized, 96, 160, false);
                viewImage.setImageBitmap(thumbnail);
                label="1";
            }
        }
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
