package ooga.Model;

import javafx.scene.layout.BorderPane;
import ooga.Model.Characters.Character1;

/**
 *
 */
public class Player {
  private int myStocks;
  private int myStamina;
  private Character1 myCharacter;
  private Boolean hasChosenChar;
  public Player() {
  }

  public void setMyCharacter(Character1 myCharacter) {
    this.myCharacter = myCharacter;
  }
  public Character1 getMyCharacter()
  {
    return  myCharacter;
  }

  /**
   * Getter for myStamina
   * @return
   */
  public int getStamina() {
    return myStamina;
  }

  public Boolean getHasChosenChar() {
    return hasChosenChar;
  }

  public void setHasChosenChar(Boolean hasChosenChar) {
    this.hasChosenChar = hasChosenChar;
  }

  /**
   * Setter for myStamina
   * @param newStamina the new amount of stocks
   */
  public void setStamina(int newStamina) {
    myStamina = newStamina;
  }

  /**
   * Getter for myStocks
   * @return
   */
  public int getStocks() {
    return myStocks;
  }

  /**
   * Setter for myStocks
   * @param newStock the new amount of stocks
   */
  public void setStocks(int newStock) {
    myStocks = newStock;
  }
}
