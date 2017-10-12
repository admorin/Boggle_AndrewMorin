import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller extends Application {

    private static int startTime = 5;
    private int firstGame = 0;
    private int seconds = startTime;
    private int score = 0;
    private Label countdown = new Label();
    private TextField wordCheck = new TextField();
    private Stage endGame = new Stage();
    private Stage stage;
    private VBox popUpV = new VBox();
    private Button buttonStartGame = new Button();
    private Button check = new Button("Check Word");
    private Button close = new Button("Close Game");
    private Button newGame = new Button("New Game");
    private Label gameOverLabel = new Label();
    private RadioButton four = new RadioButton();
    private RadioButton five = new RadioButton();
    private Text text = new Text();
    private Scene startScene;
    private Scene endGameScene;
    private Scene gameScene;
    private Scene begin;
    private VBox game = new VBox();
    private ToggleGroup group = new ToggleGroup();
    private Library library = new Library(1);
    private String[] allWords = library.grabAll();
    private Board board;
    private VBox pieces;
    private HBox fullBox = new HBox();
    private Label scoreLabel = new Label();

    public static void main (String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        endGame.initModality(Modality.WINDOW_MODAL);


        text.setText("Choose the board size:");
        scoreLabel.setText("Score: " + score);

        buttonStartGame = new Button("Confirm Size");
        buttonStartGame.setOnAction(e -> handle(e));

        four.setText("4x4");
        four.setOnMouseClicked(e -> handleMouse(e));
        five.setText("5x5");
        five.setOnMouseClicked(e -> handleMouse(e));
        four.setToggleGroup(group);
        four.setSelected(true);
        five.setToggleGroup(group);

        close.setPadding(new Insets(5,5,5,5));
        newGame.setPadding(new Insets(5, 5, 5, 5));
        HBox popUpH = new HBox();
        popUpH.getChildren().addAll(close, newGame);
        popUpH.setPadding(new Insets(25, 20, 5, 20));
        popUpH.setSpacing(50);
        popUpH.setAlignment(Pos.CENTER);
        popUpV.getChildren().addAll(gameOverLabel, popUpH);
        popUpV.setAlignment(Pos.CENTER);

        VBox vBoxName = new VBox();
        vBoxName.setPadding(new Insets(10, 10, 10, 10));
        vBoxName.setSpacing(15);

        VBox vBoxButtons = new VBox();
        vBoxButtons.setPadding(new Insets(10, 10, 10, 10));
        vBoxButtons.setSpacing(5);

        vBoxButtons.getChildren().addAll(four, five);

        vBoxName.getChildren().addAll(text, vBoxButtons, buttonStartGame);

        startScene = new Scene(game, 250, 200);
        begin = new Scene(vBoxName, 250, 200);
        endGameScene = new Scene(popUpV, 300, 150);

        close.setOnAction(e -> handleEnd(e, begin));
        newGame.setOnAction(e -> handleEnd(e, begin));

        stage.setTitle("Boggle Game");
        stage.setScene(begin);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    private void handleMouse(MouseEvent e) {

    }

    private void timer(){
        Timeline time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);

        if(time != null){
            time.stop();
        }

        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                seconds--;

                countdown.setText("Time Remaining: " + seconds);
                if(seconds <= 0){
                    stage.setHeight(179);
                    stage.setWidth(300);
                    time.stop();
                    gameOverLabel.setText("Game Over! Your final score was: " + score);
                    stage.setScene(endGameScene);
                    seconds = 5;
                }
            }
        });

        time.getKeyFrames().add(frame);
        time.playFromStart();
    }

    private void handleEnd(ActionEvent e, Scene scene){
        Object source = e.getSource();
        if(source == close){
            Platform.exit();
        } else {
            stage.setScene(scene);
        }
    }

    private void handleCheck(ActionEvent p){
        String checkWord = wordCheck.getText();
        Text textField = new Text();
        if(board.checkValid(checkWord) == true){
            score = score+(checkWord.length() - 2);
            textField.setText(checkWord);
        } else {
            textField.setText("Not Here");
        }
        scoreLabel.setText("Score: " + score);
        game.getChildren().add(textField);
    }

    private void handle(ActionEvent e) {
        Toggle selected = group.getSelectedToggle();
        check.setOnAction(p -> handleCheck(p));
        int multiply;
        if(selected == four && firstGame == 0){
            board = new Board(4);
            pieces = board.returnVBox();
            game.getChildren().addAll(countdown, scoreLabel, wordCheck, check);
            game.setAlignment(Pos.TOP_CENTER);
            multiply = 4;
            gameScene = new Scene(fullBox, (80*multiply)+200, (80*multiply)+10);
            firstGame++;
            fullBox.getChildren().addAll(pieces, game);
        } else if(selected == five && firstGame == 0) {
            board = new Board(5);
            pieces = board.returnVBox();
            game.getChildren().addAll(countdown, scoreLabel, wordCheck, check);
            game.setAlignment(Pos.TOP_CENTER);
            multiply = 5;
            gameScene = new Scene(fullBox, (80*multiply)+200, (80*multiply)+10);
            firstGame++;
            fullBox.getChildren().addAll(pieces, game);
        } else if (selected == four){
            fullBox.getChildren().removeAll(pieces,game);
            board = new Board(4);
            pieces = board.returnVBox();
            stage.setWidth(520);
            stage.setHeight(359);
            fullBox.getChildren().addAll(pieces, game);
        } else {
            fullBox.getChildren().removeAll(pieces,game);
            board = new Board(5);
            pieces = board.returnVBox();
            stage.setWidth(600);
            stage.setHeight(439);
            fullBox.getChildren().addAll(pieces, game);
        }
        board.findAll(allWords);
        timer();
        stage.setScene(gameScene);
        System.out.println(stage.getHeight());
    }
}
