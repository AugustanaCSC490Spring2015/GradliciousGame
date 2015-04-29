package edu.augustana.csc490.mapgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ImageButton startButton = (ImageButton) findViewById(R.id.start);
        startButton.setOnClickListener(startButtonListener);

        ImageButton multiplayer = (ImageButton) findViewById(R.id.multiplayer);
        multiplayer.setOnClickListener(multiplayerButtonListener);

    }

    // method that handles a user's click and launches the single player option
    public View.OnClickListener startButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            Intent intent = new Intent(MainActivity.this, StreetMode.class);
            intent.putExtra("score", (float) 0);
            intent.putExtra("round", 0);
            //load single player mode
            startActivity(intent);

        }
    };

    // method that handles a user's click and launches the local multiplayer option
    public View.OnClickListener multiplayerButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            Intent intent = new Intent(MainActivity.this, StreetMode_Multiplayer.class);
            intent.putExtra("score", (float) 0);
            intent.putExtra("round", 0);
            intent.putExtra("playerNum", 0);
            //load multiplayer mode
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
