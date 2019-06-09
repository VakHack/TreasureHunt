package com.example.roees.treasurehunt;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.Map;

public interface GameDB {
    public String joinGame(String instructorEmail);
    public String instructorEntrance(String instructorEmail, String instructorPassword);
    public boolean editGame(Map<LatLng, String> riddlesNCoordinatesR);
    public void downloadGame();
    public Map<LatLng, String> getSavedGame(Map<LatLng, String> RNC);
    public boolean didActionSucceeded();
    public String actionFeedback();
    public LanguageImp getLanguageImp();
    public boolean isLoggedIn();
    public boolean isInstructor();
}
