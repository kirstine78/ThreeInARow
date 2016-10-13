package au.edu.holmesglen.kirstine_n.threeinarow;

import android.util.Log;

/**
 * Created by Student on 13/10/2016.
 */

public class Item {private int colorImg;
    int click = 0;

    public Item(int colImg, String title) {
        this.setColor(colImg);
    }

    public int getColor() {
        return colorImg;
    }

    public void setColor(int rDC) {
        colorImg = rDC;
    }

    public int nextColor(){
        Log.v("Kirsti", "Number click: " + click);

        // if click value has reached 2 then color change should not happen
        if (click < 2)
        {
            if (click % 2 == 0) {
                colorImg = R.drawable.green;
            }
            else {
                colorImg = R.drawable.blue;
            }
        }

        click++;

        return colorImg;

        //colorImg = R.drawable.grey;

        //if click value has reached 4 reset it back to 1 (green)
//        if(++click > 3) {
//            click = 1;
//        }
//
//        switch(click){
//            case 1:
//                colorImg = R.drawable.green;
//                break;
//            case 2:
//                colorImg = R.drawable.blue;
//                break;
//            case 3:
//                colorImg = R.drawable.grey;
//                break;
//        }
    }
}
