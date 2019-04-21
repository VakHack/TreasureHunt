package com.example.roees.treasurehunt;

import com.google.android.gms.maps.model.LatLng;

import java.util.Map;

public interface GameDB {
    public String joinGame(String instructorEmail);
    public String instructorEntrance(String instructorEmail, String instructorPassword);
    public String createGame(Map<LatLng, String> riddlesNCoordinates);
    public String editGame(Map<LatLng, String> riddlesNCoordinates);
    public LanguageImp getLanguageImp();
}
