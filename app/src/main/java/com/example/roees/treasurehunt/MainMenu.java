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

public class MainMenu extends AppCompatActivity {

    Button joinGameButton;
    TextView joinGameText;
    Button instructorButton;
    TextView instructorText;
    Button enterCodeButton;
    EditText gameCodeLine;
    boolean joinGameLineDisplayed = false;
    final Context myContext = this;
    private TreasureHuntDB db = FirebaseDB.getInstance();
    ProgressBar progressBar;
    final private String BUTTONS_FONT = db.getLanguageImp() instanceof HebrewImp ? "fonts/GveretLevin.ttf" : "fonts/PatrickHand.ttf";
    final private int TEXT_SIZE = db.getLanguageImp() instanceof HebrewImp ? 30 : 25;
    private final int INTERVAL = 2000;
    private final int MAX_NUM_OF_INTERVALS = 5;
    private final int MAX_WAIT_DURATION = INTERVAL * MAX_NUM_OF_INTERVALS;

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

        joinGameButton = findViewById(R.id.joinGame);
        joinGameText = findViewById(R.id.joinGameText);
        joinGameText.setText(db.getLanguageImp().joinGame());
        joinGameText.setTextSize(TEXT_SIZE);
        joinGameText.setTypeface(Typeface.createFromAsset(getAssets(), BUTTONS_FONT));

        instructorButton = findViewById(R.id.instructorEntrance);
        instructorText = findViewById(R.id.instructorEntranceText);
        instructorText.setText(db.getLanguageImp().instructorEntrance());
        instructorText.setTextSize(TEXT_SIZE);
        instructorText.setTypeface(Typeface.createFromAsset(getAssets(), BUTTONS_FONT));

        gameCodeLine = findViewById(R.id.gameCodeLine);
        gameCodeLine.setHint(db.getLanguageImp().enterGameCode());
        enterCodeButton = findViewById(R.id.enterCode);
        enterCodeButton.setVisibility(View.INVISIBLE);

        final Intent instructorScreen = new Intent(MainMenu.this, InstructorLogin.class);
        instructorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(instructorScreen);
            }
        });

        if (db.getPlayerGameCode() != "") gameCodeLine.setText(db.getPlayerGameCode());
        joinGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(joinGameLineDisplayed){
                    joinGameLineDisplayed = false;
                    instructorButton.setVisibility(View.VISIBLE);
                    instructorText.setVisibility(View.VISIBLE);
                    gameCodeLine.setVisibility(View.INVISIBLE);
                    enterCodeButton.setVisibility(View.INVISIBLE);
                    return;
                }

                joinGameLineDisplayed = true;
                instructorButton.setVisibility(View.INVISIBLE);
                instructorText.setVisibility(View.INVISIBLE);
                gameCodeLine.setVisibility(View.VISIBLE);
                enterCodeButton.setVisibility(View.VISIBLE);
            }
        });

        if (db.getPlayerGameCode() != "") gameCodeLine.setText(db.getPlayerGameCode());
        final Intent playerMap = new Intent(MainMenu.this, PlayerMap.class);
        enterCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String codeText = gameCodeLine.getText().toString();
                if (!codeText.isEmpty()) {
                    db.setPlayerGameCode(codeText);
                    db.playerEntrance(codeText);
                    new Thread(new Runnable() {
                        public void run() {
                            int currentWaitTime = 0;
                            while (!db.downloadGameData() && currentWaitTime <= MAX_WAIT_DURATION) {
                                SystemClock.sleep(INTERVAL);
                                currentWaitTime += INTERVAL;
                            }
                            if (currentWaitTime < MAX_WAIT_DURATION) startActivity(playerMap);
                            else {
                                showToast(FirebaseDB.getInstance().getLanguageImp().wrongCode());
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    }).start();
                } else {
                    Toast.makeText(myContext, FirebaseDB.getInstance().getLanguageImp().enterGameCode(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
