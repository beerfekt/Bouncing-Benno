package net.beerfekt.bouncingbenno;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import net.beerfekt.bouncingbenno.manager.BackgroundManager;
import net.beerfekt.bouncingbenno.manager.RunTimeManager;
import net.beerfekt.bouncingbenno.objekts.game.Player;


public class BouncingBennoView extends SurfaceView implements SurfaceHolder.Callback{
    private RunTimeManager runTimeManager;

    public BouncingBennoView(Context context, AttributeSet attrs) {
        super(context, attrs);

        runTimeManager = new RunTimeManager(getHolder(), this);

        getHolder().addCallback(this);
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!runTimeManager.player.getPlaying() && !runTimeManager.newGameCreated) {
                runTimeManager.newGame();
                runTimeManager.player.setPlaying(true);
                runTimeManager.player.setUp(true);
            } else {
                runTimeManager.player.setUp(true);
            }
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            runTimeManager.player.setUp(false);
            return true;
        }
        return false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        runTimeManager.setSurfaceHolder(holder);
        synchronized (this) {
            runTimeManager.backgroundManager = new BackgroundManager(BitmapFactory.decodeResource(getResources(), R.drawable.background_sky), BitmapFactory.decodeResource(getResources(), R.drawable.background_landscape));
            runTimeManager.player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.helicopter), 65, 25, 3);
            runTimeManager.setRunning(true);
            runTimeManager.start();
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                runTimeManager.setRunning(false);
                runTimeManager.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }
}

