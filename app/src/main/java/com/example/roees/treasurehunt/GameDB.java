package com.example.roees.treasurehunt;
import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

public interface GameDB {
    //instructor functions
    boolean instructorEntrance(String instructorEmail, String instructorPassword);
    boolean downloadGame();
    void pushRiddle(LatLng riddle, String coordinate);
    LatLng getCoordinationByNum(Integer num);
    void removeRiddleByNum(Integer num);
    String getRiddleByCoordinate(LatLng latLng);
    Integer getNumByCoordinate(LatLng latLng);
    Integer getNumOfRiddles();
    String getInstructorGameCode();
    void logout();
    boolean isLoggedIn();

    //player functions
    void playerEntrance(String code);
    LatLng coordinateInCloseProximity(LatLng latLng, int maxDistance);
    void setPlayerCurrentMarker(int num);
    int getPlayerCurrentMarker();
    void setPlayerGameCode(String code);
    String getPlayerGameCode();

    //general functions
    LanguageImp getLanguageImp();
    void initContext(Context context);
    String getSavedUsername();
    String getSavedPassword();
}
