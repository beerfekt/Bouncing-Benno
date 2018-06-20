package net.beerfekt.bouncingbenno.objekts;

import net.beerfekt.bouncingbenno.objekts.properties.KillBox;

public class HiddenKillBox extends AbstractObject implements KillBox {
    public HiddenKillBox(float x, float y, float directionX, float directionY, float width, float height) {
        super(x, y, directionX, directionY, width, height);
    }

    @Override
    public HiddenKillBox copy() {
        return new HiddenKillBox(getX(), getY(), getDirectionX(), getDirectionY(), getWidth(), getHeight());
    }
}
