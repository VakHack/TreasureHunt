package com.example.roees.treasurehunt;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class FirebaseDB implements GameDB {
    private FirebaseServerHandler fb;
    private String email;
    private String password;
    private boolean isLogged = false;

    private SharedPreferences appMap;
    private SharedPreferences.Editor appMapEditor;
    private String LOGGED_IN_CLOUD = "LOGGED_IN";
    private String USER_PASSWORD = "USER_PASSWORD";
    private String USERNAME = "USERNAME";
    private String IS_INSTRUCTOR = "IS_INSTRUCTOR";
    private String SHARED_PREFS = "SHARED_PREFS";

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
    public RiddlesNCoordinates joinGame(String instructorEmail) {
        return null;
    }
    @Override
    public String instructorEntrance(String instructorEmail, String instructorPassword) {
        if(isLogged) return HebrewImp.getInstance().alreadyLogged();

        email = instructorEmail;
        password = instructorPassword;

        if(fb.tryRegister(email, password)){
            isLogged = true;
            addUserDataToSharedprefs(true);
            return HebrewImp.getInstance().successfullyLoggedIn();
        } else if(fb.tryLogin(email, password)){
            isLogged = true;
            addUserDataToSharedprefs(true);
            return HebrewImp.getInstance().successfullyRegistered();
        }else return fb.getLogFeedback();
    }
    @Override
    public String createGame(RiddlesNCoordinates riddlesNCoordinates) {
        return "";
    }
    @Override
    public String editGame(RiddlesNCoordinates riddlesNCoordinates) {
        return "";
    }
    public String logFeedback(){ return fb.getLogFeedback();}
    public boolean isLogged() {return isLogged;}
}
