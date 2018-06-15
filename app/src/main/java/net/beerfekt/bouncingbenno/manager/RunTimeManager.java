package net.beerfekt.bouncingbenno.manager;


import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import net.beerfekt.bouncingbenno.BouncingBennoView;
import net.beerfekt.bouncingbenno.R;
import net.beerfekt.bouncingbenno.objekts.game.Player;

public class RunTimeManager extends Thread implements SurfaceHolder.Callback{
    private int FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private BouncingBennoView bouncingBennoView;
    private boolean running;
    public static Canvas canvas;


    private BackgroundManager backgroundManager;
    private Player player;

    private String renderedScoreString;
    private int renderedScore;
    private Paint paint;
    private boolean newGameCreated;

    public RunTimeManager(SurfaceHolder surfaceHolder, BouncingBennoView gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.bouncingBennoView = gamePanel;
        paint = getPaint(gamePanel);
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000 / FPS;

        while (running) {
            startTime = System.nanoTime();
            canvas = null;

            //try locking the canvas for pixel editing
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    update();
                    this.bouncingBennoView.draw(canvas);
                }
            } catch (Exception e) {
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            try {
                this.sleep(waitTime);
            } catch (Exception e) {
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == FPS) {
                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }
    }

    public void setRunning(boolean b) {
        running = b;
    }

    public void draw(Canvas canvas) {
        final float scaleFactorX = bouncingBennoView.getWidth() / (BouncingBennoView.SCREEN_WIDTH * 1.f);
        final float scaleFactorY = bouncingBennoView.getHeight() / (BouncingBennoView.SCREEN_HEIGHT * 1.f);

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
            canvas.drawText("Score: " + this.renderedScoreString, BouncingBennoView.SCREEN_WIDTH / 2, BouncingBennoView.SCREEN_HEIGHT / 8, paint);

            canvas.restoreToCount(savedState);
        }
    }

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

        return false;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                this.setRunning(false);
                this.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        backgroundManager = new BackgroundManager(BitmapFactory.decodeResource(bouncingBennoView.getResources(), R.drawable.background_sky), 100, BitmapFactory.decodeResource(bouncingBennoView.getResources(), R.drawable.background_landscape), 50);
        player = new Player(BitmapFactory.decodeResource(bouncingBennoView.getResources(), R.drawable.helicopter), 65, 25, 3);
        this.setRunning(true);
        this.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    private Paint getPaint(BouncingBennoView view) {
        Paint paint = new Paint();
        AssetManager assetManager = view.getContext().getAssets();
        Typeface army = Typeface.createFromAsset(assetManager, "fonts/army_rust.ttf");
        paint.setTypeface(army);
        paint.setColor(Color.BLACK);
        paint.setTextSize(30);
        return paint;
    }

    public void update() {

        if (player.getPlaying()) {
            backgroundManager.update();
            player.update();
        } else {
            newGameCreated = false;
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