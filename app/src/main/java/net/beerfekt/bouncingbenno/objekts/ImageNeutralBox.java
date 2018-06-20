package net.beerfekt.bouncingbenno.objekts;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import net.beerfekt.bouncingbenno.objekts.properties.Animation;
import net.beerfekt.bouncingbenno.objekts.properties.Drawable;

public class ImageNeutralBox extends HiddenNeutralBox implements Drawable {

    private Animation animation;

    public ImageNeutralBox(float x, float y, float directionX, float directionY, float width, float height, Bitmap image) {
        this(x, y, directionX, directionY, width, height, new Animation(new Bitmap[]{image}, 0));
    }

    public ImageNeutralBox(float x, float y, float directionX, float directionY, float width, float height, Animation animation) {
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
    public ImageNeutralBox copy() {
        return new ImageNeutralBox(getX(), getY(), getDirectionX(), getDirectionY(), getWidth(), getHeight(), animation.copy());
    }
}
