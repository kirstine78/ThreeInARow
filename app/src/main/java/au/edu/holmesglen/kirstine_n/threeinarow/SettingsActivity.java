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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
public class SettingsActivity extends AppCompatActivity {

    // decl constants
    public static final String MY_PREFERENCES = "MyPrefs";
    public static final String COLOR_1 = "color1Key";
    public static final String COLOR_2 = "color2Key";
    public static final String THEME = "themeKey";

    private CheckBox[] checkBoxList = new CheckBox[ThreeRow.COLOR_LIST.length];
    private int[] colorIndexList = new int[2];

    private final String[] COLOR_STRING_LIST = {"Red", "White", "Blue", "Green","Pink", "Purple",
                                                "Brown", "Orange", "Turquoise", "Yellow"};


    // list of images with different colors
    final static int[] THEME_LIST = {R.drawable.blue_background, R.drawable.copper, R.drawable.gold};

    // decl reference to SharedPreferences class
    private SharedPreferences sharedPreferences;

    private Spinner spinnerTheme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // set up preferences collection
        sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);

        // set theme of this activity to the globally chosen theme
        Utils.sTheme = getSavedValueTheme();  // get saved theme, if nothing there, then default 0
        Utils.onActivityCreateSetTheme(this);

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



        // build spinner Theme
//        final Spinner spinnerTheme = (Spinner) findViewById(R.id.spinner_theme);
        spinnerTheme = (Spinner) findViewById(R.id.spinner_theme);

        ArrayAdapter<CharSequence> adapterTheme = ArrayAdapter.createFromResource(
                this, R.array.theme, android.R.layout.simple_spinner_item);
        adapterTheme.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerTheme.setAdapter(adapterTheme);



        // build spinner Difficulty
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



        // set settings to what was saved (VALUES, not how it looks)
        displaySavedValues();




        // prepare button for save settings
        final Button btnSaveSettings = (Button) findViewById(R.id.button);

        // register a listener to button
        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // make sure that exactly two colors are chosen
                int amountChkBoxChecked = 0;
                int desiredColorsAmountChosen = 2;

                for (int i = 0; i < ThreeRow.COLOR_LIST.length; i++) {
                    if (checkBoxList[i].isChecked()) {
                        Log.v(LOGGING_TAG, "chk checked: " + i);
                        amountChkBoxChecked++;
                    }
                }


                Log.v(LOGGING_TAG, "amount checked: " + amountChkBoxChecked);

                // check how many colors chosen
                if (amountChkBoxChecked == desiredColorsAmountChosen) {

                    // do the THEME spinner
//                    String textSpinnerTheme = (String)spinnerTheme.getSelectedItem();

                    int spinnerThemeSelectedItem = spinnerTheme.getSelectedItemPosition();

                    int index = 0;  // will never be other than 0 and 1

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

                    Log.v(LOGGING_TAG, "colorIndexList 0: " + colorIndexList[0] );
                    Log.v(LOGGING_TAG, "colorIndexList 1: " + colorIndexList[1] );

                    // 2 colors are chosen

                    // get difficulty chosen
                    String textSpinnerDifficulty = spinnerDifficulty.getSelectedItem().toString();
                    Log.v(LOGGING_TAG, "Difficulty: " + textSpinnerDifficulty);

                    // get grid size chosen
                    String textSpinnerGridSize = spinnerGridSize.getSelectedItem().toString();
                    Log.v(LOGGING_TAG, "GridSize: " + textSpinnerGridSize);

                    // save the settings
                    saveSettings(spinnerThemeSelectedItem, textSpinnerDifficulty, textSpinnerGridSize, colorIndexList[0], colorIndexList[1]);
                } else {
                    // not ok amount, so don't proceed with any saving
                    Toast.makeText(SettingsActivity.this, "Please choose exactly two colors", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // decide what to do on item selected in spinner for color 1
//        spinnerColor1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
//        {
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
//            {
//                String selectedItem = parent.getItemAtPosition(position).toString();
//
//                // check which item is selected
//                if(selectedItem.equals("Red"))
//                {
//                    // do your stuff
//                    Log.v(LOGGING_TAG, "Color 1 Red chosen");
//                    selCol1 = "Red";
//                }
//                if(selectedItem.equals("White"))
//                {
//                    // do your stuff
//                    Log.v(LOGGING_TAG, "Color 1 White chosen");
//                }
//                if(selectedItem.equals("Blue"))
//                {
//                    // do your stuff
//                    Log.v(LOGGING_TAG, "Color 1 White chosen");
//                }
//            } // to close the onItemSelected
//            public void onNothingSelected(AdapterView<?> parent)
//            {
//
//            }
//        });

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


    /**
     * save the settings chosen
     * @param textSpinnerDifficulty
     * @param textSpinnerGridSize
     * @param itemNumberColor1
     * @param itemNumberColor2
     */
    public void saveSettings(int spinnerThemeSelectedItem, String textSpinnerDifficulty, String textSpinnerGridSize,
                               int itemNumberColor1, int itemNumberColor2) {

        Log.v(LOGGING_TAG, "SettingsActivity in saveSettings");
        SharedPreferences.Editor editor = sharedPreferences.edit();

        updateTheme(editor, spinnerThemeSelectedItem);
//        updateDifficulty(editor, textSpinnerDifficulty);
//        updateGridSize(editor, textSpinnerGridSize);
        updateColor1(editor, itemNumberColor1);
        updateColor2(editor, itemNumberColor2);
        Toast.makeText(this, "Settings are saved", Toast.LENGTH_SHORT).show();
    }


    /**
     * saves the theme to local storage
     * @param editor
     * @param spinnerThemeSelectedItem
     */
    public void updateTheme(SharedPreferences.Editor editor, int spinnerThemeSelectedItem) {
        // TODO update the theme in shared preferences
        Log.v(LOGGING_TAG, "SettingsActivity in updateTheme");

        // format is: editor.putString("key", "value");
        // in our example the key/value is:
        editor.putInt(THEME, spinnerThemeSelectedItem);
        editor.commit();

        // change the theme GUI for SettingsActivity
        Utils.changeToTheme(this, spinnerThemeSelectedItem);

        Log.v(LOGGING_TAG, "Theme is saved: " + spinnerThemeSelectedItem);
    }


    /**
     * saves the difficulty level to local storage
     * @param textSpinnerDifficulty
     */
    public void updateDifficulty(SharedPreferences.Editor editor, String textSpinnerDifficulty) {
        // TODO update the difficulty in shared preferences
        Log.v(LOGGING_TAG, "SettingsActivity in updateDifficulty");
    }


    /**
     * saves the difficulty grid size to local storage
     * @param textSpinnerGridSize
     */
    public void updateGridSize(SharedPreferences.Editor editor, String textSpinnerGridSize) {
        // TODO update the gridsize in shared preferences
        Log.v(LOGGING_TAG, "SettingsActivity in updateGridSize");
    }


    /**
     * saves color 1 to local storage
     * @param editor
     * @param color1
     */
    public void updateColor1(SharedPreferences.Editor editor, int color1) {
        // TODO update the color 1 in shared preferences
        Log.v(LOGGING_TAG, "SettingsActivity in updateColor1");

        // format is: editor.putString("key", "value");
        // in our example the key/value is:
        editor.putInt(COLOR_1, color1);
        editor.commit();

        Log.v(LOGGING_TAG, "Color1 is saved");
    }


    /**
     * saves color 2 to local storage
     * @param editor
     * @param color2
     */
    public void updateColor2(SharedPreferences.Editor editor, int color2) {
        // TODO update the color 2 in shared preferences
        Log.v(LOGGING_TAG, "SettingsActivity in updateColor2");

        // format is: editor.putString("key", "value");
        // in our example the key/value is:
        editor.putInt(COLOR_2, color2);
        editor.commit();

        Log.v(LOGGING_TAG, "Color2 is saved");
    }


    /**
     * display saved values so spinners, check boxes etc are display correct values (values from
     * the storage)
     */
    public void displaySavedValues() {
        // TODO put code here
        Log.v(LOGGING_TAG, "SettingsActivity in displaySavedValues");


        displaySavedValueTheme(getSavedValueTheme());

        displaySavedValueColor1(getSavedValueColor1());
        displaySavedValueColor2(getSavedValueColor2());
    }


    public int getSavedValueTheme() {
        // To retrieve an already saved shared preference we use the contains() method
        // to check that the key value is stored in the sharedpreferences collection

        int savedTheme = 0;

        if (sharedPreferences.contains(THEME)) {
            savedTheme = sharedPreferences.getInt(THEME, 0);
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            updateTheme(editor, 0);
        }

        return savedTheme;
    }


    /**
     * fetch saved color 1 from local storage
     * @return an integer representing the index to be used in checkBoxList to set the specific check
     * box to be checked.
     */
    public int getSavedValueColor1() {
        // To retrieve an already saved shared preference we use the contains() method
        // to check that the key value is stored in the sharedpreferences collection

        int i = 0;
        final int DEFAULT_RED = 0;

        if (sharedPreferences.contains(COLOR_1)) {
            i = sharedPreferences.getInt(COLOR_1, DEFAULT_RED);
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            // set i to the default color
            i = DEFAULT_RED;

            updateColor1(editor, DEFAULT_RED);
        }

        return i;
    }


    /**
     * fetch saved color 2 from local storage
     * @returnan integer representing the index to be used in checkBoxList to set the specific check
     * box to be checked.
     */
    public int getSavedValueColor2() {
        // To retrieve an already saved shared preference we use the contains() method
        // to check that the key value is stored in the sharedpreferences collection

        int i = 0;
        final int DEFAULT_WHITE = 1;

        if (sharedPreferences.contains(COLOR_2)) {
            i = sharedPreferences.getInt(COLOR_2, DEFAULT_WHITE);
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            // set i to the default color
            i = DEFAULT_WHITE;

            updateColor2(editor, DEFAULT_WHITE);
        }

        return i;
    }


    public void displaySavedValueTheme(int theme) {
        // set the spinner to this theme
        Log.v(LOGGING_TAG, "SettingsActivity in displaySavedValueTheme: " + theme);

        // set correct spinner item to be the selected one (based on value in storage)
        spinnerTheme.setSelection(theme);
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
