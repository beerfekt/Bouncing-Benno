package net.beerfekt.bouncingbenno.objekts.game;

import android.graphics.Bitmap;

import net.beerfekt.bouncingbenno.manager.RunTimeManager;
import net.beerfekt.bouncingbenno.objekts.ImageNeutralBox;
import net.beerfekt.bouncingbenno.objekts.properties.Animation;


public class Player extends ImageNeutralBox {
    private int score;
    private boolean up, playing;
    private long startTime;

    private static final int LIMIT_ACCELERATION = 8,
            LIMIT_AREA_TOP = (int) (RunTimeManager.SCREEN_HEIGHT / 10),
            LIMIT_AREA_BOTTOM = (int) (RunTimeManager.SCREEN_HEIGHT - (RunTimeManager.SCREEN_HEIGHT / 6)),
            SPEED_VERTICAL_UP = 6,
            SPEED_VERTICAL_DOWN = 2;
    private static float START_POSITION = LIMIT_AREA_BOTTOM;

    public Player(Bitmap res, float w, float h, float animSpeed) {
        super(0f, START_POSITION, 0f, 0f, w, h,  new Animation(getImagesFromOneImage(res,(int)w, (int)h, (int)animSpeed), (int)10 * 1000));

        startTime = System.nanoTime();

    }

    private static Bitmap[] getImagesFromOneImage(Bitmap immage, int width, int height, int numFrames) {
        Bitmap[] images = new Bitmap[numFrames];

        for (int i = 0; i < images.length; i++) {
            images[i] = Bitmap.createBitmap(immage, i * width, 0, width, height);
        }
        return images;
    }

    public void setUp(boolean b) {
        up = b;
    }

    public void update(float numberOfFrames) {
        super.update(numberOfFrames);
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if (elapsed > 100) {
            score++;
            startTime = System.nanoTime();
        }

        if (up) {
            setDirectionY(getDirectionY() - SPEED_VERTICAL_UP);
        } else {
            setDirectionY(getDirectionY() + SPEED_VERTICAL_DOWN);
        }

        //Acceleration limits
        if (getDirectionY() > LIMIT_ACCELERATION) setDirectionY(LIMIT_ACCELERATION - 5);
        if (getDirectionY() < -LIMIT_ACCELERATION) setDirectionY(-LIMIT_ACCELERATION);

        //increase the height of helicopter
        // add the calculated height of the acceleration
        setY(getY() + getDirectionY() * 2);

        //Height limits
        if (getY() < LIMIT_AREA_TOP) {
            setY(LIMIT_AREA_TOP);
        } else if (getY() > LIMIT_AREA_BOTTOM) {
            setY(LIMIT_AREA_BOTTOM);
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