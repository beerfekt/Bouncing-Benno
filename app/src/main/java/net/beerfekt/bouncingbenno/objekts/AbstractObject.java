package net.beerfekt.bouncingbenno.objekts;

import android.graphics.Rect;

import net.beerfekt.bouncingbenno.BouncingBennoView;

public abstract class AbstractObject {

    private int x;
    private int y;
    private int directionX;
    private int directionY;
    private int width;
    private int height;
    private long positionDuration;
    private long lastFrameTime;

    public AbstractObject(int x, int y, int directionX, int directionY, int width, int height, long positionDuration) {
        this.x = x;
        this.y = y;
        this.directionX = directionX;
        this.directionY = directionY;
        this.width = width;
        this.height = height;
        this.positionDuration = positionDuration;
        this.lastFrameTime = System.currentTimeMillis();
    }

    public void update() {
        if (positionDuration == -1) {
            return;
        }
        long elapsed = (System.currentTimeMillis() - lastFrameTime);

        if (elapsed > positionDuration) {
            long fixLag = elapsed/positionDuration;
            x +=  fixLag* directionX;
            y += fixLag * directionY;
            lastFrameTime += fixLag * positionDuration;
        }
    }

    public Rect getRectangle() {
        return new Rect(x, y, x + width, y + height);
    }

    public boolean intersect(AbstractObject obj)
    {
        return intersect(obj.getRectangle());
    }

    public boolean intersect(Rect obj)
    {
        return getRectangle().intersect(obj);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirectionX() {
        return directionX;
    }

    public void setDirectionX(int directionX) {
        this.directionX = directionX;
    }

    public int getDirectionY() {
        return directionY;
    }

    public void setDirectionY(int directionY) {
        this.directionY = directionY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isOutsideScreen()
    {
        return !getRectangle().intersect(new Rect(0, 0, BouncingBennoView.WIDTH, BouncingBennoView.HEIGHT));
    }
}
