package net.beerfekt.myfirstgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


//Smokeparts that follow the Player

public class SmokePuff extends GameObject {

    //
    public static final int MOVE_SPEED = 10;
    public int r;

    // The Parameters setting the x,y position , x from left and y from top
    public SmokePuff(int x, int y)
    {
        r = 5;
        super.x = x;
        super.y = y;
    }


    public void update()
    {
        //Smokepuff moving x-10 (to the left)
        x-= MOVE_SPEED;
    }


    public void draw(Canvas canvas)
    {
        //Smokepuff painting into the canvas
        // 3 small circles which overlapp each other

        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setAlpha(50);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(x-r, y-r, r, paint);
        canvas.drawCircle(x-r+2, y-r-2,r,paint);
        canvas.drawCircle(x-r+4, y-r+1, r, paint);
    }

}