package au.edu.holmesglen.kirstine_n.threeinarow;

/**
 * Student name:    Kirstine B. Nielsen
 * Student id:      100527988
 * Date:            13/10/2016
 * Project:         Three in a row
 * Version:         1.3
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


/**
 * ImageAdapter acts as a bridge between AdapterView and the underlying data (Images) for that view.
 * ImageAdapter provides access to the data items.
 * ImageAdapter is also responsible for making a View for each item in the data set.
 */
public class ImageAdapter extends BaseAdapter {
    // this references the array passed through the constructor
    private Item[] mGridArray;  // an array of the Item class - our Item class represents color images
    private Context mContext;  // the current state of an app or object or Activity


    /**
     * Constructor
     * @param context
     * @param gridArray
     */
    public ImageAdapter(Context context, Item[] gridArray) {
        this.mContext = context;
        this.mGridArray = gridArray;
    }


    /**
     * Gets the size of the array mGridArray.
     * @return      integer representing array size.
     */
    public int getCount() {
        return mGridArray.length;
    }


    /**
     * Gets the current Item (color) in a grid on GridView
     * @param position
     * @return      Item object
     */
    public Item getItem(int position) {
        return null;
    }


    /**
     * Gets the position in GridView of where the color can be found
     * @param position
     * @return
     */
    public long getItemId(int position) {
        return position;
    }


    /**
     * Gets an ImageView to display the correct color image for the position passed in.     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            int width = ((GridView) parent).getColumnWidth();
            imageView.setLayoutParams(new GridView.LayoutParams(width, width));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mGridArray[position].getColor());
        return imageView;
    }
}  // end class
