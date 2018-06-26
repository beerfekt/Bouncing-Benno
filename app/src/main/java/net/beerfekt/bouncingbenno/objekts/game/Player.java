package net.beerfekt.bouncingbenno.objekts.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import net.beerfekt.bouncingbenno.R;
import net.beerfekt.bouncingbenno.manager.RunTimeManager;
import net.beerfekt.bouncingbenno.objekts.ImageNeutralBox;
import net.beerfekt.bouncingbenno.objekts.properties.Animation;


public class Player extends ImageNeutralBox {
    private int score;
    private boolean up, playing;
    private long startTime;

    private float jumpStrength = 90f;
    private float weight = 8f;
    private boolean jumping = false;

    private static final int LIMIT_AREA_BOTTOM = (int) (RunTimeManager.SCREEN_HEIGHT - (RunTimeManager.SCREEN_HEIGHT / 4));
    private static float START_POSITION = LIMIT_AREA_BOTTOM;

    public Player(Bitmap res, float w, float h, Bitmap benno) {
        super(200f, START_POSITION, 0f, 0f, w, h, benno);
        startTime = System.nanoTime();
    }

    public void setUpTrue (){
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
        if (jumping){
            if (getY() > LIMIT_AREA_BOTTOM) {
                jumping = false;
                setY(LIMIT_AREA_BOTTOM);
            }
            setDirectionY(-jumpStrength);
            jumpStrength -= weight;
        }
        else if (up){
            jumping = true;
            setY(LIMIT_AREA_BOTTOM - 1f);
            up = false;
        }

        else {
            setY(LIMIT_AREA_BOTTOM);
            resetDY();
            jumpStrength = 90f;
        }
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

}//