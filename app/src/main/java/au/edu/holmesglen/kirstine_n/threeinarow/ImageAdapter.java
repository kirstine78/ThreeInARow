package au.edu.holmesglen.kirstine_n.threeinarow;

/**
 * Student name:    Kirstine B. Nielsen
 * Student id:      100527988
 * Date:            13/10/2016
 * Project:         Three in a row
 * Version:         1.0 - Prototype
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


public class ImageAdapter extends BaseAdapter {
    // this references the 2D array passed through the constructor
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

    // return the size of the array
    public int getCount() {
        return mGridArray.length;
    }

    // return the current Item (color) in a grid on GridView
    public Item getItem(int position) {
        return null;
    }

    // return the position in GridView of where the color can be found
    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
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
}
