package com.eko.wazetestproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DisplayInfoActivity extends AppCompatActivity {


    private TextView etaTextView;
    private TextView instructionTextView;
    private TextView distanceTextView;
    private TextView geometryTextView;
    private TextView isConnectedTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_info);

        Intent intent = getIntent();

        String strETA = intent.getStringExtra("ETA");
        String distance = intent.getStringExtra("DISTANCE");
        String instruction = intent.getStringExtra("INSTRUCTION");
        String routeGeometry = intent.getStringExtra("GEOJSON");
        boolean isConnected = intent.getBooleanExtra("ISCONNECTED", true);


        if(strETA != null){
            etaTextView = (TextView) findViewById(R.id.ETA);
            etaTextView.setText("Eta: " + strETA + " Minutes");
        }

        if(instruction != null){
            instructionTextView = (TextView) findViewById(R.id.instruction);
            instructionTextView.setText("Instruction: " + instruction);
        }

        if(distance != null){
            distanceTextView = (TextView) findViewById(R.id.distance);
            distanceTextView.setText("Distance: " + distance);
        }

        if(routeGeometry != null){
            geometryTextView = (TextView) findViewById(R.id.Geo_Json);
            geometryTextView.setText("Route Geometry: " + routeGeometry);
        }


        isConnectedTextView = (TextView) findViewById(R.id.connection_Status);
        isConnectedTextView.setText("Connection status: " + isConnected);

    }
}
