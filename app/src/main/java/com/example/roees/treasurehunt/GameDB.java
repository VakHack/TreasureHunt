package com.example.roees.treasurehunt;

import android.content.Context;
import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;

import java.util.Map;

public interface GameDB {
    void initContext(Context context);
    boolean instructorEntrance(String instructorEmail, String instructorPassword);
    boolean downloadGame();
    boolean isInstructor();
    boolean editGame(Integer num, LatLng riddle, String coordinate);
    String getGameCode();
    Pair<LatLng, String> getRiddleByNum(Integer num);
    void removeRiddleByNum(Integer num);
    String getRiddleByCoordinate(LatLng latLng);
    Integer getNumByCoordinate(LatLng latLng);
    Integer getNumOfRiddles();
    void playerEntrance(String code);
    String downloadFeedback();
    String loginFeedback();
    LanguageImp getLanguageImp();
    void login();
    void logout();
    boolean isLoggedIn();
    String getSavedUsername();
    String getSavedPassword();
}
