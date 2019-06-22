package com.example.roees.treasurehunt;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.Vector;

public class InstructorMap extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap map;
    private Button deleteMarker;
    private Button enterRiddle;
    private Button logout;
    private Button saveButton;
    private Button instructorShowcase;
    private Marker activatedMarker = null;
    private EditText riddleLine;
    private ImageView mockupLayout;
    private ImageView locationBackground;
    private TreasureHuntDB db = FirebaseDB.getInstance();
    final String DEFAULT_RIDDLE_HINT = FirebaseDB.getInstance().getLanguageImp().addNewRiddle();
    final Context myContext = this;
    final float ZOOM_FACTOR = 14;
    final LatLng DEFAULT_LATLNG = new LatLng(32.109333, 34.855499);
    private LatLng myLoc = DEFAULT_LATLNG;
    private ShowcaseHandler showcaseHandler;
    private final int INTERVAL = 1000;

    void zoomToCurrentLocation() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                while (ActivityCompat.checkSelfPermission(myContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(myContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    SystemClock.sleep(INTERVAL);
                }
                zoomToLocation();
            }
        });
    }

    @SuppressLint("MissingPermission")
    void zoomToLocation() {
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
        for (int i = 1; i <= db.getNumOfRiddles(); ++i) {
            map.addMarker(new MarkerOptions().position(db.getCoordinationByNum(i)));
        }
    }

    void showInfoWindow(String title, String riddle) {
        activatedMarker.setTitle(title);
        activatedMarker.setSnippet(riddle);
        activatedMarker.showInfoWindow();
    }

    void initShowCase() {
        Vector<Pair<View, Pair<String, String>>> instructions = new Vector<>();
        instructions.add(new Pair<View, Pair<String, String>>(mockupLayout, new Pair<>(db.getLanguageImp().instructorClickMapPrimary(), db.getLanguageImp().instructorClickMapSecondary())));
        instructions.add(new Pair<View, Pair<String, String>>(riddleLine, new Pair<>(db.getLanguageImp().instructorEnterRiddlePrimary(), db.getLanguageImp().instructorEnterRiddleSecondary())));
        instructions.add(new Pair<View, Pair<String, String>>(mockupLayout, new Pair<>(db.getLanguageImp().instructorClickMarkerPrimary(), db.getLanguageImp().instructorClickMarkerSecondary())));
        instructions.add(new Pair<View, Pair<String, String>>(deleteMarker, new Pair<>(db.getLanguageImp().instructorDeleteMarkerPrimary(), db.getLanguageImp().instructorDeleteMarkerSecondary())));
        instructions.add(new Pair<View, Pair<String, String>>(locationBackground, new Pair<>(db.getLanguageImp().instructorLocatePrimary(), db.getLanguageImp().instructorLocateSecondary())));
        instructions.add(new Pair<View, Pair<String, String>>(saveButton, new Pair<>(db.getLanguageImp().instructorStartGamePrimary(), db.getLanguageImp().instructorStartGameSecondary())));
        showcaseHandler = new ShowcaseHandler(this, instructions);
    }

    void runRelevantShowcaseIfActive() {
        if (db.isShowCaseActive()) showcaseHandler.callRelevantShowcase();
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

    void deletePreviousMarkerIfNotSaved() {
        if (activatedMarker != null && db.getNumByCoordinate(activatedMarker.getPosition()) == null) {
            activatedMarker.remove();
        }
    }

    void saveGameDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(myContext).create();
        alertDialog.setTitle(db.getLanguageImp().gameSavedSuccessfully());
        alertDialog.setMessage(db.getLanguageImp().newGameCodeTitle() + ": " + db.getInstructorGameCode());
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
                        ClipData clip = ClipData.newPlainText("game code", db.getInstructorGameCode());
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(myContext, FirebaseDB.getInstance().getLanguageImp().gameCodeCopiedSuccessfully(), Toast.LENGTH_SHORT).show();
                    }
                });
        alertDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        deleteMarker = findViewById(R.id.deleteMarkerBackground);
        riddleLine = findViewById(R.id.riddleLine);
        enterRiddle = findViewById(R.id.enterRiddle);
        logout = findViewById(R.id.logout);
        saveButton = findViewById(R.id.save);
        mockupLayout = findViewById(R.id.mockupLayout);
        locationBackground = findViewById(R.id.instructorLocationBackground);
        instructorShowcase = findViewById(R.id.instructorShowcase);
        db.downloadGameData();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;
        googleMap.setOnMarkerClickListener(this);
        buttonsVisibility(View.INVISIBLE);
        initGoogleMapUtils(googleMap);
        addSavedMarkers();
        initShowCase();
        runRelevantShowcaseIfActive();
        zoomToCurrentLocation();

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                runRelevantShowcaseIfActive();
                if (latLng == null) return;
                deletePreviousMarkerIfNotSaved();
                String riddle = db.getRiddleByCoordinate(latLng);
                if (riddle == null) riddleLine.setHint(DEFAULT_RIDDLE_HINT);
                else riddleLine.setHint(riddle);
                activatedMarker = map.addMarker(new MarkerOptions().position(latLng));
                buttonsVisibility(View.VISIBLE);
            }
        });

        deleteMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runRelevantShowcaseIfActive();
                if (activatedMarker != null) {
                    Integer riddleNum = db.getNumByCoordinate(activatedMarker.getPosition());
                    if (riddleNum != null) db.removeRiddleByNum(riddleNum);
                    activatedMarker.remove();
                    activatedMarker = null;
                    buttonsVisibility(View.INVISIBLE);
                }
            }
        });

        enterRiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runRelevantShowcaseIfActive();
                if (activatedMarker != null && !riddleLine.getText().toString().isEmpty()) {
                    int numOfRiddles = db.getNumOfRiddles() + 1;
                    db.pushRiddle(activatedMarker.getPosition(), riddleLine.getText().toString());
                    buttonsVisibility(View.INVISIBLE);
                    showInfoWindow(db.getLanguageImp().riddleTitle() + numOfRiddles, riddleLine.getText().toString());
                    activatedMarker = null;
                    riddleLine.setText("");
                }
            }
        });

        locationBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runRelevantShowcaseIfActive();
                locationBackground.setVisibility(View.INVISIBLE);
            }
        });

        instructorShowcase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.toggleShowcase(true);
                showcaseHandler.initShowcase();
                locationBackground.setVisibility(View.VISIBLE);
                runRelevantShowcaseIfActive();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.logout();
                final Intent welcomeScreen = new Intent(InstructorMap.this, MainMenu.class);
                startActivity(welcomeScreen);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.toggleShowcase(false);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        while (!db.saveGame()) {
                            SystemClock.sleep(INTERVAL);
                        }
                        saveGameDialog();
                    }
                });
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        runRelevantShowcaseIfActive();
        deletePreviousMarkerIfNotSaved();
        if (marker == null) return false;
        activatedMarker = marker;
        buttonsVisibility(View.VISIBLE);
        riddleLine.setText("");
        String riddle = db.getRiddleByCoordinate(marker.getPosition());
        Integer riddleNum = db.getNumByCoordinate(marker.getPosition());
        if (riddle != null) {
            showInfoWindow(db.getLanguageImp().riddleTitle() + riddleNum, riddle);
            riddleLine.setHint(riddle);
        } else {
            riddleLine.setHint(DEFAULT_RIDDLE_HINT);
        }
        return false;
    }
}
