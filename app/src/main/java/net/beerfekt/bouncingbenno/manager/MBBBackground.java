package net.beerfekt.bouncingbenno.manager;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import net.beerfekt.bouncingbenno.objekts.ImageNeutralBox;

import java.util.List;

public class MBBBackground{
    ImageNeutralBox sky;
    ImageNeutralBox landscape;
    List<ImageNeutralBox> land;

    public MBBBackground(ImageNeutralBox sky, ImageNeutralBox landscape, List<ImageNeutralBox> land) {
        this.sky = sky;
        this.landscape = landscape;
        this.land = land;
    }

    public void addLand(ImageNeutralBox image)
    {
        land.add(image);
    }

    public void removeLand

    public void draw(Canvas canvas)
    {
        sky.draw(canvas);
        landscape.draw(canvas);
        for(int i = 0; i <= 2 || i < land.size()) {
            land.get(0).draw(canvas);
            land.get(1).draw(canvas);
            land.get(2).draw(canvas);
        }
    }
}
