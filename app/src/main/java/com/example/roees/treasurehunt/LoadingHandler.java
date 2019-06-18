package com.example.roees.treasurehunt;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class LoadingHandler {
    private final int intervalDuration;
    private final int maxTrialDuration;
    private TreasureHuntDB db;
    private Context context;
    private int currentWaitDuration;
    private Intent nextScreen;
    private boolean loginRequired;
    boolean isSucceed;
    private View indicator;

    public LoadingHandler(int intervalDuration, int numOfIntervals, TreasureHuntDB db,
                          Context context, Intent nextScreen, boolean loginRequired, View indicator) {
        this.intervalDuration = intervalDuration;
        maxTrialDuration = intervalDuration*numOfIntervals;
        this.db = db;
        this.context = context;
        this.nextScreen = nextScreen;
        this.loginRequired = loginRequired;
        this.indicator = indicator;
    }

    private void login(){

        currentWaitDuration = 0;
        while(currentWaitDuration <= maxTrialDuration) {
            db.login(db.getSavedUsername(), db.getSavedPassword());
            SystemClock.sleep(intervalDuration);
            if(db.isDownloadSucceeded()) break;
            currentWaitDuration += intervalDuration;
        }
        isSucceed = currentWaitDuration < maxTrialDuration;
    }

    private void download(){
        currentWaitDuration = 0;
        while(currentWaitDuration <= maxTrialDuration) {
            db.downloadGameData();
            SystemClock.sleep(intervalDuration);
            if(db.isDownloadSucceeded()) break;
            currentWaitDuration += intervalDuration;
        }
        isSucceed = currentWaitDuration < maxTrialDuration;
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
        }).run();

        return isSucceed;
    }


    private void failureMessageHandler(String failureMessage){
        Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show();
    }

    public boolean waitUntilLoaded(){
        indicator.setVisibility(View.VISIBLE);
        if(loadingHandler()){
            context.startActivity(nextScreen);
            return true;
        }
        else {
            indicator.setVisibility(View.INVISIBLE);
            return false;
        }
    }

    public void waitUntilLoaded(String failureMessage){
        if(!waitUntilLoaded()) failureMessageHandler(failureMessage);
    }
}
