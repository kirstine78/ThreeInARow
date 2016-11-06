package au.edu.holmesglen.kirstine_n.threeinarow;

/**
 * Student name:    Kirstine B. Nielsen
 * Student id:      100527988
 * Date:            01/11/2016
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
 * Class contains functions that are common to all activities in Three in a row, for example
 * accessing shared preferences.
 */
public class CommonActivity extends AppCompatActivity {

    // decl constants
    public static final String MY_PREFERENCES = "MyPrefs";
    public static final String COLOR_1 = "color1Key";
    public static final String COLOR_2 = "color2Key";
    public static final String THEME = "themeKey";
    public static final String GRID_SIZE = "gridsizeKey";

    public static final String TOTAL_GAMES = "totalgamesKey";

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


    /**
     * Gets the currently set value of the selected theme, or the default if not set.
     * @return   integer representing the theme.
     */
    public int getSavedValueTheme() {
        // To retrieve an already saved shared preference we use the contains() method
        // to check that the key value is stored in the sharedpreferences collection

        int savedTheme = 0;

        if (sharedPreferences.contains(THEME)) {
            savedTheme = sharedPreferences.getInt(THEME, 0);
        } else {
            // if no theme was saved, then save default theme
            updateThemeInSharedPreferences(0);
        }

        return savedTheme;
    }


    /**
     * Gets the currently set value of the selected grid size, or the default if not set.
     * @return   integer representing the grid size.
     */
    public int getSavedValueGridSize() {
        // To retrieve an already saved shared preference we use the contains() method
        // to check that the key value is stored in the sharedpreferences collection

        // remember we save 4, 5, or 6, and not item selected 0, 1, or 2
        int savedGridSize = 4;

        if (sharedPreferences.contains(GRID_SIZE)) {
            savedGridSize = sharedPreferences.getInt(GRID_SIZE, 4);
        } else {
            // if no grid size was saved, then save default grid size (0 will be converted to 4)
            updateGridSizeInSharedPreferences(0);
        }

        return savedGridSize;
    }


    /**
     * Gets the currently set value of the selected color 1, or the default if not set.
     * @return      integer representing the color 1. This is the index to be used in checkBoxList
     *              to set the specific check box to be checked.
     */
    public int getSavedValueColor1() {
        // To retrieve an already saved shared preference we use the contains() method
        // to check that the key value is stored in the sharedpreferences collection

        int i;
        final int DEFAULT_RED = 0;

        if (sharedPreferences.contains(COLOR_1)) {
            i = sharedPreferences.getInt(COLOR_1, DEFAULT_RED);
        } else {
            // set i to the default color
            i = DEFAULT_RED;

            // update to default
            updateColor1InSharedPreferences(DEFAULT_RED);
        }

        return i;
    }


    /**
     * Gets the currently set value of the selected color 2, or the default if not set.
     * @return      integer representing the color 2. This is the index to be used in checkBoxList
     *              to set the specific check box to be checked.
     */
    public int getSavedValueColor2() {
        // To retrieve an already saved shared preference we use the contains() method
        // to check that the key value is stored in the sharedpreferences collection

        int i;
        final int DEFAULT_WHITE = 1;

        if (sharedPreferences.contains(COLOR_2)) {
            i = sharedPreferences.getInt(COLOR_2, DEFAULT_WHITE);
        } else {
            // set i to the default color
            i = DEFAULT_WHITE;

            // update to default
            updateColor2InSharedPreferences(DEFAULT_WHITE);
        }

        return i;
    }


    /**
     * Gets the currently set value of the total games, or the default if not set.
     * @return      integer representing the total games.
     */
    public int getSavedValueTotalGames() {
        // To retrieve an already saved shared preference we use the contains() method
        // to check that the key value is stored in the sharedpreferences collection

        int savedTotalGames = 0;

        if (sharedPreferences.contains(TOTAL_GAMES)) {
            savedTotalGames = sharedPreferences.getInt(TOTAL_GAMES, 0);
        } else {
            // if no TOTAL_GAMES was saved, then save default total games 0
            saveDefaultZeroTotalGames();
        }

        return savedTotalGames;
    }


    /**
     * Saves zero as value for total games.
     */
    public void saveDefaultZeroTotalGames() {
        // format is: editor.putInt("key", value);
        // in our example the key/value is:
        editor.putInt(TOTAL_GAMES, 0);
        editor.commit();

        Log.v(LOGGING_TAG, "Total games is default: 0");
    }


    /**
     * Updates the set value of the total games.
     */
    public void updateTotalGamesInSharedPreferences() {
        // get what was saved and then add 1
        int currentlyTotalGames = getSavedValueTotalGames();
        Log.v(LOGGING_TAG, "currently Total games saved: " + currentlyTotalGames);

        // format is: editor.putInt("key", value);
        // in our example the key/value is:
        editor.putInt(TOTAL_GAMES, (currentlyTotalGames + 1));
        editor.commit();

        Log.v(LOGGING_TAG, "Total games is saved: " + (currentlyTotalGames + 1));
    }


    /**
     * Updates the set value of the theme.
     * @param spinnerThemeSelectedItem representing the theme to update to.
     */
    public void updateThemeInSharedPreferences(int spinnerThemeSelectedItem) {
        // format is: editor.putInt("key", value);
        // in our example the key/value is:
        editor.putInt(THEME, spinnerThemeSelectedItem);
        editor.commit();

        Log.v(LOGGING_TAG, "Theme is saved: " + spinnerThemeSelectedItem);
    }


    /**
     * Updates the set value of the difficulty level.
     * @param textSpinnerDifficulty representing the difficulty level to update to.
     */
    public void updateDifficultyInSharedPreferences(String textSpinnerDifficulty) {
        // TODO update the difficulty in shared preferences
        Log.v(LOGGING_TAG, "CommonActivity in updateDifficultyInSharedPreferences");
    }


    /**
     * Updates the set value of the grid size.
     * @param spinnerGridSizeSelectedItem representing the grid size to update to.
     */
    public void updateGridSizeInSharedPreferences(int spinnerGridSizeSelectedItem) {
        // TODO update the gridsize in shared preferences
        Log.v(LOGGING_TAG, "CommonActivity in updateGridSizeInSharedPreferences: " + spinnerGridSizeSelectedItem);

        // convert to the actual row/colum size (4, 5, or 6)
        if (spinnerGridSizeSelectedItem == 0) {
            spinnerGridSizeSelectedItem = 4;
        } else if (spinnerGridSizeSelectedItem == 1) {
            spinnerGridSizeSelectedItem = 5;
        } else {
            spinnerGridSizeSelectedItem = 6;
        }

        // format is: editor.putInt("key", value);
        // in our example the key/value is:
        editor.putInt(GRID_SIZE, spinnerGridSizeSelectedItem);
        editor.commit();

        Log.v(LOGGING_TAG, "Gridsize is saved: " + spinnerGridSizeSelectedItem);
    }


    /**
     * Updates the set value of the color 1.
     * @param color1 representing the color 1 to update to.
     */
    public void updateColor1InSharedPreferences(int color1) {
        // format is: editor.putInt("key", value);
        // in our example the key/value is:
        editor.putInt(COLOR_1, color1);
        editor.commit();

        Log.v(LOGGING_TAG, "Color1 is saved");
    }


    /**
     * Updates the set value of the color 2.
     * @param color2 representing the color 2 to update to.
     */
    public void updateColor2InSharedPreferences(int color2) {
        // TODO update the color 2 in shared preferences
        Log.v(LOGGING_TAG, "CommonActivity in updateColor2InSharedPreferences");

        // format is: editor.putInt("key", value);
        // in our example the key/value is:
        editor.putInt(COLOR_2, color2);
        editor.commit();

        Log.v(LOGGING_TAG, "Color2 is saved");
    }
}  // end CommonActivity

