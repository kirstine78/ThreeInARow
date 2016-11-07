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

/**
 * Created by Kirsti on 2/11/2016.
 */

public class MyCount extends CountDownTimer {

    private TextView tvTimerValue;
    private ImageView ivInfoImage;
    private TextView tvInfoText;
    private ThreeRow game;

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
    }


    @Override
    public void onFinish() {
        tvTimerValue.setText("0:00");

        // make sure to set the game to be over
        game.setGameOverToTrue();

        // set info text
        tvInfoText.setText(R.string.losing_time_ran_out_msg);

        // hide image of color hint
        ivInfoImage.setVisibility(View.GONE);
    }


    @Override
    public void onTick(long millisUntilFinished) {

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
