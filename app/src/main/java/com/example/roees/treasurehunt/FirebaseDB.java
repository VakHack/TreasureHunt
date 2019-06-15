package com.example.roees.treasurehunt;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.util.HashMap;
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
    private String UID = "UID";
    private String PLAYER_CURRENT_RIDDLE = "PLAYER_CURRENT_RIDDLE";
    private Context appContext;
    private static final FirebaseDB ourInstance = new FirebaseDB();
    private final int NUMBER_OF_DOWNLOAD_ATTEMPTS = 15;
    private Map<Integer, Pair<LatLng, String>> RNCMap = new HashMap<>();

    public static FirebaseDB getInstance() {
        return ourInstance;
    }

    private void debugGetMapContent(){
        for (Map.Entry<Integer, Pair<LatLng, String>> entry : RNCMap.entrySet()){
            Log.e("th_log", entry+"");
        }
        Log.e("th_log", "size: "+RNCMap.size());
    }

    private FirebaseDB() {
        fb = new FirebaseServerHandler();
        //initialize shared preferences
    }

    private void getSavedGame() {
        for (int i = 0; i < NUMBER_OF_DOWNLOAD_ATTEMPTS; ++i) {
            RiddlesNCoordinates newRNC = (RiddlesNCoordinates) fb.getRetrievedData();
            if (newRNC != null) {
                RNCMap = newRNC.get();
            }
        }
    }

    @Override
    public void initContext(Context context) {
        appContext = context;
        appMap = appContext.getApplicationContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
    }

    @Override
    public boolean isLoggedIn() {
        return appMap.getBoolean(LOGGED_IN_CLOUD, false);
    }

    private void addInstructorDetailsToSharedprefs() {
        appMapEditor = appMap.edit();
        appMapEditor.putBoolean(LOGGED_IN_CLOUD, true);
        appMapEditor.putBoolean(IS_INSTRUCTOR, true);
        appMapEditor.putString(USER_PASSWORD, password);
        appMapEditor.putString(USERNAME, email);
        appMapEditor.putString(UID, fb.getServerUID());
        appMapEditor.apply();
    }

    @Override
    public boolean instructorEntrance(String instructorEmail, String instructorPassword) {
        if (isLogged) return true;
        email = instructorEmail;
        password = instructorPassword;
        if (fb.tryRegister(email, password)) {
            isLogged = true;
            addInstructorDetailsToSharedprefs();
            return isLogged;
        } else if (fb.tryLogin(email, password)) {
            isLogged = true;
            addInstructorDetailsToSharedprefs();
            return true;
        } else return false;
    }

    @Override
    public void pushRiddle(LatLng riddle, String coordinate) {
        Pair<LatLng, String> newRNC = new Pair<>(riddle, coordinate);
        RNCMap.put(RNCMap.size()+1, newRNC);
        while(!fb.tryUploadData(new RiddlesNCoordinates(RNCMap))){
            try {
                appContext.wait(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(appContext, FirebaseDB.getInstance().getLanguageImp().riddleAddedSuccessfully(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public LanguageImp getLanguageImp() {
        return languageImp;
    }

    @Override
    public boolean downloadGame() {
        if(fb.tryRetrieveData(appMap.getString(UID, ""))){
            getSavedGame();
            return true;
        } else return false;
    }

    @Override
    public void logout() {
        appMapEditor = appMap.edit();
        appMapEditor.putBoolean(LOGGED_IN_CLOUD, false);
        appMapEditor.apply();
    }

    @Override
    public String getGameCode() {
        return fb.getServerUID();
    }

    @Override
    public void playerEntrance(String code) {
        appMapEditor = appMap.edit();
        appMapEditor.putBoolean(LOGGED_IN_CLOUD, true);
        appMapEditor.putBoolean(IS_INSTRUCTOR, false);
        appMapEditor.putString(UID, code);
        appMapEditor.apply();
    }

    @Override
    public String getSavedUsername() {
        return appMap.getString(USERNAME, "");
    }

    @Override
    public String getSavedPassword() {
        return appMap.getString(USER_PASSWORD, "");
    }

    @Override
    public LatLng getCoordinationByNum(Integer num) {
        return RNCMap.get(num).first;
    }

    @Override
    public String getRiddleByCoordinate(LatLng latLng) {
        for (Map.Entry<Integer, Pair<LatLng, String>> entry : RNCMap.entrySet())
            if(entry.getValue().first.equals(latLng))
                return entry.getValue().second;
        return null;
    }

    @Override
    public Integer getNumByCoordinate(LatLng latLng) {
        for (Map.Entry<Integer, Pair<LatLng, String>> entry : RNCMap.entrySet()){
            if(entry.getValue().first.equals(latLng)){
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public void removeRiddleByNum(Integer num) {
        for(int i = num; i < RNCMap.size(); ++i){
            RNCMap.put(i, RNCMap.get(i + 1));
        }
        RNCMap.remove(RNCMap.size());
        fb.tryUploadData(new RiddlesNCoordinates(RNCMap));
    }

    @Override
    public Integer getNumOfRiddles() {
        return RNCMap.size();
    }

    @Override
    public LatLng coordinateInCloseProximity(LatLng latLng, int maxDistance) {
        for (Map.Entry<Integer, Pair<LatLng, String>> entry : RNCMap.entrySet()){
            if(SphericalUtil.computeDistanceBetween(entry.getValue().first, latLng) < maxDistance){
                return entry.getValue().first;
            }
        }
        return null;
    }

    @Override
    public void setPlayerCurrentRiddle(int num) {
        appMapEditor = appMap.edit();
        appMapEditor.putInt(PLAYER_CURRENT_RIDDLE, num);
        appMapEditor.apply();
    }

    @Override
    public int getPlayerCurrentRiddle() {
        return appMap.getInt(PLAYER_CURRENT_RIDDLE, 0);
    }
}
