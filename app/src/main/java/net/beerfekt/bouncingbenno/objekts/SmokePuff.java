package net.beerfekt.bouncingbenno.objekts;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


//Smokeparts that follow the Player

public class SmokePuff extends AbstractObject {

    //
    public static final int MOVE_SPEED = 10;
    public int r;

    // The Parameters setting the x,y position , x from left and y from top
    public SmokePuff(int x, int y)
    {
        super(x,y,0,0,0,0);
        r = 5;
    }


    public void update()
    {
        setX(getX()- MOVE_SPEED);
    }


    public void draw(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setAlpha(50);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(getX()-r, getY()-r, r, paint);
        canvas.drawCircle(getX()-r+2, getY()-r-2,r,paint);
        canvas.drawCircle(getX()-r+4, getY()-r+1, r, paint);
    }

}