package au.edu.holmesglen.kirstine_n.threeinarow;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import static au.edu.holmesglen.kirstine_n.threeinarow.MainActivity.LOGGING_TAG;
import static au.edu.holmesglen.kirstine_n.threeinarow.SettingsActivity.COLOR_1;
import static au.edu.holmesglen.kirstine_n.threeinarow.SettingsActivity.COLOR_2;
import static au.edu.holmesglen.kirstine_n.threeinarow.SettingsActivity.MY_PREFERENCES;
import static au.edu.holmesglen.kirstine_n.threeinarow.SettingsActivity.THEME;

/**
 * Created by Kirsti on 1/11/2016.
 */

public class CommonActivity extends AppCompatActivity {

    // decl reference to SharedPreferences class
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set up preferences collection
        sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);

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
            updateThemeInSharedPreferences(editor, 0);
        }

        return savedTheme;
    }



    public void updateThemeInSharedPreferences(SharedPreferences.Editor editor, int spinnerThemeSelectedItem) {
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
    public void updateDifficultyInSharedPreferences(SharedPreferences.Editor editor, String textSpinnerDifficulty) {
        // TODO update the difficulty in shared preferences
        Log.v(LOGGING_TAG, "SettingsActivity in updateDifficulty");
    }


    /**
     * saves the difficulty grid size to local storage
     * @param textSpinnerGridSize
     */
    public void updateGridSizeInSharedPreferences(SharedPreferences.Editor editor, String textSpinnerGridSize) {
        // TODO update the gridsize in shared preferences
        Log.v(LOGGING_TAG, "SettingsActivity in updateGridSize");
    }


    /**
     * saves color 1 to local storage
     * @param editor
     * @param color1
     */
    public void updateColor1InSharedPreferences(SharedPreferences.Editor editor, int color1) {
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
    public void updateColor2InSharedPreferences(SharedPreferences.Editor editor, int color2) {
        // TODO update the color 2 in shared preferences
        Log.v(LOGGING_TAG, "SettingsActivity in updateColor2InSharedPreferences");

        // format is: editor.putString("key", "value");
        // in our example the key/value is:
        editor.putInt(COLOR_2, color2);
        editor.commit();

        Log.v(LOGGING_TAG, "Color2 is saved");
    }

}
