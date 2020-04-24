package ooga.View;


import java.awt.MouseInfo;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javax.sound.midi.SysexMessage;
import ooga.Controller.MusicManager;

import java.io.File;
import java.util.ResourceBundle;

import static javafx.geometry.Pos.BOTTOM_CENTER;
import static javafx.geometry.Pos.CENTER;

public class Home extends Application {

  private Stage currentStage;
  public static final ResourceBundle buttonStyles = ResourceBundle
          .getBundle("ooga.Resources.stylesheets.buttonStyle");

  @Override
  public void start(Stage primaryStage) {
    Scene homeScreen = new Scene(makeGridPane());
    primaryStage.setScene(homeScreen);
    primaryStage.setHeight(800);
    primaryStage.setWidth(1200);
    currentStage = primaryStage;
    primaryStage.show();

    //MusicManager.playMainMenuMusic();

  }

  private BorderPane makeGridPane() {
    BorderPane myGP = new BorderPane();
    BackgroundImage homeScreen = new BackgroundImage(
        new Image("SmashHomeScreen.jpg", 1200, 800, false, true),
        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
        BackgroundSize.DEFAULT);
    myGP.setBackground(new Background(homeScreen));

    Button playLocal = new Button();
    playLocal.setAlignment(Pos.BOTTOM_LEFT);
    playLocal.setGraphic(new ImageView(new Image("LocalSmash.png", 575, 175, false, true)));

    Button playOnline = new Button();
    playOnline.setAlignment(Pos.BOTTOM_RIGHT);

    playOnline.setGraphic(new ImageView(new Image("OnlineSmash.png", 575, 175, false, true)));

    HBox buttonHolder = new HBox();
    buttonHolder.getChildren().addAll(playLocal,playOnline);
    buttonHolder.setAlignment(BOTTOM_CENTER);

    playLocal.setOnAction(e -> localPlay());
    playOnline.setOnAction(e -> onlinePlay());

    myGP.setCenter(buttonHolder);

    return myGP;
  }


  private void localPlay() {
    currentStage.hide();
    new StageSelect().start(new Stage());
  }

  private void onlinePlay() {
    currentStage.hide();
    new OnlinePopUp();
  }

  public void settings()
  {
    new SettingsPopUp();
  }
}
