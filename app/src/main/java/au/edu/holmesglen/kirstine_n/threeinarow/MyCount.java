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
    public int secondsTaken;
    private static final long TWEAK_MILLISECONDS = 3000;  // necessary because of bad CountDownTimer class


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

        super(millisInFuture + TWEAK_MILLISECONDS, countDownInterval);
        this.tvTimerValue = tvTimerValue;
        this.tvInfoText = tvInfoText;
        this.ivInfoImage = ivInfoImage;
        this.game = aGame;
        this.millisecondsLeft = millisInFuture;
        this.secondsTaken = -1;
    }


    @Override
    public void onFinish() {
        Log.v(LOGGING_TAG, "in onFinish");

        // ******************* DON'T DELETE YET BEFORE THOROUGH TESTING *********************

//        // check if game is won, or lost 3 in row case
//        if (game.isTheGameOver()) {
//            Log.v(LOGGING_TAG, "in onFinish, the game is over (won or 3 in a row)");
//
//            // stop the counter
//            cancel();
//        }
//        else {
//            Log.v(LOGGING_TAG, "in onFinish the game is NOT over");
//
//            // make sure to set the game to be over
//            game.setGameOverToTrue();
//
//            // set info text
//            tvInfoText.setText(R.string.losing_time_ran_out_msg);
//
//            // hide image of color hint
//            ivInfoImage.setVisibility(View.GONE);
//        }
        // ************** DON'T DELETE THE ABOVE YET BEFORE THOROUGH TESTING ****************
    }


    @Override
    public void onTick(long millisUntilFinished) {

        // update milliseconds left
        millisecondsLeft = millisUntilFinished - TWEAK_MILLISECONDS;

        Log.v(LOGGING_TAG, "millisecs left: " + millisecondsLeft);

        Log.v(LOGGING_TAG, "secondsTaken: " + secondsTaken);

        // check if game is won, or lost 3 in row case
        if (game.isTheGameOver()) {
            Log.v(LOGGING_TAG, "in onTick, the game is over (won, or 3 in a row)");

            // stop the counter
            cancel();
        }
        else if (millisecondsLeft < 0) {
            Log.v(LOGGING_TAG, "in onTick, time ran out");

            // stop the counter
            cancel();

            tvTimerValue.setText("0:00");

            // make sure to set the game to be over
            game.setGameOverToTrue();

            // set info text
            tvInfoText.setText(R.string.losing_time_ran_out_msg);

            // hide image of color hint
            ivInfoImage.setVisibility(View.GONE);

        } else {
            Log.v(LOGGING_TAG, "in onTick, the game is NOT over (neither win nor 3 in a row)");

            secondsTaken++;

            // calc min
            long min  = (millisecondsLeft + 1000) / 60000;

            // cal millisec left over
            long millisec = (millisecondsLeft + 1000) % 60000;

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
