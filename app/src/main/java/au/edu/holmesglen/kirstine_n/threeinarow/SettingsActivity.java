package au.edu.holmesglen.kirstine_n.threeinarow;

/**
 * Student name:    Kirstine B. Nielsen
 * Student id:      100527988
 * Date:            13/10/2016
 * Project:         Three in a row
 * Version:         1.1
 */

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import static au.edu.holmesglen.kirstine_n.threeinarow.MainActivity.LOGGING_TAG;

/**
 * SettingsActivity is used for storing preferences in phones local storage
 * You can save difficulty of game, grid size, colors etc.
 */
public class SettingsActivity extends CommonActivity {

    private CheckBox[] checkBoxList = new CheckBox[ThreeRow.COLOR_LIST.length];
    private int[] colorIndexList = new int[2];

    private final String[] COLOR_STRING_LIST = {"Red", "White", "Blue", "Green","Pink", "Purple",
                                                "Brown", "Orange", "Turquoise", "Yellow"};

    private Spinner spinnerTheme;
    private Spinner spinnerDifficulty;
    private Spinner spinnerGridSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.v(LOGGING_TAG, "SettingsActivity in onCreate");

        LinearLayout llChkBoxContainerLeft = (LinearLayout)findViewById(R.id.checkboxLinearLayoutContainerLeft);
        LinearLayout llChkBoxContainerRight = (LinearLayout)findViewById(R.id.checkboxLinearLayoutContainerRight);

        // build the checkboxes dynamically
        for(int i = 0; i < ThreeRow.COLOR_LIST.length; i++) {
            CheckBox cb = new CheckBox(this);
            cb.setText(COLOR_STRING_LIST[i]);
            cb.setId(i + ThreeRow.COLOR_LIST.length);
            cb.setPadding(8, 32, 16, 32);  // left, top, right, bottom
            cb.setTextColor(Color.WHITE);
            cb.setTextSize(16);

            // check which container to add checkbox to? left or right
            if (i < ThreeRow.COLOR_LIST.length/2)
            {
                Log.v(LOGGING_TAG, "chk box i: " + i + " left");
                llChkBoxContainerLeft.addView(cb);
            }
            else
            {
                Log.v(LOGGING_TAG, "chk box i: " + i + " right");
                llChkBoxContainerRight.addView(cb);
            }

            // add check box to the list of check boxes
            checkBoxList[i] = cb;
        }

        // build spinner Theme and set adapter
        spinnerTheme = (Spinner) findViewById(R.id.spinner_theme);
        spinnerTheme.setAdapter(getArrayAdapter(R.array.theme));

        // build spinner Difficulty and set adapter
        spinnerDifficulty = (Spinner) findViewById(R.id.spinner_difficulty);
        spinnerDifficulty.setAdapter(getArrayAdapter(R.array.difficulty));

        // build spinner grid size and set adapter
        spinnerGridSize = (Spinner) findViewById(R.id.spinner_grid_size);
        spinnerGridSize.setAdapter(getArrayAdapter(R.array.grid_size));

        // set settings VALUES from to what they are saved as in shared preferences (VALUES, not how it looks)
        displaySavedValues();

        // prepare button for save settings
        final Button btnSaveSettings = (Button) findViewById(R.id.button);

        // I want to change the color of the existing background resource.
        // To avoid overwriting/removing any existing background resource, including any borders,
        // corners, padding, etc. use the below instead of setBackgoundColor.
//        btnSaveSettings.getBackground().setColorFilter(Color.parseColor("#2D57B9"), PorterDuff.Mode.DARKEN);
//        btnSaveSettings.setTextColor(Color.parseColor("#ffffff"));

        // register a listener to button
        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // make sure that exactly two colors are chosen
                final int DESIRED_CHECKBOXES_CHECKED = 2;
                int amountChkBoxChecked = 0;

                // find out how many check boxes for colors were checked
                for (int i = 0; i < ThreeRow.COLOR_LIST.length; i++) {
                    if (checkBoxList[i].isChecked()) {
                        Log.v(LOGGING_TAG, "chk checked: " + i);
                        amountChkBoxChecked++;
                    }
                }

                Log.v(LOGGING_TAG, "amount checked: " + amountChkBoxChecked);

                // check if amount of colors chosen is exactly 2
                if (amountChkBoxChecked == DESIRED_CHECKBOXES_CHECKED) {
                    // ok to proceed with saving

                    // do the THEME spinner
                    int spinnerThemeSelectedItem = spinnerTheme.getSelectedItemPosition();

                    // do the COLORS
                    // put the correct indexes chosen from checkBoxList array into the colorIndexList array
                    populateColorIndexList();

                    Log.v(LOGGING_TAG, "colorIndexList 0: " + colorIndexList[0] );
                    Log.v(LOGGING_TAG, "colorIndexList 1: " + colorIndexList[1] );

                    // get difficulty chosen
                    String textSpinnerDifficulty = spinnerDifficulty.getSelectedItem().toString();
                    Log.v(LOGGING_TAG, "Difficulty: " + textSpinnerDifficulty);

                    // get GRID SIZE spinner
                    int spinnerGridSizeSelectedItem = spinnerGridSize.getSelectedItemPosition();
//                    String textSpinnerGridSize = spinnerGridSize.getSelectedItem().toString();
//                    Log.v(LOGGING_TAG, "GridSize: " + textSpinnerGridSize);

                    // save the settings
                    saveSettings(spinnerThemeSelectedItem, textSpinnerDifficulty, spinnerGridSizeSelectedItem, colorIndexList[0], colorIndexList[1]);
                } else {
                    // not ok amount of checkbox colors chosen, so don't proceed with any saving
                    Toast.makeText(SettingsActivity.this, "Please choose exactly two colors", Toast.LENGTH_SHORT).show();
                }
            }
        });  // end btnSaveSettings.setOnClickListener

    }  // end onCreate


    /**
     * will populate our colorIndexList array with the checkboxes chosen from the checkBoxList array
     */
    public void populateColorIndexList() {
        int index = 0;  // will never be more than 1

        // we know only two color checkboxes are checked
        for (int i = 0; i < ThreeRow.COLOR_LIST.length; i++)
        {
            // if checked, then assign i to colorIndexList[index]
            if (checkBoxList[i].isChecked()) {
                Log.v(LOGGING_TAG, "chk checked: " + i);
                // add to list
                colorIndexList[index] = i;
                index++;
            }
        }
    }


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


    /**
     * create an adapter using string array from resources in strings.xml
     * @param someStringArray
     * @return   ArrayAdapter with values from resources
     */
    public ArrayAdapter<CharSequence> getArrayAdapter(int someStringArray){
        ArrayAdapter<CharSequence> adapterDifficulty = ArrayAdapter.createFromResource(
                this, someStringArray, android.R.layout.simple_spinner_item);
        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return adapterDifficulty;
    }


    /**
     * save the settings chosen
     * @param textSpinnerDifficulty
     * @param spinnerGridSizeSelectedItem
     * @param itemNumberColor1
     * @param itemNumberColor2
     */
    public void saveSettings(int spinnerThemeSelectedItem, String textSpinnerDifficulty, int spinnerGridSizeSelectedItem,
                               int itemNumberColor1, int itemNumberColor2) {

        Log.v(LOGGING_TAG, "SettingsActivity in saveSettings");

        updateThemeInSharedPreferences(spinnerThemeSelectedItem);
//        updateDifficultyInSharedPreferences(textSpinnerDifficulty);
        updateGridSizeInSharedPreferences(spinnerGridSizeSelectedItem);
        updateColor1InSharedPreferences(itemNumberColor1);
        updateColor2InSharedPreferences(itemNumberColor2);

        // change the theme GUI
        Utils.changeToTheme(this, spinnerThemeSelectedItem);

        // change button GUI


        Toast.makeText(this, "Settings are saved", Toast.LENGTH_SHORT).show();
    }


    /**
     * display saved values so spinners, check boxes etc are display correct values (values from
     * the storage)
     */
    public void displaySavedValues() {
        // TODO put code here
        Log.v(LOGGING_TAG, "SettingsActivity in displaySavedValues");

        displaySavedValueTheme(getSavedValueTheme());
        displaySavedValueGridSize(getSavedValueGridSize());

        displaySavedValueColor1(getSavedValueColor1());
        displaySavedValueColor2(getSavedValueColor2());
    }


    public void displaySavedValueTheme(int theme) {
        // set the spinner to this theme
        Log.v(LOGGING_TAG, "SettingsActivity in displaySavedValueTheme: " + theme);

        // set correct spinner item to be the selected one (based on value in storage)
        spinnerTheme.setSelection(theme);
    }


    public void displaySavedValueGridSize(int gridsize) {
        // set the spinner to this theme
        Log.v(LOGGING_TAG, "SettingsActivity in displaySavedValueGridSize: " + gridsize);

        // convert the row/colum size (4, 5, or 6) to (0, 1, or 2)
        if (gridsize == 4) {
            gridsize = 0;
        } else if (gridsize == 5) {
            gridsize = 1;
        } else {
            gridsize = 2;
        }

        // set correct spinner item to be the selected one (based on value in storage)
        spinnerGridSize.setSelection(gridsize);
    }


    /**
     * set the correct checkbox for a color 1 to be checked
     * @param checkbox
     */
    public void displaySavedValueColor1(int checkbox) {
        checkBoxList[checkbox].setChecked(true);
    }


    /**
     * set the correct checkbox for a color 2 to be checked
     * @param checkbox
     */
    public void displaySavedValueColor2(int checkbox) {
        checkBoxList[checkbox].setChecked(true);
    }

}  // end class SettingsActivity
