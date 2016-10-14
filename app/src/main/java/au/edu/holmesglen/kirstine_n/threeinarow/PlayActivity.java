package au.edu.holmesglen.kirstine_n.threeinarow;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import static au.edu.holmesglen.kirstine_n.threeinarow.ThreeRow.FULL_GRID;
import static au.edu.holmesglen.kirstine_n.threeinarow.ThreeRow.GRID_CHARACTER_1;
import static au.edu.holmesglen.kirstine_n.threeinarow.ThreeRow.GRID_CHARACTER_2;
import static au.edu.holmesglen.kirstine_n.threeinarow.ThreeRow.POSITIONS_RANDOMLY_OCCUPIED;
import static au.edu.holmesglen.kirstine_n.threeinarow.ThreeRow.ROWS;


public class PlayActivity extends AppCompatActivity {
    // represents the internal state of the game
    private ThreeRow mGame;

    GridView gridview;

    // array to hold all Item objects. These will represent the ?x? grid
    Item[] gridArray = new Item[ROWS * COLUMNS];

    ImageAdapter imageAdapter;

    // Various text displayed
    private TextView mInfoTextView;
    private ImageView mInfoImageView;

    // flag to keep track of game state
    private boolean mGameOver = false;
//    private boolean mHasLost = false;

    private int mCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // instantiate mGame so the activity can access the ThreeRow game logic
        mGame = new ThreeRow();

        // generate array with all Items in the gridArray set to the grey image
        for (int i = 0; i < 16; i++)
        {
            gridArray[i] = new Item(R.drawable.grey, "grey");
        }

        // instantiate the the gridview
        gridview = (GridView) findViewById(R.id.gridview);

        // use the ImageAdapter to pass the array to the GridView object
        imageAdapter = new ImageAdapter(this, gridArray);
        gridview.setAdapter(imageAdapter);

        mInfoTextView = (TextView) findViewById(R.id.information);
        mInfoImageView = (ImageView) findViewById(R.id.imageNextColor);

        // then start a game
        startNewGame();

        // give gridview an item click listener
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            // every time an Item is clicked this method is called
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // only process if game has not finished yet
                if (!mGameOver)
                {
                    if (gridArray[position].getClickCount() < 1)
                    {
                        Log.v("Kirsti", "click is less than one");

                        // convert position to x, y coordinates
                        int coordinateList[] = getXYCoordFrom1DArrayIndex(position, ROWS);

                        // game logic
                        mGame.updateGrid(mCounter, coordinateList[0], coordinateList[1]);

//                        updateGridUI(coordinateList[0], coordinateList[1]);

                        // ui
                        int colorImg =  gridArray[position].nextColor(mCounter);
                        ((ImageView) v).setImageResource(colorImg);

                        mCounter++;

                        mInfoTextView.setText(displayNextColorHint());

                        // check if 3 in a row first
                        checkForThreeInARow();

                        // then check if all fields in grid has been filled up and not 3 in a row
                        checkForCompleteGameWin();
                    }
                }
            }  // end onItemClick
        });  // end setOnItemClickListener

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }  // end onCreate

    // Set up the game board.
    private void startNewGame()
    {
        Log.v("Kirsti", "into startNewGame");
        // reset flags and counter
        mGameOver = false;
        mCounter = 0;
        Log.v("Kirsti", "mCounter: " + mCounter);

        mGame.clearBoard();

        // instantiate the the gridview
        gridview = (GridView) findViewById(R.id.gridview);

        // use the ImageAdapter to pass the array to the GridView object
        imageAdapter = new ImageAdapter(this, gridArray);
        gridview.setAdapter(imageAdapter);


        // Reset all positions in the grid
        // loop through array;
        for(int i = 0; i < ROWS * COLUMNS; i++)
        {
            gridArray[i].resetClickCount();
            gridArray[i].setColor(R.drawable.grey);
            Log.v("Kirsti", "click count: " + gridArray[i].getClickCount());
        }

        // fill out randomly selected fields
        preOccupyPositions();

        // show msg to player
        mInfoTextView.setText(displayNextColorHint());

        // show image hint
        mInfoImageView.setVisibility(View.VISIBLE);
    } // End of startNewGame



    /***************** fill out 4 positions randomly *******************/
    public void preOccupyPositions()
    {
        Log.v("Kirsti", "into preOccupyPositions");
        // generate 4 different positions on grid randomly
        for (int i = 0; i < POSITIONS_RANDOMLY_OCCUPIED; i++)
        {
            // game logic
            int coordinateList[] = mGame.preOccupyPosition(mCounter);

            int x = coordinateList[0];
            int y = coordinateList[1];

            // game logic
            mGame.updateGrid(mCounter, x, y);

            // UI
            updateGridUI(x, y);
        }
    }


    private void updateGridUI(int x, int y)
    {
        Log.v("Kirsti", "inside updateGridUI x, y: " + x + ", " + y);
        Log.v("Kirsti", "inside updateGridUI mCounter: " + mCounter);

        // ui
        int colorImg;

        int aPosition = get1DArrayIndexFromXYCoord(x, y, ROWS);

        // pick correct color
        if (mCounter % 2 == 0) {
            Log.v("Kirsti", "mCounter even");

            colorImg = R.drawable.red;
        }
        else {
            Log.v("Kirsti", "mCounter odd");

            colorImg = R.drawable.white;
        }

        // set item to correct color
        gridArray[aPosition].setColor(colorImg);

        // increment click count for specific Item
        gridArray[aPosition].incrementClick();

        // increment the counter
        mCounter++;
    }


    private String displayNextColorHint() {
        String str = "Next  ";

        setImage();

        return str;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play_screen, menu);
//        menu.add("New Game");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

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
     * @return  index in array
     */
    public int get1DArrayIndexFromXYCoord(int xCoord, int yCoord, int dimension) {

        int indexOneDimensionArray = xCoord + (yCoord * dimension);

        return indexOneDimensionArray;
    }


    public int[] getXYCoordFrom1DArrayIndex(int position, int dimension) {

        int xCoordinate = position % dimension;
        int yCoordinate = position / dimension;  //integer division


        int[] coordinateList = new int[2];
        coordinateList[0] = xCoordinate;
        coordinateList[1] = yCoordinate;

        return coordinateList;
    }

    public void setImage()
    {
        ImageView img = (ImageView) findViewById(R.id.imageNextColor);

        img.setImageResource(R.drawable.blue);

        if (mCounter % 2 == 0)
        {
            img.setImageResource(R.drawable.red);
        }
        else
        {
            img.setImageResource(R.drawable.white);
        }
    }

    public void checkForThreeInARow()
    {
        if (mGame.isThreeInARowVersion2D(GRID_CHARACTER_1) || mGame.isThreeInARowVersion2D(GRID_CHARACTER_2))
        {
            // update flags
            mGameOver = true;
//                            mHasLost = true;

            // display appropriate msg to player
            mInfoTextView.setText(R.string.losing_msg);

            // hide image of color hint
            mInfoImageView.setVisibility(View.INVISIBLE);
        }
    }

    public void checkForCompleteGameWin()
    {
        if (mCounter == FULL_GRID &&
                mGame.isThreeInARowVersion2D(GRID_CHARACTER_1) == false &&
                mGame.isThreeInARowVersion2D(GRID_CHARACTER_2) == false)
        {
            // update flag mGameOver
            mGameOver = true;

            // display appropriate msg to player
            mInfoTextView.setText(R.string.winning_msg);

            // hide image of color hint
            mInfoImageView.setVisibility(View.INVISIBLE);
        }
    }

}
