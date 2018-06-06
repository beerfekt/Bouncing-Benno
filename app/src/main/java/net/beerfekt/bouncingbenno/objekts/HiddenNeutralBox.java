package net.beerfekt.bouncingbenno.objekts;

import net.beerfekt.bouncingbenno.objekts.properties.NeutralBox;

public class HiddenNeutralBox extends AbstractObject implements NeutralBox {
    public HiddenNeutralBox(int x, int y, int directionX, int directionY, int width, int height, long positionDuration) {
        super(x, y, directionX, directionY, width, height, positionDuration);
    }
}
