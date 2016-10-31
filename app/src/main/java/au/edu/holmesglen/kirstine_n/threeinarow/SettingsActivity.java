package au.edu.holmesglen.kirstine_n.threeinarow;

/**
 * Student name:    Kirstine B. Nielsen
 * Student id:      100527988
 * Date:            13/10/2016
 * Project:         Three in a row
 * Version:         1.1
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static au.edu.holmesglen.kirstine_n.threeinarow.MainActivity.LOGGING_TAG;

public class SettingsActivity extends AppCompatActivity {

    // decl constants
    public static final String MY_PREFERENCES = "MyPrefs";
    public static final String COLOR_1 = "color1Key";
    public static final String COLOR_2 = "color2Key";

    public String[] colorList = {"Red", "White", "Blue"};

    public String selCol1;

    // decl ref to SharedPreferences class
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.v(LOGGING_TAG, "SettingsActivity in onCreate");

        // set up preferences collection
        sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);



        // build spinner
        final Spinner spinnerDifficulty = (Spinner) findViewById(R.id.spinner_difficulty);

        ArrayAdapter<CharSequence> adapterDifficulty = ArrayAdapter.createFromResource(
                this, R.array.difficulty, android.R.layout.simple_spinner_item);
        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerDifficulty.setAdapter(adapterDifficulty);

        // build spinner grid size
        final Spinner spinnerGridSize = (Spinner) findViewById(R.id.spinner_grid_size);

        ArrayAdapter<CharSequence> adapterGridSize = ArrayAdapter.createFromResource(
                this, R.array.grid_size, android.R.layout.simple_spinner_item);
        adapterGridSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerGridSize.setAdapter(adapterGridSize);

        // build spinner color 1
        final Spinner spinnerColor1 = (Spinner) findViewById(R.id.spinner_color1);
        List<String> color1Values = new ArrayList<String>(Arrays.asList(colorList));

        ArrayAdapter<String> adapterColor1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, color1Values);

//        ArrayAdapter<CharSequence> adapterColor1 = ArrayAdapter.createFromResource(
//                this, R.array.colour_1_arr, android.R.layout.simple_spinner_item);
        adapterColor1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerColor1.setAdapter(adapterColor1);

        // build spinner color 2
        final Spinner spinnerColor2 = (Spinner) findViewById(R.id.spinner_color2);

        ArrayAdapter<CharSequence> adapterColor2 = ArrayAdapter.createFromResource(
                this, R.array.colour_2_arr, android.R.layout.simple_spinner_item);
        adapterColor2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerColor2.setAdapter(adapterColor2);


        // prepare button for save settings
        final Button btnSaveSettings = (Button) findViewById(R.id.button);

        // register a listener to button
        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get difficulty chosen
                String textSpinnerDifficulty = spinnerDifficulty.getSelectedItem().toString();
                Log.v(LOGGING_TAG, "Difficulty: " + textSpinnerDifficulty);

                // get grid size chosen
                String textSpinnerGridSize = spinnerGridSize.getSelectedItem().toString();
                Log.v(LOGGING_TAG, "GridSize: " + textSpinnerGridSize);

                // get colors chosen
                String textSpinnerColor1 = spinnerColor1.getSelectedItem().toString();
                String textSpinnerColor2 = spinnerColor2.getSelectedItem().toString();
                int indexSpinnerColor1 = spinnerColor1.getSelectedItemPosition() - 1;  // minus one because of "Please choose" item
                int indexSpinnerColor2 = spinnerColor2.getSelectedItemPosition() - 1;  // minus one because of "Please choose" item

                Log.v(LOGGING_TAG, "spinner one item no: " + indexSpinnerColor1);
                Log.v(LOGGING_TAG, "Col1: " + textSpinnerColor1);
                Log.v(LOGGING_TAG, "Col2: " + textSpinnerColor2);

                // save the settings
                saveSettings(textSpinnerDifficulty, textSpinnerGridSize, indexSpinnerColor1, textSpinnerColor2);
            }
        });

        // decide what to do on item selected in spinner for color 1
        spinnerColor1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();

                // check which item is selected
                if(selectedItem.equals("Red"))
                {
                    // do your stuff
                    Log.v(LOGGING_TAG, "Color 1 Red chosen");
                    selCol1 = "Red";
                }
                if(selectedItem.equals("White"))
                {
                    // do your stuff
                    Log.v(LOGGING_TAG, "Color 1 White chosen");
                }
                if(selectedItem.equals("Blue"))
                {
                    // do your stuff
                    Log.v(LOGGING_TAG, "Color 1 White chosen");
                }
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

    }  // end onCreate


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings_screen, menu);
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
            case R.id.win_lose_record:
                intent = new Intent(this, WinLoseActivity.class);
                startActivity(intent);
                return true;
        }
        return false;  // nothing happened  no menu items has been selected
    }  // end onOptionsItemSelected


    public void saveSettings(String textSpinnerDifficulty,
                               String textSpinnerGridSize,
                               int itemNumberColor1,
                               String textSpinnerColor2) {

        Log.v(LOGGING_TAG, "SettingsActivity in saveSettings");

        SharedPreferences.Editor editor = sharedPreferences.edit();

//        updateDifficulty(textSpinnerDifficulty);
//        updateGridSize(textSpinnerGridSize);
        updateColor1(editor, itemNumberColor1);
//        updateColor2(textSpinnerColor2);
    }

    public void updateDifficulty(String textSpinnerDifficulty) {
        // TODO update the difficulty in shared preferences
        Log.v(LOGGING_TAG, "SettingsActivity in updateDifficulty");


    }


    public void updateGridSize(String textSpinnerGridSize) {
        // TODO update the gridsize in shared preferences
        Log.v(LOGGING_TAG, "SettingsActivity in updateGridSize");
    }


    public void updateColor1(SharedPreferences.Editor editor, int selectedColItem) {
        // TODO update the color 1 in shared preferences
        Log.v(LOGGING_TAG, "SettingsActivity in updateColor1");

        // format is: editor.putString("key", "value");
        // in our example the key/value is:
        editor.putString(COLOR_1, colorList[selectedColItem]);

        editor.commit();
        Toast.makeText(this, "Color is saved", Toast.LENGTH_SHORT).show();
    }


    public void updateColor2(String textSpinnerColor2) {
        // TODO update the color 2 in shared preferences
        Log.v(LOGGING_TAG, "SettingsActivity in updateColor2");
    }


    public void filterColorSpinner(Spinner spinner, int colorArr, String colorToFilterOut) {

        // we need the resource string array
        Resources resources = getResources();
        String[] colors = resources.getStringArray(R.array.colour_1_arr);


        // now filter it
        for (int i = 0; i < colors.length; i++) {

        }



        if (colorArr == 1) {



            ArrayAdapter<CharSequence> adapterColor1 = ArrayAdapter.createFromResource(
                    this, R.array.colour_1_arr, android.R.layout.simple_spinner_item);
            adapterColor1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(adapterColor1);
        }


    }


}  // end class SettingsActivity
