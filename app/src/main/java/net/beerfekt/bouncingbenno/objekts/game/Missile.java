package net.beerfekt.bouncingbenno.objekts.game;

import android.graphics.Bitmap;

import net.beerfekt.bouncingbenno.objekts.ImageKillBox;
import net.beerfekt.bouncingbenno.objekts.properties.Animation;

import java.util.Random;


public class Missile extends ImageKillBox {

    private static Random rand = new Random();


    private static final int MAX_SPEED = 40;

    //Konstruktor

    public Missile(Bitmap immage, int x, int y, int w, int h, int s, int numFrames) {
        super(x, y, getSpeed(s), 0, w, h, 30, new Animation(getImagesFromOneImage(immage, w, h, numFrames), 100 - getSpeed(s) * 1000));
    }

    @Override
    public int getWidth() {
        //offset slightly for more realistic collision detection
        //Rakete dringt vor explosion um 10px ein
        return getWidth() - 10;
    }

    private static int getSpeed(int score) {
        int speed = 7 + (int) (rand.nextDouble() * score / 30);
        return  speed > MAX_SPEED ? MAX_SPEED : speed;
    }

    private static Bitmap[] getImagesFromOneImage(Bitmap immage, int width, int height, int numFrames)
    {
        Bitmap[] images = new Bitmap[numFrames];

        for (int i = 0; i < images.length; i++) {
            images[i] = Bitmap.createBitmap(immage, 0, i* height, width, height);
        }
        return  images;
    }

}