package net.beerfekt.bouncingbenno.manager;


import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.SurfaceHolder;

import net.beerfekt.bouncingbenno.BouncingBennoView;
import net.beerfekt.bouncingbenno.objekts.AbstractObject;
import net.beerfekt.bouncingbenno.objekts.game.Death;
import net.beerfekt.bouncingbenno.objekts.game.Player;

import java.util.ArrayList;

public class RunTimeManager extends Thread {
    public final static float SCREEN_WIDTH = 1920, SCREEN_HEIGHT = 1080;
    public final static Rect SCREEN_RECT = new Rect(0, 0, (int) SCREEN_WIDTH, (int) SCREEN_HEIGHT);

    private SurfaceHolder surfaceHolder;
    private BouncingBennoView bouncingBennoView;
    private Paint paint;

    //Attributes for method run
    private long msPerFrame = 38;
    private boolean running;
    private int fpsSamples[] = new int[50];
    private int samplePos = 0;
    private int samplesSum = 0;

    //Game Objects
    public BackgroundManager backgroundManager;
    public Player player;
    public MonsterManager monsterManager;
    public Death death;

    //Score
    private int score;
    private long scoreTime = System.currentTimeMillis();

    //Misc
    private boolean gameRunning;
    private boolean dead;


    public RunTimeManager(SurfaceHolder surfaceHolder, BouncingBennoView gamePanel, BackgroundManager backgroundManager, Player player, MonsterManager monsterManager, Death death) {
        this.surfaceHolder = surfaceHolder;
        this.bouncingBennoView = gamePanel;
        this.backgroundManager = backgroundManager;
        this.player = player;
        this.monsterManager = monsterManager;
        this.death = death;
        paint = getPaint(gamePanel);
    }

    @Override
    public void run() {
        Canvas canvas = null;
        long thisFrameTime;
        long lastFrameTime = System.currentTimeMillis();
        float framesSinceLastFrame;

        while (running) {
            try {
                canvas = surfaceHolder.lockCanvas();
                if (canvas == null) continue;
                synchronized (surfaceHolder) {
                    draw(canvas);
                    float fps = 1000.0f / ((float) samplesSum / fpsSamples.length);
                    canvas.drawText(String.format("FPS: %f", fps), 10, 10, new Paint());
                }

                if (gameRunning) {
                    if (dead && death.getAnimation().wasPlayedOnce()) {
                        death.setAnimation(death.getAnimation().copy());
                        monsterManager.removeAllMonster();
                        dead = false;
                        gameRunning = false;
                    } else {
                        thisFrameTime = System.currentTimeMillis();
                        framesSinceLastFrame = (float) (thisFrameTime - lastFrameTime) / msPerFrame;
                        update(framesSinceLastFrame);
                        checkPlayerCollision(monsterManager.getOnScreenMonster());
                        if (!dead) {
                            if (scoreTime + 100 * 1000 > System.currentTimeMillis()) {
                                score++;
                                scoreTime = System.currentTimeMillis();
                            }
                        }
                    }
                }

                thisFrameTime = System.currentTimeMillis();
                int timeDelta = (int) (thisFrameTime - lastFrameTime);

                samplesSum -= fpsSamples[samplePos];
                fpsSamples[samplePos++] = timeDelta;
                samplesSum += timeDelta;
                samplePos %= fpsSamples.length;

                lastFrameTime = thisFrameTime;

                if (timeDelta < msPerFrame)
                    sleep(msPerFrame - timeDelta);

            } catch (InterruptedException e) {

            } finally {
                if (canvas != null)
                    surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void draw(Canvas canvas) {
        float scaleFactorX = bouncingBennoView.getWidth() / (SCREEN_WIDTH * 1.f);
        float scaleFactorY = bouncingBennoView.getHeight() / (SCREEN_HEIGHT * 1.f);
        canvas.scale(scaleFactorX, scaleFactorY);

        canvas.drawColor(Color.WHITE);
        backgroundManager.draw(canvas);
        monsterManager.draw(canvas);
        if (dead) {
            death.draw(canvas);
        } else {
            player.draw(canvas);
        }

        canvas.drawText("Score: " + score, SCREEN_WIDTH / 2, SCREEN_HEIGHT / 8, paint);
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

    public void update(float numberOfFrames) {
        backgroundManager.update(numberOfFrames);
        monsterManager.update(numberOfFrames);
        if (!dead) {
            player.update(numberOfFrames);
        } else {
            death.update(numberOfFrames);
        }
    }

    public void newGame() {
        score = 0;
        player.resetPosition();

        gameRunning = true;
        running = true;
    }

    public void stopGame() {
        running = false;
        try {
            join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean onTouchEvent() {
        if (gameRunning) {
            if (!dead) {
                player.jump();
            }
        } else {
            newGame();
        }
        return true;
    }

    private <T extends AbstractObject> void checkPlayerCollision(ArrayList<T> objects) {
        for (AbstractObject o : objects) {
            if (o.intersect(player)) {
                dead = true;
                break;
            }
        }
    }
}