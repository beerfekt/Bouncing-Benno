package net.beerfekt.bouncingbenno.objekts.properties;

import android.graphics.Bitmap;

public class Animation {
    private Bitmap[] immages;
    private int currentImmage;
    private long immageDuration;
    private long lastFrameTime;
    private boolean playedOnce;

    /**
     * @param immages        Die Bilder in der Animations Reihnfolge
     * @param immageDuration Die Anzeigezeit eines Bildes in Millisekunden
     */
    public Animation(Bitmap[] immages, long immageDuration) {
        this.immages = immages;
        this.currentImmage = 0;
        this.immageDuration = immageDuration;
        this.lastFrameTime = System.currentTimeMillis();
        this.playedOnce = false;
    }

    public Bitmap getImage() {
        return immages[currentImmage];
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
        long elapsed = (System.currentTimeMillis() - lastFrameTime);

        if (elapsed > immageDuration) {
            currentImmage++;
            lastFrameTime += immageDuration;
            if (currentImmage == immages.length) {
                currentImmage = 0;
                playedOnce = true;
            }
        }
    }
}