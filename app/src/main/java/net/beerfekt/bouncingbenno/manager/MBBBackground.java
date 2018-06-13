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

    public void removeLand(){
        boolean isOutside = land.get(0).isOutsideScreen();
        while(isOutside)
        {
            land.remove(0);
            isOutside = land.get(0).isOutsideScreen();
        }
    }

    public void draw(Canvas canvas)
    {
        removeLand();
        sky.draw(canvas);
        landscape.draw(canvas);
        if(land.size() >= 1) {
            land.get(0).draw(canvas);
            if(land.size() >= 2) {
                land.get(1).draw(canvas);
            }
        }
    }
}
