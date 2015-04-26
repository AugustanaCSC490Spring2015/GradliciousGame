package edu.augustana.csc490.mapgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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


public class ScoringScreenMultiplayer extends Activity implements PopupMenu.OnMenuItemClickListener {


    String newScore;
    int round;
    int playerNum;
    String actualPosition;
    float scorePlayer0;
    float scorePlayer1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        newScore = intent.getStringExtra("newScore");
        round = intent.getIntExtra("round",-1);
        playerNum = intent.getIntExtra("playerNum", -1);
        Log.w("ScoringScreenMulti",Integer.toString(playerNum));
        actualPosition = intent.getStringExtra("actualPosition");
        scorePlayer0 = intent.getFloatExtra("scorePlayer0", -1);
        scorePlayer1 = intent.getFloatExtra("scorePlayer1", -1);

        setContentView(R.layout.scoringscreen_multiplayer);
        ImageButton nextLocationButton = (ImageButton) findViewById(R.id.newLoc);
        nextLocationButton.setOnClickListener(nextLocButtonListener);


        TextView scoreView = (TextView) findViewById(R.id.scoreTextView);
        TextView totalScore = (TextView) findViewById(R.id.totalScore);

        TextView roundNumView = (TextView) findViewById(R.id.roundNumView);
        roundNumView.setText("Round "+ Integer.toString(round));

        Log.w("newScore","" + newScore);
        scoreView.setText("Last Round: " + newScore + " km");
        if(playerNum == 0) {
            totalScore.setText("Total Score: " + String.format("%.0f", scorePlayer0) + " km");
        }else{
            totalScore.setText("Total Score: " + String.format("%.0f", scorePlayer1) + " km");
        }

        TextView playerNumView = (TextView) findViewById(R.id.playerNumView);
        playerNumView.setText("Player:" + (playerNum+1));

        if(round >= 2 && playerNum == 1){
            nextLocationButton.setVisibility(View.GONE);

            /////////////// http://stackoverflow.com/questions/7965494/how-to-put-some-delay-in-calling-an-activity-from-another-activity

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent2 = new Intent(ScoringScreenMultiplayer.this,GameOverMultiplayer.class);
                    intent2.putExtra("scorePlayer0", scorePlayer0);
                    intent2.putExtra("scorePlayer1", scorePlayer1);
                    startActivity(intent2);

                }
            }, 3000);

            /////////////// END http://stackoverflow.com/questions/7965494/how-to-put-some-delay-in-calling-an-activity-from-another-activity
        }

    }

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
                Intent i = new Intent(ScoringScreenMultiplayer.this, MainActivity.class);
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

    public View.OnClickListener nextLocButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            Intent intent = new Intent(ScoringScreenMultiplayer.this, StreetMode_Multiplayer.class);
            intent.putExtra("round", round);
            if(playerNum == 0){
                intent.putExtra("playerNum", 1);
                intent.putExtra("actualPosition", actualPosition);
                Log.w("playerNum = 0","");
            }else if(playerNum == 1){
                intent.putExtra("playerNum", 0);
                Log.w("playerNum = 1","");
            }
            intent.putExtra("scorePlayer0", scorePlayer0);
            intent.putExtra("scorePlayer1", scorePlayer1);
            startActivity(intent);

        }
    };




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



}
