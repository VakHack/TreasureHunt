package com.example.roees.treasurehunt;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

public class InstructorMap extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap map;
    private HashMap<LatLng, String> riddlesNCoordinates = new HashMap();
    private ImageView deleteIcon;
    private Button deleteMarkerBack;
    private Marker activatedMarker = null;
    private EditText riddleLine;
    private ImageView enterRiddle;
    final String DEFAULT_RIDDLE_HINT = FirebaseDB.getInstance().getLanguageImp().addNewRiddle();
    final Context thisMap = this;

    void zoomToCurrentLocation() {
        float zoomLevel = 10.0f;
        //currently Tel Aviv
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(32.109333, 34.855499), zoomLevel));
    }

    void buttonsVisibility(int view){
        deleteIcon.setVisibility(view);
        deleteMarkerBack.setVisibility(view);
        riddleLine.setVisibility(view);
        enterRiddle.setVisibility(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        deleteIcon = findViewById(R.id.deleteMarker);
        deleteMarkerBack = findViewById(R.id.deleteMarkerBackground);
        riddleLine = findViewById(R.id.riddleLine);
        enterRiddle = findViewById(R.id.enterRiddle);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        googleMap.setOnMarkerClickListener(this);
        zoomToCurrentLocation();
        buttonsVisibility(View.INVISIBLE);
        googleMap.getUiSettings().setMapToolbarEnabled(false);


        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                riddlesNCoordinates.put(latLng, DEFAULT_RIDDLE_HINT);
                map.addMarker(new MarkerOptions().position(latLng));
            }
        });

        deleteMarkerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteIcon.performClick();
            }
        });

        riddleLine.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    riddlesNCoordinates.put(activatedMarker.getPosition(), v.toString());
                }
                return false;
            }
        });

        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(activatedMarker != null){
                activatedMarker.remove();
                activatedMarker = null;
                buttonsVisibility(View.INVISIBLE);
            }
            }
        });

        enterRiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(activatedMarker != null && !riddleLine.getText().toString().isEmpty()){
                riddlesNCoordinates.put(activatedMarker.getPosition(), riddleLine.getText().toString());
                Toast.makeText(thisMap, FirebaseDB.getInstance().getLanguageImp().riddleAddedSuccessfully(), Toast.LENGTH_SHORT).show();
                buttonsVisibility(View.INVISIBLE);
            }
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker){
        activatedMarker = marker;
        activatedMarker.showInfoWindow();
        buttonsVisibility(View.VISIBLE);

        if(riddlesNCoordinates.get(marker.getPosition())==DEFAULT_RIDDLE_HINT){
            riddleLine.setHint(DEFAULT_RIDDLE_HINT);
        } else {
            activatedMarker.setTitle(riddlesNCoordinates.get(marker.getPosition()));
            activatedMarker.showInfoWindow();
        }

        return false;
    }
}
