package net.beerfekt.bouncingbenno;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import net.beerfekt.bouncingbenno.manager.RunTimeManager;


public class BouncingBennoView extends SurfaceView {
    private RunTimeManager runTimeManager;

    public final static int SCREEN_WIDTH = 1920, SCREEN_HEIGHT = 1080;
    public final static Rect SCREEN_RECT = new Rect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

    public BouncingBennoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        runTimeManager = new RunTimeManager(getHolder(), this);
        getHolder().addCallback(runTimeManager);
        setFocusable(true);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        runTimeManager.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return runTimeManager.onTouchEvent(event) ? true : super.onTouchEvent(event);
    }

}

