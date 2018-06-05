package net.beerfekt.bouncingbenno.objekts;

import android.graphics.Bitmap;

import net.beerfekt.bouncingbenno.objekts.properties.Animation;

public class AnimationJumpBox extends ImmageJumpBox {

    private Animation animation;

    public AnimationJumpBox(int x, int y, int dx, int dy, int width, int height, Bitmap immage, Animation animation) {
        super(x, y, dx, dy, width, height, immage);
        this.animation = animation;
    }

    public Animation getAnimation() {
        return animation;
    }

    @Override
    public Bitmap getImmage() {
        return animation.getImage();
    }
}
