package ooga.Model.Characters;

import java.awt.MouseInfo;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import ooga.Exceptions.ExceptionHelper;
import ooga.Model.GameEngine.SpriteAnimation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Bunny extends AbstractCharacter {

  private static final int COLUMNS = 10;
  private static final int COUNT = 10;
  private static final int OFFSET_X = 0;
  private static final int OFFSET_Y = 0;
  private static final int WIDTH = 100;
  private static final int HEIGHT = 100;

  public Bunny(String name, int testNumber){
    super(name);
    try {
      setImageFiles();
    } catch (FileNotFoundException e) {
      new ExceptionHelper(e);
    }
    spriteImageView = new ImageView(IDLE_IMAGE_RIGHT);
    this.centerX = 0;
    this.centerY = 0;
    spriteImageView.setX(centerX);
    spriteImageView.setY(centerY);

    spriteImageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));

    hurtBox = makeHurtBox(centerX, centerY);

    hitBox = makeHitBox();

    getGroup().getChildren().addAll(hurtBox, getCharacterImage());
  }


  public Bunny(String name) {
    super(name);
    try {
      setImageFiles();
    } catch (FileNotFoundException e) {
      new ExceptionHelper(e);
    }
    spriteImageView = new ImageView(IDLE_IMAGE_RIGHT);
    this.centerX = 0;
    this.centerY = 0;
    spriteImageView.setX(centerX);
    spriteImageView.setY(centerY);

    spriteImageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));
    spriteAnimation = new SpriteAnimation(
        spriteImageView,
        Duration.millis(1000),
        COUNT, COLUMNS,
        OFFSET_X, OFFSET_Y,
        WIDTH, HEIGHT
    );

    spriteAnimation.setCycleCount(Animation.INDEFINITE);
    spriteAnimation.play();

    hurtBox = makeHurtBox(centerX, centerY);

    hitBox = makeHitBox();

    getGroup().getChildren().addAll(hurtBox, getCharacterImage());
  }

  private Circle makeHitBox() {
    double x;
    if (facingRight) {
      x = spriteImageView.getBoundsInParent().getMaxX();
    } else {
      x = spriteImageView.getBoundsInParent().getMinX();
    }
    double y = (spriteImageView.getBoundsInParent().getMaxY() + spriteImageView.getBoundsInParent()
        .getMinY()) / 2;
    double height = spriteImageView.getImage().getHeight();
    double radius = 20;
    Circle hitbox = new Circle(x, y, radius);
    hitbox.setStroke(Color.RED);
    hitbox.setFill(Color.rgb(200, 200, 200, 0.5));
    return hitbox;
  }

  private Rectangle makeHurtBox(double x, double y) {
    int width = 50;
    double newX = x + (100 - width) / 2;
    Rectangle hurtbox = new Rectangle(newX, y, width, 100);
    hurtbox.setStroke(Color.YELLOW);
    hurtbox.setFill(Color.rgb(200, 200, 200, 0.5));
    return hurtbox;

  }

  @Override
  public void idle() {
    playIdleAnimation();
  }

  @Override
  public void moveLeft() {
    facingRight = false;
    playRunLeftAnimation();
    spriteImageView.setX(centerX -= xSpeed);
    double x = hurtBox.getX() - xSpeed;
    hurtBox.setX(x);
  }

  @Override
  public void moveRight() {
    facingRight = true;
    playRunRightAnimation();
    spriteImageView.setX(centerX += xSpeed);
    double x = hurtBox.getX() + xSpeed;
    hurtBox.setX(x);

  }

  @Override
  public void moveDown() {

  }

  @Override
  public void jump() {
    jumpTransition(spriteImageView);
    jumpTransition(hurtBox);
    playJumpAnimation();
  }

  private void jumpTransition(Node jumpNode) {
    TranslateTransition jump = new TranslateTransition(Duration.millis(500), jumpNode);
    jump.interpolatorProperty().set(Interpolator.SPLINE(.1, .1, .7, .7));
    jump.setByY(-230);
    jump.setAutoReverse(false);
    jump.setCycleCount(1);
    jump.play();
  }

  @Override
  public void attack() {
    playAttackAnimation();
  }

  @Override
  public void special() {

  }

  private void playIdleAnimation() {
    if (facingRight) {
      spriteImageView.setImage(IDLE_IMAGE_RIGHT);
    } else {
      spriteImageView.setImage(IDLE_IMAGE_LEFT);
    }
    spriteAnimation.setAnimation(
        spriteImageView,
        Duration.millis(1000),
        COUNT, COLUMNS,
        OFFSET_X, OFFSET_Y,
        WIDTH, HEIGHT
    );
    spriteAnimation.setCycleCount(Animation.INDEFINITE);
    spriteAnimation.play();

  }

  private void playRunRightAnimation() {
    spriteImageView.setImage(RUN_IMAGE_RIGHT);
    spriteAnimation.setAnimation(
        spriteImageView,
        Duration.millis(1000),
        COUNT, COLUMNS,
        OFFSET_X, OFFSET_Y,
        WIDTH, HEIGHT);
    spriteAnimation.play();
  }

  private void playRunLeftAnimation() {
    spriteImageView.setImage(RUN_IMAGE_LEFT);
    spriteAnimation.setAnimation(
        spriteImageView,
        Duration.millis(1000),
        COUNT, COLUMNS,
        OFFSET_X, OFFSET_Y,
        WIDTH, HEIGHT);
    spriteAnimation.play();
  }

  private void playAttackAnimation() {
    spriteAnimation.stop();
    if (facingRight) {
      spriteImageView.setImage(ATTACK_IMAGE_RIGHT);

    } else {
      spriteImageView.setImage(ATTACK_IMAGE_LEFT);
    }
    spriteAnimation.setAnimation(
        spriteImageView,
        Duration.millis(1000),
        6, 6,
        OFFSET_X, OFFSET_Y,
        WIDTH, HEIGHT);
    spriteAnimation.setCycleCount(1);
    spriteAnimation.play();

    super.getGroup().getChildren().remove(hitBox);
    hitBox = makeHitBox();
    super.getGroup().getChildren().add(hitBox);

    spriteAnimation.setOnFinished(event -> {
      super.getGroup().getChildren().remove(hitBox);
      spriteAnimation.stop();
      playIdleAnimation();
    });
  }

  private void playJumpAnimation() {
    spriteAnimation.stop();
    if (facingRight) {
      spriteImageView.setImage(JUMP_IMAGE_RIGHT);
    } else {
      spriteImageView.setImage(JUMP_IMAGE_LEFT);
    }
    spriteAnimation.setAnimation(
        spriteImageView,
        Duration.millis(1000),
        COUNT, COLUMNS,
        OFFSET_X, OFFSET_Y,
        WIDTH, HEIGHT);
    spriteAnimation.setCycleCount(1);
    spriteAnimation.play();

    spriteAnimation.setOnFinished(event -> {
      spriteAnimation.stop();
      playIdleAnimation();
    });
  }


  public Pane getRoot() {
    return root;
  }

  public ImageView getCharacterImage() {
    return spriteImageView;
  }

  public int getCenterY() {
    return
        (int) (spriteImageView.getBoundsInParent().getMaxY() + spriteImageView.getBoundsInParent()
            .getMinY()) / 2;
  }


  public Circle getHitBox() {
    return hitBox;
  }

  public Rectangle getHurtBox() {
    return hurtBox;
  }

  public void setImageFiles() throws FileNotFoundException {
    IDLE_IMAGE_RIGHT = new Image(
        new FileInputStream("data/spritesheets/bunny/bunny-idle-right.png"));
    IDLE_IMAGE_LEFT = new Image(new FileInputStream("data/spritesheets/bunny/bunny-idle-left.png"));

    RUN_IMAGE_RIGHT = new Image(new FileInputStream("data/spritesheets/bunny/bunny-run-right.png"));
    RUN_IMAGE_LEFT = new Image(new FileInputStream("data/spritesheets/bunny/bunny-run-left.png"));

    ATTACK_IMAGE_RIGHT = new Image(
        new FileInputStream("data/spritesheets/bunny/bunny-attack-right.png"));
    ATTACK_IMAGE_LEFT = new Image(
        new FileInputStream("data/spritesheets/bunny/bunny-attack-left.png"));

    JUMP_IMAGE_RIGHT = new Image(
        new FileInputStream("data/spritesheets/bunny/bunny-jump-right.png"));
    JUMP_IMAGE_LEFT = new Image(new FileInputStream("data/spritesheets/bunny/bunny-jump-left.png"));
  }

}
