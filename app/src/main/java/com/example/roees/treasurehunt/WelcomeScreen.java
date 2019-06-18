package com.example.roees.treasurehunt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeScreen extends AppCompatActivity {

    Button joinGameButton;
    TextView joinGameText;
    Button instructor;
    TextView instructorText;
    Button enterCodeButton;
    EditText gameCodeLine;
    final Context myContext = this;
    private TreasureHuntDB db = FirebaseDB.getInstance();
    ProgressBar progressBar;
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

        //adding context to DB
        db.initContext(this);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        final Intent playerMap = new Intent(WelcomeScreen.this, PlayerMap.class);
        final LoadingHandler loadingHandler = new LoadingHandler(1500,
                5, db, myContext, playerMap, false, progressBar);

        joinGameButton = findViewById(R.id.joinGame);
        joinGameText = findViewById(R.id.joinGameText);
        joinGameText.setText(db.getLanguageImp().joinGame());
        joinGameText.setTextSize(TEXT_SIZE);
        joinGameText.setTypeface(Typeface.createFromAsset(getAssets(), BUTTONS_FONT));

        instructor = findViewById(R.id.instructorEntrance);
        instructorText = findViewById(R.id.instructorEntranceText);
        instructorText.setText(db.getLanguageImp().instructorEntrance());
        instructorText.setTextSize(TEXT_SIZE);
        instructorText.setTypeface(Typeface.createFromAsset(getAssets(), BUTTONS_FONT));

        gameCodeLine = findViewById(R.id.gameCodeLine);
        gameCodeLine.setHint(db.getLanguageImp().enterGameCode());
        enterCodeButton = findViewById(R.id.enterCode);
        enterCodeButton.setVisibility(View.INVISIBLE);

        final Intent instructorScreen = new Intent(WelcomeScreen.this, InstructorLogin.class);
        instructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(instructorScreen);
            }
        });

        if (db.getPlayerGameCode() != "") gameCodeLine.setText(db.getPlayerGameCode());
        joinGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameCodeLine.setVisibility(View.VISIBLE);
                joinGameText.setVisibility(View.INVISIBLE);
                enterCodeButton.setVisibility(View.VISIBLE);
            }
        });

        enterCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codeText = gameCodeLine.getText().toString();
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
