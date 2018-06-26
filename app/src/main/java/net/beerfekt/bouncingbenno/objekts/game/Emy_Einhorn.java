package net.beerfekt.bouncingbenno.objekts.game;

        import android.graphics.Bitmap;

        import net.beerfekt.bouncingbenno.manager.RunTimeManager;
        import net.beerfekt.bouncingbenno.objekts.ImageKillBox;

public class Emy_Einhorn extends ImageKillBox {


    public Emy_Einhorn(float w, float h, Bitmap emy){
        super(RunTimeManager.SCREEN_WIDTH-1, 720, -1.5f, 0f , w, h, emy);
    }

    public void update(float numberOfFrames) {
        super.update(numberOfFrames);
    }
}
