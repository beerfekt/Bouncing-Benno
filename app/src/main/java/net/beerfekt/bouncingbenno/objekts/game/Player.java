package net.beerfekt.bouncingbenno.objekts.game;

import net.beerfekt.bouncingbenno.manager.RunTimeManager;
import net.beerfekt.bouncingbenno.objekts.ImageNeutralBox;
import net.beerfekt.bouncingbenno.objekts.properties.Animation;



public class Player extends ImageNeutralBox {
    private static final int LIMIT_AREA_BOTTOM = (int) (RunTimeManager.SCREEN_HEIGHT - (RunTimeManager.SCREEN_HEIGHT / 4));

    private float jumpStrength = 45f;
    private float jumpStrengthProgress = jumpStrength;
    private float jumpWeight = 3.5f;
    private boolean jumpingPending = false;
    private boolean jumping = false;

    public Player(float w, float h, Animation benno) {
        super(200f, LIMIT_AREA_BOTTOM, 0f, 0f, w, h, benno);
    }

    public void jump() {
        jumpingPending = true;
    }

    public void update(float numberOfFrames) {
        super.update(numberOfFrames);
        //Jump
        if (jumpingPending && !jumping) {
            jumping = true;
            jumpingPending = false;
            setY(LIMIT_AREA_BOTTOM);
        }
        if (jumping) {
            if (getY() > LIMIT_AREA_BOTTOM) {
                jumping = false;
                setY(LIMIT_AREA_BOTTOM);
            } else {
                for(int i = 0 ; i < numberOfFrames ; i++) {
                    jumpProgress();
                    if(i != numberOfFrames-1) {
                        setY(getY() + getDirectionY());
                    }
                }
            }
        } else {
            setY(LIMIT_AREA_BOTTOM);
            resetDY();
            jumpStrengthProgress = jumpStrength;
        }
    }

    private void jumpProgress() {
        setDirectionY(-jumpStrengthProgress);
        jumpStrengthProgress -= jumpWeight;
    }

    public void resetDY() {
        setDirectionY(0);
    }

    public void resetStartPosition() {
        setY(LIMIT_AREA_BOTTOM);
    }
}