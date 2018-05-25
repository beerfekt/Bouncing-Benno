package net.beerfekt.myfirstgame;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    public static final int WIDTH = 856,          // Abmessungen des Screens -> Bildfläche/Zeichenfläche
                            HEIGHT = 480,
                            MOVESPEED = -5,       // Geschwindigkeit des Hintergrundes
                            SMOKE_DELAY = 120,    // Rauchabstände via Verzögerung
                            MISSILES_DELAY = 2000; // Raketenabstände via Verzögerung
    //Elemente
    private MainThread thread;
    private Background background;
    private Player player;

    //Smoke
    private ArrayList<SmokePuff> smoke; //SmokePuffs from the Helicopter - 3 puffs in one class drawn
    private long smokeStartTime;       //Timer for the smoke-timing (to prevent continous streaming out of the helicopter)

    //Missiles
    private ArrayList<Missile> missiles;
    private long   missileStartTime,       //zeitpunkt wenn missile losfliegt -> um zeitliche abstände zwischen missiles zu takten
                   missileElapsed;         //wenn rakete
    private Random random = new Random();  //Generator für Zufallswerte für y koordinate starterposition der missiles


    public GamePanel(Context context)
    {
        super(context);


        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }



    @Override
    public void surfaceCreated(SurfaceHolder holder){

        //creating elements
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.grassbg1));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.helicopter), 65, 25, 3);

        //smoke
        smoke = new ArrayList<SmokePuff>();
        //we want little puffs, not a continous streaming, only a few puffs:
        //this is why we create a smokeStartTimer
        smokeStartTime = System.nanoTime();

        //missiles
        missiles = new ArrayList<Missile>();
        missileStartTime = System.nanoTime();

        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();

    }



    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}


    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        //thread beenden
        boolean retry = true;
        while(retry)
        {
            try{thread.setRunning(false);
                thread.join();
                retry = false;
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }//while
    }//surfaceDestroyed



    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            if(!player.getPlaying())
            {
                player.setPlaying(true);
            } else {
                player.setUp(true);
            }
            return true;
        }
        if( event.getAction() == MotionEvent.ACTION_UP)
        {
            player.setUp(false);
            return true;
        }

        return super.onTouchEvent(event);
    }


    public void update()
    {
        if(player.getPlaying()) {

            background.update();
            player.update();

            //SMOKE  - add smokepuffs on timer------------------------------------

            // timestamp difference between now and smokeStart
            long elapsed = (System.nanoTime() - smokeStartTime)/1000000;

            //Nach 120 ms wird ein neuer Smokepuff erstellt
            if (elapsed > SMOKE_DELAY){
                //add new smokepuff with the coordinates of the player,
                // it will move to left away
                smoke.add(new SmokePuff( player.getX(), player.getY()+10 ));
                //reset the start time
                smokeStartTime = System.nanoTime();
            }


            //this loop go to every smokepuff object and update it

            for (int i = 0; i < smoke.size(); i++ ) {
                //update every smokepuff
                smoke.get(i).update();
                //wenn die Position des SmokePuffs < -10 (da größe 10px) ist, dann wird er entfernt
                // aus dem speicher
                if (smoke.get(i).getX() < -10 ) {
                    smoke.remove(i);
                }
            }//for


            // MISSILES --------------------------------------

            long missilesElapsed = (System.nanoTime() - missileStartTime)/1000000;

            //Verzögerung/Abstände zwischen den Raketen:
            //je höher der score, desto kleiner die abstände (Verzögerung - Score = kleinere Verzögerung = kleinere Abstände)
            if (missilesElapsed >  (MISSILES_DELAY -player.getScore()/4) ) {


                //starthöhe ermitteln

                int missilePositionY;

                if (missiles.size() == 0) {
                   //erste startet in der mitte der bildschirmhöhe
                   missilePositionY = HEIGHT/2;
                } else {
                    //die folgenden über zufällige y positionen
                    missilePositionY =(int) (random.nextDouble()*HEIGHT);
                }

                //Rakete mit einbezug der höhe (oben) erstellen
                missiles.add (

                        new Missile( BitmapFactory.decodeResource(getResources(),R.drawable.missile),
                                     WIDTH+10,
                                     missilePositionY,
                                     45,
                                     15,
                                     player.getScore(),
                                     13 )
                );

               missileStartTime = System.nanoTime(); //Timer resetten
            }//if missilesElapsed


            //loop through every missile and look if its collidet with other
            //gameobject
            for (int i = 0; i < missiles.size(); i++ ){

               Missile currentMissile = missiles.get(i);
               currentMissile.update();
                //check collision between missile, other gameobject passed to the function
               if (collision(currentMissile, player)){
                   //wenn kollidiert, rakete entfernen, player stoppen und spiel loop stoppen
                   missiles.remove(i);
                   player.setPlaying(false);
                   break;
               }
               //wenn rakete rechts aus dem bildbereich verschwindet (-100 pixel nach links)
                //wird sie entfernt
               if (currentMissile.getX() < - 100) {
                   missiles.remove(i);
                   break;
               }
            }

        }//if getPlaying

    }//update


    private boolean collision(GameObject a, GameObject b)
    {
        //Get the boxes of the gameobjects
        Rect collisionBoxA = a.getRectangle();
        Rect collisionBoxB = b.getRectangle();

        //Prüfen ob sie sich berühren
        if (Rect.intersects(collisionBoxA, collisionBoxB)){
            return true;
        }
        return false;
    }//collision


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        final float scaleFactorX = getWidth() / (WIDTH * 1.f);
        final float scaleFactorY = getHeight() / (HEIGHT * 1.f);

        if (canvas != null) {
            final int savedState = canvas.save();

            canvas.scale(scaleFactorX, scaleFactorY);
            background.draw(canvas);
            player.draw(canvas);

            //draw the smokepuffs:
            for (SmokePuff smokePuff : smoke) {
                smokePuff.draw(canvas);
            }


            //draw the missiles
            for (Missile missile : missiles){
               missile.draw(canvas);
            }

            canvas.restoreToCount(savedState);
        }
    }//draw


}//class

