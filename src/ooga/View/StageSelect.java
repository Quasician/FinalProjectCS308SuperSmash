package ooga.View;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import ooga.Model.StageClasses.StageBuilder;
import java.io.*;
import java.util.ArrayList;


import static javafx.geometry.Pos.*;

public class StageSelect extends AbstractSelectScreen{
  private BorderPane borderPane = new BorderPane();
  private Button go;
  private Stage currentStage;
  private ooga.Model.StageClasses.Stage chosenStage;

  private ArrayList<ooga.Model.StageClasses.Stage> stageList = new ArrayList<>();
  private ArrayList<Button> buttonList = new ArrayList<>();
  private Group root = new Group();
  private int colThresh = 8;

  public StageSelect() throws IOException {
    super();
    System.out.println("SADSA" + prop.keySet());
  }

  public void initStages() throws FileNotFoundException {

    File dir = new File("src/ooga/Model/StageClasses/Stages");
    System.out.println(dir.getAbsolutePath());
    File[] directoryListing = dir.listFiles();
    if (directoryListing != null) {
      for (File child : directoryListing) {
        System.out.println(child.getName());
        StageBuilder stage = new StageBuilder("Stages/"+child.getName());
        stageList.add(stage);
      }
    }
    GridPane charGrid = setupStageGrid();
    createStageButtons(charGrid);
  }

  private void createStageButtons(GridPane charGrid) {
    int colCount = 0;
    int rowCount = 0;
    for (ooga.Model.StageClasses.Stage stage : stageList) {
      Button button = new Button();
      button.setOnMouseClicked((e) -> {
        chosenStage = stage;
        go.setDisable(false);
      });
      ImageView stageBackground = new ImageView(stage.getBackground());
      stageBackground.setFitHeight(100);
      stageBackground.setFitWidth(100);
      button.setGraphic(stageBackground);
      charGrid.add(button, colCount, rowCount);
      colCount++;
      if (colCount >= colThresh) {
        colCount = 0;
        rowCount++;
      }
    }
  }

  private GridPane setupStageGrid() {
    GridPane charGrid = new GridPane();
    charGrid.setStyle("-fx-background-color: rgba(0,0,0, 1)");
    charGrid.setGridLinesVisible(true);
    charGrid.setMaxHeight(300);
    charGrid.setMaxWidth(800);
    borderPane.setStyle("-fx-background-color: rgba(200, 200, 240, 0.5)");
    borderPane.setCenter(charGrid);
    return charGrid;
  }


  public void goToSelectScreen() throws IOException {
    System.out.println("Going to Select Screen ... ");
    currentStage.hide();
    CharacterSelect characterSelect = new CharacterSelect(chosenStage);
    characterSelect.start(new Stage());
  }


  private BorderPane integrateBorderPane() throws IOException {
    VBox header = createToolbar();
    setupGoButton();
    return setupBorderPane(header);
  }

  private BorderPane setupBorderPane(VBox header) {
    borderPane.setTop(header);
    VBox bottomElements = new VBox();
    bottomElements.getChildren().add(go);
    bottomElements.setAlignment(TOP_CENTER);
    borderPane.setBottom(bottomElements);
    return borderPane;
  }

  private void setupGoButton() {
    go = new Button("GO!");
    go.setStyle(prop.getProperty("playerText"));
    go.setMinWidth(300);
    go.setMinHeight(100);
    go.setOnMouseClicked((e) -> {
      try {
        goToSelectScreen();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    });
    go.setDisable(true);
    go.setId("#GoButton");
  }

  public void start(Stage primaryStage) throws IOException {
    try {
      Scene selectScene = new Scene(integrateBorderPane());
      currentStage = primaryStage;
      primaryStage.setScene(selectScene);
      primaryStage.setHeight(800);
      primaryStage.setWidth(1200);
      initStages();
      primaryStage.show();
    } catch (IOException e) {
      System.out.println(e.getLocalizedMessage());
    }
  }

  public Button getGo() {
    return go;
  }
}
