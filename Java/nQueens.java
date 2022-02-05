package Java;
import Java.entities.inputOutput;
/*
 * My solution to the classical nQueens problem, in this case, I believe the algorithm
 * can work for both mandatory and non-mandatory queen position (with a few changing on user input),
 * so I just let the old board I used for tests at the end of the code. Also, this problem took me
 * a lot of time (to the point of it being considered a project), since I tried solving it with the
 * same approach I used on a previous project (sudokuSolver), and without looking things up
 * on the internet or seeking any help. Besides that, halfway through the problem I thought I couldn't do it,
 * so I basically gave up trying and started just taking quick looks at it occasionally, but yesterday 04/02/2022(dd/MM/yyyy)
 * I had an idea popping into my mind and did it. So anyway, I am very proud of the results, and hope the code works for everyone */
public class nQueens {
    //this class is only defined so we can use grid size as a global variable (it's not a good habit tho
    //i just did it this time because i wanted to avoid OOP as much as possible, since i will post a purely OOP solution)
    public static class Sizes {
        public static int grid_Size;
    }

    public static void main(String[] args) {
        System.out.println("enter the size of the board: ");
        Sizes.grid_Size = inputOutput.readInt();
        char[][] board = new char[Sizes.grid_Size][Sizes.grid_Size];
        for (int x = 0; x < Sizes.grid_Size; x++) {
            for (int y = 0; y < Sizes.grid_Size; y++) {
                board[x][y] = '-';
            }
        }

        printBoard(board);

        if (solveBoard(board, 0)) {
            System.out.println("Solved!");
        } else {
            System.out.println("Oops, there's no solution for this grid mate");
        }
        printBoard(board);
    }

    private static void printBoard(char[][] board) {
        for (int row = 0; row < Sizes.grid_Size; row++) {
            for (int column = 0; column < Sizes.grid_Size; column++) {
                System.out.print(" " + board[row][column]);
            }
            System.out.println();
        }
    }

    /*
    so, I am not going to comment everything, since the code is pretty straightforward,but the
    idea behind these isValidMethods() is that we check whether we have a queen on a position we shouldn't
    ,and they are divided for purely organization purposes
    */
    public static boolean isValidHorizontal(char[][] board, int row, int column) {
        for (int x = 0; x < Sizes.grid_Size; x++) {
            if (board[row][x] == 'Q') {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidVertical(char[][] board, int row, int column) {
        for (int x = 0; x < Sizes.grid_Size; x++) {
            if (board[x][column] == 'Q') {
                return false;
            }
        }
        return true;
    }
    /*
    * also, I want to talk about this part, because that was my first approach to check the diagonals,
    * so I will let it here in case someone needs it. Basically it was based on a formula I came up with,
    * to find if the queens are on the same diagonals, looking at it now, the first error I see is that I
    * am not using the Math.abs() function, so when we compare a negative value with a positive one, the result
    * will be false even though it should be true. I will keep these methods here as comments.

    public static boolean isValid_RightDiagonal(char[][] board, int row, int column) {
        for (int x = 0; x < Sizes.grid_Size; x++) {
            for (int y = 0; y < Sizes.grid_Size; y++) {
                //when going down to the right
                if (x - y == row && board[x][y] == 'Q') {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isValid_LeftDiagonal(char[][] board, int row, int column) {
        for (int x = 0; x < Sizes.grid_Size; x++) {
            for (int y = 0; y < Sizes.grid_Size; y++) {
                //when going down to the left
                if (x + y == row + column && board[x][y] == 'Q') {
                    return false;
                }
            }
        }
        return true;
    }
    */

    /*
    checks diagonals in both sides, left and right.
     */
    public static boolean isValid_Diagonal(char[][] board, int row, int column) {
        for (int x = 0; x < Sizes.grid_Size; x++) {
            for (int y = 0; y < Sizes.grid_Size; y++) {
                /*actually, I was doing a lot of wrong stuff here, basically my backtrack was alright,
                  but I was messing things up when checking the diagonals, it took me so long to realize that
                  I was checking them the wrong way.
                * the funny part, is that I came up with this idea down below when I thought about a problem
                * I had solved long before. The problem was about finding the minimum amount of steps a man
                * would need to take if he wanted to move from a coordinate A to a coordinate B, and it resulted
                * in a similar formula.
                * you can validate this formula in a board I wrote as comment at the end of the code.
                */
                int diffRow = Math.abs(row - x);
                int diffColumn = Math.abs(column - y);
                if (board[x][y] == 'Q' && diffRow == diffColumn) {
                    return false;
                }
            }
        }
        return true;
    }

    /*
     * Nothing amazing here, just checking if the queen can be placed in the position
     */
    public static boolean isValidPlacement(char[][] board, int row, int column) {
        return isValidHorizontal(board, row, column) &&
                isValidVertical(board, row, column) &&
                isValid_Diagonal(board, row, column);
    }

    /*
     * and here comes the backtracking idea, and to be honest, that wasn't really hard, since I already had
     * the experience from sudoku, understanding the recursion there wasn't as hard as it was on the first time.
     * */
    public static boolean solveBoard(char[][] board, int queenCount) {
        //basically we are running through the board, and checking if a spot is open
        for (int row = 0; row < Sizes.grid_Size; row++) {
            for (int column = 0; column < Sizes.grid_Size; column++) {
                //so, if a spot is open
                if (board[row][column] == '-') {
                    //we check if this is spot is a valid placement
                    if (isValidPlacement(board, row, column)) {
                        //and if it is a valid placement, we put a queen there.
                        board[row][column] = 'Q';
                        //ok, but what if we can't solve the board with a queen in this position?
                        /*
                         * here is when recursion comes in handy, basically, we are checking if we
                         * can solve the board in the given position, if we can't, we set the place as
                         * '-' open, and iterate through the matrix(board) once again, but in the next
                         * spot
                         */
                        if (solveBoard(board, queenCount + 1)) {
                            return true;
                        } else {
                            board[row][column] = '-';
                        }
                    }
                }
            }
        }
        /*so this right here, guarantees that we loop until we get N queens on the NxN board,
         *otherwise, the code would stop before reaching the N queens, since we can easily have
         *a solution using fewer queens on a bigger board.
         */
        return queenCount == Sizes.grid_Size;
    }
    /*
    0,0 | 0,1 | 0,2 | 0,3| 0,4| 0,5| 0,6| 0,7|
    1,0 | 1,1 | 1,2 | 1,3| 1,4| 1,5| 1,6| 1,7|
    2,0 | 2,1 | 2,2 | 2,3| 2,4| 2,5| 2,6| 2,7|
    3,0 | 3,1 | 3,2 | 3,3| 3,4| 3,5| 3,6| 3,7|
    4,0 | 4,1 | 4,2 | 4,3| 4,4| 4,5| 4,6| 4,7|
    5,0 | 5,1 | 5,2 | 5,3| 5,4| 5,5| 5,6| 5,7|
    6,0 | 6,1 | 6,2 | 6,3| 6,4| 6,5| 6,6| 6,7|
    7,0 | 7,1 | 7,2 | 7,3| 7,4| 7,5| 7,6| 7,7|
     */
    /*      that's the old board i used for tests, ill keep it here
                in case i need it for something.

                char[][] board = {
                {'-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-'}
                */


}
