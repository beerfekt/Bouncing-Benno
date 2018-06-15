package net.beerfekt.bouncingbenno;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import net.beerfekt.bouncingbenno.manager.RunTimeManager;


public class BouncingBennoView extends SurfaceView implements SurfaceHolder.Callback {
    private RunTimeManager runTimeManager;

    public final static int SCREEN_WIDTH = 1920, SCREEN_HEIGHT = 1080;
    public final static Rect SCREEN_RECT = new Rect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

    public BouncingBennoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        setFocusable(true);

        runTimeManager = new RunTimeManager(getHolder(), this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        runTimeManager.surfaceCreated(holder);
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        runTimeManager.surfaceDestroyed(holder);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return runTimeManager.onTouchEvent(event) ? true : super.onTouchEvent(event);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        runTimeManager.draw(canvas);
    }


}

