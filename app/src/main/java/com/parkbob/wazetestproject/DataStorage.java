package com.parkbob.wazetestproject;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Eko on 16.06.2017.
 */

public class DataStorage {


    private String strETA;
    private String instruction;
    private String distance;
    private String routeGeometry;
    private boolean connected;

    private Context context;
    SharedPreferences settings;



    public static final String PREFS_NAME = "MyPrefsFile";

    public DataStorage(Context context){

        this.context = context;

        settings = context.getSharedPreferences(PREFS_NAME, 0);

        strETA = settings.getString("ETA", null);
        instruction = settings.getString("INSTRUCTION", null);
        distance = settings.getString("DISTANCE", null);
        routeGeometry = settings.getString("GEOJSON", null);
        connected = settings.getBoolean("ISCONNECTED", false);

    }



    public String getStrETA() {
        return strETA;
    }

    public void setStrETA(String strETA) {

        settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("ETA", strETA);
        this.strETA = strETA;
        editor.commit();

    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {

        settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("INSTRUCTION", instruction);
        this.instruction = instruction;
        editor.commit();

    }

    public String getDistance() {return distance;}

    public void setDistance(String distance) {

        settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("DISTANCE", distance);
        this.distance = distance;
        editor.commit();

    }

    public String getRouteGeometry() {
        return routeGeometry;
    }

    public void setRouteGeometry(String routeGeometry) {

        settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("GEOJSON", routeGeometry);
        this.routeGeometry = routeGeometry;
        editor.commit();

    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("ISCONNECTED", connected);
        this.connected = connected;
        editor.commit();

    }
}
