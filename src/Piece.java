import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Created by Andrew on 10/9/2017.
 */
public class Piece extends Pane {
    private char value;
    private ImageView image;
    private Rectangle rect = new Rectangle();
    private StackPane fullPiece = new StackPane();

    public Piece (char letter){
        this.value = letter;
        this.image = makeImage(letter);
    }

    private ImageView makeImage (char c){
        char upper = Character.toUpperCase(c);
        String letter = Character.toString(upper);
        Text text = new Text(letter);
        text.setFont(Font.font("Calibri", FontWeight.NORMAL, 24));

        rect.setWidth(80);
        rect.setHeight(80);
        rect.setArcWidth(25);
        rect.setArcHeight(25);
        rect.setFill(Color.CORNFLOWERBLUE);

        fullPiece.getChildren().addAll(rect, text);
        fullPiece.setPadding(new Insets(1,1,1,1));

        WritableImage write = fullPiece.snapshot(null, null);
        ImageView image = new ImageView(write);
        return image;
    }

    public StackPane returnIV(){
        return fullPiece;
    }
}
