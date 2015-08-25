/**
 * Compile the program first time:
 *      >> javac PuzzleSolver.java
 * Run the program:
 *      >> java PuzzleSolver
 * It will ask for giving file name as input
 * Pass the .txt file name containing the puzzle.
 *
 * Example of file: Puzzle.txt
 * ----------------------------
 * catapult
 * xzttoyoo
 * yotoxtxx
 *
 * The program will count the number of words found in the puzzle.
 */


import java.util.*;
import java.io.*;


public class PuzzleSolver {

    public static String[] DICTIONARY = {"OX","CAT","TOY","AT","DOG","CATAPULT","T"};
    public char[][] puzzle;

    public static boolean IsWord(String testWord)
    {
        for(String word : DICTIONARY) {
            if(word.toLowerCase().equals(testWord)) return true;
        }
        return false;
    }

    public boolean BuildPuzzle(ArrayList<String> puzzleLines) {
        this.puzzle = new char[puzzleLines.size()][puzzleLines.get(0).length()];
        try {
            for (int rows = 0; rows < puzzleLines.size(); rows++) {
                this.puzzle[rows] = puzzleLines.get(rows).toCharArray();
            }

            return true;
        } catch (ArrayIndexOutOfBoundsException err) {
            System.out.println("Error in building puzzle.");
            return false;
        }
    }

    public static int FindWords(char[][] puzzle) {

        int row = puzzle.length;
        int column = puzzle[0].length;
        ArrayList<String> wordDiscovered = new ArrayList<String>();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                boolean[][] isVisited = new boolean[row][column];
                FindWordsUtils(puzzle, wordDiscovered, i, j, Integer.MAX_VALUE, Integer.MAX_VALUE, isVisited, "");
            }
        }
        return wordDiscovered.size();
    }

    public static void FindWordsUtils(char[][] puzzle, ArrayList<String> wordDiscovered, int x, int y,
                                      int horizontal, int vertical, boolean[][] isVisited, String builtStr) {

        int row = puzzle.length;
        int column = puzzle[0].length;

        if (x < 0 || x >= row || y < 0 || y >= column) {
            return;
        } else if (isVisited[x][y]) {
            return;
        }

        builtStr += puzzle[x][y];

        if(builtStr.equals(null)) return;
        if(IsWord(builtStr)) wordDiscovered.add(builtStr);

        isVisited[x][y] = true;

        for(int xDir = -1; xDir <= 1; xDir++) {
            for(int yDir = -1; yDir <=1; yDir++) {
                if(xDir == 0 && yDir == 0) {
                    //Do nothing
                } else if((horizontal == Integer.MAX_VALUE && vertical == Integer.MAX_VALUE) ||
                        (horizontal == xDir && vertical == yDir)){
                    FindWordsUtils(puzzle, wordDiscovered, x + xDir, y + yDir, xDir, yDir, isVisited, builtStr);
                }
            }
        }

        isVisited[x][y] = false;
    }

    public static void main(String[] args) {

        System.out.println("Enter the file name contains Puzzle: ");
        Scanner sc = new Scanner(System.in);
        String fileName = sc.nextLine();

        ArrayList<String> puzzleLines = new ArrayList<String>();
        String eachLine;

        try {
            FileReader file = new FileReader(fileName);
            BufferedReader buffer = new BufferedReader(file);

            while((eachLine = buffer.readLine()) != null) {
                puzzleLines.add(eachLine.trim().toLowerCase());
            }

            buffer.close();
        }
        catch(FileNotFoundException err) {
            System.out.println("File does not exist.");
            System.exit(2);
        }
        catch(IOException err) {
            System.out.println("IO Exception.");
            System.exit(2);
        }

        PuzzleSolver solvePuzzle = new PuzzleSolver();
        if(solvePuzzle.BuildPuzzle(puzzleLines)) {
            System.out.println("Total words found in the puzzle: " + FindWords(solvePuzzle.puzzle));
        } else {
            System.out.println("Unable to build the puzzle from input file. Please try again.");
        }
    }
}