package net.beerfekt.bouncingbenno.objekts.game;

import net.beerfekt.bouncingbenno.manager.RunTimeManager;
import net.beerfekt.bouncingbenno.objekts.ImageNeutralBox;
import net.beerfekt.bouncingbenno.objekts.properties.Animation;

public class Death extends ImageNeutralBox {
    public Death(float w, float h, Animation death) {
        super(200f, RunTimeManager.SCREEN_HEIGHT - (RunTimeManager.SCREEN_HEIGHT / 4), 0, 0f, w, h, death);
    }

    /*
    @Override
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
    */
}

