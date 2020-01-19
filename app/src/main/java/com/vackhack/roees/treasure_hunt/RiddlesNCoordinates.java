package com.vackhack.roees.treasure_hunt;

import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.LinkedList;

public class RiddlesNCoordinates implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private LinkedList<String> riddlesNCoordinates = new LinkedList<>();

    public RiddlesNCoordinates(LinkedList<Pair<LatLng, String>> riddlesNCoordinates) {
        convertMapToSerializable(riddlesNCoordinates);
    }

    public LinkedList<Pair<LatLng, String>> get() {
        return convertSerializableToMap();
    }

    private void convertMapToSerializable(LinkedList<Pair<LatLng, String>> riddlesNCoordinates) {
        for (Pair<LatLng, String> entry : riddlesNCoordinates) {
            String strLatLng = entry.first.latitude + " " + entry.first.longitude;
            String strPair = strLatLng+" "+entry.second;
            this.riddlesNCoordinates.add(strPair);
        }
    }

    private LinkedList<Pair<LatLng, String>> convertSerializableToMap() {
        LinkedList<Pair<LatLng, String>> retval = new LinkedList<>();
        for (String entry : riddlesNCoordinates) {
            String[] split = entry.split(" ");
            LatLng latLng = new LatLng(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
            Pair<LatLng, String> newPair = new Pair(latLng, split[2]);
            retval.add(newPair);
        }
        return retval;
    }
}
