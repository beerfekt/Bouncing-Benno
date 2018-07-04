package net.beerfekt.bouncingbenno.objekts.game;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import net.beerfekt.bouncingbenno.manager.RunTimeManager;
import net.beerfekt.bouncingbenno.objekts.ImageNeutralBox;
import net.beerfekt.bouncingbenno.objekts.properties.Animation;



public class Player extends ImageNeutralBox {
    private int score;
    private boolean up, playing;
    private long startTime;

    private Animation normalAnimation;

    private float jumpStrength = 30f;
    private float weight = 1.5f;
    private boolean jumping = false;

    private static final int LIMIT_AREA_BOTTOM = (int) (RunTimeManager.SCREEN_HEIGHT - (RunTimeManager.SCREEN_HEIGHT / 4));
    private static float START_POSITION = LIMIT_AREA_BOTTOM;

    private Bitmap[] death;
    private boolean died;

    public Player(float w, float h, Animation benno, Bitmap[] explosion) {
        super(200f, START_POSITION, 0f, 0f, w, h, benno);
        startTime = System.nanoTime();
        death = explosion;
    }

    public void setUpTrue() {
        up = true;
    }

    public void update(float numberOfFrames) {
        super.update(numberOfFrames);

        //Score
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if (elapsed > 100) {
            score++;
            startTime = System.nanoTime();
        }

        //Jump
        if (jumping) {
            if (getY() > LIMIT_AREA_BOTTOM) {
                jumping = false;
                setY(LIMIT_AREA_BOTTOM);
            } else {
                for(int i = 0 ; i < numberOfFrames ; i++) {
                    jumpStrength();
                    if(i != numberOfFrames-1) {
                        setY(getY() + getDirectionY());
                    }
                }
            }
        } else if (up) {
            jumping = true;
            setY(LIMIT_AREA_BOTTOM);
            up = false;
        } else {
            setY(LIMIT_AREA_BOTTOM);
            resetDY();
            jumpStrength = 30f;
        }
    }

    private void jumpStrength() {
        setDirectionY(-jumpStrength);
        jumpStrength -= weight;
    }

    public void deathAnimation() {
        Animation d = new Animation(death, 10);
        setAnimation(d);
        died = true;
    }

    public void revive(){
        setAnimation(normalAnimation);
        died = false;
        getAnimation().setPlayedOnce(false);
    }

    public boolean isDied() {
        return died;
    }

    public int getScore() {
        return score;
    }

    public boolean getPlaying() {
        return playing;
    }

    public void setPlaying(boolean b) {
        playing = b;
    }

    public void resetScore() {
        score = 0;
    }

    public void resetDY() {
        setDirectionY(0);
    }

    public void resetStartPosition() {
        setY(START_POSITION);
    }
}