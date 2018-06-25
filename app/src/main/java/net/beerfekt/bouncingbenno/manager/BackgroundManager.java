package net.beerfekt.bouncingbenno.manager;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import net.beerfekt.bouncingbenno.BouncingBennoView;
import net.beerfekt.bouncingbenno.objekts.ImageNeutralBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BackgroundManager {
    ImageNeutralBox sky1;
    ImageNeutralBox sky2;
    ImageNeutralBox landscape1;
    ImageNeutralBox landscape2;
    ImageNeutralBox street1;
    ImageNeutralBox street2;
    List<ImageNeutralBox> onScreenObjects = new ArrayList<>();
    ArrayList<Bitmap> objects;
    static Random rand= new Random();
    int objectsWaitTime = 4000;
    private long lastFrameTime = System.currentTimeMillis();

    public BackgroundManager(Bitmap sky, Bitmap landscape, Bitmap street, ArrayList<Bitmap> objects) {
        this.sky1 = new ImageNeutralBox(0f, 0f, -1f, 0f, RunTimeManager.SCREEN_WIDTH, RunTimeManager.SCREEN_HEIGHT, sky);
        this.sky2 = sky1.copy();
        sky2.setX(RunTimeManager.SCREEN_WIDTH);
        this.landscape1 = new ImageNeutralBox(0f, 0f, -1f, 0f, RunTimeManager.SCREEN_WIDTH, RunTimeManager.SCREEN_HEIGHT, landscape);
        this.landscape2 = landscape1.copy();
        landscape2.setX(RunTimeManager.SCREEN_WIDTH);
        this.street1 = new ImageNeutralBox(0f, 0f, -1f, 0f, RunTimeManager.SCREEN_WIDTH, RunTimeManager.SCREEN_HEIGHT, street);
        this.street2 = street1.copy();
        street2.setX(RunTimeManager.SCREEN_WIDTH);
        this.objects=objects;
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
        street1.draw(canvas);
        street2.draw(canvas);
        for(ImageNeutralBox box : onScreenObjects) {
            if(box.intersect(RunTimeManager.SCREEN_RECT)) {
                box.draw(canvas);
            }
        }
    }

    public void update(float numberOfFrames) {
        sky1.update(3);
        sky2.update(3);
        landscape1.update(5);
        landscape2.update(5);
        street1.update(20);
        street2.update(20);
        long elapsed = (System.currentTimeMillis() - lastFrameTime);

        if (elapsed > objectsWaitTime) {
            lastFrameTime += objectsWaitTime;
            objectsWaitTime = rand.nextInt(3001)+2000;
            onScreenObjects.add(new ImageNeutralBox(RunTimeManager.SCREEN_WIDTH-1, (float) rand.nextInt(144)+350, -1f, 0f, 200f, 300f, objects.get(rand.nextInt(2))));
        }

        for (ImageNeutralBox box : onScreenObjects) {
            box.update(5);
        }
        removeLand();
    }

    private void removeLand(){
        reposBackground(sky1);
        reposBackground(sky2);
        reposBackground(landscape1);
        reposBackground(landscape2);
        reposBackground(street1);
        reposBackground(street2);
        ArrayList<ImageNeutralBox> toBeRemoved = new ArrayList<>();
        for(ImageNeutralBox box : onScreenObjects) {
            if(box.isOutsideScreen()) {
                toBeRemoved.add(box);
            }
        }
        onScreenObjects.removeAll(toBeRemoved);
    }
}
