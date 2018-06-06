package net.beerfekt.bouncingbenno.objekts.properties;

import android.graphics.Bitmap;

public class Animation {
    private Bitmap[] images;
    private int currentImage;
    private long imageDuration;
    private long lastFrameTime;
    private boolean playedOnce;

    /**
     * @param images        Die Bilder in der Animations Reihnfolge
     * @param imageDuration Die Anzeigezeit eines Bildes in Millisekunden
     */
    public Animation(Bitmap[] images, long imageDuration) {
        this.images = images;
        this.currentImage = 0;
        this.imageDuration = imageDuration;
        this.lastFrameTime = System.currentTimeMillis();
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
    public void update() {
        if (imageDuration == -1) {
            return;
        }
        long elapsed = (System.currentTimeMillis() - lastFrameTime);

        if (elapsed > imageDuration) {
            currentImage++;
            lastFrameTime += imageDuration;
            if (currentImage == images.length) {
                currentImage = 0;
                playedOnce = true;
            }
        }

    }
}