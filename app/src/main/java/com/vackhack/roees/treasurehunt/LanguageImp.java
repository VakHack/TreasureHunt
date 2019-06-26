package com.vackhack.roees.treasurehunt;

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
    public String gameSavedSuccessfully();
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
    public String tryAgain();
    public String allowGpsTitle();
    public String allowGpsContent();

    //showcase content
    public String instructorClickMapPrimary();
    public String instructorClickMapSecondary();
    public String instructorEnterRiddlePrimary();
    public String instructorEnterRiddleSecondary();
    public String instructorSendRiddlePrimary();
    public String instructorSendRiddleSecondary();
    public String instructorClickMarkerPrimary();
    public String instructorClickMarkerSecondary();
    public String instructorDeleteMarkerPrimary();
    public String instructorDeleteMarkerSecondary();
    public String instructorLocatePrimary();
    public String instructorLocateSecondary();
    public String instructorStartGamePrimary();
    public String instructorStartGameSecondary();
    public String playerRiddlePrimary();
    public String playerRiddleSecondary();
    public String playerLocationVerifyPrimary();
    public String playerLocationVerifySecondary();
    public String playerLocatePrimary();
    public String playerLocateSecondary();
}
