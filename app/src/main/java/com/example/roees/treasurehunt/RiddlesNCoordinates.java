package com.example.roees.treasurehunt;

import android.util.Log;
import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RiddlesNCoordinates implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private Map<Integer, String> riddlesNCoordinates = new HashMap<>();

    public RiddlesNCoordinates(Map<Integer, Pair<LatLng, String>> riddlesNCoordinates) {
        convertMapToSerializable(riddlesNCoordinates);
    }

    public Map<Integer, Pair<LatLng, String>> get() {
        return convertSerializableToMap();
    }

    private void convertMapToSerializable(Map<Integer, Pair<LatLng, String>> riddlesNCoordinates) {
        for (Map.Entry<Integer, Pair<LatLng, String>> entry : riddlesNCoordinates.entrySet()) {
            String strLatLng = entry.getValue().first.latitude + " " + entry.getValue().first.longitude;
            String strPair = strLatLng+" "+entry.getValue().second;
            this.riddlesNCoordinates.put(entry.getKey(), strPair);
        }
    }

    private Map<Integer, Pair<LatLng, String>> convertSerializableToMap() {
        Map<Integer, Pair<LatLng, String>> retval = new HashMap<>();
        for (Map.Entry<Integer, String> entry : riddlesNCoordinates.entrySet()) {
            String[] split = entry.getValue().split(" ");
            LatLng latLng = new LatLng(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
            Pair<LatLng, String> newPair = new Pair(latLng, split[2]);
            retval.put(entry.getKey(), newPair);
        }
        return retval;
    }
}
