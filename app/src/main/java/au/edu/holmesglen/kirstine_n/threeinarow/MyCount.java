package au.edu.holmesglen.kirstine_n.threeinarow;

/**
 * Student name:    Kirstine B. Nielsen
 * Student id:      100527988
 * Date:            02/11/2016
 * Project:         Three in a row
 * Version:         1.1
 */

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static au.edu.holmesglen.kirstine_n.threeinarow.MainActivity.LOGGING_TAG;

/**
 * Created by Kirsti on 2/11/2016.
 */

public class MyCount extends CountDownTimer {

    private TextView tvTimerValue;  // will show the seconds remaining
    private TextView tvInfoText;    // will show the txt "Next:"
    private ImageView ivInfoImage;  // will show the next color
    private ThreeRow game;
    public long millisecondsLeft;

    /**
     * constructor
     * @param millisInFuture
     * @param countDownInterval
     * @param tvTimerValue
     * @param aGame
     */
    public MyCount(long millisInFuture, long countDownInterval,
                   TextView tvTimerValue, TextView tvInfoText,
                   ImageView ivInfoImage, ThreeRow aGame) {

        super(millisInFuture, countDownInterval);
        this.tvTimerValue = tvTimerValue;
        this.tvInfoText = tvInfoText;
        this.ivInfoImage = ivInfoImage;
        this.game = aGame;
        this.millisecondsLeft = millisInFuture;
    }


    @Override
    public void onFinish() {
        Log.v(LOGGING_TAG, "reached zero");

        // check if game is won, or lost 3 in row case
        if (game.isTheGameOver()) {
            Log.v(LOGGING_TAG, "on finish the game is over (won or 3 in a row)");

            // stop the counter
            cancel();
        }
        else {
            Log.v(LOGGING_TAG, "on finish the game is NOT over");
            tvTimerValue.setText("0:00");

            // make sure to set the game to be over
            game.setGameOverToTrue();

            // set info text
            tvInfoText.setText(R.string.losing_time_ran_out_msg);

            // hide image of color hint
            ivInfoImage.setVisibility(View.GONE);
        }
    }


    @Override
    public void onTick(long millisUntilFinished) {

//        // calc min
//        long min  = millisUntilFinished / 60000;
//
//        // cal millisec left over
//        long millisec = millisUntilFinished % 60000;
//
//        // calc sec
//        long sec = millisec / 1000;
//
//        // make sure that 0-9 seconds appear 00-09
//        if (sec < 10)
//        {
//            tvTimerValue.setText(min + ":0" + sec);
//        }
//        else
//        {
//            tvTimerValue.setText(min + ":" + sec);
//        }

        // check if game is won, or lost 3 in row case
        if (game.isTheGameOver()) {
            Log.v(LOGGING_TAG, "the game is over (won, or 3 in a row)");

            // stop the counter
            cancel();
        }
        else {
            Log.v(LOGGING_TAG, "the game is NOT over (neither win nor 3 in a row)");

            // update milliseconds left
            millisecondsLeft = millisUntilFinished;
            Log.v(LOGGING_TAG, "millisecs left: " + millisecondsLeft);

            // calc min
            long min  = millisUntilFinished / 60000;

            // cal millisec left over
            long millisec = millisUntilFinished % 60000;

            // calc sec
            long sec = millisec / 1000;

            // make sure that 0-9 seconds appear 00-09
            if (sec < 10)
            {
                tvTimerValue.setText(min + ":0" + sec);
            }
            else
            {
                tvTimerValue.setText(min + ":" + sec);
            }
        }
    }
}
