package net.beerfekt.bouncingbenno.objekts;

import android.graphics.Rect;

public abstract class AbstractObject {

    private int x;
    private int y;
    private int directionX;
    private int directionY;
    private int width;
    private int height;

    public AbstractObject(int x, int y, int dx, int dy, int width, int height) {
        this.x = x;
        this.y = y;
        this.directionX = dx;
        this.directionY = dy;
        this.width = width;
        this.height = height;
    }

    public void updatePosition() {
        x += directionX;
        y += directionY;
    }

    public Rect getRectangle() {
        return new Rect(x, y, x + width, y + height);
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


}
