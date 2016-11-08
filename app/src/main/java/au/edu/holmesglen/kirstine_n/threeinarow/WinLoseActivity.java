package au.edu.holmesglen.kirstine_n.threeinarow;

/**
 * Student name:    Kirstine B. Nielsen
 * Student id:      100527988
 * Date:            13/10/2016
 * Project:         Three in a row
 * Version:         1.1
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import static au.edu.holmesglen.kirstine_n.threeinarow.MainActivity.LOGGING_TAG;

/**
 * Shows the best times of the game.
 */
public class WinLoseActivity extends CommonActivity {

    private TextView tvBestTime4x4Easy;
    private TextView tvBestTime4x4Medium;
    private TextView tvBestTime4x4Hard;
    private TextView tvBestTime5x5Easy;
    private TextView tvBestTime5x5Medium;
    private TextView tvBestTime5x5Hard;
    private TextView tvBestTime6x6Easy;
    private TextView tvBestTime6x6Medium;
    private TextView tvBestTime6x6Hard;

    private LinearLayout llBestTime4x4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_lose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get the views
        TextView[][] textViewsList = getTextViewArray();

        // set the text on the text views
        for (int i = 0; i < textViewsList.length; i++) {
            for (int j = 0; j < textViewsList[i].length; j++) {
                Log.v(LOGGING_TAG, "for loop: " + i + " " + j);

                int bestTime = getSavedValueBestTime(i, j);
                Log.v(LOGGING_TAG, "best time: " + bestTime);
                textViewsList[i][j].setText("" + bestTime);
            }
        }
    }


    public TextView[][] getTextViewArray() {
        // get the relevant views
        tvBestTime4x4Easy = (TextView) findViewById(R.id.tv_bestTime4x4easy);
        tvBestTime4x4Medium = (TextView) findViewById(R.id.tv_bestTime4x4medium);
        tvBestTime4x4Hard = (TextView) findViewById(R.id.tv_bestTime4x4hard);
        tvBestTime5x5Easy = (TextView) findViewById(R.id.tv_bestTime5x5easy);
        tvBestTime5x5Medium = (TextView) findViewById(R.id.tv_bestTime5x5medium);
        tvBestTime5x5Hard = (TextView) findViewById(R.id.tv_bestTime5x5hard);
        tvBestTime6x6Easy = (TextView) findViewById(R.id.tv_bestTime6x6easy);
        tvBestTime6x6Medium = (TextView) findViewById(R.id.tv_bestTime6x6medium);
        tvBestTime6x6Hard = (TextView) findViewById(R.id.tv_bestTime6x6hard);

        // populata array
        TextView[][] textViewsList = {{tvBestTime4x4Easy, tvBestTime4x4Medium, tvBestTime4x4Hard},
                {tvBestTime5x5Easy, tvBestTime5x5Medium, tvBestTime5x5Hard},
                {tvBestTime6x6Easy, tvBestTime6x6Medium, tvBestTime6x6Hard}};

        return textViewsList;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_win_lose_screen, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.

        // declare an intent, the activity to start
        Intent intent;

        switch (item.getItemId())  // which menu item has been selected
        {
            case R.id.main:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.new_game:
                intent = new Intent(this, PlayActivity.class);
                startActivity(intent);
                return true;
            case R.id.help:
                intent = new Intent(this, HelpActivity.class);
                startActivity(intent);
                return true;
            case R.id.settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return false;  // nothing happened, no menu items has been selected
    }  // end onOptionsItemSelected
}  // end class WinLoseActivity
