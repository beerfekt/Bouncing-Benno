package net.beerfekt.bouncingbenno.objekts;

import net.beerfekt.bouncingbenno.objekts.properties.NeutralBox;

public class HiddenNeutralBox extends AbstractObject implements NeutralBox {
    public HiddenNeutralBox(float x, float y, float directionX, float directionY, float width, float height) {
        super(x, y, directionX, directionY, width, height);
    }

    @Override
    public HiddenNeutralBox copy() {
        return new HiddenNeutralBox(getX(), getY(), getDirectionX(), getDirectionY(), getWidth(), getHeight());
    }
}
