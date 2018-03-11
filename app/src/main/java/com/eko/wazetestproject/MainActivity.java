package com.eko.wazetestproject;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.waze.sdk.WazeSDKManager;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity{

    private Messenger cBReceiveData;
    private MessageHandler mHandler;
    private PendingIntent pendingIntent;
    private WazeSDKManager wazeManager;
    private String wazeVersionNumber;

    public static final String PREFS_NAME = "MyPrefsFile";



    private TextView textView;
    private Button wazeButton;
    private Button sendRequestButton;
    private Button requestNavigationButton;
    private Button stopRequestButton;
    private Button terminateButton;
    private Button newOpenButton;
    private Button showRouteDetails;
    private Button changeRouteButton;

    boolean wazeConnected;


    private  final int requestCode = 01;

    private String strETA;
    private String instruction;
    private String distance;
    private String routeGeometry;
    private boolean isConnected;


    private DataStorage dataStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wazeManager = new WazeSDKManager().getInstance();
        wazeVersionNumber = wazeManager.getWazebuildnumber(this);

        textView = (TextView) findViewById(R.id.textView);
        textView.setText("Waze Version: " + wazeVersionNumber);

        dataStorage = new DataStorage(this);

        final Intent intent = new Intent(this, MainActivity.class);

        mHandler = new MessageHandler(this, dataStorage );


        cBReceiveData = new Messenger(mHandler);
        pendingIntent = PendingIntent.getActivity(getBaseContext(), requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        wazeButton = (Button) findViewById(R.id.wazeButton);
        wazeButton.setText("Initialize SDK");
        wazeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                wazeManager.initSDK(getBaseContext(), cBReceiveData, pendingIntent, 16.3670056, 48.2272914, null);


            }
        });

        sendRequestButton = (Button) findViewById(R.id.requestButton);
        sendRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wazeManager.driveRequest(16.3558056, 48.2416914 );




            }
        });


        requestNavigationButton = (Button) findViewById(R.id.requestNavigationButton);
        requestNavigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wazeManager.searchRequestWithNavigation("Pfeilgasse", 16.3455016, 48.2092914);

            }
        });


        changeRouteButton = (Button) findViewById(R.id.change_route_button);
        changeRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wazeManager.driveRequest(16.3455016, 48.2092914);

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        wazeManager.driveRequest(16.3958056, 48.2416914 );
                    }
                }, 15*1000);


            }
        });


        stopRequestButton = (Button) findViewById(R.id.stopRequestButton);
        stopRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wazeManager.stopNavigationRequest();

            }
        });

        terminateButton = (Button) findViewById(R.id.terminate_button);
        terminateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wazeManager.terminateSDK();

                boolean isConnected = dataStorage.isConnected();
                if (isConnected == false) {
                    Toast toast = Toast.makeText(getBaseContext(), "App has been disconnected from Waze", Toast.LENGTH_LONG);
                    toast.show();
                }


            }
        });


        newOpenButton = (Button) findViewById(R.id.different_open_button);
        newOpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wazeManager.openWaze();
            }
        });


        showRouteDetails = (Button) findViewById(R.id.show_info_button);
        showRouteDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent switchIntent = new Intent(getBaseContext(), DisplayInfoActivity.class);

                strETA = dataStorage.getStrETA();
                distance = dataStorage.getDistance();
                instruction = dataStorage.getInstruction();
                routeGeometry = dataStorage.getRouteGeometry();
                isConnected = dataStorage.isConnected();

                switchIntent.putExtra("ETA", strETA);
                switchIntent.putExtra("DISTANCE", distance);
                switchIntent.putExtra("INSTRUCTION", instruction);
                switchIntent.putExtra("GEOJSON", routeGeometry);
                switchIntent.putExtra("ISCONNECTED", isConnected);
                switchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(switchIntent);
            }
        });
    }

    @Override
    public void onDestroy(){

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("ETA", null);
        editor.putString("DISTANCE", null);
        editor.putString("GEOJSON", null);
        editor.putString("INSTRUCTION", null);
        editor.commit();

        super.onDestroy();

    }

}