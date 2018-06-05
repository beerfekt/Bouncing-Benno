package net.beerfekt.bouncingbenno.objekts;

import android.graphics.Bitmap;

import net.beerfekt.bouncingbenno.objekts.properties.Drawable;

public class ImmageJumpBox extends HiddenJumpBox implements Drawable {

    private Bitmap immage;

    public ImmageJumpBox(int x, int y, int dx, int dy, int width, int height, Bitmap immage) {
        super(x, y, dx, dy, width, height);
        this.immage = immage;
    }

    @Override
    public Bitmap getImmage() {
        return immage;
    }

    @Override
    public void draw() {
        //TODO DRAW
    }
}
