package Java;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class nQueens {
    
    static final int GRID_SIZE = 8;

    public static class inputOutput {

        private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.ISO_8859_1));
        private static String charset = "ISO-8859-1";

        public static void setCharset(String charset_) {
            charset = charset_;
            in = new BufferedReader(new InputStreamReader(System.in, Charset.forName(charset)));
        }

        public static double readDouble() {
            double d = -1;
            try {
                d = Double.parseDouble(readString().trim().replace(",", "."));
            } catch (Exception ignored) {
            }
            return d;
        }

        public static double readDouble(String str) {
            try {
                PrintStream out = new PrintStream(System.out, true, charset);
                out.print(str);
            } catch (UnsupportedEncodingException e) {
                System.out.println("Erro: charset invalido");
            }
            return readDouble();
        }

        public static float readFloat() {
            return (float) readDouble();
        }

        public static float readFloat(String str) {
            return (float) readDouble(str);
        }

        public static int readInt() {
            int i = -1;
            try {
                i = Integer.parseInt(readString().trim());
            } catch (Exception ignored) {
            }
            return i;
        }

        public static int readInt(String str) {
            try {
                PrintStream out = new PrintStream(System.out, true, charset);
                out.print(str);
            } catch (UnsupportedEncodingException e) {
                System.out.println("Erro: charset invalido");
            }
            return readInt();
        }

        public static String readString() {
            StringBuilder s = new StringBuilder();
            char tmp;
            try {
                do {
                    tmp = (char) in.read();
                    if (tmp != '\n' && tmp != ' ' && tmp != 13) {
                        s.append(tmp);
                    }
                } while (tmp != '\n' && tmp != ' ');
            } catch (IOException ioe) {
                System.out.println("lerPalavra: " + ioe.getMessage());
            }
            return s.toString();
        }

        public static String readString(String str) {
            try {
                PrintStream out = new PrintStream(System.out, true, charset);
                out.print(str);
            } catch (UnsupportedEncodingException e) {
                System.out.println("Erro: charset invalido");
            }
            return readString();
        }

        public static String readLine() {
            String s = "";
            char tmp;
            try {
                do {
                    tmp = (char) in.read();
                    if (tmp != '\n' && tmp != 13) {
                        s += tmp;
                    }
                } while (tmp != '\n');
            } catch (IOException ioe) {
                System.out.println("lerPalavra: " + ioe.getMessage());
            }
            return s;
        }

        public static String readLine(String str) {
            try {
                PrintStream out = new PrintStream(System.out, true, charset);
                out.print(str);
            } catch (UnsupportedEncodingException e) {
                System.out.println("Erro: charset invalido");
            }
            return readLine();
        }

        public static char readChar() {
            char resp = ' ';
            try {
                resp = (char) in.read();
            } catch (Exception ignored) {
            }
            return resp;
        }

        public static char readChar(String str) {
            try {
                PrintStream out = new PrintStream(System.out, true, charset);
                out.print(str);
            } catch (UnsupportedEncodingException e) {
                System.out.println("Erro: charset invalido");
            }
            return readChar();
        }

        public static boolean readBoolean() {
            boolean resp = false;
            String str = "";

            try {
                str = readString();
            } catch (Exception ignored) {
            }

            if (str.equals("true") || str.equals("TRUE") || str.equals("t") || str.equals("1") ||
                    str.equals("verdadeiro") || str.equals("VERDADEIRO") || str.equals("V")) {
                resp = true;
            }

            return resp;
        }

        public static boolean readBoolean(String str) {
            try {
                PrintStream out = new PrintStream(System.out, true, charset);
                out.print(str);
            } catch (UnsupportedEncodingException e) {
                System.out.println("Erro: charset invalido");
            }
            return readBoolean();
        }

        public static void pause() {
            try {
                in.read();
            } catch (Exception ignored) {
            }
        }

        public static void pause(String str) {
            try {
                PrintStream out = new PrintStream(System.out, true, charset);
                out.print(str);
            } catch (UnsupportedEncodingException e) {
                System.out.println("Erro: charset invalido");
            }
            pause();
        }

    }

    public static void main(String[] args) {
        System.out.println("enter the size of the board: ");
        final int GRID_SIZE = inputOutput.readInt();
        char[][] board = new char[GRID_SIZE][GRID_SIZE];
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE; y++) {
                board[x][y] = '-';
            }
        }

        //        char[][] board = {
//                {'-', '-', '-', '-', '-', '-', '-', '-'},
//                {'-', '-', '-', '-', '-', '-', '-', '-'},
//                {'-', '-', '-', '-', '-', '-', '-', '-'},
//                {'-', '-', '-', '-', '-', '-', '-', '-'},
//                {'-', '-', '-', '-', '-', '-', '-', '-'},
//                {'-', '-', '-', '-', '-', '-', '-', '-'},
//                {'-', '-', '-', '-', '-', '-', '-', '-'},
//                {'-', '-', '-', '-', '-', '-', '-', '-'}
//        };
        printBoard(board);

        if (solveBoard(board, 0)) {
            System.out.println("Solved!");
        } else {
            System.out.println("ISH");
        }
        printBoard(board);
    }

    private static void printBoard(char[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++) {
                System.out.print(" " + board[row][column]);
            }
            System.out.println();
        }
    }

    public static boolean isValidHorizontal(char[][] board, int row, int column) {
        for (int x = 0; x < GRID_SIZE; x++) {
            if (board[row][x] == 'Q') {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidVertical(char[][] board, int row, int column) {
        for (int x = 0; x < GRID_SIZE; x++) {
            if (board[x][column] == 'Q') {
                return false;
            }
        }
        return true;
    }

    public static boolean isValid_RightDiagonal(char[][] board, int row, int column) {
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE; y++) {
                //when going down to the right
                if (x - y == row && board[x][y] == 'Q') {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isValid_LeftDiagonal(char[][] board, int row, int column) {
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE; y++) {
                //when going down to the left
                if (x + y == row + column && board[x][y] == 'Q') {
                    return false;
                }
            }
        }
        return true;
    }

    /*
    checks diagonals in both sides, left and right.
     */
    public static boolean isValid_Diagonal(char[][] board, int row, int column) {
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE; y++) {
                /*actually, i was doing a lot of wrong stuff here, basically my backtrack was alright,
                  but i was messing things up when checking the diagonals
                  it took me so long to realize that i was checking the diagonals the wrong way
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

    public static boolean isValidPlacement(char[][] board, int row, int column) {
        return isValidHorizontal(board, row, column) &&
                isValidVertical(board, row, column) &&
                isValid_Diagonal(board, row, column);
    }

    public static boolean solveBoard(char[][] board, int queenCount) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++) {
                if (board[row][column] == '-') {
                    if (isValidPlacement(board, row, column)) {
                        board[row][column] = 'Q';
                        if (solveBoard(board, queenCount + 1)) {
                            return true;
                        } else {
                            board[row][column] = '-';
                        }
                    }
                }
            }
        }
        return queenCount == GRID_SIZE;
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

}
