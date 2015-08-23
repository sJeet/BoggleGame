/**
 * Created by jeet on 8/19/15.
 */


import java.util.*;
import java.io.*;


public class PuzzleSolver {

    public static String[] DICTIONARY = {"OX","CAT","TOY","AT","DOG","CATAPULT","T"};

    public static class Item {
        public final int xCord, yCord;
        public final String prefixString;

        public Item(int rowIndex, int columnIndex, String prefixStr) {
            this.xCord = rowIndex;
            this.yCord = columnIndex;
            this.prefixString = prefixStr;
        }
    }

    public static Trie generateTrie() {
        Trie trie = new Trie();

        for (String word : DICTIONARY) {
            word = word.trim().toLowerCase();
            trie.addWord(word);
        }
        return trie;
    }

    public static int findWords(char[][] puzzle) {

        Trie dictionary = generateTrie();

        return findWords(puzzle, dictionary);

    }

    public static int findWords(char[][] puzzle, Trie dict) {

        int m = puzzle.length;
        int n = puzzle[0].length;
        ArrayList<String> foundWords = new ArrayList<String>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                boolean[][] visited = new boolean[m][n];
                findWordsDFS(foundWords, dict, puzzle, visited, new Item(i, j, ""), 7, 7);
            }
        }
        return foundWords.size();
    }

    public static void findWordsDFS(ArrayList<String> foundWords, Trie dict, char[][] puzzle,
                             boolean[][] visited, Item item, int xDir, int yDir) {

        int m = puzzle.length;
        int n = puzzle[0].length;
        int x = item.xCord;
        int y = item.yCord;

        if (x < 0 || x >= m || y < 0 || y >= n) {
            return;
        } else if (visited[x][y]) {
            return;
        }

        String newPrefixString = item.prefixString + puzzle[x][y];
        TrieNode foundWord = dict.matchWithDict(newPrefixString);

        if (foundWord == null) return;
        if (foundWord.isWord()) foundWords.add(newPrefixString);

        visited[x][y] = true;

        for(int xOff = -1; xOff <= 1; xOff++) {
            for(int yOff = -1; yOff <=1; yOff++) {
                if(xOff == 0 && yOff == 0) {
                    continue;
                } else if((xDir == xOff && yDir == yOff) || (xDir == 7 && yDir == 7)){
                    findWordsDFS(foundWords, dict, puzzle, visited, new Item(x + xOff, y + yOff, newPrefixString), xOff, yOff);
                }
            }
        }

        visited[x][y] = false;
    }

    public static void main(String[] args) {

        String fileName = "input.txt";
        String line = "";
        if(args.length > 0) {
            fileName = args[0];
        }

        int indexForExt = fileName.lastIndexOf(".");
        if(!(indexForExt > 0 && (fileName.substring(indexForExt + 1).equals("txt")))) {
            System.out.println("Please pass '.txt' file containing puzzle.");
            System.exit(2);
        }

        ArrayList<String> puzzleRows = new ArrayList<String>();

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                puzzleRows.add(line.trim().toLowerCase());
            }

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("'" + fileName + "': File does not exist.");
            System.exit(2);
        }
        catch(IOException ex) {
            System.out.println("'" + fileName + "': Error reading file.");
            System.exit(2);
        }

        char[][] puzzle = new char[puzzleRows.size()][puzzleRows.get(0).length()];
        for (int rows = 0; rows < puzzleRows.size(); rows++) {
            puzzle[rows] = puzzleRows.get(rows).toCharArray();
        }

        System.out.println("===========================================");
        System.out.println("Dictionary");
        System.out.println("----------");
        for(String wordInDict : DICTIONARY) {
            System.out.print("'" + wordInDict + "' ");
        }
        System.out.println("\n===========================================");
        System.out.println("Given Puzzle");
        System.out.println("===========================================");
        for(String eachLine: puzzleRows) {
            System.out.println(eachLine.trim().toUpperCase());
        }
        System.out.println("===========================================");
        System.out.println(findWords(puzzle) + " words are found in the given Puzzle");
        System.out.println("===========================================");
    }
}