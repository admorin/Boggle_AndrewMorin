/**
 * Andrew Morin
 * October 10, 2017
 */

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Piece extends Pane {
    private char value;
    private ImageView image;
    private Rectangle rect = new Rectangle();
    private Rectangle rectarea = new Rectangle();
    private StackPane fullPiece = new StackPane();
    private char[] guess;
    private Text text;
    private Background background;

    public Piece (char letter, char[] guess){
        //I create the background image for the pieces.
        Image image = new Image(Controller.class.getResourceAsStream("words/pieceBack2.jpg"));
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        background = new Background(backgroundImage);

        this.guess = guess;
        this.value = letter;
        this.image = makeImage(letter);
        //Adding action listeners to the pieces.
        fullPiece.setAlignment(Pos.CENTER);
        fullPiece.setOnDragDetected(e -> handleTwo(e));
        rectarea.setOnMouseDragEntered(e -> handleThree(e));
    }

    private void handleTwo(MouseEvent e) {
        //Initial choice.
        if(fullPiece.getOpacity() == 1){
            guess[0] = value;
            fullPiece.setOpacity(.25);
            fullPiece.startFullDrag();
        }
    }

    private void handleThree(MouseDragEvent e) {
        //Subsequent letters.
        for(int i = 0; i < 25; i++){
            if(guess[i] == '\0' && fullPiece.getOpacity() == 1){
                guess[i] = value;
                fullPiece.setOpacity(.25);
                return;
            }
        }
    }

    private ImageView makeImage (char c){
        //Creates the piece as a stack pane.
        char upper = Character.toUpperCase(c);
        String letter = Character.toString(upper);
        text = new Text(letter);
        text.setFont(Font.font("Calibri", FontWeight.NORMAL, 48));

        rectarea.setWidth(50);
        rectarea.setHeight(50);
        rectarea.setFill(Color.TRANSPARENT);

        rect.setWidth(100);
        rect.setHeight(100);
        rect.setArcWidth(25);
        rect.setArcHeight(25);
        rect.setVisible(false);

        fullPiece.getChildren().addAll(rect, rectarea, text);
        fullPiece.setPadding(new Insets(1,1,1,1));
        fullPiece.setBackground(background);

        WritableImage write = fullPiece.snapshot(null, null);
        ImageView image = new ImageView(write);
        return image;
    }

    public StackPane returnIV(){
        return fullPiece;
    }
}
