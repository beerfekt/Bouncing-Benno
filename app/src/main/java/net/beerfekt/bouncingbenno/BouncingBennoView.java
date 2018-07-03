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
    private Bitmap emy1;
    private Bitmap emy2;
    private Bitmap emy3;
    private Bitmap emy4;
    private Bitmap emy5;
    private Bitmap emy6;
    private Bitmap[] emy = new Bitmap[6];
    private Bitmap flo1;
    private Bitmap flo2;
    private Bitmap flo3;
    private Bitmap flo4;
    private Bitmap[] flo = new Bitmap[4];
    private Bitmap hans1;
    private Bitmap hans2;
    private Bitmap hans3;
    private Bitmap hans4;
    private Bitmap[] hans = new Bitmap[4];
    private Bitmap rio;
    private Bitmap rolph1;
    private Bitmap rolph2;
    private Bitmap rolph3;
    private Bitmap rolph4;
    private Bitmap rolph5;
    private Bitmap rolph6;
    private Bitmap[] rolph = new Bitmap[6];

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
        emy1 = getBitmap(R.drawable.ic_emy_einhorn);
        emy2 = getBitmap(R.drawable.ic_emy_einhorn_2);
        emy3 = getBitmap(R.drawable.ic_emy_einhorn_3);
        emy4 = getBitmap(R.drawable.ic_emy_einhorn_4);
        emy5 = getBitmap(R.drawable.ic_emy_einhorn_5);
        emy6 = getBitmap(R.drawable.ic_emy_einhorn_6);
        emy[0] = emy1; emy[1] = emy2; emy[2] = emy3; emy[3] = emy4; emy[4] = emy5; emy[5] = emy6;

        flo1 = getBitmap(R.drawable.ic_flying_flo2);
        flo2 = getBitmap(R.drawable.ic_flying_flo3);
        flo3 = getBitmap(R.drawable.ic_flying_flo4);
        flo4 = getBitmap(R.drawable.ic_flying_flo5);
        flo[0] = flo1; flo[1] = flo2; flo[2] = flo3; flo[3] = flo4;

        hans1 = getBitmap(R.drawable.ic_hans_horny);
        hans2 = getBitmap(R.drawable.ic_hans_horny_1);
        hans3 = getBitmap(R.drawable.ic_hans_horny_3);
        hans4 = getBitmap(R.drawable.ic_hans_horny_4);
        hans[0] = hans1; hans[1] = hans2; hans[2] = hans3; hans[3] = hans4;

        rio = getBitmap(R.drawable.ic_rio_reisnagel);

        rolph1 = getBitmap(R.drawable.ic_rolph_ruessel);
        rolph2 = getBitmap(R.drawable.ic_rolph_ruessel_2);
        rolph3 = getBitmap(R.drawable.ic_rolph_ruessel_3);
        rolph4 = getBitmap(R.drawable.ic_rolph_ruessel_4);
        rolph5 = getBitmap(R.drawable.ic_rolph_ruessel_5);
        rolph6 = getBitmap(R.drawable.ic_rolph_ruessel_6);
        rolph[0] = rolph1; rolph[1] = rolph2; rolph[2] = rolph3; rolph[3] = rolph4; rolph[4] = rolph5; rolph[5] = rolph6;
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
            //monster.add(emy);
            //monster.add(flo);
            //monster.add(hans);
            monster.add(rio);
            //monster.add(rolph);
            MonsterManager monsterManager = new MonsterManager(monster, emy, flo, hans, rolph);

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

