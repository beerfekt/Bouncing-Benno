package net.beerfekt.bouncingbenno.manager;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import net.beerfekt.bouncingbenno.objekts.ImageKillBox;
import net.beerfekt.bouncingbenno.objekts.game.Emy_Einhorn;
import net.beerfekt.bouncingbenno.objekts.game.Flying_Flo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MonsterManager {
    List<Bitmap> monster;
    ArrayList<ImageKillBox> onScreenMonster = new ArrayList<>();

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
            objectsWaitTime = rand.nextInt(1501) + 1000;
            int rand = MonsterManager.rand.nextInt(2);
            Bitmap randMonster = monster.get(rand);

            if (rand == 0)
                onScreenMonster.add(new Emy_Einhorn(randMonster.getWidth()/5, randMonster.getHeight()/5 ,monster.get(rand)));
            if (rand == 1)
                onScreenMonster.add(new Flying_Flo(randMonster.getWidth()/3.5f, randMonster.getHeight()/3.5f ,monster.get(rand)));


        }

        for (ImageKillBox box : onScreenMonster) {
            if (box.intersect(RunTimeManager.SCREEN_RECT)) {
                box.update(20);
            }
        }
        removeMonster();
    }

    public ArrayList<ImageKillBox> getonScreenMonster(){
        return onScreenMonster;
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

    public void removeAllMonster() {
        ArrayList<ImageKillBox> toBeRemoved = new ArrayList<>();
        for (ImageKillBox box : onScreenMonster){
                toBeRemoved.add(box);
        }
        onScreenMonster.removeAll(toBeRemoved);
    }
}
