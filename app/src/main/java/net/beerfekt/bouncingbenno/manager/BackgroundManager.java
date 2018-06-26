package net.beerfekt.bouncingbenno.manager;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import net.beerfekt.bouncingbenno.objekts.ImageNeutralBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BackgroundManager {
    private ImageNeutralBox overlay;
    private ImageNeutralBox sky1;
    private ImageNeutralBox sky2;
    private ImageNeutralBox landscape1_1;
    private ImageNeutralBox landscape1_2;
    private ImageNeutralBox landscape2_1;
    private ImageNeutralBox landscape2_2;
    private ImageNeutralBox street1;
    private ImageNeutralBox street2;
    private List<ImageNeutralBox> onScreenObjects = new ArrayList<>();
    private ArrayList<Bitmap> objects;
    private static Random rand = new Random();
    private int objectsWaitTime = 4000;
    private long lastFrameTime = System.currentTimeMillis();


    public BackgroundManager(Bitmap overlay, Bitmap sky, Bitmap landscape1, Bitmap landscape2, Bitmap street, ArrayList<Bitmap> objects) {
        this.overlay=new ImageNeutralBox(0f, 0f, 0f, 0f, RunTimeManager.SCREEN_WIDTH, RunTimeManager.SCREEN_HEIGHT, overlay);

        this.sky1 = new ImageNeutralBox(0f, 0f, -1f, 0f, RunTimeManager.SCREEN_WIDTH, RunTimeManager.SCREEN_HEIGHT, sky);
        this.sky2 = sky1.copy();
        sky2.setX(RunTimeManager.SCREEN_WIDTH);

        this.landscape1_1 = new ImageNeutralBox(0f, 200, -3f, 0f, RunTimeManager.SCREEN_WIDTH, landscape1.getHeight()*2, landscape1);
        this.landscape1_2 = landscape1_1.copy();
        landscape1_2.setX(RunTimeManager.SCREEN_WIDTH);

        this.landscape2_1 = new ImageNeutralBox(0f, 350, -6f, 0f, RunTimeManager.SCREEN_WIDTH, landscape2.getHeight(), landscape2);
        this.landscape2_2 = landscape2_1.copy();
        landscape2_1.setX(RunTimeManager.SCREEN_WIDTH);

        this.street1 = new ImageNeutralBox(0f,800, -12f, 0f, RunTimeManager.SCREEN_WIDTH, street.getHeight(), street);
        this.street2 = street1.copy();
        street2.setX(RunTimeManager.SCREEN_WIDTH);

        this.objects = objects;
    }

    private void reposBackground(ImageNeutralBox box) {
        if (!box.intersect(RunTimeManager.SCREEN_RECT) && box.getX() + box.getWidth() < 0) {
            box.setX(box.getX() + 2 * box.getWidth());
        }
    }

    public void draw(Canvas canvas) {
        sky1.draw(canvas);
        sky2.draw(canvas);
        landscape1_1.draw(canvas);
        landscape1_2.draw(canvas);
        landscape2_1.draw(canvas);
        landscape2_2.draw(canvas);
        street1.draw(canvas);
        street2.draw(canvas);
        for (ImageNeutralBox box : onScreenObjects) {
            if (box.intersect(RunTimeManager.SCREEN_RECT)) {
                box.draw(canvas);
            }
        }
        overlay.draw(canvas);
    }

    public void update(float numberOfFrames) {
        sky1.update(numberOfFrames);
        sky2.update(numberOfFrames);
        landscape1_1.update(numberOfFrames);
        landscape1_2.update(numberOfFrames);
        landscape2_1.update(numberOfFrames);
        landscape2_2.update(numberOfFrames);
        street1.update(numberOfFrames);
        street2.update(numberOfFrames);


        long elapsed = (System.currentTimeMillis() - lastFrameTime);
        if (elapsed > objectsWaitTime) {
            lastFrameTime += objectsWaitTime;
            objectsWaitTime = rand.nextInt(3001) + 2000;
            Bitmap randObject = objects.get(rand.nextInt(3));
            onScreenObjects.add(new ImageNeutralBox(RunTimeManager.SCREEN_WIDTH - 1, (float) rand.nextInt(144) + 350, -6f, 0f, 200, 300, randObject));
        }

        for (ImageNeutralBox box : onScreenObjects) {
            box.update(numberOfFrames);
        }
        removeLand();
    }

    private void removeLand() {
        reposBackground(sky1);
        reposBackground(sky2);
        reposBackground(landscape1_1);
        reposBackground(landscape1_2);
        reposBackground(landscape2_1);
        reposBackground(landscape2_2);
        reposBackground(street1);
        reposBackground(street2);
        ArrayList<ImageNeutralBox> toBeRemoved = new ArrayList<>();
        for (ImageNeutralBox box : onScreenObjects) {
            if (box.isOutsideScreen()) {
                toBeRemoved.add(box);
            }
        }
        onScreenObjects.removeAll(toBeRemoved);
    }
}
