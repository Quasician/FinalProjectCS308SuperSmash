package ooga.View;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Preloader.StateChangeNotification;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ooga.Model.Characters.Character2;
import ooga.Model.Characters.CharacterSuper;
import ooga.Model.Player;

import java.util.ArrayList;

public class GameView extends Application implements ViewInternal {

  private ArrayList<Player> playerList;
  private Scene scene;
  private Group root;

  private BooleanProperty A_PRESSED = new SimpleBooleanProperty();
  private BooleanProperty D_PRESSED = new SimpleBooleanProperty();
  private BooleanProperty LEFT_PRESSED = new SimpleBooleanProperty();
  private BooleanProperty RIGHT_PRESSED = new SimpleBooleanProperty();

  CharacterSuper bunny;
  CharacterSuper bunny2;
  int y;
  int y2;
  Rectangle stage = new Rectangle(100, 600, 500, 100);;

  @Override
  public void resetGame() {

  }

  @Override
  public void setCharacter() {

  }

  @Override
  public void setStage() {
  }


  public void setKeyBinds() {
    // Main game loop
    AnimationTimer animationTimer = new AnimationTimer() {
      @Override
      public void handle(long now) {

        if (!bunny.getHurtBox().getBoundsInParent().intersects(stage.getBoundsInParent()))
        {
          y += 3;
          bunny.getHurtBox().setY(bunny.getHurtBox().getY() + 3);
          bunny.setCenterY(y);
        }
        if (!bunny2.getHurtBox().getBoundsInParent().intersects(stage.getBoundsInParent()))
        {
          y2 += 3;
          bunny2.getHurtBox().setY(bunny2.getHurtBox().getY() + 3);
          bunny2.setCenterY(y2);
        }

//        if (!bunny.getHurtBox().getBoundsInParent().intersects(stage.getBoundsInParent()))
//        {
//          //y += 3;
//          bunny.setCenterY(y);
//          bunny2.setCenterY(y);
//        }
//        if (!bunny2.getHurtBox().getBoundsInParent().intersects(stage.getBoundsInParent()))
//        {
//          //y2 += 3;
//          bunny2.setCenterY(y);
//        }

        //Update and render
        if (D_PRESSED.get()){
          bunny.moveRight();
        }
        if (A_PRESSED.get()) {
          bunny.moveLeft();
        }
        if (LEFT_PRESSED.get()) {
          bunny2.moveLeft();
        }
        if (RIGHT_PRESSED.get()) {
          bunny2.moveRight();
        }
      }
    };
    animationTimer.start();
  }

  public GameView(ArrayList playerlist, Group root)
  {
    this.playerList = playerlist;
    this.root = root;
    bunny = playerList.get(0).getMyCharacter();
    bunny2 = playerList.get(1).getMyCharacter();
    y = bunny.getCenterY();
    y2 = bunny2.getCenterY();
    root.getChildren().add(stage);

  }

  @Override
  public void start(Stage primaryStage) {
    scene = new Scene(root, 1200, 800);

    scene.setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.D) {
        D_PRESSED.set(true);
        bunny2.getHurtBox().setStroke(Color.YELLOW);
      }
      if (e.getCode() == KeyCode.T) {
        bunny.attack();
        if(bunny.getHitBox().getBoundsInParent().intersects(bunny2.getHurtBox().getBoundsInParent())){
          bunny2.getHurtBox().setStroke(Color.RED);
        }

      }
      if (e.getCode() == KeyCode.W) {
        bunny.jump();
      }
      if (e.getCode() == KeyCode.A) {
        A_PRESSED.set(true);
      }
      if (e.getCode() == KeyCode.LEFT) {
        LEFT_PRESSED.set(true);
      }
      if (e.getCode() == KeyCode.RIGHT) {
        RIGHT_PRESSED.set(true);
      }
      if (e.getCode() == KeyCode.UP) {
        bunny2.jump();
      }
      if (e.getCode() == KeyCode.L) {
        bunny2.attack();
      }
    });

    scene.setOnKeyReleased(e -> {
              if (e.getCode() == KeyCode.A) {
                A_PRESSED.set(false);
              }
              if (e.getCode() == KeyCode.D) {
                D_PRESSED.set(false);
              }
              if (e.getCode() == KeyCode.LEFT) {
                LEFT_PRESSED.set(false);
              }
              if (e.getCode() == KeyCode.RIGHT) {
                RIGHT_PRESSED.set(false);
              }

    });

    primaryStage.setTitle("FIGHT!");
      primaryStage.setScene(scene);
      primaryStage.show();
      setKeyBinds();

  }
}