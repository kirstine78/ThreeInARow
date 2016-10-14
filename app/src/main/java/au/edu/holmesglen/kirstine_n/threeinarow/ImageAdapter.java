package au.edu.holmesglen.kirstine_n.threeinarow;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by Kirstine Nielsen 100527988 on 13/10/2016.
 */

public class ImageAdapter extends BaseAdapter {
    // this references the 2D array passed through the constructor
    Item[] gridArray;  // an array of arrays of the Item class - our Item class represents color images
    private Context mContext;  // the current state of an app or object or Activity

    // constructor
//    public ImageAdapter(Context context, Item[][] gridArray) {
//        mContext = context;
//        this.gridArray = gridArray;
//    }
    public ImageAdapter(Context context, Item[] gridArray) {
        mContext = context;
        this.gridArray = gridArray;
    }

    // return the size of the array
    // TODO what do we want here???
    public int getCount() {
        return gridArray.length;
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

        imageView.setImageResource(gridArray[position].getColor());
        return imageView;
    }
}
