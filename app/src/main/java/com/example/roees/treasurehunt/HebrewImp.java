package com.example.roees.treasurehunt;

public class HebrewImp implements LanguageImp {
    @Override
    public String CreateGame() {
        return "צור משחק";
    }
    @Override
    public String JoinGame() {
        return "הצטרף למשחק";
    }
    @Override
    public String InstructorEntrance() {
        return "כניסת מפעיל";
    }
    @Override
    public String EnterGameCode() {
        return "הכנס קוד משחק";
    }

    @Override
    public String EnterPassword() {
        return "הכנס סיסמא";
    }
    @Override
    public String EnterEmail() {
        return "הכנס אימייל";
    }

    @Override
    public String Submit() {
        return "כניסה";
    }
}
