package ooga.Model.Characters;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import ooga.Model.GameEngine.SpriteAnimation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public abstract class AbstractCharacter {

  private int myStocks;
  private int myStamina;
  private int health = 100;
  private String name;

  protected int centerX;
  protected int centerY;
  protected int xSpeed = 10;

  /* Image files for character */
  protected Image IDLE_IMAGE_RIGHT;
  protected Image IDLE_IMAGE_LEFT;
  protected Image RUN_IMAGE_RIGHT;
  protected Image RUN_IMAGE_LEFT;
  protected Image ATTACK_IMAGE_RIGHT;
  protected Image ATTACK_IMAGE_LEFT;
  protected Image JUMP_IMAGE_RIGHT;
  protected Image JUMP_IMAGE_LEFT;

  protected boolean facingRight = true;

  protected ImageView spriteImageView;
  protected SpriteAnimation spriteAnimation;
  protected Pane root;
  protected Circle hitBox;
  protected Rectangle dummy;
  protected Rectangle hurtBox;

  public AbstractCharacter(String name) {
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

  public abstract void moveLeft();
  public abstract void moveRight();
  public abstract void moveDown();
  public abstract void attack();
  public abstract void jump();
  public abstract void idle();
  public abstract void special();
  public abstract void setImageFiles() throws FileNotFoundException;
  public abstract int getCenterY();
  public abstract void setCenterY(int centerY);

}
