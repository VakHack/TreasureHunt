package com.example.roees.treasurehunt;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;

public class InstructorMap extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap map;
    private HashMap<LatLng, String> touchRNC = new HashMap();
    private ImageView deleteIcon;
    private Button deleteMarkerBack;
    private Marker activatedMarker = null;
    private EditText riddleLine;

    void zoomToCurrentLocation() {
        float zoomLevel = 10.0f;
        //currently Tel Aviv
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(32.109333, 34.855499), zoomLevel));
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
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        googleMap.setOnMarkerClickListener(this);
        zoomToCurrentLocation();

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                touchRNC.put(latLng, FirebaseDB.getInstance().getLanguageImp().addNewRiddle());
                deleteIcon.setVisibility(View.INVISIBLE);
            }
        });

        deleteMarkerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteIcon.performClick();
            }
        });

        riddleLine.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_GO) {
                    touchRNC.put(activatedMarker.getPosition(), v.getText().toString());
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
                    deleteIcon.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker){
        deleteIcon.setVisibility(View.VISIBLE);
        activatedMarker = marker;

        riddleLine.setVisibility(View.VISIBLE);
        riddleLine.setHint(FirebaseDB.getInstance().getLanguageImp().addNewRiddle());

        return false;
    }
}
