package com.example.roees.treasurehunt;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.Map;

public class PlayerMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private Map<LatLng, String> riddlesNCoordinates = new HashMap<>();
    private Button logout;
    private GameDB db = FirebaseDB.getInstance();
    final Context thisMap = this;
    private boolean wasGameLoaded = false;
    final float ZOOM_FACTOR = 14;
    final LatLng DEFAULT_LATLNG = new LatLng(32.109333, 34.855499);
    private LatLng myLoc = DEFAULT_LATLNG;

    void zoomToCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            myLoc = new LatLng(location.getLatitude(), location.getLongitude());
                        }
                    }
                });
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc, ZOOM_FACTOR));
    }

    void initGoogleMapUtils(GoogleMap googleMap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission
                    (this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    &&
                    ActivityCompat.checkSelfPermission
                            (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, 1); // 1 is requestCode
                return;
            }
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        logout = findViewById(R.id.logout);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;
        initGoogleMapUtils(googleMap);
        zoomToCurrentLocation();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.logout();
                final Intent welcomeScreen = new Intent(PlayerMap.this, WelcomeScreen.class);
                startActivity(welcomeScreen);
            }
        });
    }
}
