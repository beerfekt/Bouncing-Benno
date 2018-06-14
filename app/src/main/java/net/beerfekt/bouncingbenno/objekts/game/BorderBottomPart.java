package net.beerfekt.bouncingbenno.objekts.game;

//Begrenzung unten
// Kann auch zu Boden umgemünzt werden
// und mit zeitlichen abständen zwischen den blöcken
// können abgründe erzeugt werden

import android.graphics.Bitmap;

import net.beerfekt.bouncingbenno.BouncingBennoView;
import net.beerfekt.bouncingbenno.objekts.ImageJumpBox;

public class BorderBottomPart extends ImageJumpBox {

    public BorderBottomPart(Bitmap image, int x, int y) {
        super(x, y, BouncingBennoView.MOVESPEED, 0, 20, 200, 30, image);
    }
}
