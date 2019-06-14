package com.example.roees.treasurehunt;

import android.content.Context;
import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;

public interface GameDB {
    void initContext(Context context);
    boolean instructorEntrance(String instructorEmail, String instructorPassword);
    void playerEntrance(String code);
    boolean downloadGame();
    boolean isInstructor();
    void pushRiddle(LatLng riddle, String coordinate);
    String getGameCode();
    Pair<LatLng, String> getRiddleByNum(Integer num);
    LatLng coordinateInCloseProximity(LatLng latLng, int maxDistance);
    void removeRiddleByNum(Integer num);
    String getRiddleByCoordinate(LatLng latLng);
    Integer getNumByCoordinate(LatLng latLng);
    Integer getNumOfRiddles();
    String downloadFeedback();
    String loginFeedback();
    boolean didActionSucceeded();
    LanguageImp getLanguageImp();
    void login();
    void logout();
    boolean isLoggedIn();
    String getSavedUsername();
    String getSavedPassword();
}
