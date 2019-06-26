package com.vackhack.roees.treasurehunt;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Vector;

public class PlayerMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private Button logout;
    private Button riddle;
    private Button verifyLocation;
    private ImageView locationBackground;
    private Button playerShowcase;
    private TreasureHuntDB db = FirebaseDB.getInstance();
    final Context myContext = this;
    final int MAX_DISTANCE_TO_DESTINATION = 5;
    final float ZOOM_FACTOR = 20;
    final LatLng DEFAULT_LATLNG = new LatLng(32.109333, 34.855499);
    private LatLng myLoc = DEFAULT_LATLNG;
    private ShowcaseHandler showcaseHandler;

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
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc, ZOOM_FACTOR));
                        } else {
                            Toast.makeText(myContext, FirebaseDB.getInstance().getLanguageImp().cannotFindLocation(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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

    void addMarkersUntilCurrentMarker() {
        for (int i = 0; i < db.getPlayerCurrentMarker(); ++i) {
            LatLng coordinate = db.getCoordinationByNum(i);
            addRelevantMarker(i, coordinate);
        }
    }

    void addRelevantMarker(int num, LatLng coordinate) {
        if (num < db.getNumOfRiddles())
            map.addMarker(new MarkerOptions().position(coordinate).icon(BitmapDescriptorFactory.fromResource(R.drawable.flag_icon)));
        else
            map.addMarker(new MarkerOptions().position(coordinate).icon(BitmapDescriptorFactory.fromResource(R.drawable.treasure_icon)));
    }

    void initShowCase() {
        Vector<Pair<View, Pair<String, String>>> instructions = new Vector<>();
        instructions.add(new Pair<View, Pair<String, String>>(riddle, new Pair<>(db.getLanguageImp().playerRiddlePrimary(), db.getLanguageImp().playerRiddleSecondary())));
        instructions.add(new Pair<View, Pair<String, String>>(verifyLocation, new Pair<>(db.getLanguageImp().playerLocationVerifyPrimary(), db.getLanguageImp().playerLocationVerifySecondary())));
        instructions.add(new Pair<View, Pair<String, String>>(locationBackground, new Pair<>(db.getLanguageImp().playerLocatePrimary(), db.getLanguageImp().playerLocateSecondary())));
        showcaseHandler = new ShowcaseHandler(this, instructions);
    }

    void runRelevantShowcaseIfActive(){
        if(db.isShowCaseActive()) showcaseHandler.callRelevantShowcase();
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
        riddle = findViewById(R.id.riddle);
        verifyLocation = findViewById(R.id.verifyLocation);
        verifyLocation.setText(db.getLanguageImp().IAmHere());
        locationBackground = findViewById(R.id.playerLocationBackground);
        playerShowcase = findViewById(R.id.playerShowcase);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;
        initGoogleMapUtils(googleMap);
        zoomToCurrentLocation();
        addMarkersUntilCurrentMarker();
        initShowCase();
        runRelevantShowcaseIfActive();

        locationBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runRelevantShowcaseIfActive();
                locationBackground.setVisibility(View.INVISIBLE);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.logout();
                final Intent welcomeScreen = new Intent(PlayerMap.this, MainMenu.class);
                startActivity(welcomeScreen);
            }
        });

        riddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runRelevantShowcaseIfActive();
                String title = db.getPlayerCurrentMarker() <= db.getNumOfRiddles() ? db.getLanguageImp().riddleTitle() + (db.getPlayerCurrentMarker() + 1)
                        : db.getLanguageImp().congratulations();
                String message = db.getPlayerCurrentMarker() <= db.getNumOfRiddles() ? db.getRiddleByCoordinate(db.getCoordinationByNum(db.getPlayerCurrentMarker() + 1))
                        : db.getLanguageImp().finishedSuccessfully();
                AlertDialog alertDialog = new AlertDialog.Builder(myContext).create();
                alertDialog.setTitle(title);
                alertDialog.setMessage(message);
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, db.getLanguageImp().OKButton(),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        playerShowcase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.toggleShowcase(true);
                showcaseHandler.initShowcase();
                locationBackground.setVisibility(View.VISIBLE);
                runRelevantShowcaseIfActive();
            }
        });

        verifyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runRelevantShowcaseIfActive();
                zoomToCurrentLocation();
                LatLng coordinate = db.coordinateInCloseProximity(myLoc, MAX_DISTANCE_TO_DESTINATION);
                if (coordinate != null) {
                    int numOfRiddle = db.getNumByCoordinate(coordinate);
                    addRelevantMarker(numOfRiddle, coordinate);
                    db.setPlayerCurrentMarker(numOfRiddle);
                    riddle.performClick();
                } else {
                    Toast.makeText(myContext, FirebaseDB.getInstance().getLanguageImp().notInLocation(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
