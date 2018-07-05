package net.beerfekt.bouncingbenno.objekts;

import android.graphics.Rect;

import net.beerfekt.bouncingbenno.manager.RunTimeManager;

public abstract class AbstractObject{

    private float x;
    private float y;
    private float directionX;
    private float directionY;
    private float width;
    private float height;

    public AbstractObject(float x, float y, float directionX, float directionY, float width, float height) {
        this.x = x;
        this.y = y;
        this.directionX = directionX;
        this.directionY = directionY;
        this.width = width;
        this.height = height;
    }

    public void update(float numberOfFrames) {
        x += numberOfFrames * directionX;
        y += numberOfFrames * directionY;
    }

    public Rect getRectangle() {
        return new Rect((int) x, (int) y, (int) (x + width), (int) (y + height));
    }

    public boolean intersect(AbstractObject obj) {
        return intersect(obj.getRectangle());
    }

    public boolean intersect(Rect obj) {
        return getRectangle().intersect(obj);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getDirectionX() {
        return directionX;
    }

    public void setDirectionX(float directionX) {
        this.directionX = directionX;
    }

    public float getDirectionY() {
        return directionY;
    }

    public void setDirectionY(float directionY) {
        this.directionY = directionY;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public boolean isOutsideScreen() {
        return !getRectangle().intersect(RunTimeManager.SCREEN_RECT);
    }

    abstract AbstractObject copy();
}
