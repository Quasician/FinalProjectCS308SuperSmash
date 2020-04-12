package ooga.Model.Characters;

import javafx.scene.image.ImageView;
import ooga.Model.GameEngine.SpriteAnimation;

public abstract class CharacterSuper {

  private int myStocks;
  private int myStamina;
  private int health = 100;
  private String name;

  ImageView spriteImageView;
  SpriteAnimation spriteAnimation;

  public CharacterSuper(String name) {
    this.name = name;
    System.out.println(name);
    System.out.println(this.name);
  }

  /**
   * Getter for myStamina
   * @return
   */
  public int getStamina() {
    return myStamina;
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

  public ImageView getCharacterImage(){
    return spriteImageView;
  }

  public void printHealth() {
    System.out.println(health);
  }

  public String getName()
  {
    return name;
  }

}
