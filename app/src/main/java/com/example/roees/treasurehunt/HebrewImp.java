package com.example.roees.treasurehunt;

import com.google.android.gms.maps.model.LatLng;

public class HebrewImp implements LanguageImp {
    @Override
    public String createGame() {
        return "צור משחק";
    }
    @Override
    public String joinGame() {
        return "הצטרף למשחק";
    }
    @Override
    public String instructorEntrance() {
        return "כניסת מפעיל";
    }
    @Override
    public String enterGameCode() {
        return "הכנס קוד משחק";
    }
    @Override
    public String enterPassword() {
        return "הכנס סיסמא";
    }
    @Override
    public String enterEmail() {
        return "הכנס אימייל";
    }
    @Override
    public String submit() {
        return "כניסה";
    }
    @Override
    public String alreadyLogged() {
        return "כבר התחברת";
    }
    @Override
    public String successfullyLoggedIn() {
        return "התחברת בהצלחה!";
    }
    @Override
    public String successfullyRegistered() {
        return "רישום בוצע בהצלחה!";
    }
    @Override
    public String addNewRiddle() {
        return "הכנס חידה כאן";
    }
}
