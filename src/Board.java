import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by Andrew on 10/9/2017.
 */
public class Board {
    private char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private int[] pickedLetters = new int[26];
    private VBox pieces = new VBox();
    public Board (int size){
        HBox[] rows = new HBox[size];
        for(int i = 0; i < size; i++){
            HBox hbox = new HBox();
            rows[i] = hbox;
            for(int j = 0; j < size; j++){
                int valid = 0;
                while(valid == 0){
                    int picked = (int) (Math.random() * 26);
                    if(pickedLetters[picked] != 4){
                        hbox.getChildren().add(new Piece(alphabet[picked]).returnIV());
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
}
