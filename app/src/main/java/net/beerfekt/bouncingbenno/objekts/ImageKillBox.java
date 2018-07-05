package net.beerfekt.bouncingbenno.objekts;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import net.beerfekt.bouncingbenno.objekts.properties.Animation;
import net.beerfekt.bouncingbenno.objekts.properties.Drawable;

public class ImageKillBox extends HiddenKillBox implements Drawable {

    private Animation animation;

    public ImageKillBox(float x, float y, float directionX, float directionY, float width, float height, Bitmap image) {
        this(x, y, directionX, directionY, width, height, new Animation(new Bitmap[]{image}, 0));
    }

    public ImageKillBox(float x, float y, float directionX, float directionY, float width, float height, Animation animation) {
        super(x, y, directionX, directionY, width, height);
        this.animation = animation;
    }

    @Override
    public void draw(Canvas canvas) {
        animation.draw(canvas, getRectangle());
    }

    @Override
    public void update(float numberOfFrames) {
        super.update(numberOfFrames);
        animation.update(numberOfFrames);
    }

    @Override
    public ImageKillBox copy() {
        return new ImageKillBox(getX(), getY(), getDirectionX(), getDirectionY(), getWidth(), getHeight(), animation.copy());
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public Animation getAnimation() {
        return animation;
    }
}
