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
    void logout();
    boolean isLoggedIn();

    //player functions
    void playerEntrance(String code);
    LatLng coordinateInCloseProximity(LatLng latLng, int maxDistance);
    void setPlayerCurrentRiddle(int num);
    int getPlayerCurrentRiddle();

    //general functions
    LanguageImp getLanguageImp();
    void initContext(Context context);
    String getGameCode();
    String getSavedUsername();
    String getSavedPassword();
}
