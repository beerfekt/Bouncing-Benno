package net.beerfekt.bouncingbenno.objekts.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import net.beerfekt.bouncingbenno.GamePanel;
import net.beerfekt.bouncingbenno.objekts.AbstractObject;
import net.beerfekt.bouncingbenno.objekts.ImageNeutralBox;
import net.beerfekt.bouncingbenno.objekts.properties.Animation;


public class Player extends ImageNeutralBox {
    private int score;
    private boolean up, playing;
    private Animation animation;
    private long startTime;

    //PARAMETERS FOR THE MOVEMENT / POSITION of the Player
    private static final int LIMIT_ACCELERATION = 8,                                           //höchster/niedrigster Beschleunigungswert
    //Helicopter Spielraum begrenzen (sodass dieser nicht aus Bild springt)
    LIMIT_AREA_TOP = GamePanel.HEIGHT / 10,                         //1/8 der Canvas-Höhe ist abstand nach oben
            LIMIT_AREA_BOTTOM = GamePanel.HEIGHT - (GamePanel.HEIGHT / 6),     //1/4 der Canvas-Höhe ist abstand nach unten
            STARTPOSITION = LIMIT_AREA_BOTTOM,                            //Startposition Hubschrauber auf y achse
            SPEED_VERTICAL_UP = 6,                                            //heli geschwindigkeit aufsteigen
            SPEED_VERTICAL_DOWN = 2;                                            //heli                 absteigen


    public Player(Bitmap res, int w, int h, int numFrames) {
        super(0, STARTPOSITION, 0, 0, w, h, 30, new Animation(getImagesFromOneImage(res, w,h,numFrames), 10 * 1000));

        startTime = System.nanoTime();

    }

    private static Bitmap[] getImagesFromOneImage(Bitmap immage, int width, int height, int numFrames)
    {
        Bitmap[] images = new Bitmap[numFrames];

        for (int i = 0; i < images.length; i++) {
            images[i] = Bitmap.createBitmap(immage, 0, i * height, width, height);
        }
        return  images;
    }

    public void setUp(boolean b) {
        up = b;
    }

    public void update() {
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if (elapsed > 100) {
            score++;
            startTime = System.nanoTime();
        }
        animation.update();

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

    public void draw(Canvas canvas) {
        canvas.drawBitmap(animation.getImage(), getX(), getY(), null);
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
        setY(STARTPOSITION);
    }

}//