package net.beerfekt.bouncingbenno.objekts.game;

import android.graphics.Bitmap;

import net.beerfekt.bouncingbenno.manager.RunTimeManager;
import net.beerfekt.bouncingbenno.objekts.ImageKillBox;

public class Rio_Reisnagel extends ImageKillBox {
    public Rio_Reisnagel(float w, float h, Bitmap rio){
        super(RunTimeManager.SCREEN_WIDTH-1, 835, -20f, 0f , w, h, rio);
    }

    public void update(float numberOfFrames) {
        super.update(numberOfFrames);
    }
}
