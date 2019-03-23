package com.example.roees.treasurehunt;

import java.util.Vector;

public class RiddlesNCoordinates {
    private Vector<String> riddles = new Vector<>();
    private Vector<String> coordinates = new Vector<>();

    public Vector<String> getRiddles() {
        return riddles;
    }

    public void setRiddles(Vector<String> riddles) {
        this.riddles = riddles;
    }

    public Vector<String> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Vector<String> coordinates) {
        this.coordinates = coordinates;
    }
}
