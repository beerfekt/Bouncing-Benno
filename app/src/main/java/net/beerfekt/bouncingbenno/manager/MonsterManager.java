package net.beerfekt.bouncingbenno.manager;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import net.beerfekt.bouncingbenno.objekts.ImageKillBox;
import net.beerfekt.bouncingbenno.objekts.game.Emy_Einhorn;
import net.beerfekt.bouncingbenno.objekts.game.Flying_Flo;
import net.beerfekt.bouncingbenno.objekts.game.Hans_Horny;
import net.beerfekt.bouncingbenno.objekts.game.Rio_Reisnagel;
import net.beerfekt.bouncingbenno.objekts.game.Rolph_Ruessel;
import net.beerfekt.bouncingbenno.objekts.properties.Animation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MonsterManager {
    List<Bitmap> monster;
    Bitmap[] emy;
    Bitmap[] flo;
    Bitmap[] hans;
    Bitmap[] rolph;

    ArrayList<ImageKillBox> onScreenMonster = new ArrayList<>();

    private static Random rand = new Random();
    private int objectsWaitTime = 4000;
    private long lastFrameTime = System.currentTimeMillis();


    public MonsterManager(ArrayList<Bitmap> monster, Bitmap[] emy, Bitmap[] flo, Bitmap[] hans, Bitmap[] rolph){
        this.monster = monster;
        this.emy = emy;
        this.flo = flo;
        this.hans = hans;
        this.rolph = rolph;

    }

    public void draw(Canvas canvas) {
        for (ImageKillBox box : onScreenMonster) {
            if (box.intersect(RunTimeManager.SCREEN_RECT)) {
                box.draw(canvas);
            }
        }
    }

    public void update(float numberOfFrames) {
        addOnscreenMonsters();

        for (ImageKillBox box : onScreenMonster) {
            if (box.intersect(RunTimeManager.SCREEN_RECT)) {
                box.update(numberOfFrames);
            }
        }
        removeMonster();
    }

    private void addOnscreenMonsters() {
        long elapsed = (System.currentTimeMillis() - lastFrameTime);
        if (elapsed > objectsWaitTime) {
            lastFrameTime = System.currentTimeMillis();
            objectsWaitTime = rand.nextInt(1501) + 1000;
            int rand = MonsterManager.rand.nextInt(5);
            //Bitmap randMonster = monster.get(rand);

            if (rand == 0)
                onScreenMonster.add(new Emy_Einhorn(emy[0].getWidth()/4f, emy[0].getHeight()/4f, new Animation(emy, 30)));
            if (rand == 1)
                onScreenMonster.add(new Flying_Flo(flo[0].getWidth()/3f, flo[0].getHeight()/3f , new Animation(flo, 30)));
            if (rand == 2)
                onScreenMonster.add(new Hans_Horny(hans[0].getWidth()/4f, hans[0].getHeight()/4f , new Animation(hans, 30)));
            if (rand == 3)
                onScreenMonster.add(new Rio_Reisnagel(monster.get(0).getWidth()/3f, monster.get(0).getHeight()/3f , monster.get(0)));
            if (rand == 4)
                onScreenMonster.add(new Rolph_Ruessel(rolph[0].getWidth()/3.5f, rolph[0].getHeight()/3.5f , new Animation(rolph, 30)));
        }
    }

    public ArrayList<ImageKillBox> getOnScreenMonster(){
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
