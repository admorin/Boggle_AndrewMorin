import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Controller extends Application {

    private TextField wordCheck = new TextField();
    private Stage stage;
    private Button buttonStartGame = new Button();
    private RadioButton four = new RadioButton();
    private RadioButton five = new RadioButton();
    private Text text = new Text();
    private Scene startScene;
    private Scene gameScene;
    private VBox game = new VBox();
    private ToggleGroup group = new ToggleGroup();
    private Library library = new Library(1);
    private String[] allWords = library.grabAll();
    private Board board;
    private VBox pieces;
    private HBox fullBox = new HBox();
    private Text text2 = new Text("4x4 Chosen");
    private Text text3 = new Text("5x5 Chosen");

    public static void main (String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        text.setText("Choose the board size:");

        buttonStartGame = new Button("Confirm Size");
        buttonStartGame.setOnAction(e -> handle(e));

        four.setText("4x4");
        four.setOnMouseClicked(e -> handleMouse(e));
        five.setText("5x5");
        five.setOnMouseClicked(e -> handleMouse(e));
        four.setToggleGroup(group);
        four.setSelected(true);
        five.setToggleGroup(group);

        VBox vBoxName = new VBox();
        vBoxName.setPadding(new Insets(10, 10, 10, 10));
        vBoxName.setSpacing(15);

        VBox vBoxButtons = new VBox();
        vBoxButtons.setPadding(new Insets(10, 10, 10, 10));
        vBoxButtons.setSpacing(5);

        vBoxButtons.getChildren().addAll(four, five);

        vBoxName.getChildren().addAll(text, vBoxButtons, buttonStartGame);

        startScene = new Scene(game, 250, 200);

        stage.setTitle("Boggle Game");
        stage.setScene(new Scene(vBoxName, 250, 200));
        stage.setResizable(false);
        stage.show();
    }

    private void handleMouse(MouseEvent e) {

    }

    private void handleCheck(ActionEvent p){
        String checkWord = wordCheck.getText();
        Text textField = new Text();
        if(board.checkValid(checkWord) == true){
            textField.setText(checkWord);
        } else {
            textField.setText("Not Here");
        }
        game.getChildren().add(textField);
    }

    private void handle(ActionEvent e) {
        Toggle selected = group.getSelectedToggle();
        Button check = new Button("Check Word");
        check.setOnAction(p -> handleCheck(p));
        int multiply;
        if(selected == four){
            board = new Board(4);
            pieces = board.returnVBox();
            game.getChildren().addAll(text2, wordCheck, check);
            game.setAlignment(Pos.TOP_CENTER);
            multiply = 4;
        } else {
            board = new Board(5);
            pieces = board.returnVBox();
            game.getChildren().addAll(text3, wordCheck, check);
            game.setAlignment(Pos.TOP_CENTER);
            multiply = 5;
        }
        board.findAll(allWords);
        gameScene = new Scene(fullBox, (80*multiply)+200, (80*multiply)+10);
        fullBox.getChildren().addAll(pieces, game);
        stage.setScene(gameScene);
    }
}
