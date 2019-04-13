package com.example.roees.treasurehunt;

public class FirebaseDB implements GameDB {
    private FirebaseServerHandler fb;
    private String email;
    private String password;
    private boolean isLogged = false;


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
        if(isLogged) return HebrewImp.getInstance().alreadyLogged();

        email = instructorEmail;
        password = instructorPassword;

        if(fb.tryRegister(email, password)){
            isLogged = true;
            return HebrewImp.getInstance().successfullyLoggedIn();
        } else if(fb.tryLogin(email, password)){
            isLogged = true;
            return HebrewImp.getInstance().successfullyRegistered();
        }else return fb.getLogFeedback();
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

    public boolean isLogged() {return isLogged;}
}
