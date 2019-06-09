package com.example.roees.treasurehunt;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class FirebaseDB implements GameDB {
    private FirebaseServerHandler fb;
    private String email;
    private String password;
    private boolean isLogged = false;
    private LanguageImp languageImp = new HebrewImp();
    private SharedPreferences appMap;
    private SharedPreferences.Editor appMapEditor;
    private String LOGGED_IN_CLOUD = "LOGGED_IN";
    private String USER_PASSWORD = "USER_PASSWORD";
    private String USERNAME = "USERNAME";
    private String IS_INSTRUCTOR = "IS_INSTRUCTOR";
    private String SHARED_PREFS = "SHARED_PREFS";
    private Context appContext;
    private static final FirebaseDB ourInstance = new FirebaseDB();
    private final int NUMBER_OF_DOWNLOAD_ATTEMPTS = 15;

    public static FirebaseDB getInstance() {
        return ourInstance;
    }
    private FirebaseDB() {
        fb = new FirebaseServerHandler();
        //initialize shared preferences
    }
    public void initContext(Context context){
        appContext = context;
        appMap = appContext.getApplicationContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
    }
    private void addUserDataToSharedprefs(boolean isInstructor){
        appMapEditor = appMap.edit();
        appMapEditor.putBoolean(LOGGED_IN_CLOUD, true);
        appMapEditor.putBoolean(IS_INSTRUCTOR, isInstructor);
        appMapEditor.putString(USER_PASSWORD, password);
        appMapEditor.putString(USERNAME, email);
        appMapEditor.apply();
    }
    @Override
    public boolean isLoggedIn(){
        return appMap.getBoolean(LOGGED_IN_CLOUD, false);
    }
    @Override
    public boolean isInstructor(){
        return appMap.getBoolean(IS_INSTRUCTOR, false);
    }
    @Override
    public String joinGame(String instructorEmail) {
        return null;
    }
    @Override
    public String instructorEntrance(String instructorEmail, String instructorPassword) {
        if(isLogged) return languageImp.alreadyLogged();
        email = instructorEmail;
        password = instructorPassword;
        if(fb.tryRegister(email, password)){
            isLogged = true;
            addUserDataToSharedprefs(true);
            return languageImp.successfullyLoggedIn();
        } else if(fb.tryLogin(email, password)){
            isLogged = true;
            addUserDataToSharedprefs(true);
            return languageImp.successfullyRegistered();
        }else return fb.getLogFeedback();
    }
    @Override
    public boolean editGame(Map<LatLng, String> riddlesNCoordinatesR) {
        return fb.tryUploadData(new RiddlesNCoordinates(riddlesNCoordinatesR));
    }
    @Override
    public LanguageImp getLanguageImp() {
        return languageImp;
    }
    @Override
    public boolean didActionSucceeded() {
        return fb.didActionSucceeded();
    }
    @Override
    public String actionFeedback() {
        return fb.getStorageFeedback();
    }
    @Override
    public void downloadGame() {
        fb.tryRetrieveData();
    }
    @Override
    public Map<LatLng, String> getSavedGame(Map<LatLng, String> RNC) {
        Map<LatLng, String> newRNCMap = null;
        for(int i = 0; i < NUMBER_OF_DOWNLOAD_ATTEMPTS; ++i){
            RiddlesNCoordinates newRNC=(RiddlesNCoordinates)fb.getRetrievedData();
            if(newRNC!=null){
                newRNCMap = newRNC.get();
                for(Map.Entry<LatLng, String> entry : RNC.entrySet()) {
                    newRNCMap.put(entry.getKey(),entry.getValue());
                }
                break;
            }
        }
        return newRNCMap;
    }
}
