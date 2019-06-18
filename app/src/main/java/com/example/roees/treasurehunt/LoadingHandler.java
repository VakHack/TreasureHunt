package com.example.roees.treasurehunt;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;

public class LoadingHandler {
    private final int INTERVAL_DURATION;
    private final int NUM_OF_INTERVALS;
    private TreasureHuntDB db;
    private Context context;
    private int currentWaitTime;
    private Intent nextScreen;
    private boolean loginRequired;
    boolean isSucceed;

    public LoadingHandler(int INTERVAL_DURATION, int NUM_OF_INTERVALS, TreasureHuntDB db,
                          Context context, Intent nextScreen, boolean loginRequired) {
        this.INTERVAL_DURATION = INTERVAL_DURATION;
        this.NUM_OF_INTERVALS = NUM_OF_INTERVALS;
        this.db = db;
        this.context = context;
        this.nextScreen = nextScreen;
        this.loginRequired = loginRequired;
    }

    private void login(){
        currentWaitTime = 0;
        while (!db.login(db.getSavedUsername(), db.getSavedPassword()) && currentWaitTime <= NUM_OF_INTERVALS) {
            SystemClock.sleep(INTERVAL_DURATION);
            currentWaitTime += INTERVAL_DURATION;
        }
        isSucceed = currentWaitTime < NUM_OF_INTERVALS;
    }

    private void download(){
        currentWaitTime = 0;
        while (!db.downloadGameData() && currentWaitTime <= NUM_OF_INTERVALS) {
            SystemClock.sleep(INTERVAL_DURATION);
            currentWaitTime += INTERVAL_DURATION;
        }
        isSucceed = currentWaitTime < NUM_OF_INTERVALS;
    }

    private boolean loadingHandler(){
        new Thread(new Runnable() {
            public void run() {
                if(loginRequired){
                    login();
                    if(!isSucceed) return;
                }
                download();
            }
        });

        return isSucceed;
    }


    private void failureMessageHandler(String failureMessage){
        Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show();
    }

    public void waitUntilLoaded(){
        if(loadingHandler()) context.startActivity(nextScreen);
    }

    public void waitUntilLoaded(String failureMessage){
        if(!loadingHandler()) failureMessageHandler(failureMessage);
        else context.startActivity(nextScreen);
    }
}
