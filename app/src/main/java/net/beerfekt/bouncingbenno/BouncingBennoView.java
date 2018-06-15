package net.beerfekt.bouncingbenno;


import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import net.beerfekt.bouncingbenno.manager.BackgroundManager;
import net.beerfekt.bouncingbenno.manager.RunTimeManager;
import net.beerfekt.bouncingbenno.objekts.game.Player;


public class BouncingBennoView extends SurfaceView implements SurfaceHolder.Callback {
    private RunTimeManager runTimeManager;
    private BackgroundManager backgroundManager;

    public static final int WIDTH = 1920, HEIGHT = 1080;
    public final static Rect screen = new Rect(0, 0, WIDTH, HEIGHT);
    //Elemente
    private RunTimeManager thread;
    private Player player;

    //Score
    private String renderedScoreString;
    private int renderedScore;
    private Paint paint;
    private boolean newGameCreated;


    public BouncingBennoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);

        thread = new RunTimeManager(getHolder(), this);

        setFocusable(true);

        paint = getPaint();
    }

    private Paint getPaint() {
        Paint paint = new Paint();
        AssetManager assetManager = getContext().getAssets();
        Typeface army = Typeface.createFromAsset(assetManager, "fonts/army_rust.ttf");
        paint.setTypeface(army);
        paint.setColor(Color.BLACK);
        paint.setTextSize(30);
        return paint;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        backgroundManager = new BackgroundManager(BitmapFactory.decodeResource(getResources(), R.drawable.background_sky), 100, BitmapFactory.decodeResource(getResources(), R.drawable.background_landscape), 50);
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.helicopter), 65, 25, 3);
        thread.setRunning(true);
        thread.start();

    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
                thread = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!player.getPlaying() && !newGameCreated) {
                newGame();
                player.setPlaying(true);
                player.setUp(true);
            } else {
                player.setUp(true);
            }
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            player.setUp(false);
            return true;
        }

        return super.onTouchEvent(event);
    }


    public void update() {

        if (player.getPlaying()) {
            backgroundManager.update();
            player.update();
        } else {
            newGameCreated = false;
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        final float scaleFactorX = getWidth() / (WIDTH * 1.f);
        final float scaleFactorY = getHeight() / (HEIGHT * 1.f);

        if (canvas != null) {
            final int savedState = canvas.save();

            canvas.scale(scaleFactorX, scaleFactorY);
            backgroundManager.draw(canvas);
            player.draw(canvas);

            int score = player.getScore();

            if (score % 20 == 0) {
                if (score != this.renderedScore || this.renderedScoreString == null) {
                    this.renderedScore = score;
                    this.renderedScoreString = Integer.toString(this.renderedScore);
                }
            }
            canvas.drawText("Score: " + this.renderedScoreString, WIDTH / 2, HEIGHT / 8, paint);

            canvas.restoreToCount(savedState);
        }
    }

    public void newGame() {
        player.resetDY();
        player.resetScore();
        this.renderedScoreString = Integer.toString(player.getScore());
        player.resetStartPosition();
        newGameCreated = true;
    }


}

