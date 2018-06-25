package net.beerfekt.bouncingbenno.manager;

import android.graphics.Canvas;

import net.beerfekt.bouncingbenno.objekts.ImageKillBox;

import java.util.ArrayList;
import java.util.List;

public class MonsterManager {
    List<ImageKillBox> monster;
    List<ImageKillBox> onScreenMonster;

    public MonsterManager(ArrayList<ImageKillBox> monster){
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
        for (ImageKillBox box : onScreenMonster) {
            if (box.intersect(RunTimeManager.SCREEN_RECT)) {
                box.update(numberOfFrames);
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
