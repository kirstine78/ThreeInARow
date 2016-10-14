package au.edu.holmesglen.kirstine_n.threeinarow;

/**
 * Created by Kirstine Nielsen 100527988  on 9/10/2016.
 */

import android.util.Log;

import java.util.Random;

/**
 *
 * @author Kirsti
 */
public class ThreeRow
{
    // constant, the amount needed in a row
    final static int IN_A_ROW_SUCCESS = 3;

    final static String GRID_CHARACTER_DEFAULT  = "?";
    final static String GRID_CHARACTER_1        = "Red";
    final static String GRID_CHARACTER_2        = "White";

    // minimum should be grid of 4 x 4
    final static int ROWS       = 4;
    final static int COLUMNS    = 4;
    final static int FULL_GRID  = ROWS * COLUMNS;

    final static int POSITIONS_RANDOMLY_OCCUPIED = 4;

    // declare a 2D array  String[rows][columns]
    private String[][] mBoard = getTwoDimensionalArray(ROWS, COLUMNS);

    private int noOfPositionsOccupied = 0;

    private boolean mGameOver = false;

    // constructor
    public ThreeRow()
    {
        // get a 2D array
        mBoard = getTwoDimensionalArray(ROWS, COLUMNS);
    }

    public int getNoOfPositionsOccupied()
    {
        return noOfPositionsOccupied;
    }

    /** Clear the board of all X's and O's by setting all spots to GRID_CHARACTER_DEFAULT. */
    public void clearBoard()
    {
        // reset
        noOfPositionsOccupied = 0;
        mGameOver = false;

        // loop through outer array;
        for(int i = 0; i < ROWS; i++)
        {
            // loop through inner array
            for(int j = 0; j < COLUMNS; j++)
            {
                mBoard[i][j] = GRID_CHARACTER_DEFAULT;
            }
        }
    }


    /***************** fill out 4 positions randomly *******************/
    public int[] preOccupyPosition()
    {
        // flag
        boolean keepGeneratingRandomPosition = true;

        int[] coordinateList = new int[2];

        while (keepGeneratingRandomPosition)
        {
            int randomX = generateRandomInteger(COLUMNS);
            int randomY = generateRandomInteger(ROWS);

            if (isPositionAvailable(randomX, randomY))
            {
                coordinateList[0] = randomX;
                coordinateList[1] = randomY;

                // update flag
                keepGeneratingRandomPosition = false;

                // update grid
                updateGrid(randomX, randomY);
            }
        }

        return coordinateList;
    }


    /***************** generate random number *******************/
    private static int generateRandomInteger(int limit)
    {
        int randomNumber;

        Random randomGenerator = new Random();

        // generate pseudorandom. zero included, limit excluded
        randomNumber = randomGenerator.nextInt(limit);

//        System.out.println("random: " + randomNumber);

        return randomNumber;
    }


    /**
     * ***************** 1D array *******************
     * Build an array of strings
     * @param length
     * @return Array of strings
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
     * Checks if there is 3 in a row of a specific character that is passed as argument
     * @param someArray the array being searched through
     * @param someCharacter the character we search if there is 3 in a row of
     * @return true if there is 3 in a row of the character passed in, else false
     */
    private boolean isThreeInARowHorizontally(String[] someArray, String someCharacter)
    {
        int arrayLength = someArray.length;

        // boolean to return
        boolean isInARowPresent = false;

        // loop through array; make sure you only check until and including third last element
        for(int i = 0; i <= arrayLength - IN_A_ROW_SUCCESS; i++)
        {
            if (someArray[i].equals(someCharacter) && someArray[i + 1].equals(someCharacter) && someArray[i + 2].equals(someCharacter))
            {
                isInARowPresent = true;
                return isInARowPresent;
            }
        }
        return isInARowPresent;
    }


    /**
     * **************** 2D array ********************
     * @param rows
     * @param columns
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
     *
     * @param someCharacter to find three in a row of
     * @return true if there is three in a row vertically or horizontally
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
     *
     * @param some2DArray a grid
     * @param someCharacter a character you want to see if there is three in a row of
     * @return true if three in a row was found in a row in a grid
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
     * Update grid on certain x, y position to a certain character
     * @param columNumber
     * @param rowNumber
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
        noOfPositionsOccupied++;
    }


    public int getCurrentColor(int columNumber, int rowNumber)
    {
        if (mBoard[rowNumber][columNumber].equals(GRID_CHARACTER_1))
        {
            return R.drawable.red;
        }
        else
        {
            return R.drawable.white;
        }
    }


    /**
     * checks if position x, y is free
     * @param columNumber
     * @param rowNumber
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


    public String getNextColor()
    {
        // decide which character
        if (noOfPositionsOccupied % 2 == 0)  // even
        {
            Log.v("Kirsti", "next color: " + GRID_CHARACTER_1);
            return GRID_CHARACTER_1;
        }
        else  // odd
        {
            Log.v("Kirsti", "next color: " + GRID_CHARACTER_2);
            return GRID_CHARACTER_2;
        }
    }


    public boolean checkForThreeInARow()
    {
        if (isThreeInARowVersion2D(GRID_CHARACTER_1) || isThreeInARowVersion2D(GRID_CHARACTER_2))
        {
            // update flags
            mGameOver = true;
        }

        return mGameOver;
    }


    public boolean isGridFull()
    {
        // update flags
        mGameOver = noOfPositionsOccupied == FULL_GRID;

        return mGameOver;
    }


    public boolean isTheGameOver()
    {
        return mGameOver;
    }

    /**
     *
     * @param someTwoDimensionalArray which shall be a non-ragged array and minimum 3 x 3
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
}
