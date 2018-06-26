package net.beerfekt.bouncingbenno.objekts.game;

import android.graphics.Bitmap;

import net.beerfekt.bouncingbenno.manager.RunTimeManager;
import net.beerfekt.bouncingbenno.objekts.ImageKillBox;

import java.util.Random;

public class Flying_Flo extends ImageKillBox {
    private static Random rand = new Random();
    private float dy = rand.nextFloat()-1;
    private float intensity = rand.nextFloat()+1;
    private float weigth =  0.13f + rand.nextFloat() * (0.2f - 0.13f);

    public Flying_Flo(float w, float h, Bitmap flo) {
        super(RunTimeManager.SCREEN_WIDTH-1, rand.nextInt(400)+115, -1.5f, 0f, w, h, flo);
    }

    public void update(float numberOfFrames) {
        super.update(numberOfFrames);
        setDirectionY(dy);
        dy += weigth;

        if (dy > intensity){
            weigth = -weigth;
        }
        if (dy < -intensity){
            weigth = -weigth;
        }
    }
}
