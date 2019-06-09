package com.example.roees.treasurehunt;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.Map;

public interface GameDB {
    public void initContext(Context context);
    public String instructorEntrance(String instructorEmail, String instructorPassword);
    public boolean isInstructor();
    public boolean editGame(Map<LatLng, String> riddlesNCoordinatesR);
    public void downloadGame();
    public Map<LatLng, String> getSavedGame(Map<LatLng, String> RNC);
    public String joinGame(String instructorEmail);
    public String actionFeedback();
    public LanguageImp getLanguageImp();
    public void login();
    public void logout();
    public boolean isLoggedIn();
}
