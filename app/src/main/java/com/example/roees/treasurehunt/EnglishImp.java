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
        return "Enter clue here";
    }

    @Override
    public String riddleAddedSuccessfully() {
        return "Clue added successfully";
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
        return "Clue Number ";
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

    @Override
    public String instructorClickMapPrimary() {
        return "Add new location to the game";
    }

    @Override
    public String instructorClickMapSecondary() {
        return "By clicking the map";
    }

    @Override
    public String instructorEnterRiddlePrimary() {
        return "";
    }

    @Override
    public String instructorEnterRiddleSecondary() {
        return "Here you can enter the clue leading the selected location";
    }

    @Override
    public String instructorSendRiddlePrimary() {
        return "";
    }

    @Override
    public String instructorSendRiddleSecondary() {
        return "Save location here";
    }

    @Override
    public String instructorClickMarkerPrimary() {
        return "You can always re-read and edit the clue";
    }

    @Override
    public String instructorClickMarkerSecondary() {
        return "Just tap the clue marker";
    }

    @Override
    public String instructorDeleteMarkerPrimary() {
        return "";
    }

    @Override
    public String instructorDeleteMarkerSecondary() {
        return "While the the clue marker is selected, you can delete it by clicking the trash icon";
    }

    @Override
    public String instructorLocatePrimary() {
        return "lost?";
    }

    @Override
    public String instructorLocateSecondary() {
        return "Find your current location here";
    }

    @Override
    public String instructorStartGamePrimary() {
        return "Ready to go?";
    }

    @Override
    public String instructorStartGameSecondary() {
        return "The play button will generate a new unique code. Copy and send it to the player";
    }

    @Override
    public String playerRiddlePrimary() {
        return "Welcome mighty treasure hunters!";
    }

    @Override
    public String playerRiddleSecondary() {
        return "Here you can watch a clue to your next location";
    }

    @Override
    public String playerLocationVerifyPrimary() {
        return "Got to the location?";
    }

    @Override
    public String playerLocationVerifySecondary() {
        return "Click here to check it out!";
    }

    @Override
    public String playerLocatePrimary() {
        return "Lost?";
    }

    @Override
    public String playerLocateSecondary() {
        return "Find your current location here";
    }

    @Override
    public String gameSavedSuccessfully() {
        return "Game saved successfully!";
    }

    @Override
    public String tryAgain() {
        return "Something happened. Please try again";
    }

    @Override
    public String allowGpsTitle() {
        return "Welcome!";
    }

    @Override
    public String allowGpsContent() {
        return "This app is GPS based. Please approve the usage of location services on the following message";
    }
}
