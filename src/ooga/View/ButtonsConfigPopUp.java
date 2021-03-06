package ooga.View;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ooga.Controller.KeyBindManager;
import ooga.Exceptions.ExceptionHelper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;
import java.util.ResourceBundle;

public class ButtonsConfigPopUp {

  private Properties prop;
  private BorderPane borderPane;
  private Stage buttonConfigsStage;
  private KeyBindManager buttonConfigurer = new KeyBindManager();

  private VBox p1Config= new VBox(10);
  private Label p1Text;
  private TextField leftButton1 = new TextField(buttonConfigurer.getPlayer1LeftKey());
  private TextField rightButton1 = new TextField(buttonConfigurer.getPlayer1RightKey());
  private TextField jumpButton1 = new TextField(buttonConfigurer.getPlayer1JumpKey());
  private TextField fallButton1 = new TextField(buttonConfigurer.getPlayer1FallKey());
  private TextField attackButton1 = new TextField(buttonConfigurer.getPlayer1AttackKey());
  private TextField specialButton1 = new TextField(buttonConfigurer.getPlayer1SpecialKey());

  private VBox p2Config= new VBox(10);
  private Label p2Text;
  private TextField leftButton2 = new TextField(buttonConfigurer.getPlayer2LeftKey());
  private TextField rightButton2 = new TextField(buttonConfigurer.getPlayer2RightKey());
  private TextField jumpButton2 = new TextField(buttonConfigurer.getPlayer2JumpKey());
  private TextField fallButton2 = new TextField(buttonConfigurer.getPlayer2FallKey());
  private TextField attackButton2 = new TextField(buttonConfigurer.getPlayer2AttackKey());
  private TextField specialButton2 = new TextField(buttonConfigurer.getPlayer2SpecialKey());

  private VBox buttonLabels = new VBox(10);
  private Label buttonsText = new Label("Buttons");
  private Label leftText = new Label("Left");
  private Label rightText = new Label("Right");
  private Label jumpText = new Label("Jump");
  private Label fallText = new Label("Fall");
  private Label attackText = new Label("Attack");
  private Label specialText = new Label("Special");

  HBox topElements = new HBox();
  private Button configure;

  private Button selectConfigP1;
  private Button selectConfigP2;

  private RadioButton toggleP1;
  private RadioButton toggleP2;
  private boolean newConfigFileP1 = false;
  private boolean newConfigFileP2 = false;

  public ButtonsConfigPopUp(Properties prop)
  {
    this.prop = prop;
    buttonConfigsStage=new Stage();
    buttonConfigsStage.initModality(Modality.APPLICATION_MODAL);
    buttonConfigsStage.setTitle("Button Configurations");

    borderPane = new BorderPane();
    Label settingsText= new Label("Button Configurations");
    settingsText.setStyle(prop.getProperty("characterText"));
    topElements.setAlignment(Pos.TOP_CENTER);
    topElements.getChildren().add(settingsText);

    createToggleButtons();
    createConfigButtons();

    topElements.getChildren().add(selectConfigP1);
    topElements.getChildren().add(selectConfigP2);

    setTextOfElements(prop);
    setLabelStyles(prop);
    addElementsToParents();
    createConfigureButton(prop);
    configureBorderPane(topElements);
  }

  private void setTextOfElements(Properties prop) {
    p1Text = new Label("Player 1");
    p2Text = new Label("Player 2");
    p1Text.setStyle(prop.getProperty("characterText"));
    p2Text.setStyle(prop.getProperty("characterText"));

    buttonsText = new Label("Buttons");
    leftText = new Label("Left");
    rightText = new Label("Right");
    jumpText = new Label("Jump");
    fallText = new Label("Fall");
    attackText = new Label("Attack");
    specialText = new Label("Special");
  }

  private void configureBorderPane(HBox topElements) {
    HBox bottomElements = new HBox();
    VBox alignBottomElements = new VBox();
    bottomElements.setAlignment(Pos.CENTER);
    alignBottomElements.setAlignment(Pos.CENTER);
    alignBottomElements.getChildren().add(configure);
    alignBottomElements.getChildren().add(toggleP1);
    alignBottomElements.getChildren().add(toggleP2);
    bottomElements.getChildren().add(alignBottomElements);
    borderPane.setTop(topElements);
    borderPane.setBottom(bottomElements);
    borderPane.setLeft(p1Config);
    borderPane.setRight(p2Config);
    borderPane.setCenter(buttonLabels);

    Scene settingsScene= new Scene(borderPane, 500, 400);
    buttonConfigsStage.setScene(settingsScene);
    buttonConfigsStage.show();
  }

  private void createConfigureButton(Properties prop) {
    configure= new Button("Configure Both Players");
    configure.setOnAction(e -> {
      buttonConfigurer.setPlayer1KeyBinds(leftButton1.getText(),rightButton1.getText(), jumpButton1.getText(), fallButton1.getText(),attackButton1.getText(),specialButton1.getText(), newConfigFileP1);
      buttonConfigurer.setPlayer2KeyBinds(leftButton2.getText(),rightButton2.getText(), jumpButton2.getText(), fallButton2.getText(),attackButton2.getText(),specialButton2.getText(), newConfigFileP2);
      buttonConfigsStage.close();
    });
    configure.setStyle(prop.getProperty("playerText"));
  }

  private void addElementsToParents() {
    p1Config.getChildren().addAll(p1Text,leftButton1, rightButton1, jumpButton1, fallButton1,attackButton1,specialButton1);
    p2Config.getChildren().addAll(p2Text,leftButton2, rightButton2, jumpButton2, fallButton2,attackButton2,specialButton2);
    buttonLabels.getChildren().addAll(buttonsText,leftText,rightText,jumpText,fallText,attackText,specialText);
    buttonLabels.setAlignment(Pos.CENTER);
    p1Config.setAlignment(Pos.CENTER);
    p2Config.setAlignment(Pos.CENTER);
  }

  private void setLabelStyles(Properties prop) {
    leftText.setStyle(prop.getProperty("labelText"));
    rightText.setStyle(prop.getProperty("labelText"));
    jumpText.setStyle(prop.getProperty("labelText"));
    fallText.setStyle(prop.getProperty("labelText"));
    attackText.setStyle(prop.getProperty("labelText"));
    specialText.setStyle(prop.getProperty("labelText"));
  }

  private void createToggleButtons() {
    toggleP1 = new RadioButton();
    toggleP1.setText("Create New Config File for Player 1");
    toggleP1.setOnAction(e -> {
        newConfigFileP1 = !newConfigFileP1;
    });
    toggleP2 = new RadioButton();
    toggleP2.setText("Create New Config File for Player 2");
    toggleP2.setOnAction(e -> {
      newConfigFileP2 = !newConfigFileP2;
    });
  }

  private void createConfigButtons() {
    JSONParser parser = new JSONParser();
    selectConfigP1 = new Button();
    selectConfigP1.setText("Choose P1 Binds");
    selectConfigP1.setOnAction(e -> {
      FileChooser fileChooser = new FileChooser();
      File selectedConfig = fileChooser.showOpenDialog(null);
      System.out.println(selectedConfig.getAbsolutePath());
      try {
        JSONObject object = (JSONObject) parser.parse(new FileReader(selectedConfig.getAbsolutePath()));
        buttonConfigurer.setPlayer1KeyBinds(object.get("left").toString(), object.get("right").toString(), object.get("jump").toString(), object.get("fall").toString(),
                object.get("attack").toString(), object.get("special").toString(), false);
      } catch (ParseException | IOException ex) {
        new ExceptionHelper(ex);
      }
    });
    selectConfigP2 = new Button();
    selectConfigP2.setText("Choose P2 Binds");
    selectConfigP2.setOnAction(e -> {
      FileChooser fileChooser = new FileChooser();
      File selectedConfig = fileChooser.showOpenDialog(null);
      JSONObject object = null;
      try {
        object = (JSONObject) parser.parse(selectedConfig.getPath());
        buttonConfigurer.setPlayer2KeyBinds(object.get("left").toString(), object.get("right").toString(), object.get("jump").toString(), object.get("fall").toString(),
                object.get("attack").toString(), object.get("special").toString(), false);
      } catch (ParseException ex) {
        new ExceptionHelper(ex);
      }
    });
  }
}