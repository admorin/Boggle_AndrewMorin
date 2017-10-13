import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Created by Andrew on 10/9/2017.
 */
public class Board {
    private char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private String consanants = "bcdfghjklmnpqrstuvwxyz";
    private String vowels = "aeiou";
    private int[] pickedLetters = new int[26];
    private char[][] charArray;
    private VBox pieces = new VBox();
    private List<String> allThese;
    private int size;
    private char[] guess = new char[25];
    public boolean pressed = false;
    private char lastPicked;
    public Board (int size){
        charArray = new char[size][size];
        this.size = size;
        HBox[] rows = new HBox[size];
        for(int i = 0; i < size; i++){
            HBox hbox = new HBox();
            rows[i] = hbox;
            for(int j = 0; j < size; j++){
                int valid = 0;
                int probability = (int) (Math.random() * 100);
                while(valid == 0){
                    int picked = (int) (Math.random() * 26);
                    if(lastPicked == 'q'){
                        int probabilityQ = (int) (Math.random() * 100);
                        if(probabilityQ >= 50 && pickedLetters[20] < 4){
                            hbox.setPadding(new Insets(0, 10, 0, 0));
                            hbox.getChildren().add(new Piece(alphabet[20], guess).returnIV());
                            charArray[i][j] = alphabet[20];
                            pickedLetters[20]++;
                            lastPicked = alphabet[20];
                            valid = 1;
                        } else {
                            if(probability >= 50 && (vowels.indexOf(alphabet[picked]) >= 0)
                                    || (probability < 50 && consanants.indexOf(alphabet[picked]) >= 0)){
                                hbox.setPadding(new Insets(0, 10, 0, 0));
                                hbox.getChildren().add(new Piece(alphabet[picked], guess).returnIV());
                                charArray[i][j] = alphabet[picked];
                                pickedLetters[picked]++;
                                lastPicked = alphabet[picked];
                                valid = 1;
                            }
                        }
                    } else if(pickedLetters[picked] != 4){
                        if(probability >= 50 && (vowels.indexOf(alphabet[picked]) >= 0)
                                || (probability < 50 && consanants.indexOf(alphabet[picked]) >= 0)){
                            hbox.setPadding(new Insets(0, 10, 0, 0));
                            hbox.getChildren().add(new Piece(alphabet[picked], guess).returnIV());
                            charArray[i][j] = alphabet[picked];
                            pickedLetters[picked]++;
                            lastPicked = alphabet[picked];
                            valid = 1;
                        }
                    }
                }
            }
            pieces.getChildren().add(hbox);
        }
    }

    public String resetPieces (){
        for(int i =0; i < size; i++){
            Node node = pieces.getChildren().get(i);
            for(int j = 0; j < size; j++){
                Node node2 = ((HBox)node).getChildren().get(j);
                node2.setOpacity(1);
            }
        }
        String guess2String = new String(guess);
        boolean done = false;
        int count = 0;
        while(!done){
            guess[0+count] = '\0';
            count++;
            if(count==25)done=true;
        }
        return guess2String;
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
