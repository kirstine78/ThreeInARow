package au.edu.holmesglen.kirstine_n.threeinarow;

/**
 * Student name:    Kirstine B. Nielsen
 * Student id:      100527988
 * Date:            13/10/2016
 * Project:         Three in a row
 * Version:         1.3
 * Code taken from: http://stackoverflow.com/questions/18301554/android-change-app-theme-on-onclick
 */

import android.app.Activity;
import android.content.Intent;

/**
 * Common utility static methods, for example to change theme of an activity.
 */
public class Utils
{
    public static int sTheme;
    public final static int THEME_DEFAULT = 0;  // BLUE
    public final static int THEME_COPPER = 1;
    public final static int THEME_GOLD = 2;


    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */
    public static void changeToTheme(Activity activity, int theme)
    {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }


    /**
     * Set the theme of the activity, according to the configuration.
     */
    public static void onActivityCreateSetTheme(Activity activity)
    {
        switch (sTheme)
        {
            default:
            case THEME_DEFAULT:
                activity.setTheme(R.style.myThemeBlue);  // get resource from themes.xml
                break;
            case THEME_COPPER:
                activity.setTheme(R.style.myThemeCopper);
                break;
            case THEME_GOLD:
                activity.setTheme(R.style.myThemeGold);
                break;
        }
    }
}  // end class Utils
