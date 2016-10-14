package au.edu.holmesglen.kirstine_n.threeinarow;

/**
 * Created by Kirsti on 9/10/2016.
 */

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

    // declare a 2D array
    private String[][] mBoard = getTwoDimensionalArray(ROWS, COLUMNS);

//    int logicCounter = 0;

    public ThreeRow()
    {
        // get a 2D array
        mBoard = getTwoDimensionalArray(ROWS, COLUMNS);
    }

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args)
//    {
//        // declare variables
//        int counter = 0;
//        boolean keepPlaying = true;  // flag
//        boolean hasLost = false;  // flag
//
//        // get a 2D array
//        String[][] grid = getTwoDimensionalArray(ROWS, COLUMNS);
//
//        // generate 4 different positions on grid randomly
//        preOccupyPositions(grid, POSITIONS_RANDOMLY_OCCUPIED);
//
//        // update count
//        counter += POSITIONS_RANDOMLY_OCCUPIED;
//
//        // play until board is full or losing
//        while (keepPlaying)
//        {
////            JOptionPane.showMessageDialog(null, "BOARD\n\n" + displayTwoDimensionalArray(grid));
//
//            boolean positionOk = false;
//            int positionX = 0;
//            int positionY = 0;
//
//            // keep asking for position if not free
//            while (!positionOk)
//            {
//                // prompt user to pick a position
//                positionX = Integer.parseInt(JOptionPane.showInputDialog(null, "BOARD\n\n"
//                        + displayTwoDimensionalArray(grid)
//                        + "\n\nPick X coordinate (0 - "
//                        + (COLUMNS - 1) + ")" + " for: "
//                        + getCharacter(counter)));
//
//                positionY = Integer.parseInt(JOptionPane.showInputDialog(null, "BOARD\n\n"
//                        + displayTwoDimensionalArray(grid)
//                        + "\n\nPick Y coordinate (0 - "
//                        + (ROWS - 1) + ")" + " for: "
//                        + getCharacter(counter)));
//
//                // check if free
//                if (isPositionAvailable(grid, positionX, positionY))
//                {
//                    // update flag
//                    positionOk = true;
//                }
//                else
//                {
//                    JOptionPane.showMessageDialog(null, "Sorry position is not free, try again");
//                }
//            }
//
//            // update grid
//            updateGrid(grid, getCharacter(counter), positionX, positionY);
//
//            // increment counter
//            counter++;
//
//            // check if 3 in a row
//            if (isThreeInARowVersion2D(grid, GRID_CHARACTER_1) || isThreeInARowVersion2D(grid, GRID_CHARACTER_2))
//            {
//                // update flags
//                keepPlaying = false;
//                hasLost = true;
//            }
//
//            // check if grid is full
//            if (counter == FULL_GRID)
//            {
//                // update flag keepPlaying
//                keepPlaying = false;
//            }
//        }
//
//        if (hasLost)
//        {
//            JOptionPane.showMessageDialog(null, "You LOST\n\n" + displayTwoDimensionalArray(grid));
//        }
//        else
//        {
//            JOptionPane.showMessageDialog(null, "You WON\n\n" + displayTwoDimensionalArray(grid));
//        }
//    }



    /** Clear the board of all X's and O's by setting all spots to GRID_CHARACTER_DEFAULT. */
    public void clearBoard()
    {
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
//    public void preOccupyPositions()
//    {
//        // generate 4 different positions on grid randomly
//        for (int i = 0; i < POSITIONS_RANDOMLY_OCCUPIED; i++)
//        {
//            // flag
//            boolean keepGeneratingRandomPosition = true;
//
//            while (keepGeneratingRandomPosition)
//            {
//                int randomX = generateRandomInteger(COLUMNS);
//                int randomY = generateRandomInteger(ROWS);
//
//                if (isPositionAvailable(randomX, randomY))
//                {
//                    // update flag
//                    keepGeneratingRandomPosition = false;
//
//                    // update grid
//                    updateGrid(i, randomX, randomY);
//                }
//            }
//        }
//    }


    public int[] preOccupyPosition(int someCounter)
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
                updateGrid(someCounter, randomX, randomY);
            }
        }

        return coordinateList;
    }


    /***************** generate random number *******************/
    public static int generateRandomInteger(int limit)
    {
        int randomNumber;

        Random randomGenerator = new Random();

        // generate pseudorandom. zero included, limit excluded
        randomNumber = randomGenerator.nextInt(limit);

        System.out.println("random: " + randomNumber);

        return randomNumber;
    }



    /***************** 1D array *******************/

    /**
     * Build an array of strings
     * @param length
     * @return Array of strings
     */
    public static String[] getArray(int length)
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
     *
     * @param someArray
     * @return String build from elements values in array
     */
//    public static String displayArray(String[] someArray)
//    {
//        // string to return
//        String str = "";
//
//        // loop through array
//        for(String value : someArray)
//        {
//            // add to str
//            str += value + " ";
//        }
//        return str;
//    }


    /**
     * Checks if there is 3 in a row of a specific character that is passed as argument
     * @param someArray the array being searched through
     * @param someCharacter the character we search if there is 3 in a row of
     * @return true if there is 3 in a row of the character passed in, else false
     */
    public static boolean isThreeInARowHorizontally(String[] someArray, String someCharacter)
    {
        int arrayLength = someArray.length;

        // boolean to return
        boolean isInARowPresent = false;

        // loop through array; make sure you only check until and including third last element
        for(int i = 0; i <= arrayLength - IN_A_ROW_SUCCESS; i++)
        {
//            System.out.println("Start at column: " + i);
//
//            System.out.println(someArray[i] + " " + someArray[i+1] + " " + someArray[i+2]);
//            System.out.println();

            if (someArray[i].equals(someCharacter) && someArray[i + 1].equals(someCharacter) && someArray[i + 2].equals(someCharacter))
            {
                isInARowPresent = true;
                return isInARowPresent;
            }
        }
        return isInARowPresent;
    }


    /**************** 2D array ********************/

    /**
     *
     * @param rows
     * @param columns
     * @return grid, array of arrays
     */
    public static String[][] getTwoDimensionalArray(int rows, int columns)
    {
        String[][] aTwoDimensionalArray = new String[rows][columns];

//        String[] anArray = new String[columns];

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
     * @param someTwoDimensionalArray which shall be a non-ragged array and minimum 3 x 3
     * @return grid values represented as a string
     */
    public static String displayTwoDimensionalArray(String[][] someTwoDimensionalArray)
    {
        // assumining that the grid is always

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


    /**
     *
     * @param someCharacter to find three in a row of
     * @return true if there is three in a row vertically or horizontally
     */
    public boolean isThreeInARowVersion2D(String someCharacter)
    {
//        System.out.println("CHECKING HORIZONTALLY");
//        System.out.println();

        // check if there is 3 in a row horizontally for all rows in the grid
        for(int i = 0; i < mBoard.length; i++)
        {
//            System.out.println("row: " + i);
//            System.out.println();

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
    public static boolean isThreeInARowVertically(String[][] some2DArray, String someCharacter)
    {
//        System.out.println("CHECKING VERTICALLY");
//        System.out.println();

        // get rows amount in grid
        int arrayLength = some2DArray.length;

        // boolean to return
        boolean isInARowPresent = false;

        // loop through outer array; ; make sure you only check until and including third last row amount
        for(int i = 0; i <= arrayLength - IN_A_ROW_SUCCESS; i++)
        {
//            System.out.println("row number: " + i);
//            System.out.println();

            // loop through inner array
            for(int j = 0; j < some2DArray[i].length; j++)
            {
//                System.out.println("column number: " + j);
//
//                System.out.println(some2DArray[i][j]);
//                System.out.println(some2DArray[i+1][j]);
//                System.out.println(some2DArray[i+2][j]);

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
     * @param xPos
     * @param yPos
     */
    public void updateGrid(int someCounter, int xPos, int yPos)
    {
        // check if location is free
        if (mBoard[yPos][xPos] == GRID_CHARACTER_DEFAULT)
        {
            // update location to correct character
            mBoard[yPos][xPos] = getCharacter(someCounter);
        }
    }


    /**
     * checks if position x, y is free
     * @param xPos
     * @param yPos
     * @return true if position is free, else false
     */
    public boolean isPositionAvailable(int xPos, int yPos)
    {
        boolean isFree = false;

        // check if position x, y has GRID_CHARACTER_DEFAULT
        if (mBoard[yPos][xPos].equals(GRID_CHARACTER_DEFAULT))
        {
//            System.out.println("Position is FREE");
//            System.out.println();
            isFree = true;
        }
        else
        {
//            System.out.println("Position is OCCUPIED");
//            System.out.println();
        }
        return isFree;
    }


    public String getCharacter(int number)
    {
        // decide which character
        if (number % 2 == 0)  // even
        {
            return GRID_CHARACTER_1;
        }
        else  // odd
        {
            return GRID_CHARACTER_2;
        }
    }

}
