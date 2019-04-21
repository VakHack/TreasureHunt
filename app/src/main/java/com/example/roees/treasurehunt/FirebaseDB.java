package com.example.roees.treasurehunt;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;

import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class FirebaseDB implements GameDB {
    private FirebaseServerHandler fb;
    private java.lang.String email;
    private java.lang.String password;
    private boolean isLogged = false;
    private LanguageImp languageImp = new HebrewImp();

    private SharedPreferences appMap;
    private SharedPreferences.Editor appMapEditor;
    private java.lang.String LOGGED_IN_CLOUD = "LOGGED_IN";
    private java.lang.String USER_PASSWORD = "USER_PASSWORD";
    private java.lang.String USERNAME = "USERNAME";
    private java.lang.String IS_INSTRUCTOR = "IS_INSTRUCTOR";
    private java.lang.String SHARED_PREFS = "SHARED_PREFS";

    private Context appContext;

    private static final FirebaseDB ourInstance = new FirebaseDB();
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
    public boolean isLoggedIn(){
        return appMap.getBoolean(LOGGED_IN_CLOUD, false);
    }
    public boolean isInstructor(){
        return appMap.getBoolean(IS_INSTRUCTOR, false);
    }

    @Override
    public String joinGame(java.lang.String instructorEmail) {
        return null;
    }
    @Override
    public String instructorEntrance(java.lang.String instructorEmail, java.lang.String instructorPassword) {
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
    public String createGame(Map<LatLng, String> riddlesNCoordinates) {
        return null;
    }
    @Override
    public String editGame(Map<LatLng, String> riddlesNCoordinates) {
        return null;
    }
    public String logFeedback(){ return fb.getLogFeedback();}
    public boolean isLogged() {return isLogged;}

    @Override
    public LanguageImp getLanguageImp() {
        return languageImp;
    }
}
