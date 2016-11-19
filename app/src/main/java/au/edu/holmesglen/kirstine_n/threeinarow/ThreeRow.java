package au.edu.holmesglen.kirstine_n.threeinarow;

/**
 * Student name:    Kirstine B. Nielsen
 * Student id:      100527988
 * Date:            13/10/2016
 * Project:         Three in a row
 * Version:         1.3
 */

import android.graphics.Point;

import java.util.Random;


/**
 * Class holds the game logic.
 * The class represents a game with all rules implemented.
 * The class builds a game board and the grid
 * The class keeps track of game status, color to display, etc
 */
public class ThreeRow
{
    // constant, the amount needed in a row
    final static int IN_A_ROW_SUCCESS = 3;

    final static String GRID_CHARACTER_DEFAULT  = "?";
    final static String GRID_CHARACTER_1        = "1";
    final static String GRID_CHARACTER_2        = "2";

    // list of images with different colors
    final static int[] COLOR_LIST = {R.drawable.red, R.drawable.white, R.drawable.blue,
                                    R.drawable.green, R.drawable.pink, R.drawable.purple,
                                    R.drawable.brown, R.drawable.orange, R.drawable.turquoise,
                                    R.drawable.yellow};

    public int[] mPlayColorList = new int[2];  // hold two elements from COLOR_LIST

    // minimum should be grid of 4 x 4
    // since business rule is a square grid you could change it to only have DIMENSION = 4
    static int mRows;
    static int mColumns;
    static int mFullGrid;

    final static int POSITIONS_RANDOMLY_OCCUPIED = 4;

    // declare a 2D array  String[rows][columns]
    private String[][] mBoard;

    // flags
    private int mNoOfPositionsOccupied = 0;
    private boolean mGameOver = false;


    /**
     * constructor for ThreeRow
     */
    public ThreeRow(int gridDimension)
    {
        mRows       = gridDimension;
        mColumns    = gridDimension;
        mFullGrid = mRows * mColumns;

        // get a 2D array
        mBoard = getTwoDimensionalArray(mRows, mColumns);
    }


    /**
     * Clear the board of all GRID_CHARACTER_1's and GRID_CHARACTER_2's by
     * setting all spots to GRID_CHARACTER_DEFAULT.
     * Reset flags.
     */
    public void clearBoard()
    {
        // reset
        mNoOfPositionsOccupied = 0;
        mGameOver = false;

        // loop through outer array;
        for(int i = 0; i < mRows; i++)
        {
            // loop through inner array
            for(int j = 0; j < mColumns; j++)
            {
                mBoard[i][j] = GRID_CHARACTER_DEFAULT;
            }
        }
    }


    /**
     * fill out a positions randomly with either GRID_CHARACTER_1 or GRID_CHARACTER_2
     * @return array containing x, y coordinate for the random position
     */
    public Point preOccupyPosition()
    {
        // flag
        boolean keepGeneratingRandomPosition = true;

        Point point = new Point();

        while (keepGeneratingRandomPosition)
        {
            int randomX = generateRandomInteger(mColumns);
            int randomY = generateRandomInteger(mRows);

            if (isPositionAvailable(randomX, randomY))
            {
                point = new Point(randomX, randomY);

                // update flag
                keepGeneratingRandomPosition = false;

                // update grid
                updateGrid(randomX, randomY);
            }
        }

        return point;
    }


    /**
     * generate random number, integer from zero included to limit excluded
     * @param limit upper limit for random number (excluded)
     * @return the random number
     */
    private static int generateRandomInteger(int limit)
    {
        int randomNumber;

        Random randomGenerator = new Random();

        // generate pseudorandom. zero included, limit excluded
        randomNumber = randomGenerator.nextInt(limit);

        return randomNumber;
    }


    /**
     * Build a 2D array of strings
     * @param rows  how many inner arrays
     * @param columns  length of inner arrays
     * @return grid, array of arrays
     */
    private String[][] getTwoDimensionalArray(int rows, int columns)
    {
        String[][] aTwoDimensionalArray = new String[rows][columns];

        // populate 2D array
        for (int i = 0; i < rows; i++)
        {
            // get a 1D array
            String[] anArray = getArray(columns);

            // add 1D array to the 2D array
            aTwoDimensionalArray[i] = anArray;
        }

        return aTwoDimensionalArray;
    }


    /**
     * Build a 1D array of strings
     * @param length  length of array
     * @return Array of strings (GRID_CHARACTER_DEFAULT)
     */
    private String[] getArray(int length)
    {
        String[] anArray = new String[length];

        // populate array
        for (int i = 0; i < length; i++)
        {
            anArray[i] = GRID_CHARACTER_DEFAULT;
        }

        return anArray;
    }


    /**
     * check if there is "three in a row" either horizontally or vertically in a grid
     * @param someCharacter to find three in a row of
     * @return true if there is three in a row vertically or horizontally, else false
     */
    private boolean isThreeInARowVersion2D(String someCharacter)
    {
        // check if there is 3 in a row horizontally for all rows in the grid
        for(int i = 0; i < mBoard.length; i++)
        {
            if (isThreeInARowHorizontally(mBoard[i], someCharacter))
            {
                return true;
            }
        }

        // check if there is 3 in a row vertically in the grid
        return isThreeInARowVertically(mBoard, someCharacter);
    }


    /**
     * Checks if there is "3 in a row" of a specific String horizontally in a grid
     * @param someArray the array being searched through
     * @param aString the string we search if there is 3 in a row of
     * @return true if there is "3 in a row" horizontally, else false
     */
    private boolean isThreeInARowHorizontally(String[] someArray, String aString)
    {
        int arrayLength = someArray.length;

        // boolean to return
        boolean isInARowPresent = false;

        // loop through array; make sure you only check until and including third last element
        for(int i = 0; i <= arrayLength - IN_A_ROW_SUCCESS; i++)
        {
            if (someArray[i].equals(aString) && someArray[i + 1].equals(aString) && someArray[i + 2].equals(aString))
            {
                isInARowPresent = true;
                return isInARowPresent;
            }
        }
        return isInARowPresent;
    }


    /**
     * Checks if there is "3 in a row" of a specific String vertically in a grid
     * @param some2DArray a grid
     * @param someCharacter a string you want to see if there is three in a row of
     * @return true if "three in a row" vertically, else false
     */
    private boolean isThreeInARowVertically(String[][] some2DArray, String someCharacter)
    {
        // get rows amount in grid
        int arrayLength = some2DArray.length;

        // boolean to return
        boolean isInARowPresent = false;

        // loop through outer array; ; make sure you only check until and including third last row amount
        for(int i = 0; i <= arrayLength - IN_A_ROW_SUCCESS; i++)
        {
            // loop through inner array
            for(int j = 0; j < some2DArray[i].length; j++)
            {
                if (some2DArray[i][j].equals(someCharacter) && some2DArray[i+1][j].equals(someCharacter) && some2DArray[i+2][j].equals(someCharacter))
                {
                    isInARowPresent = true;
                    return isInARowPresent;
                }
            }
        }

        return isInARowPresent;
    }


    /**
     * Update grid on certain x, y position to a certain GRID_CHARACTER
     * @param columNumber  x coordinate
     * @param rowNumber   y coordinate
     */
    public void updateGrid(int columNumber, int rowNumber)
    {
        // check if location is free
        if (mBoard[rowNumber][columNumber] == GRID_CHARACTER_DEFAULT)
        {
            // update location to correct character
            mBoard[rowNumber][columNumber] = getNextColor();
        }

        // increment
        mNoOfPositionsOccupied++;
    }


    /**
     * get color on board position
     * @param columNumber  x coordinate in grid
     * @param rowNumber  y coordinate in grid
     * @return  color represented as int
     */
    public int getCurrentColor(int columNumber, int rowNumber)
    {
        if (mBoard[rowNumber][columNumber].equals(GRID_CHARACTER_1))
        {
            return mPlayColorList[0];
        }
        else
        {
            return mPlayColorList[1];
        }
    }


    /**
     * checks if position on board is free
     * @param columNumber  x coordinate in grid
     * @param rowNumber   y coordinate in grid
     * @return true if position is free, else false
     */
    private boolean isPositionAvailable(int columNumber, int rowNumber)
    {
        boolean isFree = false;

        // check if position x, y has GRID_CHARACTER_DEFAULT
        if (mBoard[rowNumber][columNumber].equals(GRID_CHARACTER_DEFAULT))
        {
            isFree = true;
        }

        return isFree;
    }


    /**
     * get the next color
     * @return next color as string
     */
    public String getNextColor()
    {
        // decide which character
        if (mNoOfPositionsOccupied % 2 == 0)  // even
        {
            // Log.v("Kirsti", "next color: " + GRID_CHARACTER_1);
            return GRID_CHARACTER_1;
        }
        else  // odd
        {
            // Log.v("Kirsti", "next color: " + GRID_CHARACTER_2);
            return GRID_CHARACTER_2;
        }
    }


    /**
     * check if there is "three in a row"
     * @return true if there is "three in row" either horizontally or vertically, else false
     */
    public boolean checkForThreeInARow()
    {
        if (isThreeInARowVersion2D(GRID_CHARACTER_1) || isThreeInARowVersion2D(GRID_CHARACTER_2))
        {
            // update flags
            mGameOver = true;
        }

        return mGameOver;
    }


    /**
     * check if grid is filled up with colours
     * @return true if grid is full, else false
     */
    public boolean isGridFull()
    {
        // update flags
        mGameOver = mNoOfPositionsOccupied == mFullGrid;

        return mGameOver;
    }


    /**
     * check if game is over
     * @return true if game is over, else false
     */
    public boolean isTheGameOver()
    {
        return mGameOver;
    }


    /**
     * Sets mGameOver to true
     */
    public void setGameOverToTrue() {
        mGameOver = true;
    }


    /**
     * display grid
     * @param someTwoDimensionalArray which shall be a non-ragged array and minimum 4 x 4
     * @return grid values represented as a string
     */
    private String displayTwoDimensionalArray(String[][] someTwoDimensionalArray)
    {
        // string to return
        String str = "";

        int rowCount = someTwoDimensionalArray.length;

        int columnsCount = someTwoDimensionalArray[0].length;

        // build grid from 2D array
        for (int i = 0; i < rowCount; i++)
        {
            for (int j = 0; j < columnsCount; j++)
            {
                // add to the string
                str += someTwoDimensionalArray[i][j] + " ";
            }
            str += "\n";
        }

        return str;
    }

}  // end class ThreeRow
