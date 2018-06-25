package net.beerfekt.bouncingbenno.manager;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import net.beerfekt.bouncingbenno.objekts.ImageKillBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MonsterManager {
    List<Bitmap> monster;
    List<ImageKillBox> onScreenMonster = new ArrayList<>();

    private static Random rand = new Random();
    private int objectsWaitTime = 4000;
    private long lastFrameTime = System.currentTimeMillis();


    public MonsterManager(ArrayList<Bitmap> monster){
        this.monster = monster;
    }

    public void draw(Canvas canvas) {
        for (ImageKillBox box : onScreenMonster) {
            if (box.intersect(RunTimeManager.SCREEN_RECT)) {
                box.draw(canvas);
            }
        }
    }

    public void update(float numberOfFrames) {
        long elapsed = (System.currentTimeMillis() - lastFrameTime);
        if (elapsed > objectsWaitTime) {
            lastFrameTime += objectsWaitTime;
            objectsWaitTime = rand.nextInt(3001) + 2000;
            Bitmap randObject = monster.get(rand.nextInt(1));
            onScreenMonster.add(new ImageKillBox(RunTimeManager.SCREEN_WIDTH-1, 740, -1f, 0f, randObject.getWidth()/5, randObject.getHeight()/5, randObject));
        }

        for (ImageKillBox box : onScreenMonster) {
            if (box.intersect(RunTimeManager.SCREEN_RECT)) {
                box.update(20);
            }
        }
        removeMonster();
    }

    private void removeMonster() {
        ArrayList<ImageKillBox> toBeRemoved = new ArrayList<>();
        for (ImageKillBox box : onScreenMonster){
            if (box.isOutsideScreen()) {
                toBeRemoved.add(box);
            }
        }
        onScreenMonster.removeAll(toBeRemoved);
    }


}
