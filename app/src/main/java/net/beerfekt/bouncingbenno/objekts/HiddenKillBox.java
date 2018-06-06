package net.beerfekt.bouncingbenno.objekts;

import net.beerfekt.bouncingbenno.objekts.properties.KillBox;

public class HiddenKillBox extends AbstractObject implements KillBox {
    public HiddenKillBox(int x, int y, int directionX, int directionY, int width, int height, long positionDuration) {
        super(x, y, directionX, directionY, width, height, positionDuration);
    }
}
