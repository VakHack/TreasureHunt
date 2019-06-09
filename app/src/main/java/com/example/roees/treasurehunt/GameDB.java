package com.example.roees.treasurehunt;

public interface GameDB {
    public String joinGame(String instructorEmail);
    public String instructorEntrance(String instructorEmail, String instructorPassword);
    public boolean editGame(RiddlesNCoordinates riddlesNCoordinatesR);
    public boolean didActionSucceeded();
    public String actionFeedback();
    public LanguageImp getLanguageImp();
    public boolean isLoggedIn();
    public boolean isInstructor();
}
