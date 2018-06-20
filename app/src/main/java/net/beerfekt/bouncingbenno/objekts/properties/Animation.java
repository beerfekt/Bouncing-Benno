package net.beerfekt.bouncingbenno.objekts.properties;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Animation {
    private Bitmap[] images;
    private float currentImage;
    private float animationSpeed;
    private boolean playedOnce;

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
        return images[(int) currentImage];
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
        currentImage += animationSpeed * numberOfFrames;
        if (currentImage >= images.length) {
            currentImage -= images.length;
            playedOnce = true;
        }
    }

    public void draw(Canvas canvas, Rect rect) {
        canvas.drawBitmap(getImage(), null, rect, null);
    }

    public Animation copy() {
        return new Animation(images,animationSpeed);
    }
}