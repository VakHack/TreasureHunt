package com.example.roees.treasurehunt;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class InstructorLogin extends AppCompatActivity {

    Button submit;
    EditText password;
    EditText email;
    final Context myContext = this;
    private TreasureHuntDB db = FirebaseDB.getInstance();
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_screen);

        password = findViewById(R.id.password);
        password.setHint(db.getLanguageImp().enterPassword());
        if (db.getSavedUsername() != "") password.setText(db.getSavedPassword());

        email = findViewById(R.id.email);
        email.setHint(db.getLanguageImp().enterEmail());
        if (db.getSavedUsername() != "") email.setText(db.getSavedUsername());

        submit = findViewById(R.id.submit);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        final Intent instructorMapScreen = new Intent(InstructorLogin.this, InstructorMap.class);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    public void run() {
                        while (!db.login(email.getText().toString(), password.getText().toString())) {
                            SystemClock.sleep(1500);
                        }
                        while (!db.downloadGameData()) {
                            SystemClock.sleep(1500);
                        }
                        startActivity(instructorMapScreen);
                    }
                }).start();
            }
        });
    }
}
