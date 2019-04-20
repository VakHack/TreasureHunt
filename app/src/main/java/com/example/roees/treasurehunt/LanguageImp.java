package com.example.roees.treasurehunt;

import com.google.android.gms.maps.model.LatLng;

public abstract interface LanguageImp {
    public String createGame();
    public String joinGame();
    public String instructorEntrance();
    public String enterGameCode();
    public String enterPassword();
    public String enterEmail();
    public String submit();
    public String alreadyLogged();
    public String successfullyLoggedIn();
    public String successfullyRegistered();
    public String addNewRiddle();
}
