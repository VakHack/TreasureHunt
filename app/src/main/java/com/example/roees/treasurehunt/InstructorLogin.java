package com.example.roees.treasurehunt;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class InstructorLogin extends AppCompatActivity {

    Button submit;
    EditText password;
    EditText email;
    final Context myContext = this;
    private GameDB db = FirebaseDB.getInstance();
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_screen);

        password = findViewById(R.id.password);
        password.setHint(db.getLanguageImp().enterPassword());
        if(db.getSavedUsername()!="") password.setText(db.getSavedPassword());

        email = findViewById(R.id.email);
        email.setHint(db.getLanguageImp().enterEmail());
        if(db.getSavedUsername()!="") email.setText(db.getSavedUsername());

        submit = findViewById(R.id.submit);
        submit.setText(db.getLanguageImp().submit());

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        final Intent instructorMapScreen = new Intent(InstructorLogin.this, InstructorMap.class);
        if(db.isInstructor()){
            db.login();
            startActivity(instructorMapScreen);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                db.instructorEntrance(email.getText().toString(),password.getText().toString());
                while(!db.downloadGame()){
                    try {
                        wait(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                startActivity(instructorMapScreen);
            }
        });
    }
}
