package edu.augustana.csc490.mapgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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


public class GameOverMultiplayer extends Activity implements PopupMenu.OnMenuItemClickListener {

    float scorePlayer0;
    float scorePlayer1;
    SharedPreferences highScorePref;
    SharedPreferences.Editor highScoreEditor;
    TextView scorePlayer0View;
    TextView scorePlayer1View;
    TextView winnerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        scorePlayer0 = intent.getFloatExtra("scorePlayer0", -1);
        scorePlayer1 = intent.getFloatExtra("scorePlayer1", -1);

        int scoreIntPlayer0 = Integer.parseInt(String.format("%.0f", scorePlayer0));
        Log.w("scoreIntPlayer0", Integer.toString(scoreIntPlayer0));
        int scoreIntPlayer1 = Integer.parseInt(String.format("%.0f", scorePlayer1));
        Log.w("scoreIntPlayer0", Integer.toString(scoreIntPlayer1));

        setContentView(R.layout.gameover_multiplayer);
        ImageButton playAgainButton = (ImageButton) findViewById(R.id.playAgain);
        playAgainButton.setOnClickListener(startButtonListener);

        initiateViews();
        displayWinner();


        SharedPreferences locations = this.getSharedPreferences("locations", Context.MODE_PRIVATE);
        Log.w("Locations SharedPreferences", locations.getAll().toString());

    }

    public void initiateViews(){
        //Player 1's score
        scorePlayer0View = (TextView) findViewById(R.id.scorePlayer0View);
        //Player 2's score
        scorePlayer1View = (TextView) findViewById(R.id.scorePlayer1View);
        winnerTextView = (TextView) findViewById(R.id.winner);
    }

    public void displayWinner(){
        scorePlayer0View.setText("Player 1: " + String.format("%.0f", scorePlayer0) + " km");
        scorePlayer1View.setText("Player 2: "+ String.format("%.0f", scorePlayer1) + " km");

        //check to see whose score is higher or if the game is a tie
        //and set score accordingly
        if(scorePlayer0 < scorePlayer1){
            winnerTextView.setText("Player 1 Wins!");
        }else if(scorePlayer1 < scorePlayer0){
            winnerTextView.setText("Player 2 Wins!");
        }else{
            winnerTextView.setText("Tie");
        }
    }

    //Listener for the start button
    public View.OnClickListener startButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            Intent intent = new Intent(GameOverMultiplayer.this, StreetMode.class);
            intent.putExtra("score", (float) 0);
            intent.putExtra("round", 0);
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
                Intent i = new Intent(GameOverMultiplayer.this, MainActivity.class);
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
