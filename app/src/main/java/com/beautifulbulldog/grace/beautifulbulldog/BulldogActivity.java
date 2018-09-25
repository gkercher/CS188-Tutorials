package com.beautifulbulldog.grace.beautifulbulldog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import io.realm.Realm;

public class BulldogActivity extends AppCompatActivity {

    private Button saveButton;
    private TextView selectedName;
    private TextView selectedAge;
    private TextView rating;
    private Realm realm;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulldog);

        saveButton = (Button) findViewById(R.id.save_button);
        selectedName = (TextView) findViewById(R.id.selected_name);
        selectedAge = (TextView) findViewById(R.id.selected_age);
        rating = (TextView) findViewById(R.id.bulldog_rating);
        realm = Realm.getDefaultInstance();

        String id = (String) getIntent().getStringExtra("bulldog");
        final Bulldog bulldog = realm.where(Bulldog.class).equalTo("id", id).findFirst();

        selectedName.setText(bulldog.getName());
        selectedAge.setText(bulldog.getAge());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        String username = (String) getIntent().getStringExtra("username");
                        user = realm.where(User.class).equalTo("username", username).findFirst();
                        Vote vote = new Vote();

                        vote.setRating(Integer.parseInt(String.valueOf(rating)));
                        vote.setBulldog(bulldog);
                        vote.setOwner(user);
                        realm.copyToRealmOrUpdate(vote);

                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra("rating", vote.getRating());
                        startActivity(intent);
                    }
                });
            }
        });

    }
}
