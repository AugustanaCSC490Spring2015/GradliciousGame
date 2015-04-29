package edu.augustana.csc490.mapgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

/**
 * Created by andrewshearouse11 on 4/29/2015.
 */
public class MapInstantiater extends Activity {

    Intent intent;
    MapModeMultiplayer multiPlayer;
    MapMode singlePlayer;

    public MapInstantiater(Intent intent, int indicator, Object caller){
        this.intent = intent;
        if(indicator == 0){
            singlePlayer = (MapMode) caller;
        } else{
            multiPlayer = (MapModeMultiplayer) caller;
        }
    }

    public int setRoundNumber(){
        int round = intent.getIntExtra("round",-1);
        TextView roundNumView = (TextView) findViewById(R.id.roundNumView);
        roundNumView.setText("Round "+ Integer.toString(round));
        return round;
    }

    public void setSwitchToStreetViewListener(int indicator){
        ImageButton switchToStreet = (ImageButton) findViewById(R.id.button2);
        switch(indicator){
            case 0:
                switchToStreet.setOnClickListener(singlePlayer.switchToStreetListener);
            case 1:
                switchToStreet.setOnClickListener(multiPlayer.switchToStreetListener);
        }

    }

    public void setSubmitButtonListener(int indicator){
        ImageButton submitButton = (ImageButton) findViewById(R.id.submitButton);
        switch(indicator){
            case 0:
                submitButton.setOnClickListener(singlePlayer.switchToScoring);
            case 1:
                submitButton.setOnClickListener(multiPlayer.switchToScoring);
        }

    }

    public void getMap(){
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapviewfragment);
        mapFragment.getMapAsync(this);
    }


}
