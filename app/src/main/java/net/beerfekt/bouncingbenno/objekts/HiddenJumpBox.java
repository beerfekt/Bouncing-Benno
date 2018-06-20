package net.beerfekt.bouncingbenno.objekts;

import net.beerfekt.bouncingbenno.objekts.properties.JumpBox;

public class HiddenJumpBox extends AbstractObject implements JumpBox {
    public HiddenJumpBox(float x, float y, float directionX, float directionY, float width, float height) {
        super(x, y, directionX, directionY, width, height);
    }

    @Override
    public HiddenJumpBox copy() {
        return new HiddenJumpBox(getX(), getY(), getDirectionX(), getDirectionY(), getWidth(), getHeight());
    }
}
