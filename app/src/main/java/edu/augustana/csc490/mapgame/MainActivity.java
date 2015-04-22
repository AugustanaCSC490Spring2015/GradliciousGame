package edu.augustana.csc490.mapgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ImageButton startButton = (ImageButton) findViewById(R.id.start);
        startButton.setOnClickListener(startButtonListener);

        ImageButton localMultiplayer = (ImageButton) findViewById(R.id.localMultiplayer);
        startButton.setOnClickListener(localMultiplayerButtonListener);

        ImageButton multiplayer = (ImageButton) findViewById(R.id.multiplayer);
        startButton.setOnClickListener(multiplayerButtonListener);

    }

    public View.OnClickListener startButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            Intent intent = new Intent(MainActivity.this, StreetMode.class);
            intent.putExtra("score", (float) 0);
            intent.putExtra("round", 0);
            startActivity(intent);

        }
    };

    public View.OnClickListener localMultiplayerButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            Intent intent = new Intent(MainActivity.this, streetModeMultiplayer.class);
            intent.putExtra("score", (float) 0);
            intent.putExtra("round", 0);
            intent.putExtra("playerNum", 0);
            startActivity(intent);

        }
    };

    public View.OnClickListener multiplayerButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            Intent intent = new Intent(MainActivity.this, streetModeMultiplayer.class);
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



}
