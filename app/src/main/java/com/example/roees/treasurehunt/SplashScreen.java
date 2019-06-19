package com.example.roees.treasurehunt;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        TextView title = findViewById(R.id.title);
        title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Caligraf.ttf"));
        title.setTextSize(100);

        TextView sub = findViewById(R.id.sub);
        sub.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Caligraf.ttf"));
        sub.setTextSize(30);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                Intent mainIntent = new Intent(SplashScreen.this, MainMenu.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
