/**
 * Andrew Morin
 * October 10, 2017
 */

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.List;

public class Board {
    private char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private String consanants = "bcdfghjklmnpqrstuvwxyz";
    private String vowels = "aeiou";

    private int[] pickedLetters = new int[26]; //Keep track of letters already picked.
    private char[][] charArray; //2D array of board.
    private VBox pieces = new VBox(); //Holds all 4/5 rows of pieces.
    private List<String> allThese; //List of words.
    private int size; //Size picked for board.
    private char[] guess = new char[25]; //Array to hold the user guess.
    private char lastPicked; //Last picked letter, used for Q-U pairing.

    public Board (int size){
        /**
         * Board will go through the entire size of the array and assign a new piece to each
         * location. It is composed of a single VBox, with HBox's each row, and places a
         * piece into each of the columns.
         */
        charArray = new char[size][size];
        this.size = size;
        HBox[] rows = new HBox[size];
        for(int i = 0; i < size; i++){
            HBox hbox = new HBox();
            rows[i] = hbox;
            for(int j = 0; j < size; j++){
                int valid = 0;
                int probability = (int) (Math.random() * 100); //Probability to choose vowel or consonant.
                while(valid == 0){
                    int picked = (int) (Math.random() * 26); //Which letter in the alphabet.
                    if(lastPicked == 'q'){ //Loop to hand Q-U pairing.
                        int probabilityQ = (int) (Math.random() * 100);
                        if(probabilityQ >= 50 && pickedLetters[20] < 4){
                            hbox.setPadding(new Insets(0, 10, 0, 0));
                            //Creates a new piece for each column. Adds to array and continues.
                            hbox.getChildren().add(new Piece(alphabet[20], guess).returnIV());
                            charArray[i][j] = alphabet[20];
                            pickedLetters[20]++;
                            lastPicked = alphabet[20];
                            valid = 1;
                        } else { //If we don't need to worry about pairing, it picks randomly.
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
        /**
         * resetPieces will do just that. Goes through each HBox inside the VBox and sets the nodes(StackPane) to
         * 1.0 Opacity. This will essentially reset their use. Since this is called every time the user guesses,
         * it will also check the word and send it back to the Controller.
         */
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
        /**
         * This method is where I use the prefix tree.
         */
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
