package net.beerfekt.bouncingbenno.objekts;

//Begrenzung unten
// Kann auch zu Boden umgemünzt werden
// und mit zeitlichen abständen zwischen den blöcken
// können abgründe erzeugt werden

import android.graphics.Bitmap;
import android.graphics.Canvas;

import net.beerfekt.bouncingbenno.GamePanel;

public class BorderBottomPart extends AbstractObject {

    private Bitmap image;
    public BorderBottomPart(Bitmap res, int x, int y)
    {
        super(x, y, GamePanel.MOVESPEED, 0, 20, 200);

        image = Bitmap.createBitmap(res, 0, 0, getWidth(), getHeight());

    }
    public void update()
    {
        updatePosition();
    }
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, getX(), getY(), null);

    }
}
