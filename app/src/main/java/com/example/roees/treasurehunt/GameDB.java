package com.example.roees.treasurehunt;

public interface GameDB {
    public RiddlesNCoordinates joinGame(String instructorEmail);
    public String instructorEntrance(String instructorEmail, String instructorPassword);
    public String createGame(RiddlesNCoordinates riddlesNCoordinates);
    public String editGame(RiddlesNCoordinates riddlesNCoordinates);
}
