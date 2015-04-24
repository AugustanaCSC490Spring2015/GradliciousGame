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


public class MapModeMultiplayer extends Activity implements OnMapReadyCallback, PopupMenu.OnMenuItemClickListener {

    GoogleMap mainMap;
    String actualPosition;
    Marker myMarker;
    float scorePlayer0;
    float scorePlayer1;
    int round;

    int playerNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Log.w("MapModeMulti",Integer.toString(playerNum));

        Intent intent = getIntent();
        actualPosition = intent.getStringExtra("actualPosition");

        scorePlayer0 = intent.getFloatExtra("scorePlayer0", -1);
        scorePlayer1 = intent.getFloatExtra("scorePlayer1", -1);
        playerNum = intent.getIntExtra("playerNum", -1);

        round = intent.getIntExtra("round",-1);

        Log.w("position", actualPosition);

        setContentView(R.layout.mapview);

        TextView roundNumView = (TextView) findViewById(R.id.roundNumView);
        roundNumView.setText("Round "+ Integer.toString(round));

        ImageButton switchToStreet = (ImageButton) findViewById(R.id.button2);
        switchToStreet.setOnClickListener(switchToStreetListener);

        ImageButton submitButton = (ImageButton) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(switchToScoring);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapviewfragment);
        mapFragment.getMapAsync(this);




    }


    @Override
    public void onMapReady(GoogleMap map) {
        mainMap = map;
        mainMap.setOnMapLongClickListener(mapLongClickListener);


        myMarker = mainMap.addMarker(new MarkerOptions().position(new LatLng(39.828127, -98.579404)).icon(BitmapDescriptorFactory.fromResource(R.drawable.pinsmallred)));

        myMarker.setDraggable(true);

    }

    public View.OnClickListener switchToStreetListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            Intent intent = new Intent(MapModeMultiplayer.this, StreetMode_Multiplayer.class);
            intent.putExtra("actualPosition", actualPosition);
            if(playerNum == 0) {
                intent.putExtra("scorePlayer0", scorePlayer0);
            }else if(playerNum == 1){
                intent.putExtra("scorePlayer1", scorePlayer1);
            }
            intent.putExtra("round", round);
            intent.putExtra("playerNum",  playerNum);
            startActivity(intent);
        }
    };


    public View.OnClickListener switchToScoring = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            Marker correctMarker = mainMap.addMarker(new MarkerOptions().position(stringToLatLng(actualPosition)).icon(BitmapDescriptorFactory.fromResource(R.drawable.pinsmallblue)));

            ImageButton switchToStreet = (ImageButton) findViewById(R.id.button2);

            ImageButton submitButton = (ImageButton) findViewById(R.id.submitButton);

            switchToStreet.setClickable(false);
            submitButton.setClickable(false);


            /////////////// http://stackoverflow.com/questions/7965494/how-to-put-some-delay-in-calling-an-activity-from-another-activity

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(stringToLatLng(actualPosition),3);
                    mainMap.animateCamera(cameraUpdate);

                }
            }, 500);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(stringToLatLng(actualPosition),6);
                    mainMap.animateCamera(cameraUpdate);

                }
            }, 1500);


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(MapModeMultiplayer.this, ScoringScreenMultiplayer.class);
                    intent.putExtra("newScore", String.format("%.0f", calculateScore()));
                    if(playerNum == 0) {
                        intent.putExtra("score", scorePlayer0);
                        intent.putExtra("actualPosition", actualPosition);
                    }else if(playerNum == 1){
                        intent.putExtra("score", scorePlayer1);
                        intent.putExtra("actualPosition", actualPosition);
                    }
                    intent.putExtra("round", round);
                    intent.putExtra("playerNum",  playerNum);
                    startActivity(intent);

                }
            }, 6000);

            ////////////// END http://stackoverflow.com/questions/7965494/how-to-put-some-delay-in-calling-an-activity-from-another-activity

        }
    };

    public LatLng stringToLatLng(String stringLoc){
        String[] latlong =  stringLoc.split(",");
        String latString = latlong[0].substring(latlong[0].indexOf("(")+1);
        String longString = latlong[1].substring(0,latlong[1].length()-1);
        double latitude;
        double longitude;
        if(latString.substring(0,1).equals("-")){
            Log.w("negative Latitude","");
            latString = latString.substring(1);
            latitude = Double.parseDouble(latString) * -1;
        }else {
            latitude = Double.parseDouble(latString);
        }
        if(longString.substring(0,1).equals("-")){
            Log.w("negative Longitude","");
            longString = longString.substring(1);
            longitude = Double.parseDouble(longString) * -1;
        }else {
            longitude = Double.parseDouble(longString);
        }
        return new LatLng(latitude,longitude);
    }

    public float calculateScore(){

        LatLng guessPosition = myMarker.getPosition();

        LatLng realPosition = stringToLatLng(actualPosition);

        Log.w("Guess", guessPosition.toString());
        Log.w("Actual", realPosition.toString());



        //////////////////////// http://stackoverflow.com/questions/14394366/find-distance-between-two-points-on-map-using-google-map-api-v2

        float[] results = new float[1];
        Location.distanceBetween(guessPosition.latitude , guessPosition.longitude,
                realPosition.latitude, realPosition.longitude, results);

        /////////////////////// END http://stackoverflow.com/questions/14394366/find-distance-between-two-points-on-map-using-google-map-api-v2

        float newScore = results[0]/1000;

        if(playerNum == 0) {
            scorePlayer0 += newScore;
        }else if(playerNum == 1){
            scorePlayer1 += newScore;
        }

        return  newScore;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public GoogleMap.OnMapLongClickListener mapLongClickListener = new GoogleMap.OnMapLongClickListener() {
        @Override
        public void onMapLongClick(LatLng point){
            myMarker.setPosition(point);
        }

    };

    public void popUpOptions(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.actions, popup.getMenu());
        popup.show();
    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.optionsMainMenu:
                Intent i = new Intent(MapModeMultiplayer.this, MainActivity.class);
                startActivity(i);
                return true;
            case R.id.optionsResetLocations:
                SharedPreferences locations = this.getSharedPreferences("locations", Context.MODE_PRIVATE);
                SharedPreferences.Editor locationsEdit = locations.edit();
                locationsEdit.clear();
                locationsEdit.commit();
                return true;
            case R.id.optionsResetBestScore:
                SharedPreferences bestScore = this.getSharedPreferences("scores", Context.MODE_PRIVATE);
                SharedPreferences.Editor bestScoreEdit = bestScore.edit();
                bestScoreEdit.clear();
                bestScoreEdit.commit();
                return true;
            default:
                return false;
        }
    }

}
