package net.beerfekt.bouncingbenno.objekts;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import net.beerfekt.bouncingbenno.objekts.properties.Animation;
import net.beerfekt.bouncingbenno.objekts.properties.Drawable;

public class ImageNeutralBox extends HiddenNeutralBox implements Drawable {

    private Animation animation;

    public ImageNeutralBox(int x, int y, int directionX, int directionY, int width, int height, long positionDuration, Bitmap image) {
        this(x, y, directionX, directionY, width, height, positionDuration, new Animation(new Bitmap[]{image}, -1));
    }

    public ImageNeutralBox(int x, int y, int directionX, int directionY, int width, int height, long positionDuration, Animation animation) {
        super(x, y, directionX, directionY, width, height, positionDuration);
        this.animation = animation;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(animation.getImage(), null, getRectangle(), null);
    }

    @Override
    public void update() {
        super.update();
        animation.update();
    }
}
