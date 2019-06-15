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
    public String gameLoadedSuccessfully();
    public String newGameCodeTitle();
    public String riddleTitle();
    public String copyGameCodeButton();
    public String OKButton();
    public String gameCodeCopiedSuccessfully();
    public String riddleAddedSuccessfully();
    public String IAmHere();
    public String cannotFindLocation();
    public String notInLocation();
    public String wrongCode();
    public String congratulations();
    public String finishedSuccessfully();
}
