/**
 * Andrew Morin
 * October 10, 2017
 */

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller extends Application {

    private static int startTime = 180;//The amount of time per round.
    private int firstGame = 0;//Certain things only need to be instantiated once.
    private int seconds = startTime;//Allows me to modify further rounds.
    private int score = 0;//Player score.
    //My labels.
    private Label countdown = new Label();//Countdown label.
    private Label scoreLabel = new Label();
    private Label gameOverLabel = new Label();
    //The 5 buttons in my program.
    private Button check = new Button("Check Word");
    private Button close = new Button("Close Game");
    private Button newGame = new Button("New Game");
    private RadioButton four = new RadioButton();
    private ToggleGroup group = new ToggleGroup();
    private RadioButton five = new RadioButton();
    //The text/text fields in my program.
    private Text text = new Text();
    private TextField wordCheck = new TextField();//Where the user can type.
    //My stages/scenes.
    private Stage endGame = new Stage();//Final stage window.
    private Stage stage;//Main stage.
    private Scene endGameScene;
    private Scene gameScene;
    private Scene begin;
    //VBox's and HBox's
    private VBox game = new VBox();
    private VBox pieces;
    private VBox guesses = new VBox();
    private HBox fullBox = new HBox();
    private VBox popUpV = new VBox();//Final stage VBox.
    //Main objects.
    private Library library = new Library();
    private String[] allWords = library.grabAll();
    private Board board;
    private ScrollPane guessPane = new ScrollPane(guesses);
    private Background background;

    public static void main (String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //I create a background image for my main gameboard here.
        Image image = new Image(Controller.class.getResourceAsStream("words/stageBack.jpg"));
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        background = new Background(backgroundImage);


        //Everything after this point is just setting fonts/sizes/insets.
        stage = primaryStage;
        endGame.initModality(Modality.WINDOW_MODAL);

        text.setText("Choose the board size:");
        scoreLabel.setText("Score: " + score);
        scoreLabel.setTextFill(Color.SLATEGRAY);
        scoreLabel.setFont(Font.font("Calibri", FontWeight.BOLD,  16));

        countdown.setTextFill(Color.SLATEGRAY);
        countdown.setFont(Font.font("Calibri", FontWeight.BOLD, 16));

        guessPane.setPrefViewportHeight(200);
        guessPane.setPrefViewportWidth(100);

        Button buttonStartGame = new Button("Confirm Size");
        buttonStartGame.setOnAction(this::handle);

        four.setText("4x4");
        five.setText("5x5");
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

        begin = new Scene(vBoxName, 300, 150);
        endGameScene = new Scene(popUpV, 300, 150);

        close.setOnAction(e -> handleEnd(e, begin));
        newGame.setOnAction(e -> handleEnd(e, begin));

        stage.setTitle("Boggle Game");
        stage.setScene(begin);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    private void handleTwo() {
        /**
         * handleTwo is meant for click and drag selection of words. I have the string stored, and
         * check it with the library. If it is valid, I increase the score by the correct amount,
         * and paste their guess in green on the right. Otherwise it is red.
         */
        wordCheck.setText(board.resetPieces());
        String checkWord = wordCheck.getText();
        Text textField = new Text();
        if(board.checkValid(checkWord)){
            score = score+(checkWord.length() - 2);
            textField.setText(checkWord.toUpperCase());
            textField.setFont(new Font("Calibri", 24));
            textField.setFill(Color.GREEN);
        } else {
            textField.setText(checkWord.toUpperCase());
            textField.setFont(new Font("Calibri", 24));
            textField.setFill(Color.RED);
        }
        scoreLabel.setText("Score: " + score);
        guesses.getChildren().add(textField);
        guessPane.setVvalue(1.0);

    }

    private void timer(){
        /**
         * Simply timer method. Basically just displays the timer and it changes the stage
         * at the end of the game.
         */
        Timeline time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);

        time.stop();

        KeyFrame frame = new KeyFrame(Duration.seconds(1), event -> {

            seconds--;

            countdown.setText("Time Remaining: " + seconds);
            if(seconds <= 0){
                stage.setHeight(179);
                stage.setWidth(300);
                time.stop();
                gameOverLabel.setText("Game Over! Your final score was: " + score);
                stage.setScene(endGameScene);
                seconds = 180;
                guesses.getChildren().clear();
            }
        });

        time.getKeyFrames().add(frame);
        time.playFromStart();
    }

    private void handleEnd(ActionEvent e, Scene scene){
        /**
         * handleEnd is my handler for the two buttons at the final stage. If the user wants to be done
         * the program will close. Otherwise it starts over.
         */
        Object source = e.getSource();
        if(source == close){
            Platform.exit();
        } else {
            stage.setScene(scene);
            game.getChildren().clear();
            score = 0;
            scoreLabel.setText("Score: " + score);
            wordCheck.clear();
        }
    }

    private void handleCheck(ActionEvent e){
        /**
         * Very similar to the first handleCheck, except this one is for text entry of guesses.
         */
        String checkWord = wordCheck.getText();
        Text textField = new Text();
        if(board.checkValid(checkWord)){
            score = score+(checkWord.length() - 2);
            textField.setText(checkWord.toUpperCase());
            textField.setFont(new Font("Calibri", 24));
            textField.setFill(Color.GREEN);
        } else {
            textField.setText(checkWord.toUpperCase());
            textField.setFont(new Font("Calibri", 24));
            textField.setFill(Color.RED);
        }
        scoreLabel.setText("Score: " + score);
        guesses.getChildren().add(textField);
        guessPane.setVvalue(1.0);
    }

    private void handle(ActionEvent e) {
        /**
         * Beginning handle check for starting the game. It essentially takes the user choice of
         * 4x4 or 5x5 and creates the appropriate board.
         */
        Toggle selected = group.getSelectedToggle();
        check.setOnAction(this::handleCheck);
        int size;

        if(selected == four) size = 4;
        else size = 5;

        board = new Board(size);

        if(selected == four && firstGame == 0){
            pieces = board.returnVBox();
            game.setAlignment(Pos.TOP_CENTER);
            gameScene = new Scene(fullBox, (100*size)+200, (100*size)+10);
            firstGame++;
        } else if(selected == five && firstGame == 0) {
            pieces = board.returnVBox();
            game.setAlignment(Pos.TOP_CENTER);
            gameScene = new Scene(fullBox, (100*size)+200, (100*size)+10);
            firstGame++;
        } else if (selected == four){
            fullBox.getChildren().removeAll(pieces,game);
            pieces = board.returnVBox();
            stage.setWidth(600);
            stage.setHeight(439);
        } else {
            fullBox.getChildren().removeAll(pieces,game);
            pieces = board.returnVBox();
            stage.setWidth(700);
            stage.setHeight(539);
        }
        game.getChildren().addAll(countdown, scoreLabel, wordCheck, check, guessPane);
        game.setPadding(new Insets(5,5,5,5));
        game.setSpacing(5);
        fullBox.getChildren().addAll(pieces, game);
        pieces.setOnMouseReleased(ev -> handleTwo());
        board.findAll(allWords);
        timer();
        game.setBackground(Background.EMPTY);
        fullBox.setBackground(background);
        stage.setScene(gameScene);
    }
}
