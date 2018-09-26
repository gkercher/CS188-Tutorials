package com.beautifulbulldog.grace.beautifulbulldog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.ByteArrayOutputStream;

import io.realm.Realm;

public class NewBulldogActivity extends AppCompatActivity {

    private ImageButton bulldogImageButton;
    private EditText enteredName;
    private EditText enteredAge;
    private Button saveButton;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bulldog);

        bulldogImageButton = (ImageButton) findViewById(R.id.bulldog_image_button);
        enteredName = (EditText) findViewById(R.id.entered_name);
        enteredAge = (EditText) findViewById(R.id.entered_age);
        saveButton = (Button) findViewById(R.id.save_button);

        realm = Realm.getDefaultInstance();

        bulldogImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent,1);
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!enteredName.getText().toString().matches("")
                        && !enteredAge.getText().toString().matches("")
                    && bulldogImageButton.getDrawable() != null) {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Bulldog bulldog = new Bulldog();
                            bulldog.setAge(enteredAge.getText().toString());
                            bulldog.setName(enteredName.getText().toString());
                            bulldog.setId(realm.where(Bulldog.class).findAllSorted("id").last().getId() + 1);
                            BitmapDrawable image = (BitmapDrawable) bulldogImageButton.getDrawable();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            image.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] imageInBytes = baos.toByteArray();
                            bulldog.setImage(imageInBytes);

                            realm.copyToRealm(bulldog);
                            finish();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            bulldogImageButton.setImageBitmap(imageBitmap);
        }
    }
}
