package com.example.roees.treasurehunt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeScreen extends AppCompatActivity {

    Button joinGame;
    TextView joinGameText;
    Button instructor;
    TextView instructorText;
    EditText gameCode;
    final Context myContext = this;
    private TreasureHuntDB db = FirebaseDB.getInstance();
    ProgressBar progressBar;
    boolean joinButtonFlag = false;
    final private String BUTTONS_FONT = "fonts/Nehama.ttf";
    final private int TEXT_SIZE = 40;

    public void showToast(final String toast)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(myContext, toast, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        final Intent playerMap = new Intent(WelcomeScreen.this, PlayerMap.class);
        final LoadingHandler loadingHandler = new LoadingHandler(1000,
                4, db, myContext, playerMap, false);

        //adding context to DB
        db.initContext(this);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        joinGame = findViewById(R.id.joinGame);
        joinGameText = findViewById(R.id.joinGameText);
        joinGameText.setText(db.getLanguageImp().joinGame());
        joinGameText.setTextSize(TEXT_SIZE);
        joinGameText.setTypeface(Typeface.createFromAsset(getAssets(), BUTTONS_FONT));

        instructor = findViewById(R.id.instructorEntrance);
        instructorText = findViewById(R.id.instructorEntranceText);
        instructorText.setText(db.getLanguageImp().instructorEntrance());
        instructorText.setTextSize(TEXT_SIZE);
        instructorText.setTypeface(Typeface.createFromAsset(getAssets(), BUTTONS_FONT));

        gameCode = findViewById(R.id.gameCode);
        gameCode.setHint(db.getLanguageImp().enterGameCode());

        final Intent instructorScreen = new Intent(WelcomeScreen.this, InstructorLogin.class);
        instructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(instructorScreen);
            }
        });

        if (db.getPlayerGameCode() != "") gameCode.setText(db.getPlayerGameCode());
        joinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String codeText = gameCode.getText().toString();
                if (!codeText.isEmpty()) {
                    db.setPlayerGameCode(codeText);
                    db.playerEntrance(codeText);
                    loadingHandler.waitUntilLoaded(FirebaseDB.getInstance().getLanguageImp().wrongCode());
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(myContext, FirebaseDB.getInstance().getLanguageImp().enterGameCode(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
