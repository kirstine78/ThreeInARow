package au.edu.holmesglen.kirstine_n.threeinarow;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import static au.edu.holmesglen.kirstine_n.threeinarow.MainActivity.LOGGING_TAG;
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
        // TODO update the theme in shared preferences
        Log.v(LOGGING_TAG, "SettingsActivity in updateTheme");

        // format is: editor.putString("key", "value");
        // in our example the key/value is:
        editor.putInt(THEME, spinnerThemeSelectedItem);
        editor.commit();


        Log.v(LOGGING_TAG, "Theme is saved: " + spinnerThemeSelectedItem);
    }
}
