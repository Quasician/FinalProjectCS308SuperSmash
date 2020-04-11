package ooga.Model.Characters;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import ooga.Model.Character;
import ooga.Model.GameEngine.SpriteAnimation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Character1 extends CharacterSuper implements Character {

    private static final int COLUMNS  =   10;
    private static final int COUNT    =  10;
    private static final int OFFSET_X =  0;
    private static final int OFFSET_Y =  0;
    private static final int WIDTH    = 366;
    private static final int HEIGHT   = 461;

    private int centerX = 100;
    private int centerY = 200;
    private int xSpeed = 25;

    private int health = 100;

    Image RUN_IMAGE = new Image(new FileInputStream("data/spritesheets/chracter1/run.png"));
    Image IDLE_IMAGE = new Image(new FileInputStream("data/spritesheets/chracter1/idle.png"));
    Image ATTACK_IMAGE = new Image(new FileInputStream("data/spritesheets/chracter1/attack.png"));
    Image JUMP_IMAGE = new Image(new FileInputStream("data/spritesheets/chracter1/jump.png"));


    public Character1() throws FileNotFoundException {
        super();
        spriteImageView = new ImageView(IDLE_IMAGE);
        spriteImageView.setX(centerX);
        spriteImageView.setY(centerY);

        spriteImageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));
        spriteAnimation = new SpriteAnimation(
                spriteImageView,
                Duration.millis(1000),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                234, 442);

        spriteAnimation.setCycleCount(Animation.INDEFINITE);
        spriteAnimation.play();
    }

    public void idle(){
        playIdleAnimation();
    }

    @Override
    public void setSpriteSheet() {

    }

    @Override
    public void moveLeft() {

    }

    public void moveRight() {
        playRunRightAnimation();
        spriteImageView.setX(centerX += xSpeed);
    }

    @Override
    public void moveDown() {

    }

    @Override
    public void jump() {
        TranslateTransition jump = new TranslateTransition(Duration.millis(500), spriteImageView);
        jump.interpolatorProperty().set(Interpolator.SPLINE(.1, .1, .7, .7));
        jump.setByY(-50);
        jump.setAutoReverse(true);
        jump.setCycleCount(2);
        jump.play();

        playJumpAnimation();
    }

    @Override
    public void special() {

    }

    public void attack() {
        playAttackAnimation();
    }

    private void playIdleAnimation() {
        spriteImageView.setImage(IDLE_IMAGE);
        spriteAnimation.setAnimation(
                spriteImageView,
                Duration.millis(1000),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                234, 441
        );
        spriteAnimation.setCycleCount(Animation.INDEFINITE);
        spriteAnimation.play();
    }

    private void playRunRightAnimation() {
        spriteImageView.setImage(RUN_IMAGE);
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
        spriteImageView.setImage(ATTACK_IMAGE);
        spriteAnimation.setAnimation(
                spriteImageView,
                Duration.millis(1000),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                536, 495);
        spriteAnimation.setCycleCount(1);
        spriteAnimation.play();

        spriteAnimation.setOnFinished(event -> {
            spriteAnimation.stop();
            playIdleAnimation();
        });
    }

    private void playJumpAnimation() {
        spriteAnimation.stop();
        spriteImageView.setImage(JUMP_IMAGE);
        spriteAnimation.setAnimation(
                spriteImageView,
                Duration.millis(1000),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                365, 486);
        spriteAnimation.setCycleCount(1);
        spriteAnimation.play();

        spriteAnimation.setOnFinished(event -> {
            spriteAnimation.stop();
            playIdleAnimation();
        });
    }

    public ImageView getCharacterImage(){
        return spriteImageView;
    }

    public void printHealth() {
        System.out.println(health);
    }

}
