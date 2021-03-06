package com.vackhack.roees.treasure_hunt;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.util.LinkedList;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class FirebaseDB implements TreasureHuntDB {
    private FirebaseServerHandler fb;
    private String email;
    private String password;
    private LanguageImp languageImp;
    private SharedPreferences appMap;
    private SharedPreferences.Editor appMapEditor;
    private final String USER_PASSWORD = "USER_PASSWORD";
    private final String USERNAME = "USERNAME";
    private final String IS_INSTRUCTOR = "IS_INSTRUCTOR";
    private final String IS_SHOWCASE_ACTIVE = "IS_SHOWCASE_ACTIVE";
    private final String SHARED_PREFS = "SHARED_PREFS";
    private final String UID = "UID";
    private final String PLAYER_GAME_CODE = "PLAYER_GAME_CODE";
    private final String PLAYER_CURRENT_MARKER = "PLAYER_CURRENT_MARKER";
    private Context appContext;
    private static final FirebaseDB ourInstance = new FirebaseDB();
    private LinkedList<Pair<LatLng, String>> RNCArr = new LinkedList<>();

    public static FirebaseDB getInstance() {
        return ourInstance;
    }

    private void debugGetMapContent() {
        for (Pair<LatLng, String> entry : RNCArr) {
            Log.e("th_log", entry + "");
        }
        Log.e("th_log", "size: " + RNCArr.size());
    }

    private FirebaseDB() {
        fb = new FirebaseServerHandler();
        //initialize shared preferences
        if(Locale.getDefault().getDisplayLanguage().equals("עברית")) languageImp = new HebrewImp();
        else languageImp = new EnglishImp();
    }

    @Override
    public void initContext(Context context) {
        appContext = context;
        appMap = appContext.getApplicationContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
    }

    private void addInstructorDetailsToSharedprefs() {
        appMapEditor = appMap.edit();
        appMapEditor.putBoolean(IS_INSTRUCTOR, true);
        appMapEditor.putString(UID, fb.getServerUID());
        appMapEditor.apply();
    }

    @Override
    public boolean login(String instructorEmail, String instructorPassword) {
        email = instructorEmail;
        password = instructorPassword;

        if (fb.tryRegister(email, password))
            addInstructorDetailsToSharedprefs();
        else if (fb.tryLogin(email, password))
            addInstructorDetailsToSharedprefs();
        else return false;

        return true;
    }

    @Override
    public void pushRiddle(LatLng riddle, String coordinate) {
        Pair<LatLng, String> newRNC = new Pair<>(riddle, coordinate);
        RNCArr.add(newRNC);
        Toast.makeText(appContext, FirebaseDB.getInstance().getLanguageImp().riddleAddedSuccessfully(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public LanguageImp getLanguageImp() {
        return languageImp;
    }

    private boolean getSavedGame() {
        RiddlesNCoordinates newRNC = (RiddlesNCoordinates) fb.getRetrievedData();
        if (newRNC != null) {
            RNCArr = newRNC.get();
            return true;
        } else return false;
    }

    @Override
    public boolean downloadGameData() {
        boolean isInstructor = appMap.getBoolean(IS_INSTRUCTOR, false);
        String uid = isInstructor ? appMap.getString(UID, "") : appMap.getString(PLAYER_GAME_CODE, "");
        if (fb.tryRetrieveData(uid)) {
            return getSavedGame();
        } else return false;
    }

    @Override
    public void logout() {
        appMapEditor = appMap.edit();
        appMapEditor.putBoolean(IS_INSTRUCTOR, false);
        appMapEditor.apply();
    }

    @Override
    public String getInstructorGameCode() {
        return fb.getServerUID();
    }

    @Override
    public void playerEntrance(String code) {
        appMapEditor = appMap.edit();
        appMapEditor.putBoolean(IS_INSTRUCTOR, false);
        appMapEditor.putString(UID, code);
        appMapEditor.apply();
    }

    @Override
    public String getSavedUsername() {
        return appMap.getString(USERNAME, "");
    }

    @Override
    public String getSavedPassword() {
        return appMap.getString(USER_PASSWORD, "");
    }

    @Override
    public LatLng getCoordinationByNum(Integer num) {
        return RNCArr.get(num).first;
    }

    @Override
    public String getRiddleByCoordinate(LatLng latLng) {
        for (Pair<LatLng, String> entry : RNCArr)
            if (entry.first.equals(latLng))
                return entry.second;
        return null;
    }

    @Override
    public Integer getNumByCoordinate(LatLng latLng) {
        for (int i=0; i<RNCArr.size(); ++i) {
            if (RNCArr.get(i).first.equals(latLng)) {
                return i + 1;
            }
        }
        return null;
    }

    @Override
    public void removeRiddleByNum(Integer num) {
        RNCArr.remove(num - 1);
    }

    @Override
    public Integer getNumOfRiddles() {
        return RNCArr.size();
    }

    @Override
    public LatLng coordinateInCloseProximity(LatLng latLng, int maxDistance) {
        for (Pair<LatLng, String> entry : RNCArr) {
            if (SphericalUtil.computeDistanceBetween(entry.first, latLng) < maxDistance) {
                return entry.first;
            }
        }
        return null;
    }

    @Override
    public void setPlayerCurrentMarker(int num) {
        appMapEditor = appMap.edit();
        appMapEditor.putInt(PLAYER_CURRENT_MARKER, num - 1);
        appMapEditor.apply();
    }

    @Override
    public int getPlayerCurrentMarker() {
        return appMap.getInt(PLAYER_CURRENT_MARKER, 0) + 1;
    }

    @Override
    public void setPlayerGameCode(String code) {
        appMapEditor = appMap.edit();
        appMapEditor.putString(PLAYER_GAME_CODE, code);
        appMapEditor.apply();
    }

    @Override
    public String getPlayerGameCode() {
        return appMap.getString(PLAYER_GAME_CODE, "");
    }

    @Override
    public void saveInstructorDetails(String instructorEmail, String instructorPassword) {
        appMapEditor = appMap.edit();
        appMapEditor.putString(USER_PASSWORD, instructorPassword);
        appMapEditor.putString(USERNAME, instructorEmail);
        appMapEditor.apply();
    }

    @Override
    public void toggleShowcase(boolean isShowcaseActive) {
        appMapEditor = appMap.edit();
        appMapEditor.putBoolean(IS_SHOWCASE_ACTIVE, isShowcaseActive);
        appMapEditor.apply();
    }

    @Override
    public boolean isShowCaseActive() {
        return appMap.getBoolean(IS_SHOWCASE_ACTIVE, true);
    }

    @Override
    public String getLoginFeedback() {
        return fb.getLogFeedback();
    }

    @Override
    public String getDownloadFeedback() {
        return fb.getStorageFeedback();
    }

    @Override
    public boolean isNewInstructor() {
        return fb.isUserContentEmpty();
    }

    @Override
    public boolean saveGame() {
        return fb.tryUploadData(new RiddlesNCoordinates(RNCArr));
    }
}
