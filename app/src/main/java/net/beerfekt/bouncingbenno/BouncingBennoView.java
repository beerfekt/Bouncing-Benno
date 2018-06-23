package net.beerfekt.bouncingbenno;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import net.beerfekt.bouncingbenno.manager.BackgroundManager;
import net.beerfekt.bouncingbenno.manager.RunTimeManager;
import net.beerfekt.bouncingbenno.objekts.game.Player;

import java.util.ArrayList;


public class BouncingBennoView extends SurfaceView implements SurfaceHolder.Callback {
    private RunTimeManager runTimeManager;

    private Bitmap background_sky;
    private Bitmap background_landscape;
    private Bitmap background_street;
    private Bitmap background_baum;
    private Bitmap background_haus;
    private Bitmap helicopter;

    public BouncingBennoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        setFocusable(true);

        background_sky = BitmapFactory.decodeResource(context.getResources(), R.drawable.background_sky);
        background_landscape = BitmapFactory.decodeResource(context.getResources(), R.drawable.background_landscape);
        background_street = BitmapFactory.decodeResource(context.getResources(), R.drawable.background_street);
        background_baum = BitmapFactory.decodeResource(getResources(), R.drawable.background_baum);
        background_haus = BitmapFactory.decodeResource(getResources(), R.drawable.background_haus);
        helicopter = BitmapFactory.decodeResource(getResources(), R.drawable.helicopter);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        synchronized (this) {
            ArrayList<Bitmap> backgroundObjects = new ArrayList<>();
            backgroundObjects.add(background_baum);
            backgroundObjects.add(background_haus);

            BackgroundManager backgroundManager = new BackgroundManager(background_sky, background_landscape, background_street, backgroundObjects);

            Player player = new Player(helicopter, 200, 100, 0.5f);

            runTimeManager = new RunTimeManager(holder, this, backgroundManager, player);
            runTimeManager.startGame();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        synchronized (this) {
            if (runTimeManager != null) {
                runTimeManager.stopGame();
            }
            runTimeManager = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return runTimeManager.onTouchEventDown();
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            return runTimeManager.onTouchEventUp();
        }
        return false;
    }
}

