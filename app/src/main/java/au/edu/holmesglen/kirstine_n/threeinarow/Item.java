package au.edu.holmesglen.kirstine_n.threeinarow;

/**
 * Student name:    Kirstine B. Nielsen
 * Student id:      100527988
 * Date:            13/10/2016
 * Project:         Three in a row
 * Version:         1.0 - Prototype
 */


/**
 * Item class which represents an image
 */
public class Item {
    private int colorImg;
    private int click = 0;

    /**
     * Constructor
     * @param colImg
     * @param title
     */
    public Item(int colImg, String title) {
        this.setColor(colImg);
    }

    public int getColor() {
        return colorImg;
    }

    public void setColor(int rDC) {
        colorImg = rDC;
    }

    /**
     * will increment the amount of time clicked on Item
     */
    public void incrementClick() {
        click++;
    }

    /**
     * To get the amount of time an Item has been clicked
     * @return amount of time clicked
     */
    public int getClickCount() {
        return click;
    }

    /**
     * will reset the amount of time clicked on Item to zero
     */
    public void resetClickCount() {
        click = 0;
    }

}
