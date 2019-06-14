package com.example.roees.treasurehunt;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class WelcomeScreen extends AppCompatActivity {

    Button joinGame;
    Button instructor;
    EditText gameCode;
    final Context myContext = this;
    private GameDB db = FirebaseDB.getInstance();
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        //adding context to DB
        db.initContext(this);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

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

        if (db.getGameCode() != "") gameCode.setText(db.getGameCode());
        final Intent playerMap = new Intent(WelcomeScreen.this, PlayerMap.class);
        joinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String codeText = gameCode.getText().toString();
            if (!codeText.isEmpty()) {
                progressBar.setVisibility(View.VISIBLE);
                db.playerEntrance(codeText);
                new Thread(new Runnable() {
                    public void run() {
                        while (!db.downloadGame()) {
                            SystemClock.sleep(1500);
                        }
                        startActivity(playerMap);
                    }
                }).start();
            } else {
                Toast.makeText(myContext, FirebaseDB.getInstance().getLanguageImp().enterGameCode(), Toast.LENGTH_SHORT).show();
            }
            }
        });
    }
}
