package com.parkbob.wazetestproject;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.waze.sdk.WazeSDKManager;

import java.lang.ref.WeakReference;

/**
 * Created by Eko on 14.06.2017.
 */

public class MessageHandler extends Handler {
    private final WeakReference<MainActivity> mActivity;


    private DataStorage dataStorage;


    public MessageHandler(MainActivity activity, DataStorage dataStorage) {

        mActivity = new WeakReference<MainActivity>(activity);

        this.dataStorage = dataStorage;

    }


    @Override
    public void handleMessage(Message msg) {

        int MessageType = msg.what;

        WazeSDKManager.Waze_Message message = WazeSDKManager.Waze_Message.values()[MessageType];
        Log.d("WAZE_HANDLER:", "In Handler" + message);

        switch (message)

        {

            case Waze_Message_CONNECTION_STATUS:

            {

                String ConnectedString = msg.getData().getString(

                        "STATUS");
                Log.d("WAZE_LOG Status", msg.getData().getString("STATUS"));

                dataStorage.setConnected(Boolean.valueOf(ConnectedString));

                break;

            }

            case Waze_Message_ETA:

            {

                dataStorage.setStrETA(msg.getData().getString(

                        "ETA_MINUTES"));

                Log.d("WAZE_LOG ETA", msg.getData().getString("ETA_MINUTES"));




                break;

            }

            case Waze_Message_INSTRUCTION:

            {

                String instruction = msg.getData().getString(

                        "INSTRUCTION");




                int instructionCode = Integer.valueOf(instruction);

                Enum[] instructionEnumList = WazeSDKManager.Waze_Instructions_Types.values();
                String inst = instructionEnumList[instructionCode].toString();

                Log.d("WAZE_LOG Instruction", inst);

                dataStorage.setInstruction(inst);

                break;

            }

// This message is received only when the instruction is roundabout (this is the exit number)

            case Waze_Message_INSTRUCTION_EXIT:

            {

                String instructionExit = msg.getData().getString(

                        "INSTRUCTION_EXIT");


                int instructionCode = Integer.valueOf(instructionExit);


                Log.e("Transportation", "INSTRUCTION EXIT MSG : " + instructionExit);
                Log.d("WAZE_LOG  InstExit: ", instructionExit);

                break;

            }


            case Waze_Message_DISTANCE:

            {

                dataStorage.setDistance(msg.getData().getString(

                        "DISTANCE_METERS"));

                String DisplayDistance = msg.getData().getString(

                        "DISTANCE_DISPLAY");


                Log.e("Transportation", "Distance MSG : " + msg.getData().getString(

                        "DISTANCE_METERS") + " Meters");
                Log.d("WAZE_LOG Distance:", msg.getData().getString("DISTANCE_METERS"));
                Log.e("Transportation", "Distance MSG DISPLAY : " + DisplayDistance);
               // Log.d("WAZE_LOG DisDisplay:", msg.getData().getString("DISTANCE_DISPLAY"));

                break;

            }

            case Waze_Message_UNUSED:

            {

                String strLeftLane = msg.getData().getString(

                        "LEFT_LANE");


                boolean bIsLeftLane = Boolean.valueOf(strLeftLane);


                Log.e("Transportation", "Left Lane : " + bIsLeftLane);
                Log.e("WAZE_LOG Trans", "Left Lane : " + bIsLeftLane);

                break;

            }

            case Waze_Message_ROUTE:

            {

                dataStorage.setRouteGeometry(msg.getData().getString(

                        "GeoJson"));
                Log.d("WAZE_LOG GeoJson:", msg.getData().getString("GeoJson"));


                break;

            }
        }

    }
}
