package net.beerfekt.bouncingbenno.objekts.game;

import android.graphics.Bitmap;

import net.beerfekt.bouncingbenno.manager.RunTimeManager;
import net.beerfekt.bouncingbenno.objekts.ImageNeutralBox;
import net.beerfekt.bouncingbenno.objekts.properties.Animation;


public class Player extends ImageNeutralBox {
    private int score;
    private boolean up, playing;
    private long startTime;

    private float jumpStrength = 75f;
    private float weight = 5f;
    private boolean jumping = false;

    private static final int LIMIT_ACCELERATION = 8,
            LIMIT_AREA_TOP = (int) (RunTimeManager.SCREEN_HEIGHT / 10),
            LIMIT_AREA_BOTTOM = (int) (RunTimeManager.SCREEN_HEIGHT - (RunTimeManager.SCREEN_HEIGHT / 4)),
            SPEED_VERTICAL_UP = 10,
            SPEED_VERTICAL_DOWN = 10;

    private static float START_POSITION = LIMIT_AREA_BOTTOM;

    public Player(Bitmap res, float w, float h, float animSpeed) {
        super(10f, START_POSITION, 0f, 0f, w, h,  new Animation(getImagesFromOneImage(res, 3), animSpeed));

        startTime = System.nanoTime();

    }

    private static Bitmap[] getImagesFromOneImage(Bitmap immage, int numFrames) {
        Bitmap[] images = new Bitmap[numFrames];

        for (int i = 0; i < numFrames; i++) {
            images[i] = Bitmap.createBitmap(immage, i * immage.getWidth()/numFrames, 0, immage.getWidth()/numFrames, immage.getHeight());
        }
        return images;
    }

    public void setUp(boolean b) {
        up = b;
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
            if (getY() >= LIMIT_AREA_BOTTOM) {
                jumping = false;
                setY(LIMIT_AREA_BOTTOM);
            }
            setDirectionY(-jumpStrength);
            jumpStrength -= weight;
        }
        else if (up){
            jumping = true;
        }

        else {
            setY(LIMIT_AREA_BOTTOM);
            setDirectionY(0);
            jumpStrength = 75f;
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