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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        joinGame = findViewById(R.id.joinGame);
        joinGame.setText(HebrewImp.getInstance().joinGame());

        instructor = findViewById(R.id.instructorEntrance);
        instructor.setText(HebrewImp.getInstance().instructorEntrance());

        gameCode = findViewById(R.id.gameCode);
        gameCode.setHint(HebrewImp.getInstance().enterGameCode());

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
