package au.edu.holmesglen.kirstine_n.threeinarow;

/**
 * Student name:    Kirstine B. Nielsen
 * Student id:      100527988
 * Date:            13/10/2016
 * Project:         Three in a row
 * Version:         1.3
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * MainActivity where user can start a game from.
 */
public class MainActivity extends CommonActivity {

    public static final String LOGGING_TAG = "KIRSTI ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Log.v(LOGGING_TAG, "MainActivity, in onCreate");

        final Button btnStartGame = (Button) findViewById(R.id.btn_home_screen_start_game);

        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Log.v(LOGGING_TAG, "MainActivity, in onClick btnStartGame");

                // start game
                startGame(view);
            }
        });
    }  // end onCreate


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.

        // declare an intent, the activity to start
        Intent intent;

        switch (item.getItemId())  // which menu item has been selected
        {
            case R.id.new_game:
                intent = new Intent(this, PlayActivity.class);
                startActivity(intent);
                return true;
            case R.id.help:
                intent = new Intent(this, HelpActivity.class);
                startActivity(intent);
                return true;
            case R.id.best_times:
                intent = new Intent(this, BestTimesActivity.class);
                startActivity(intent);
                return true;
            case R.id.settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return false;  // nothing happened, no menu items has been selected
    }  // end onOptionsItemSelected


    /**
     * Starts a game.
     * @param view
     */
    public void startGame(View view) {
        Intent intent = new Intent(MainActivity.this, PlayActivity.class);
        startActivity(intent);
    }

}  // end class MainActivity
