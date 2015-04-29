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
    MapMode mode;

    public MapInstantiater(Intent intent){
        this.intent = intent;
    }

    public int setRoundNumber(){
        int round = intent.getIntExtra("round",-1);
        TextView roundNumView = (TextView) findViewById(R.id.roundNumView);
        roundNumView.setText("Round "+ Integer.toString(round));
        return round;
    }

    public void setSwitchToStreetViewListener(){
        ImageButton switchToStreet = (ImageButton) findViewById(R.id.button2);
        switchToStreet.setOnClickListener(switchToStreetListener);
    }

    public void setSubmitButtonListener(){
        ImageButton submitButton = (ImageButton) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(switchToScoring);
    }

    public void getMap(){
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapviewfragment);
        mapFragment.getMapAsync(this);
    }


}
