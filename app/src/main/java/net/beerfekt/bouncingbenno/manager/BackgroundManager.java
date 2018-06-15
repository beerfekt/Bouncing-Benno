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

    /**
     *
     * @param sky
     * @param skyDuration wie lange es dauert bis das Bild um einen Pixel verschoben wird
     * @param landscape wie lange es dauert bis das Bild um einen Pixel verschoben wird
     */
    public BackgroundManager(Bitmap sky, long skyDuration, Bitmap landscape, long landscapeDuration) {
        this.sky1 = new ImageNeutralBox(0, 0, -1, 0, BouncingBennoView.SCREEN_WIDTH, BouncingBennoView.SCREEN_HEIGHT, skyDuration, sky);
        this.sky2 = new ImageNeutralBox(BouncingBennoView.SCREEN_WIDTH, 0, -1, 0, BouncingBennoView.SCREEN_WIDTH, BouncingBennoView.SCREEN_HEIGHT, skyDuration, sky);
        this.landscape1 = new ImageNeutralBox(0, 0, -1, 0, BouncingBennoView.SCREEN_WIDTH, BouncingBennoView.SCREEN_HEIGHT, landscapeDuration, landscape);
        this.landscape2 = new ImageNeutralBox(BouncingBennoView.SCREEN_WIDTH, 0, -1, 0, BouncingBennoView.SCREEN_WIDTH, BouncingBennoView.SCREEN_HEIGHT, landscapeDuration, landscape);
        this.backgroundObjects = new ArrayList<>();
    }

    private void removeLand(){
        reposBackground(sky1);
        reposBackground(sky2);
        reposBackground(landscape1);
        reposBackground(landscape2);
        ArrayList<ImageNeutralBox> toBeRemoved = new ArrayList<>();
        for(ImageNeutralBox box : backgroundObjects) {
            if(!box.intersect(BouncingBennoView.SCREEN_RECT)) {
                toBeRemoved.add(box);
            }
        }
        backgroundObjects.removeAll(toBeRemoved);
    }

    private void reposBackground(ImageNeutralBox box) {
        if(!box.intersect(BouncingBennoView.SCREEN_RECT) && box.getX()+box.getWidth() < 0) {
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
            if(box.intersect(BouncingBennoView.SCREEN_RECT)) {
                box.draw(canvas);
            }
        }
    }

    public void update() {
        sky1.update();
        sky2.update();
        landscape1.update();
        landscape2.update();
        for(ImageNeutralBox box : backgroundObjects) {
            box.update();
        }
        removeLand();
    }
}
