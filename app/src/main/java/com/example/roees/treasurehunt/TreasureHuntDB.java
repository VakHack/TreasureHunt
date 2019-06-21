package com.example.roees.treasurehunt;
import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

public interface TreasureHuntDB {
    //instructorButton functions
    void saveInstructorDetails(String instructorEmail, String instructorPassword);
    boolean login(String instructorEmail, String instructorPassword);
    boolean downloadGameData();
    void pushRiddle(LatLng riddle, String coordinate);
    LatLng getCoordinationByNum(Integer num);
    void removeRiddleByNum(Integer num);
    String getRiddleByCoordinate(LatLng latLng);
    Integer getNumByCoordinate(LatLng latLng);
    Integer getNumOfRiddles();
    String getInstructorGameCode();
    void logout();
    boolean isLoggedIn();
    boolean isDownloadSucceeded();

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

    //showcase functions
    void toggleShowcase();
    boolean isShowCaseActive();
}
