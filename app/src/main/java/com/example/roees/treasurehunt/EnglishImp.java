package com.example.roees.treasurehunt;

public class EnglishImp implements LanguageImp {
    @Override
    public String createGame() {
        return "Create game";
    }

    @Override
    public String joinGame() {
        return "Player entrance";
    }

    @Override
    public String instructorEntrance() {
        return "Instructor entrance";
    }

    @Override
    public String enterGameCode() {
        return "Enter game";
    }

    @Override
    public String enterPassword() {
        return "Enter password";
    }

    @Override
    public String enterEmail() {
        return "Enter email";
    }

    @Override
    public String submit() {
        return "Submit";
    }

    @Override
    public String alreadyLogged() {
        return "You are already logged in";
    }

    @Override
    public String successfullyLoggedIn() {
        return "Logged in successfully!";
    }

    @Override
    public String successfullyRegistered() {
        return "Registration succeeded";
    }

    @Override
    public String addNewRiddle() {
        return "Enter riddle here";
    }

    @Override
    public String riddleAddedSuccessfully() {
        return "Riddle added successfully";
    }

    @Override
    public String gameLoadedSuccessfully() {
        return "Game loaded successfully";
    }

    @Override
    public String newGameCodeTitle() {
        return "Game entrance code";
    }

    @Override
    public String copyGameCodeButton() {
        return "Copy to clipboard";
    }

    @Override
    public String gameCodeCopiedSuccessfully() {
        return "Code copied successfully!";
    }

    @Override
    public String OKButton() {
        return "OK";
    }

    @Override
    public String riddleTitle() {
        return "Riddle Number ";
    }

    @Override
    public String IAmHere() {
        return "Dig";
    }

    @Override
    public String cannotFindLocation() {
        return "Cannot find your location, please try again";
    }

    @Override
    public String notInLocation() {
        return "Not yet there!";
    }

    @Override
    public String wrongCode() {
        return "Wrong code, please try again";
    }

    @Override
    public String congratulations() {
        return "Congratulations!";
    }

    @Override
    public String finishedSuccessfully() {
        return "The treasure is yours";
    }
}
