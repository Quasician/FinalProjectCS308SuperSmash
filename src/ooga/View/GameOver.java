package ooga.View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameOver extends Application {
  private String winner;
  private int remainingHealth;

  public GameOver(String whoWinner, int winnerHealth){
    winner = whoWinner;
    remainingHealth = winnerHealth;
  }
  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setScene(new Scene(makeBox()));
    primaryStage.setTitle("GAME OVER");
    primaryStage.show();
  }

  private VBox makeBox(){
    VBox mainBox = new VBox();
    Label gameOver = new Label("Game Over!");
    gameOver.setStyle("-fx-font-size: 30");
    Label whoWon = new Label(winner);
    whoWon.setStyle("-fx-font-size: 20");
    Label healthLeft = new Label("with " + remainingHealth + " health remaining!");
    healthLeft.setStyle("-fx-font-size: 15");
    mainBox.getChildren().addAll(gameOver, whoWon, healthLeft);
    return mainBox;
  }
}
