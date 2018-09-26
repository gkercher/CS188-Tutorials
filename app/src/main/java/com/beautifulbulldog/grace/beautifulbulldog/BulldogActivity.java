package com.beautifulbulldog.grace.beautifulbulldog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import io.realm.Realm;

public class BulldogActivity extends AppCompatActivity {

    private Button voteButton;
    private ImageView bulldogImage;
    private TextView selectedName;
    private TextView selectedAge;
    private TextView rating;
    private Realm realm;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulldog);

        voteButton = (Button) findViewById(R.id.vote_button);
        bulldogImage = (ImageView) findViewById(R.id.bulldog_image);
        selectedName = (TextView) findViewById(R.id.selected_name);
        selectedAge = (TextView) findViewById(R.id.selected_age);
        rating = (TextView) findViewById(R.id.bulldog_rating);
        realm = Realm.getDefaultInstance();

        String id = (String) getIntent().getStringExtra("bulldog");
        final Bulldog bulldog = realm.where(Bulldog.class).equalTo("id", id).findFirst();

        selectedName.setText(bulldog.getName());
        selectedAge.setText(bulldog.getAge());
        if(bulldog.getImage() != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(bulldog.getImage(), 0,
                    bulldog.getImage().length);
            bulldogImage.setImageBitmap(bmp);
        }

        voteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        String username = (String) getIntent().getStringExtra("username");
                        user = realm.where(User.class).equalTo("username", username).findFirst();
                        Vote vote = new Vote();

                        vote.setRating(Integer.parseInt(rating.getText().toString()));
                        vote.setBulldog(bulldog);
                        vote.setOwner(user);
                        realm.copyToRealm(vote);

                        finish();
                    }
                });
            }
        });

    }
}
