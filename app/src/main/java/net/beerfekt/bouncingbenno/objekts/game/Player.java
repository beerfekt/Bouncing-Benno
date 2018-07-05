package net.beerfekt.bouncingbenno.objekts.game;

import android.graphics.Bitmap;

import net.beerfekt.bouncingbenno.manager.RunTimeManager;
import net.beerfekt.bouncingbenno.objekts.ImageNeutralBox;
import net.beerfekt.bouncingbenno.objekts.properties.Animation;



public class Player extends ImageNeutralBox {
    private boolean up, playing;

    private float jumpStrength = 45f;
    private float weight = 3.5f;
    private boolean jumping = false;

    private static final int LIMIT_AREA_BOTTOM = (int) (RunTimeManager.SCREEN_HEIGHT - (RunTimeManager.SCREEN_HEIGHT / 4));
    private static float START_POSITION = LIMIT_AREA_BOTTOM;

    private boolean dead;


    public Player(float w, float h, Animation benno) {
        super(200f, START_POSITION, 0f, 0f, w, h, benno);
    }

    public void setUpTrue() {
        up = true;
    }

    public void update(float numberOfFrames) {
        super.update(numberOfFrames);
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
            jumpStrength = 47f;
        }
    }

    private void jumpStrength() {
        setDirectionY(-jumpStrength);
        jumpStrength -= weight;
    }

    public void deathAnimation() {
        dead = true;
    }

    public void revive(){
        dead = false;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean getPlaying() {
        return playing;
    }

    public void setPlaying(boolean b) {
        playing = b;
    }

    public void resetDY() {
        setDirectionY(0);
    }

    public void resetStartPosition() {
        setY(START_POSITION);
    }
}