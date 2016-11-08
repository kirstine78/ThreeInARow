package au.edu.holmesglen.kirstine_n.threeinarow;

/**
 * Student name:    Kirstine B. Nielsen
 * Student id:      100527988
 * Date:            13/10/2016
 * Project:         Three in a row
 * Version:         1.1
 */

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static au.edu.holmesglen.kirstine_n.threeinarow.MainActivity.LOGGING_TAG;
import static au.edu.holmesglen.kirstine_n.threeinarow.ThreeRow.COLOR_LIST;
import static au.edu.holmesglen.kirstine_n.threeinarow.ThreeRow.POSITIONS_RANDOMLY_OCCUPIED;

/**
 * Class is responsible for the GUI for the Play game screen.
 * The GUI consist of a gridview as the board which have Grey fields
 * as default.
 * User interacts with the board to place two different colors on the
 * fields.
 * The GUI will display next move to user.
 */
public class PlayActivity extends CommonActivity {
    // represents the internal state of the game
    private ThreeRow mGame;

    // where items will be displayed. represent the ?x? grid
    private GridView mGridview;

    // array to hold all Item objects. these are our images
    private Item[] mGridArray;

    private ImageAdapter mImageAdapter;

    // Various text displayed
    private TextView mInfoTextView;
    private ImageView mInfoImageView;
    private TextView mTimerValueTextView;
    private TextView mDifficultyValueTextView;
    private MyCount myCountDown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(LOGGING_TAG, "PlayActivity, in onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // instantiate mGame so the activity can access the ThreeRow game logic
        mGame = new ThreeRow(getSavedValueGridSize());

        // get the colors saved in shared preferences
        // if no color is saved, then it will get the default value
        // then assign them to mPlayColorList
        mGame.mPlayColorList[0] = COLOR_LIST[getSavedValueColor1()];
        mGame.mPlayColorList[1] = COLOR_LIST[getSavedValueColor2()];

        // build GUI grid
        mGridArray = new Item[ThreeRow.mRows * ThreeRow.mColumns];

        // find gridview and set column numbers
        GridView gridViewGame = (GridView) findViewById(R.id.gridview);
        gridViewGame.setNumColumns(ThreeRow.mRows);

        // generate array with all Items in the mGridArray set to the grey image
        for (int i = 0; i < ThreeRow.mRows * ThreeRow.mColumns; i++)
        {
            mGridArray[i] = new Item(R.drawable.grey);
        }

        // instantiate the mGridview
        mGridview = (GridView) findViewById(R.id.gridview);

        // use the ImageAdapter to pass the array to the GridView object
        mImageAdapter = new ImageAdapter(this, mGridArray);
        mGridview.setAdapter(mImageAdapter);

        mInfoTextView = (TextView) findViewById(R.id.information);
        mInfoImageView = (ImageView) findViewById(R.id.imageNextColor);
        mTimerValueTextView = (TextView) findViewById(R.id.game_timer_value);

        // process to show timer value in textview
        myCountDown = new MyCount(getMillisecondsAllowed(), 1000,
                                    mTimerValueTextView, mInfoTextView,
                                    mInfoImageView, mGame);

        // then start a game
        startNewGame(myCountDown);

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
                        Point point = getXYCoordFrom1DArrayIndex(position, ThreeRow.mRows);

                        // game logic
                        mGame.updateGrid(point.x, point.y);

                        // ui
                        int colorImg =  mGame.getCurrentColor(point.x, point.y);
                        ((ImageView) v).setImageResource(colorImg);

                        mInfoTextView.setText(displayNextColorHint());

                        // check if 3 in a row first
                        if (mGame.checkForThreeInARow())  //LOSING
                        {
                            Log.v(LOGGING_TAG, "losing because of three in a row");
                            // display appropriate msg to player
                            mInfoTextView.setText(R.string.losing_three_in_a_row_msg);

                            // hide image of color hint
                            mInfoImageView.setVisibility(View.GONE);
                        }
                        else
                        {
                            if (mGame.isGridFull())  // WINNING
                            {
                                int millisecondsSpent = getMillisecondsSpentOnGame();

                                // check if time is better than the saved
                                if ( isNewTimeBetterThanCurrentBestTime(millisecondsSpent) ){
                                    Log.v(LOGGING_TAG, "new time better");

                                    Toast.makeText(PlayActivity.this, "New best time!", Toast.LENGTH_SHORT).show();

                                    int savedGridSize = getSavedValueGridSize() % 4;    // 4, 5, or 6 - hence the % 4
                                    int savedDifficulty = getSavedValueDifficulty();  // 0, 1, or 2

                                    // update
                                    updateBestTime(millisecondsSpent, savedGridSize, savedDifficulty);
                                }
                                else {
                                    Log.v(LOGGING_TAG, "new time not better");
                                }

                                Log.v(LOGGING_TAG, "winning");
                                // display appropriate msg to player
                                mInfoTextView.setText(R.string.winning_msg);

                                // hide image of color hint
                                mInfoImageView.setVisibility(View.GONE);
                            }
                        }

                        // increment click count for Item
                        mGridArray[position].incrementClick();
                    }
                }
            }  // end onItemClick
        });  // end setOnItemClickListener

        // btn to start new game
        final Button btnStartNewGame = (Button) findViewById(R.id.btn_home_screen_start_game);

        // on btn click 'start new game'
        btnStartNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(LOGGING_TAG, "PlayActivity, in onClick btnStartNewGame");

                // declare an intent, the activity to start
                Intent intent;

                intent = new Intent(PlayActivity.this, PlayActivity.class);
                startActivity(intent);
            }
        });
    }  // end onCreate


    /**
     * Set up the game board from scratch.
     * Reset click counter.
     * Reset fields to grey
     * Make sure to randomly pick 4 new fields.
     */
    private void startNewGame(MyCount aCountDown)
    {
        Log.v(LOGGING_TAG, "PlayActivity, in startNewGame");

        mGame.clearBoard();

        // loop through array and reset all positions in the grid to grey image;
        for(int i = 0; i < ThreeRow.mRows * ThreeRow.mColumns; i++)
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

        // *************** increment TOTAL GAMES played ******************
        // DON'T DELETE YET
//        incrementTotalGames();
        // ***************************************************************

        // process to show difficulty value in textview (easy, medium, hard)
        showDifficulty();

        // start the timer
        startMyTimer(aCountDown);


    } // End of startNewGame


    /**
     * will set the text in textview for difficulty value to the current value for difficulty
     */
    private void showDifficulty() {
        mDifficultyValueTextView = (TextView) findViewById(R.id.game_difficulty_value);

        // get the saved value for difficulty (0, 1, 2)
        int currentSavedDifficultyValue = getSavedValueDifficulty();

        final String[] DIFFICULTY_VALUE_LIST = {"Easy", "Medium", "Hard"};
        mDifficultyValueTextView.setText(DIFFICULTY_VALUE_LIST[currentSavedDifficultyValue]);
    }


    /**
     * get the milliseconds allowed in a game
     * @return
     */
    public int getMillisecondsAllowed() {
        // depending on grid size AND difficulty we chose the time allowed
        int gridsize = getSavedValueGridSize();  // 4, 5, or 6 -> you will user modulo 4
        int difficulty = getSavedValueDifficulty();  // 0, 1, or 2

        // get the correct time from array[][]
        int millisecondsAllowed = listMillisecondsArray[gridsize % 4][difficulty];

        return millisecondsAllowed;
    }


    public void startMyTimer(MyCount aCountDown) {
        aCountDown.start();
    }


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
        int aPosition = get1DArrayIndexFromXYCoord(x, y, ThreeRow.mRows);

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
        return false;  // nothing happened, no menu items has been selected
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

        // check which color is the next to show
        if (mGame.getNextColor().equals(ThreeRow.GRID_CHARACTER_1))
        {
            int imageResource = mGame.mPlayColorList[0];
            img.setImageResource(imageResource);
        }
        else
        {
            int imageResource = mGame.mPlayColorList[1];
            img.setImageResource(imageResource);
        }
    }


    public int getMillisecondsSpentOnGame() {
        int timeSpent = 0;

        // what was the time allowed.
        int timeAllowed = listMillisecondsArray[getSavedValueGridSize() % 4][getSavedValueDifficulty()];
        Log.v(LOGGING_TAG, "in getMillisecondsSpentOnGame, time allowed were: " + (timeAllowed));

//        String timeStoppedAt = mTimerValueTextView.getText().toString();
//        Log.v(LOGGING_TAG, "in getMillisecondsSpentOnGame, timeStoppedAt: " + timeStoppedAt);

        // get milliseconds

        long timeStoppedAt = myCountDown.millisecondsLeft;
        Log.v(LOGGING_TAG, "in getMillisecondsSpentOnGame, timeStoppedAt: " + timeStoppedAt);

        timeSpent = timeAllowed - (int)timeStoppedAt;
        Log.v(LOGGING_TAG, "in getMillisecondsSpentOnGame, timeSpent: " + timeSpent);

        return timeSpent;
    }

//    public void incrementTotalGames()
//    {
//        updateTotalGamesInSharedPreferences();
//    }

}  // end class
