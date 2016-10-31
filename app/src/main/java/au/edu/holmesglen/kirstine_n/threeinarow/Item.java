package au.edu.holmesglen.kirstine_n.threeinarow;

/**
 * Student name:    Kirstine B. Nielsen
 * Student id:      100527988
 * Date:            13/10/2016
 * Project:         Three in a row
 * Version:         1.1
 */


/**
 * Item class which represents an image
 */
public class Item {
    private int mColorImg;
    private int mClick = 0;

    /**
     * Constructor
     * @param colImg
     * @param title
     */
    public Item(int colImg, String title) {
        this.setColor(colImg);
    }

    public int getColor() {
        return mColorImg;
    }

    public void setColor(int rDC) {
        mColorImg = rDC;
    }

    /**
     * will increment the amount of time clicked on Item
     */
    public void incrementClick() {
        mClick++;
    }

    /**
     * To get the amount of time an Item has been clicked
     * @return amount of time clicked
     */
    public int getClickCount() {
        return mClick;
    }

    /**
     * will reset the amount of time clicked on Item to zero
     */
    public void resetClickCount() {
        mClick = 0;
    }

}
