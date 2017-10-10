import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Created by Andrew on 10/9/2017.
 */
public class Piece extends Pane {
    private char value;
    private ImageView image;

    public Piece (char letter){
        this.value = letter;
        this.image = makeImage(letter);
    }

    private ImageView makeImage (char c){
        String letter = Character.toString(c);
        Text text = new Text(letter);
        text.setFont(Font.font("Calibri", FontWeight.NORMAL, 12));

        WritableImage write = text.snapshot(null, null);
        ImageView image = new ImageView(write);
        return image;
    }

    public ImageView returnIV(){
        return image;
    }
}
