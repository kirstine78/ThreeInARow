package au.edu.holmesglen.kirstine_n.threeinarow;

/**
 * Student name:    Kirstine B. Nielsen
 * Student id:      100527988
 * Date:            01.11.2016
 * Project:         Three in a row
 * Version:         1.3
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import static au.edu.holmesglen.kirstine_n.threeinarow.Utils.changeToTheme;

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

    // will be 0, 1, or 2 (integers representing easy, medium, or hard)
    public static final String DIFFICULTY = "difficultKey";

    // integer representing seconds
    public static final String BEST_TIME_4X4_EASY =     "best_time_4x4_easy_Key";
    public static final String BEST_TIME_4X4_MEDIUM =   "best_time_4x4_medium_Key";
    public static final String BEST_TIME_4X4_HARD =     "best_time_4x4_hard_Key";

    public static final String BEST_TIME_5X5_EASY =     "best_time_5x5_easy_Key";
    public static final String BEST_TIME_5X5_MEDIUM =   "best_time_5x5_medium_Key";
    public static final String BEST_TIME_5X5_HARD =     "best_time_5x5_hard_Key";

    public static final String BEST_TIME_6X6_EASY =     "best_time_6x6_easy_Key";
    public static final String BEST_TIME_6X6_MEDIUM =   "best_time_6x6_medium_Key";
    public static final String BEST_TIME_6X6_HARD =     "best_time_6x6_hard_Key";

    public static final String[][] BEST_TIME_KEY_LIST = {
                                    {BEST_TIME_4X4_EASY, BEST_TIME_4X4_MEDIUM, BEST_TIME_4X4_HARD},
                                    {BEST_TIME_5X5_EASY, BEST_TIME_5X5_MEDIUM, BEST_TIME_5X5_HARD},
                                    {BEST_TIME_6X6_EASY, BEST_TIME_6X6_MEDIUM, BEST_TIME_6X6_HARD}
                                };

    // array to hold milliseconds from each grid size
    public int[][] listMillisecondsArray = {
            { 25000, 20000, 15000 },    // 4x4 - easy, medium, hard
            { 40000, 30000, 20000 },    // 5x5 - easy, medium, hard
            { 55000, 40000, 25000 }     // 6x6 - easy, medium, hard
    } ;

    private final int NO_SCORE_YET_DEFAULT_TIME = -1000;

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


    @Override
    protected void onRestart() {
        super.onRestart();  // Always call the superclass method first
        // make sure to get the correct theme

        // set theme of this activity to the globally chosen theme
        Utils.sTheme = getSavedValueTheme();  // get saved theme, if nothing there, then default 0
        changeToTheme(this, Utils.sTheme);  // make sure theme is correct on restart (using backbutton in other activity)
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
     * Gets the currently set value of the difficulty, or the default if not set.
     * @return   integer representing the difficulty.
     */
    public int getSavedValueDifficulty() {
        // To retrieve an already saved shared preference we use the contains() method
        // to check that the key value is stored in the sharedpreferences collection

        int savedDifficulty = 0;

        if (sharedPreferences.contains(DIFFICULTY)) {
            savedDifficulty = sharedPreferences.getInt(DIFFICULTY, 0);  // Default zero (Easy)
        } else {
            // if no difficulty was saved, then save default difficulty
            updateDifficultyInSharedPreferences(0);  // Default zero (Easy)
        }

        // Log.v(LOGGING_TAG, "in getSavedValueDifficulty and the saved value difficulty: " + savedDifficulty);
        return savedDifficulty;
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
     * Gets the currently set value of the best time for a specific gridsize and specific difficulty
     * @param gridsize   the specific gridsize relevant to the best time
     * @param difficulty   the specific difficulty relevant to the best time
     * @return   int representing currently save time in milliseconds
     */
    public int getSavedValueBestTime(int gridsize, int difficulty) {

        // find out the grid size and difficulty so you can decide which best time to fetch
        String keyNameToGetInSharedPreferences = getCorrectKeyNameBestTime(gridsize, difficulty);
        // Log.v(LOGGING_TAG, "THE keyNameToGetInSharedPreferences: " + keyNameToGetInSharedPreferences);

        // To retrieve an already saved shared preference we use the contains() method
        // to check that the key value is stored in the sharedpreferences collection

        int i;

        // this to make sure to be able to differentiate between showing a time or not
        int defaultTime = NO_SCORE_YET_DEFAULT_TIME;

        // Log.v(LOGGING_TAG, "defaultTime: " + defaultTime);

        if (sharedPreferences.contains(keyNameToGetInSharedPreferences)) {
            i = sharedPreferences.getInt(keyNameToGetInSharedPreferences, defaultTime);
            // Log.v(LOGGING_TAG, "contains, i: " + i);
        } else {
            // set i to the default time
            i = defaultTime;
            // Log.v(LOGGING_TAG, "not contains i: " + i);

            // update to default for the specific grid size and difficulty
            updateBestTime(defaultTime, gridsize, difficulty);
        }

        // Log.v(LOGGING_TAG, "i: " + i);

        return i;
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

        // Log.v(LOGGING_TAG, "Theme is saved: " + spinnerThemeSelectedItem);
    }


    /**
     * Updates the set value of the difficulty level.
     * @param spinnerDifficultySelectedItem representing the difficulty level to update to.
     */
    public void updateDifficultyInSharedPreferences(int spinnerDifficultySelectedItem) {
        // Log.v(LOGGING_TAG, "CommonActivity in updateDifficultyInSharedPreferences");

        // format is: editor.putInt("key", value);
        // in our example the key/value is:
        editor.putInt(DIFFICULTY, spinnerDifficultySelectedItem);
        editor.commit();

        // Log.v(LOGGING_TAG, "Difficulty is saved: " + spinnerDifficultySelectedItem);
    }


    /**
     * Updates the set value of the grid size.
     * @param spinnerGridSizeSelectedItem representing the grid size to update to.
     */
    public void updateGridSizeInSharedPreferences(int spinnerGridSizeSelectedItem) {
        // Log.v(LOGGING_TAG, "CommonActivity in updateGridSizeInSharedPreferences: " + spinnerGridSizeSelectedItem);

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

        // Log.v(LOGGING_TAG, "Gridsize is saved: " + spinnerGridSizeSelectedItem);
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

        // Log.v(LOGGING_TAG, "Color1 is saved");
    }


    /**
     * Updates the set value of the color 2.
     * @param color2 representing the color 2 to update to.
     */
    public void updateColor2InSharedPreferences(int color2) {
        // Log.v(LOGGING_TAG, "CommonActivity in updateColor2InSharedPreferences");

        // format is: editor.putInt("key", value);
        // in our example the key/value is:
        editor.putInt(COLOR_2, color2);
        editor.commit();

        // Log.v(LOGGING_TAG, "Color2 is saved");
    }


    /**
     * Updates the set value of the best time for a specific gridsize and specific difficulty.
     * @param newTimeInMilliseconds   new time
     * @param whichGridsize   specific gridsize
     * @param whichDifficulty   specific difficulty
     */
    public void updateBestTime(int newTimeInMilliseconds, int whichGridsize, int whichDifficulty) {
        // replacement process
        // Log.v(LOGGING_TAG, "CommonActivity in updateBestTime");

        // format is: editor.putInt("key", value);
        // in our example the key/value is:
        editor.putInt(getCorrectKeyNameBestTime(whichGridsize, whichDifficulty), newTimeInMilliseconds);
        editor.commit();

        // Log.v(LOGGING_TAG, "new best time is saved");
    }


    /**
     * Checks if the new winner time is better than the currently set value of the best time
     * @param newTimeInMilliseconds   new time
     * @return   boolean representing whether new time is better or not.
     */
    public boolean isNewTimeBetterThanCurrentBestTime(int newTimeInMilliseconds) {
        boolean newTimeIsBetter = false;

        int savedGridSize = getSavedValueGridSize() % 4;    // returns 4, 5, or 6 - hence the % 4
        int savedDifficulty = getSavedValueDifficulty();  // 0, 1, or 2

        // check if it is default time (-1000) which means player has never produced a best time and
        // also check if new time is better then replace
        if (getSavedValueBestTime(savedGridSize, savedDifficulty) < 0 ||
                newTimeInMilliseconds < getSavedValueBestTime(savedGridSize, savedDifficulty)) {
            newTimeIsBetter = true;
        }
        return newTimeIsBetter;
    }


    /**
     * Gets the key name for shared preferences regarding best time.
     * The key name depends of which gridsize and difficulty the game is currently dealing with
     * @param gridSize   specific gridsize
     * @param difficulty   specific difficulty
     * @return   String representing the key name
     */
    public String getCorrectKeyNameBestTime(int gridSize, int difficulty) {
        String keyNameToGetInSharedPreferences = BEST_TIME_KEY_LIST[gridSize][difficulty];
        // Log.v(LOGGING_TAG, "in getCorrectKeyNameBestTime, keyNameToGetInSharedPreferences: " + keyNameToGetInSharedPreferences);

        return keyNameToGetInSharedPreferences;
    }

}  // end CommonActivity

