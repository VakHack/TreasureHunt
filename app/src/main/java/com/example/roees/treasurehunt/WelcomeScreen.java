package com.example.roees.treasurehunt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WelcomeScreen extends AppCompatActivity {

    Button joinGame;
    Button instructor;
    EditText gameCode;
    private GameDB db = FirebaseDB.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        //adding context to DB
        db.initContext(this);

        final Intent instructorMapScreen = new Intent(WelcomeScreen.this, InstructorMap.class);
        //if already logged jump to the relevant screen
        if(db.isLoggedIn())
            if(db.isInstructor())
                startActivity(instructorMapScreen);

        joinGame = findViewById(R.id.joinGame);
        joinGame.setText(db.getLanguageImp().joinGame());

        instructor = findViewById(R.id.instructorEntrance);
        instructor.setText(db.getLanguageImp().instructorEntrance());

        gameCode = findViewById(R.id.gameCode);
        gameCode.setHint(db.getLanguageImp().enterGameCode());

        final Intent instructorScreen = new Intent(WelcomeScreen.this, InstructorLogin.class);
        instructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(instructorScreen);
            }
        });

        joinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        gameCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
