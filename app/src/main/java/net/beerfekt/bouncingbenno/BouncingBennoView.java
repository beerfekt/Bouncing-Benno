package net.beerfekt.bouncingbenno;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import net.beerfekt.bouncingbenno.manager.BackgroundManager;
import net.beerfekt.bouncingbenno.manager.MonsterManager;
import net.beerfekt.bouncingbenno.manager.RunTimeManager;
import net.beerfekt.bouncingbenno.objekts.game.Player;

import java.util.ArrayList;


public class BouncingBennoView extends SurfaceView implements SurfaceHolder.Callback {
    private RunTimeManager runTimeManager;

    //BackgroundManager
    private Bitmap background_sky;
    private Bitmap background_landscape1;
    private Bitmap background_landscape2;
    private Bitmap background_street;
    private Bitmap background_baum;
    private Bitmap background_haus1;
    private Bitmap background_haus2;
    private Bitmap overlay;

    //Player
    private Bitmap benno;

    //MonsterManager
    private Bitmap emy;
    private Bitmap flo;

    public BouncingBennoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        setFocusable(true);

        //BackgroundManager
        background_sky = getBitmap(R.drawable.ic_himmel);
        background_landscape1 = getBitmap(R.drawable.ic_berge1);
        background_landscape2 = getBitmap(R.drawable.ic_berge2);
        background_street = getBitmap(R.drawable.ic_strasse);
        background_baum = getBitmap(R.drawable.ic_baum);
        background_haus1 = getBitmap(R.drawable.ic_haus);
        background_haus2 = getBitmap(R.drawable.ic_haus2);
        overlay = BitmapFactory.decodeResource(context.getResources(), R.drawable.texture_paper_opacity_50);

        //Player
        benno = getBitmap(R.drawable.ic_benno);

        //MonsterManager
        emy = getBitmap(R.drawable.ic_emy_einhorn);
        flo = getBitmap(R.drawable.ic_flying_flo2);
    }

    private Bitmap getBitmap(int drawableRes) {
        Drawable drawable = getResources().getDrawable(drawableRes);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        synchronized (this) {
            //BackgroundManager
            ArrayList<Bitmap> backgroundObjects = new ArrayList<>();
            backgroundObjects.add(background_baum);
            backgroundObjects.add(background_haus1);
            backgroundObjects.add(background_haus2);
            BackgroundManager backgroundManager = new BackgroundManager(overlay ,background_sky, background_landscape1, background_landscape2, background_street, backgroundObjects);

            //MonsterManager
            ArrayList<Bitmap> monster = new ArrayList<>();
            monster.add(emy);
            monster.add(flo);
            MonsterManager monsterManager = new MonsterManager(monster);

            //Player
            Player player = new Player(benno, 141.25f,127.5f, benno);

            runTimeManager = new RunTimeManager(holder, this, backgroundManager, player, monsterManager);
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
            return runTimeManager.onTouchEvent();
        }
        return false;
    }
}

