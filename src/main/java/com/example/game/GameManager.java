package com.example.game;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.geometry.Pos;
import javafx.scene.text.Text;


public class GameManager extends Application {
    private double time;

    public static final int TILE_SIZE = 40; // Grace's constant don't delete it.
    public static final int TITRIUS_SIZE = 40;
    public static final int GRID_WIDTH = 10;
    public static final int GRID_HEIGHT = 14;

    public static final String boxStyle = "-fx-background-color: #fee3c5;-fx-border-color: #000000;-fx-border-width: 2px;";

    public Integer scoreNum = 0;


    @FXML
    private void startNewGame(ActionEvent event) {
        try {
            lunchPlayBoard((Stage) ((Node) event.getSource()).getScene().getWindow());
        } catch (IOException e) {
            e.printStackTrace(); // Handle the IOException appropriately
        }
    }

    private Parent setContent(){
        Pane root = new Pane();
        root.setPrefSize(GRID_WIDTH * TITRIUS_SIZE, GRID_HEIGHT * TITRIUS_SIZE);

        Canvas canvas = new Canvas(GRID_WIDTH * TITRIUS_SIZE, GRID_HEIGHT * TITRIUS_SIZE);

        root.getChildren().add(canvas);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                time += 0.03;
                if(time >= 0.5) {
//                    render();
                    time = 0;
                }
            }
        };
        timer.start();
        return root;
    }

    public void lunchPlayBoard(Stage stage) throws IOException {

        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("play-board.fxml"));
        Parent root = loader.load(); // Load the FXML file and get the root node

        // Cast the root node to AnchorPane (or the appropriate type)
        AnchorPane anchorPane = (AnchorPane) root;

        HBox gameContainer = new HBox();


        // 获取屏幕尺寸
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // Create an ImageView and set an image
        Image image = new Image("file:./src/asset/Image/background.jpg");
        ImageView imageView = new ImageView(image);
        imageView.fitWidthProperty().bind(anchorPane.widthProperty());
        imageView.fitHeightProperty().bind(anchorPane.heightProperty());

        gameContainer.setPrefSize(screenWidth, screenHeight);
        gameContainer.setAlignment(Pos.CENTER);

        FlowPane gameBoard = new FlowPane();
        gameBoard.setPrefWidth(600);
        gameBoard.setPadding(new Insets(20, 0, 20, 0));

        //create game board boxes

        //create score box
        HBox scoreBox = new HBox();
        scoreBox.setPrefWidth(TITRIUS_SIZE * GRID_WIDTH);
        scoreBox.setAlignment(Pos.CENTER);
        scoreBox.setPadding(new Insets(10, 0, 20, 0));
        Text scoreText = new Text();
        Text scoreHolder = new Text();
        scoreText.setText("SCORE: ");
        scoreHolder.setText(scoreNum.toString());
        scoreBox.setStyle("-fx-color: #a88d53; -fx-font-size: 28px;-fx-text-alignment: center;");
        scoreBox.getChildren().addAll(scoreText, scoreHolder);

        //create playGround
        Parent playGround = setContent();
        playGround.setStyle(boxStyle);


        //link to game board
        gameBoard.getChildren().addAll(scoreBox, playGround);

        gameContainer.getChildren().add(gameBoard);
        anchorPane.getChildren().addAll(imageView, gameContainer);


        // Set up the stage and scene
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        stage.setTitle("EcoStack");
        stage.show();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage.setScene(new Scene(root, 400, 275));
        stage.setTitle("EcoStack");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}