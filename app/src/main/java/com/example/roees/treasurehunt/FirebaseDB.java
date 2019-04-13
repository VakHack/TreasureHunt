package com.example.roees.treasurehunt;

public class FirebaseDB implements GameDB {
    private FirebaseServerHandler fb;
    private String email;
    private String password;

    private static final FirebaseDB ourInstance = new FirebaseDB();
    public static FirebaseDB getInstance() {
        return ourInstance;
    }

    private FirebaseDB() {
        fb = new FirebaseServerHandler();
    }

    @Override
    public RiddlesNCoordinates joinGame(String instructorEmail) {
        return null;
    }

    @Override
    public String instructorEntrance(String instructorEmail, String instructorPassword) {
        email = instructorEmail;
        password = instructorPassword;

        if(!fb.tryLogin(email, password))
            fb.tryRegister(email, password);

        return fb.getLogFeedback();
    }

    @Override
    public String createGame(RiddlesNCoordinates riddlesNCoordinates) {
        return "";
    }

    @Override
    public String editGame(RiddlesNCoordinates riddlesNCoordinates) {
        return "";
    }

    public String logFeedback(){ return fb.getLogFeedback();}
}
