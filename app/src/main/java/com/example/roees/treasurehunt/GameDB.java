package com.example.roees.treasurehunt;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.Map;

public interface GameDB {
    public void initContext(Context context);
    public String instructorEntrance(String instructorEmail, String instructorPassword);
    public void instructorDownloadGame();
    public boolean isInstructor();
    public boolean editGame(Map<LatLng, String> riddlesNCoordinatesR);
    public String getGameCode();
    public Map<LatLng, String> getSavedGame(Map<LatLng, String> RNC);
    public void playerEntrance(String code);
    public void playerDownloadGame();
    public String actionFeedback();
    public LanguageImp getLanguageImp();
    public void login();
    public void logout();
    public boolean isLoggedIn();
}
