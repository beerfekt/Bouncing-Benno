package net.beerfekt.bouncingbenno.manager;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import net.beerfekt.bouncingbenno.BouncingBennoView;
import net.beerfekt.bouncingbenno.objekts.ImageNeutralBox;

import java.util.ArrayList;
import java.util.List;

public class BackgroundManager {
    ImageNeutralBox sky1;
    ImageNeutralBox sky2;
    ImageNeutralBox landscape1;
    ImageNeutralBox landscape2;
    List<ImageNeutralBox> backgroundObjects;

    public BackgroundManager(Bitmap sky, Bitmap landscape) {
        this.sky1 = new ImageNeutralBox(0f, 0f, -1f, 0f, RunTimeManager.SCREEN_WIDTH, RunTimeManager.SCREEN_HEIGHT, sky);
        this.sky2 = sky1.copy();
        sky2.setX(RunTimeManager.SCREEN_WIDTH);
        this.landscape1 = new ImageNeutralBox(0, 0, -1, 0, RunTimeManager.SCREEN_WIDTH, RunTimeManager.SCREEN_HEIGHT, landscape);
        this.landscape2 = landscape1.copy();
        landscape2.setX(RunTimeManager.SCREEN_WIDTH);
        this.backgroundObjects = new ArrayList<>();
    }

    private void reposBackground(ImageNeutralBox box) {
        if(!box.intersect(RunTimeManager.SCREEN_RECT) && box.getX()+box.getWidth() < 0) {
            box.setX(box.getX() + 2 * box.getWidth());
        }
    }

    public void draw(Canvas canvas)
    {
        sky1.draw(canvas);
        sky2.draw(canvas);
        landscape1.draw(canvas);
        landscape2.draw(canvas);
        for(ImageNeutralBox box : backgroundObjects) {
            if(box.intersect(RunTimeManager.SCREEN_RECT)) {
                box.draw(canvas);
            }
        }
    }

    public void update(float numberOfFrames) {
        sky1.update(numberOfFrames);
        sky2.update(numberOfFrames);
        landscape1.update(numberOfFrames);
        landscape2.update(numberOfFrames);
        for(ImageNeutralBox box : backgroundObjects) {
            box.update(numberOfFrames);
        }
        removeLand();
    }

    private void removeLand(){
        reposBackground(sky1);
        reposBackground(sky2);
        reposBackground(landscape1);
        reposBackground(landscape2);
        ArrayList<ImageNeutralBox> toBeRemoved = new ArrayList<>();
        for(ImageNeutralBox box : backgroundObjects) {
            if(box.isOutsideScreen()) {
                toBeRemoved.add(box);
            }
        }
        backgroundObjects.removeAll(toBeRemoved);
    }
}
