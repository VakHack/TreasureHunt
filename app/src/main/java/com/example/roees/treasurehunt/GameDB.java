package com.example.roees.treasurehunt;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.Map;

public interface GameDB {
    void initContext(Context context);
    boolean instructorEntrance(String instructorEmail, String instructorPassword);
    boolean downloadGame();
    boolean isInstructor();
    boolean editGame(Map<LatLng, String> riddlesNCoordinatesR);
    String getGameCode();
    Map<LatLng, String> getSavedGame(Map<LatLng, String> RNC);
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
