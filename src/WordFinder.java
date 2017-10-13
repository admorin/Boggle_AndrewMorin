/**
 * Andrew Morin
 * October 10, 2017
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * WordFinder uses Trie to find all of the words in the 2D array and stores them in a list for me to 
 * search through later on to verify words that the user guesses.
 */

public class WordFinder {
    Set<String> result = new HashSet<String>();

    public List<String> findWords(char[][] board, String[] words) {
        //HashSet<String> result = new HashSet<String>();

        Trie trie = new Trie();
        for(String word: words){
            trie.insert(word);
        }

        int m=board.length;
        int n=board[0].length;

        boolean[][] visited = new boolean[m][n];

        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                depthFirstSearch(board, visited, "", i, j, trie);
            }
        }

        return new ArrayList<String>(result);
    }

    //This method uses a searching algorithm to recurse it's way through the array.
    //It uses visited markers to ensure places aren't revisited.
    
    public void depthFirstSearch(char[][] board, boolean[][] visited, String str, int i, int j, Trie trie){
        int m=board.length;
        int n=board[0].length;

        if(i<0 || j<0||i>=m||j>=n){
            return;
        }

        if(visited[i][j])
            return;

        str = str + board[i][j];

        if(!trie.startsWith(str))
            return;

        if(trie.search(str)){
            if(!result.contains(str) && str.length() > 2){
                result.add(str);

            }
        }
        
        //The recursion aspect, searching in all 8 directions.

        visited[i][j]=true;
        depthFirstSearch(board, visited, str, i-1, j, trie);
        depthFirstSearch(board, visited, str, i+1, j, trie);
        depthFirstSearch(board, visited, str, i, j-1, trie);
        depthFirstSearch(board, visited, str, i, j+1, trie);
        depthFirstSearch(board, visited, str, i-1, j-1, trie);
        depthFirstSearch(board, visited, str, i-1, j+1, trie);
        depthFirstSearch(board, visited, str, i+1, j-1, trie);
        depthFirstSearch(board, visited, str, i+1, j+1, trie);
        visited[i][j]=false;
    }
}