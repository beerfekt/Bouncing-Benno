package net.beerfekt.bouncingbenno.objekts;

import net.beerfekt.bouncingbenno.objekts.properties.JumpBox;

public class HiddenJumpBox extends AbstractObject implements JumpBox {
    public HiddenJumpBox(int x, int y, int directionX, int directionY, int width, int height, long positionDuration) {
        super(x, y, directionX, directionY, width, height, positionDuration);
    }
}
