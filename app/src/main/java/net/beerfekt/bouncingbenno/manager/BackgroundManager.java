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
     * @param skySpeed geschwindigkeit in Pixeln pro zentel sekunde
     * @param landscape
     */
    public BackgroundManager(Bitmap sky, int skySpeed, Bitmap landscape, int landscapeSpeed) {
        this.sky1 = new ImageNeutralBox(0, 0, -skySpeed, 0, BouncingBennoView.WIDTH, BouncingBennoView.HEIGHT, 10, sky);
        this.sky2 = new ImageNeutralBox(BouncingBennoView.WIDTH, 0, -skySpeed, 0, BouncingBennoView.WIDTH, BouncingBennoView.HEIGHT, 10, sky);
        this.landscape1 = new ImageNeutralBox(0, 0, -landscapeSpeed, 0, BouncingBennoView.WIDTH, BouncingBennoView.HEIGHT, 10, landscape);
        this.landscape2 = new ImageNeutralBox(BouncingBennoView.WIDTH, 0, -landscapeSpeed, 0, BouncingBennoView.WIDTH, BouncingBennoView.HEIGHT, 10, landscape);
        this.backgroundObjects = new ArrayList<>();
    }

    private void removeLand(){
        reposBackground(sky1);
        reposBackground(sky2);
        reposBackground(landscape1);
        reposBackground(landscape2);
        ArrayList<ImageNeutralBox> toBeRemoved = new ArrayList<>();
        for(ImageNeutralBox box : backgroundObjects) {
            if(!box.intersect(BouncingBennoView.screen)) {
                toBeRemoved.add(box);
            }
        }
        backgroundObjects.removeAll(toBeRemoved);
    }

    private void reposBackground(ImageNeutralBox box) {
        if(!box.intersect(BouncingBennoView.screen) && box.getX()+box.getWidth() < 0) {
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
            if(box.intersect(BouncingBennoView.screen)) {
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
