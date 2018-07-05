package net.beerfekt.bouncingbenno.objekts.game;

import android.graphics.Bitmap;

import net.beerfekt.bouncingbenno.manager.RunTimeManager;
import net.beerfekt.bouncingbenno.objekts.ImageKillBox;
import net.beerfekt.bouncingbenno.objekts.properties.Animation;

public class Rolph_Ruessel extends ImageKillBox {
    public Rolph_Ruessel(float w, float h, Animation rolph){
        super(RunTimeManager.SCREEN_WIDTH-1, 720, -35f, 0f , w, h, rolph);
    }
}
