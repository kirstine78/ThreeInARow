package au.edu.holmesglen.kirstine_n.threeinarow;

/**
 * Student name:    Kirstine B. Nielsen
 * Student id:      100527988
 * Date:            01/1/2016
 * Project:         Three in a row
 * Version:         1.1
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import static au.edu.holmesglen.kirstine_n.threeinarow.MainActivity.LOGGING_TAG;

/**
 * Created by Kirsti on 1/11/2016.
 */

public class CommonActivity extends AppCompatActivity {

    // decl constants
    public static final String MY_PREFERENCES = "MyPrefs";
    public static final String COLOR_1 = "color1Key";
    public static final String COLOR_2 = "color2Key";
    public static final String THEME = "themeKey";
    public static final String GRID_SIZE = "gridsizeKey";

    // decl reference to SharedPreferences class
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set up preferences collection
        sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // set theme of this activity to the globally chosen theme
        Utils.sTheme = getSavedValueTheme();  // get saved theme, if nothing there, then default 0
        Utils.onActivityCreateSetTheme(this);
    }


    public int getSavedValueTheme() {
        // To retrieve an already saved shared preference we use the contains() method
        // to check that the key value is stored in the sharedpreferences collection

        int savedTheme = 0;

        if (sharedPreferences.contains(THEME)) {
            savedTheme = sharedPreferences.getInt(THEME, 0);
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            updateThemeInSharedPreferences(0);
        }

        return savedTheme;
    }


    public int getSavedValueGridSize() {
        // To retrieve an already saved shared preference we use the contains() method
        // to check that the key value is stored in the sharedpreferences collection

        int savedGridSize = 0;

        if (sharedPreferences.contains(GRID_SIZE)) {
            savedGridSize = sharedPreferences.getInt(GRID_SIZE, 0);
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            updateThemeInSharedPreferences(0);
        }

        return savedGridSize;
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

            // update to default
            updateColor1InSharedPreferences(DEFAULT_RED);
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

            // update to default
            updateColor2InSharedPreferences(DEFAULT_WHITE);
        }

        return i;
    }



    public void updateThemeInSharedPreferences(int spinnerThemeSelectedItem) {
        // format is: editor.putString("key", "value");
        // in our example the key/value is:
        editor.putInt(THEME, spinnerThemeSelectedItem);
        editor.commit();

        Log.v(LOGGING_TAG, "Theme is saved: " + spinnerThemeSelectedItem);
    }


    /**
     * saves the difficulty level to local storage
     * @param textSpinnerDifficulty
     */
    public void updateDifficultyInSharedPreferences(String textSpinnerDifficulty) {
        // TODO update the difficulty in shared preferences
        Log.v(LOGGING_TAG, "CommonActivity in updateDifficultyInSharedPreferences");
    }


    /**
     * saves the difficulty grid size to local storage
     * @param spinnerGridSizeSelectedItem
     */
    public void updateGridSizeInSharedPreferences(int spinnerGridSizeSelectedItem) {
        // TODO update the gridsize in shared preferences
        Log.v(LOGGING_TAG, "CommonActivity in updateGridSizeInSharedPreferences: " + spinnerGridSizeSelectedItem);
        // format is: editor.putString("key", "value");
        // in our example the key/value is:
        editor.putInt(GRID_SIZE, spinnerGridSizeSelectedItem);
        editor.commit();

        Log.v(LOGGING_TAG, "Gridsize is saved: " + spinnerGridSizeSelectedItem);
    }


    /**
     * saves color 1 to local storage
     * @param color1
     */
    public void updateColor1InSharedPreferences(int color1) {
        // format is: editor.putString("key", "value");
        // in our example the key/value is:
        editor.putInt(COLOR_1, color1);
        editor.commit();

        Log.v(LOGGING_TAG, "Color1 is saved");
    }


    /**
     * saves color 2 to local storage
     * @param color2
     */
    public void updateColor2InSharedPreferences(int color2) {
        // TODO update the color 2 in shared preferences
        Log.v(LOGGING_TAG, "CommonActivity in updateColor2InSharedPreferences");

        // format is: editor.putString("key", "value");
        // in our example the key/value is:
        editor.putInt(COLOR_2, color2);
        editor.commit();

        Log.v(LOGGING_TAG, "Color2 is saved");
    }




}
