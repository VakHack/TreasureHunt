package com.example.roees.treasurehunt;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.Map;

public class InstructorMap extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap map;
    private Map<LatLng, String> riddlesNCoordinates = new HashMap<>();
    private Button deleteMarker;
    private Button enterRiddle;
    private Button logout;
    private Button play;
    private Marker activatedMarker = null;
    private EditText riddleLine;
    private GameDB db = FirebaseDB.getInstance();
    final String DEFAULT_RIDDLE_HINT = FirebaseDB.getInstance().getLanguageImp().addNewRiddle();
    final Context myContext = this;
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

    void buttonsVisibility(int view) {
        deleteMarker.setVisibility(view);
        riddleLine.setVisibility(view);
        enterRiddle.setVisibility(view);
    }

    void addSavedMarkers() {
        if (!wasGameLoaded) {
            Map<LatLng, String> newRNC = db.getSavedGame(riddlesNCoordinates);
            if (newRNC != null) {
                riddlesNCoordinates = newRNC;
                wasGameLoaded = true;
                for (Map.Entry<LatLng, String> entry : riddlesNCoordinates.entrySet()) {
                    map.addMarker(new MarkerOptions().position(entry.getKey()));
                }
                Toast.makeText(myContext, FirebaseDB.getInstance().getLanguageImp().gameLoadedSuccessfully(), Toast.LENGTH_SHORT).show();
            }
        }
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
        setContentView(R.layout.activity_instructor_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        deleteMarker = findViewById(R.id.deleteMarkerBackground);
        riddleLine = findViewById(R.id.riddleLine);
        enterRiddle = findViewById(R.id.enterRiddle);
        logout = findViewById(R.id.logout);
        play = findViewById(R.id.play);
        db.downloadGame();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;
        googleMap.setOnMarkerClickListener(this);
        buttonsVisibility(View.INVISIBLE);
        initGoogleMapUtils(googleMap);
        zoomToCurrentLocation();

        if(!riddlesNCoordinates.isEmpty()) addSavedMarkers();

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (!riddlesNCoordinates.containsKey(latLng))
                    riddlesNCoordinates.put(latLng, DEFAULT_RIDDLE_HINT);
                activatedMarker = map.addMarker(new MarkerOptions().position(latLng));
                buttonsVisibility(View.VISIBLE);
            }
        });

        deleteMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activatedMarker != null) {
                    activatedMarker.remove();
                    riddlesNCoordinates.remove(activatedMarker.getPosition());
                    activatedMarker = null;
                    db.editGame(riddlesNCoordinates);
                    buttonsVisibility(View.INVISIBLE);
                }
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

        enterRiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activatedMarker != null && !riddleLine.getText().toString().isEmpty()) {
                    riddlesNCoordinates.put(activatedMarker.getPosition(), riddleLine.getText().toString());
                    db.editGame(riddlesNCoordinates);
                    buttonsVisibility(View.INVISIBLE);
                    activatedMarker.setTitle(riddlesNCoordinates.get(activatedMarker.getPosition()));
                    activatedMarker.showInfoWindow();
                    activatedMarker = null;
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.logout();
                final Intent welcomeScreen = new Intent(InstructorMap.this, WelcomeScreen.class);
                startActivity(welcomeScreen);
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(myContext).create();
                alertDialog.setTitle(db.getLanguageImp().newGameCodeTitle());
                alertDialog.setMessage(db.getGameCode());
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, db.getLanguageImp().OKButton(),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, db.getLanguageImp().copyGameCodeButton(),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //copy code to clipboard
                                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText("game code", db.getGameCode());
                                clipboard.setPrimaryClip(clip);
                                Toast.makeText(myContext, FirebaseDB.getInstance().getLanguageImp().gameCodeCopiedSuccessfully(), Toast.LENGTH_SHORT).show();
                            }
                        });
                alertDialog.show();
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        activatedMarker = marker;
        buttonsVisibility(View.VISIBLE);
        String hint = riddlesNCoordinates.get(marker.getPosition());
        if (hint != DEFAULT_RIDDLE_HINT) {
            activatedMarker.setTitle(hint);
            activatedMarker.showInfoWindow();
        }
        riddleLine.setText("");
        riddleLine.setHint(hint);
        return false;
    }
}
