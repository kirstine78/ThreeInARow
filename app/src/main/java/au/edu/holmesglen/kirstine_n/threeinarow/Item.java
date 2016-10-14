package au.edu.holmesglen.kirstine_n.threeinarow;

/**
 * Created by Student on 13/10/2016.
 */

public class Item {
    private int colorImg;
    private int click = 0;

    public Item(int colImg, String title) {
        this.setColor(colImg);
    }

    public int getColor() {
        return colorImg;
    }

    public void setColor(int rDC) {
        colorImg = rDC;
    }

    public void incrementClick() {
        click++;
    }

    public int getClickCount() {
        return click;
    }

    public void resetClickCount() {
        click = 0;
    }


//    public int nextColor(int counter){
//        Log.v("Kirsti", "inside nextColor. Number click: " + click);
//
//        // if click value has reached 2 then color change should not happen
//        if (click < 1)
//        {
//            if (counter % 2 == 0) {
//                colorImg = R.drawable.red;
//            }
//            else {
//                colorImg = R.drawable.white;
//            }
//        }
//
//        click++;
//
//        return colorImg;
//    }
}
