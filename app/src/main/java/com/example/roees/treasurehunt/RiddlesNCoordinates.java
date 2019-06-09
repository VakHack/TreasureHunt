package com.example.roees.treasurehunt;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RiddlesNCoordinates implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private Map<String, String> riddlesNCoordinates = new HashMap<>();
    public RiddlesNCoordinates(Map<LatLng, String> riddlesNCoordinates){
        convertMapToSerializable(riddlesNCoordinates);
    }
    public Map<LatLng, String>get() {
        return convertSerializableToMap();
    }
    private void convertMapToSerializable(Map<LatLng, String> riddlesNCoordinates) {
        for(Map.Entry<LatLng, String> entry : riddlesNCoordinates.entrySet()) {
            String strLatLng = entry.getKey().latitude + " " + entry.getKey().longitude;
            this.riddlesNCoordinates.put(strLatLng, entry.getValue());
        }
    }
    private Map<LatLng, String> convertSerializableToMap(){
        Map<LatLng, String> retval = new HashMap<>();
        for(Map.Entry<String, String> entry : riddlesNCoordinates.entrySet()) {
            String[] split = entry.getKey().split(" ");
            LatLng latLng = new LatLng(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
            retval.put(latLng, entry.getValue());
        }
        return retval;
    }
}
