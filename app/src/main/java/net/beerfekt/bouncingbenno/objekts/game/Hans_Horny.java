package net.beerfekt.bouncingbenno.objekts.game;

import android.graphics.Bitmap;

import net.beerfekt.bouncingbenno.manager.RunTimeManager;
import net.beerfekt.bouncingbenno.objekts.ImageKillBox;
import net.beerfekt.bouncingbenno.objekts.properties.Animation;

public class Hans_Horny extends ImageKillBox {
    public Hans_Horny(float w, float h, Animation hans){
        super(RunTimeManager.SCREEN_WIDTH-1, 750, -45f, 0f , w, h, hans);
    }

    public void update(float numberOfFrames) {
        super.update(numberOfFrames);
    }
}
