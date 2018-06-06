package net.beerfekt.bouncingbenno;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public class Background {

    private Bitmap image;
    private int x, y, dx;

    public Background(Bitmap res) {
        dx = GamePanel.MOVESPEED;
        image = res;
    }

    public void update() {
        //Schritt um den das Bild/Zeichenfläche verschoben wird
        x += dx;

        //wenn bild aus dem bildbereich rausgeht
        if (x < -GamePanel.WIDTH) {
            //setze position zurück
            x = 0;
        }
    }//update

    public void draw(Canvas canvas) {

        //1. Bild setzen
        canvas.drawBitmap(image,
                x,
                y,
                null);


        //wenn das 1.Bild aus dem bildbereich geht,
        if (x < 0)
        //was passiert mit der lücke dahinter?
        {
            //Lösung: 2.Bild dahinter setzen und scrollen lassen
            canvas.drawBitmap(image,
                    x + GamePanel.WIDTH,
                    y,
                    null);
        }

    }//draw


}


