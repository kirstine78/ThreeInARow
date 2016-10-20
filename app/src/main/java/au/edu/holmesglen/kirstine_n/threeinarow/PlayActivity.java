package au.edu.holmesglen.kirstine_n.threeinarow;

/**
 * Student name:    Kirstine B. Nielsen
 * Student id:      100527988
 * Date:            13/10/2016
 * Project:         Three in a row
 * Version:         1.0 - Prototype
 */

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import static au.edu.holmesglen.kirstine_n.threeinarow.ThreeRow.COLUMNS;
import static au.edu.holmesglen.kirstine_n.threeinarow.ThreeRow.POSITIONS_RANDOMLY_OCCUPIED;
import static au.edu.holmesglen.kirstine_n.threeinarow.ThreeRow.ROWS;

/**
 * Class representing the Play game screen
 */
public class PlayActivity extends AppCompatActivity {
    // represents the internal state of the game
    private ThreeRow mGame;

    // where items will be displayed. represent the ?x? grid
    private GridView mGridview;

    // array to hold all Item objects. these are our images
    private Item[] mGridArray = new Item[ROWS * COLUMNS];

    private ImageAdapter mImageAdapter;

    // Various text displayed
    private TextView mInfoTextView;
    private ImageView mInfoImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // instantiate mGame so the activity can access the ThreeRow game logic
        mGame = new ThreeRow();

        // generate array with all Items in the mGridArray set to the grey image
        for (int i = 0; i < 16; i++)
        {
            mGridArray[i] = new Item(R.drawable.grey, "grey");
        }

        // instantiate the mGridview
        mGridview = (GridView) findViewById(R.id.gridview);

        // use the ImageAdapter to pass the array to the GridView object
        mImageAdapter = new ImageAdapter(this, mGridArray);
        mGridview.setAdapter(mImageAdapter);

        mInfoTextView = (TextView) findViewById(R.id.information);
        mInfoImageView = (ImageView) findViewById(R.id.imageNextColor);

        // then start a game
        startNewGame();

        // give mGridview an item click listener
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            // every time an Item is clicked this method is called
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // only process if game has not finished yet
                if (!mGame.isTheGameOver())
                {
                    if (mGridArray[position].getClickCount() < 1)
                    {
                        // convert position to x, y coordinates
                        Point point = getXYCoordFrom1DArrayIndex(position, ROWS);

                        // game logic
                        mGame.updateGrid(point.x, point.y);

                        // ui
                        int colorImg =  mGame.getCurrentColor(point.x, point.y);
                        ((ImageView) v).setImageResource(colorImg);

                        mInfoTextView.setText(displayNextColorHint());

                        // check if 3 in a row first
                        if (mGame.checkForThreeInARow())
                        {
                            Log.v("Kirsti", "losing");
                            // display appropriate msg to player
                            mInfoTextView.setText(R.string.losing_msg);

                            // hide image of color hint
                            mInfoImageView.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            if (mGame.isGridFull())
                            {
                                Log.v("Kirsti", "winning");
                                // display appropriate msg to player
                                mInfoTextView.setText(R.string.winning_msg);

                                // hide image of color hint
                                mInfoImageView.setVisibility(View.INVISIBLE);
                            }
                        }

                        // increment click count for Item
                        mGridArray[position].incrementClick();
                    }
                }
            }  // end onItemClick
        });  // end setOnItemClickListener

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }  // end onCreate


    // Set up the game board.
    private void startNewGame()
    {
        Log.v("Kirsti", "into startNewGame");

        mGame.clearBoard();

        // loop through array and reset all positions in the grid to grey image;
        for(int i = 0; i < ROWS * COLUMNS; i++)
        {
            mGridArray[i].resetClickCount();
            mGridArray[i].setColor(R.drawable.grey);
        }

        // fill out randomly selected fields/positions
        preOccupyPositions();

        // show hint msg to player
        mInfoTextView.setText(displayNextColorHint());

        // show image hint
        mInfoImageView.setVisibility(View.VISIBLE);
    } // End of startNewGame


    /**
     * will randomly select 4 positions to fill out.
     * 2 positions of each colour
     */
    private void preOccupyPositions()
    {
        Log.v("Kirsti", "into preOccupyPositions");
        // generate 4 different positions on grid randomly
        for (int i = 0; i < POSITIONS_RANDOMLY_OCCUPIED; i++)
        {
            // game logic (point x,y)
            Point point = mGame.preOccupyPosition();

            // UI
            updateGridUI(point.x, point.y, mGame.getCurrentColor(point.x, point.y));
        }
    }


    /**
     * will set position on grid to a specific colour
     * @param x coordinate for x axis
     * @param y coordinate for y axis
     * @param colorImg the colour to set to
     */
    private void updateGridUI(int x, int y, int colorImg)
    {
        Log.v("Kirsti", "inside updateGridUI x, y: " + x + ", " + y);

        // ui
        int aPosition = get1DArrayIndexFromXYCoord(x, y, ROWS);

        // set item to correct color
        mGridArray[aPosition].setColor(colorImg);

        // increment click count for specific Item
        mGridArray[aPosition].incrementClick();
    }


    /**
     * get next color hint
     * @return  info about next move
     */
    private String displayNextColorHint() {
        String str = "Next  ";

        setImage();

        return str;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.

        // declare an intent, the activity to start
        Intent intent;

        switch (item.getItemId())  // which menu item has been selected
        {
            case R.id.main:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.new_game:
                intent = new Intent(this, PlayActivity.class);
                startActivity(intent);
                return true;
            case R.id.help:
                intent = new Intent(this, HelpActivity.class);
                startActivity(intent);
                return true;
            case R.id.win_lose_record:
                intent = new Intent(this, WinLoseActivity.class);
                startActivity(intent);
                return true;
            case R.id.settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return false;  // nothing happened  no menu items has been selected
    }  // end onOptionsItemSelected


    /**
     * get position in grid represented as index in a one dimensional array
     * @param xCoord  position x in grid
     * @param yCoord  position y in grid
     * @param dimension  the width of grid (same as height since always square)
     * @return  index number in an array
     */
    private int get1DArrayIndexFromXYCoord(int xCoord, int yCoord, int dimension) {

        int indexOneDimensionArray = xCoord + (yCoord * dimension);

        return indexOneDimensionArray;
    }


    /**
     * get Point object representing converted from an index in a one dimensional array
     * @param position index in an array
     * @param dimension the width of grid (same as height since always square)
     * @return Point object (with x, y coordinates)
     */
    private Point getXYCoordFrom1DArrayIndex(int position, int dimension) {

        int xCoordinate = position % dimension;
        int yCoordinate = position / dimension;  //integer division

        Point point = new Point(xCoordinate, yCoordinate);

        return point;
    }


    /**
     * set hint for next colour to the upcoming color
     */
    private void setImage()
    {
        ImageView img = (ImageView) findViewById(R.id.imageNextColor);

        if (mGame.getNextColor().equals(ThreeRow.GRID_CHARACTER_1))
        {
            img.setImageResource(R.drawable.red);
        }
        else
        {
            img.setImageResource(R.drawable.white);
        }
    }


}
