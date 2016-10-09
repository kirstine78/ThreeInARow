package au.edu.holmesglen.kirstine_n.threeinarow;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static au.edu.holmesglen.kirstine_n.threeinarow.ThreeRow.COLUMNS;
import static au.edu.holmesglen.kirstine_n.threeinarow.ThreeRow.FULL_GRID;
import static au.edu.holmesglen.kirstine_n.threeinarow.ThreeRow.GRID_CHARACTER_1;
import static au.edu.holmesglen.kirstine_n.threeinarow.ThreeRow.GRID_CHARACTER_2;
import static au.edu.holmesglen.kirstine_n.threeinarow.ThreeRow.ROWS;

public class MainActivity extends AppCompatActivity {

    // represents the internal state of the game
    private ThreeRow mGame;

    // Buttons making up the board
    private Button mBoardButtons[][];

    // Various text displayed
    private TextView mInfoTextView;

    // flag to keep track of game state
    private boolean mGameOver = false;
    private boolean mHasLost = false;

    private int mCounter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mBoardButtons = new Button[ROWS][COLUMNS];
        mBoardButtons[0][0] = (Button) findViewById(R.id.button1);
        mBoardButtons[0][1] = (Button) findViewById(R.id.button2);
        mBoardButtons[0][2] = (Button) findViewById(R.id.button3);
        mBoardButtons[1][0] = (Button) findViewById(R.id.button4);
        mBoardButtons[1][1] = (Button) findViewById(R.id.button5);
        mBoardButtons[1][2] = (Button) findViewById(R.id.button6);
        mBoardButtons[2][0] = (Button) findViewById(R.id.button7);
        mBoardButtons[2][1] = (Button) findViewById(R.id.button8);
        mBoardButtons[2][2] = (Button) findViewById(R.id.button9);
        mInfoTextView = (TextView) findViewById(R.id.information);

        // instantiate mGame so the activity can access the ThreeRow game logic
        mGame = new ThreeRow();

        // then start a game
        startNewGame();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    // Set up the game board.
    private void startNewGame() {
        // reset flags and counter
        mGameOver = false;
        mCounter = 0;

        mGame.clearBoard();

        // Reset all buttons
        // loop through outer array;
        for(int i = 0; i < ROWS; i++)
        {
            // loop through inner array
            for(int j = 0; j < COLUMNS; j++)
            {
                mBoardButtons[i][j].setText("");
                mBoardButtons[i][j].setEnabled(true);
                mBoardButtons[i][j].setOnClickListener(new ButtonClickListener(i, j));
            }
        }

        // show msg to player
        mInfoTextView.setText(R.string.info_label);
    } // End of startNewGame



    private void updateGrid(int x, int y) {
        mGame.updateGrid(mCounter, x, y);
        mBoardButtons[x][y].setEnabled(false);
        mBoardButtons[x][y].setText(mGame.getCharacter(mCounter));

//        if (player == TicTacToeGame.HUMAN_PLAYER)
//            mBoardButtons[location].setTextColor(Color.rgb(0, 200, 0));
//        else
//            mBoardButtons[location].setTextColor(Color.rgb(200, 0, 0));

        // increment the counter
        mCounter++;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
//        menu.add("New Game");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // check if "new game" is clicked in dot dot dot menu
        if (id == R.id.new_game) {
            startNewGame();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // Handles clicks on the game board buttons
    private class ButtonClickListener implements View.OnClickListener {

        int locationX;
        int locationY;

        // constructor
        public ButtonClickListener(int x, int y) {
            this.locationX = x;
            this.locationY = y;
        }

        public void onClick(View view) {
            if (!mGameOver)
            {
                if (mBoardButtons[locationX][locationY].isEnabled())
                {
                    updateGrid(locationX, locationY);

                    // check if 3 in a row
                    if (mGame.isThreeInARowVersion2D(GRID_CHARACTER_1) || mGame.isThreeInARowVersion2D(GRID_CHARACTER_2))
                    {
                        // update flags
                        mGameOver = true;
                        mHasLost = true;

                        // display appropriate msg to player
                        mInfoTextView.setText(R.string.losing_msg);
                    }

                    // check if all fields in grid has been filled up
                    if (mCounter == FULL_GRID &&
                        mGame.isThreeInARowVersion2D(GRID_CHARACTER_1) == false &&
                        mGame.isThreeInARowVersion2D(GRID_CHARACTER_2) == false)
                    {
                        // update flag mGameOver
                        mGameOver = true;

                        // display appropriate msg to player
                        mInfoTextView.setText(R.string.winning_msg);
                    }
                }
            }
        }  // end onClick

    }  // end class ButtonClickListener

}  // end class MainActivity
