package net.beerfekt.bouncingbenno.objekts.properties;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Animation {
    private Bitmap[] images;
    private int currentImage;
    private float animationSpeed;
    private boolean playedOnce;
    private float time;

    /**
     * @param images        Die Bilder in der Animations Reihnfolge
     * @param animationSpeed Die Anzeigezeit eines Bildes in Millisekunden
     */
    public Animation(Bitmap[] images, float animationSpeed) {
        this.images = images;
        this.currentImage = 0;
        this.animationSpeed = animationSpeed;
        this.playedOnce = false;
    }

    public Bitmap getImage() {
        return images[currentImage];
    }

    /**
     * @return Gibt zurück ob die Animation bereits 1x abgespielt wurde
     */
    public boolean wasPlayedOnce() {
        return playedOnce;
    }

    /**
     * Berechnet das Aktuelle Bild der Animation
     */
    public void update(float numberOfFrames) {
        time += animationSpeed * numberOfFrames;
        if (time >= 100) {
            time = 0;
            currentImage++;
            if (currentImage >= images.length)
                currentImage = 0;
                playedOnce = true;
        }
    }

    public void draw(Canvas canvas, Rect rect) {
        canvas.drawBitmap(getImage(), null, rect, null);
    }

    public Animation copy() {
        return new Animation(images,animationSpeed);
    }

    public void setPlayedOnce(boolean playedOnce) {
        this.playedOnce = playedOnce;
    }
}