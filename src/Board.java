import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Created by Andrew on 10/9/2017.
 */
public class Board {
    private char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private char[] consanants = "bcdfghjklmnpqrstuvwxyz".toCharArray();
    private char[] vowels = "aeiou".toCharArray();
    private int[] pickedLetters = new int[26];
    private char[][] charArray;
    private VBox pieces = new VBox();
    private List<String> allThese;
    private int size;
    public Board (int size){
        charArray = new char[size][size];
        this.size = size;
        HBox[] rows = new HBox[size];
        for(int i = 0; i < size; i++){
            HBox hbox = new HBox();
            rows[i] = hbox;
            for(int j = 0; j < size; j++){
                int valid = 0;
                while(valid == 0){
                    int picked = (int) (Math.random() * 26);
                    if(pickedLetters[picked] != 4){
                        hbox.setPadding(new Insets(0, 10, 0, 0));
                        hbox.getChildren().add(new Piece(alphabet[picked]).returnIV());
                        charArray[i][j] = alphabet[picked];
                        pickedLetters[picked]++;
                        valid = 1;
                    }
                }
            }
            pieces.getChildren().add(hbox);
        }
    }

    public VBox returnVBox() {
        return pieces;
    }

    public void findAll(String[] all){
        WordFinder finder = new WordFinder();
        allThese = finder.findWords(charArray, all);
    }

    public boolean checkValid(String word){
        if(allThese.contains(word)){
            while(allThese.contains(word)){
                allThese.remove(word);
            }
            return true;
        }
        return false;
    }
}
