package com.example.roees.treasurehunt;

public interface GameDB {
    public RiddlesNCoordinates joinGame(String instructorEmail);
    public boolean createGame(String instructorEmail, String instructorPassword,
                              RiddlesNCoordinates riddlesNCoordinates);
    public boolean editGame(String instructorEmail, String instructorPassword,
                              RiddlesNCoordinates riddlesNCoordinates);
}
