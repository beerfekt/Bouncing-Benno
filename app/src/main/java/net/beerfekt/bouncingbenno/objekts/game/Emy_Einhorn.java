package net.beerfekt.bouncingbenno.objekts.game;

        import android.graphics.Bitmap;

        import net.beerfekt.bouncingbenno.manager.RunTimeManager;
        import net.beerfekt.bouncingbenno.objekts.ImageKillBox;
        import net.beerfekt.bouncingbenno.objekts.properties.Animation;

public class Emy_Einhorn extends ImageKillBox {


    public Emy_Einhorn(float w, float h, Animation emy){
        super(RunTimeManager.SCREEN_WIDTH-1, 720, -30f, 0f , w, h, emy);
    }

    public void update(float numberOfFrames) {
        super.update(numberOfFrames);
    }
}
