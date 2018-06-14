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
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import net.beerfekt.bouncingbenno.manager.RunTimeManager;
import net.beerfekt.bouncingbenno.objekts.AbstractObject;
import net.beerfekt.bouncingbenno.objekts.game.BorderBottomPart;
import net.beerfekt.bouncingbenno.objekts.game.Missile;
import net.beerfekt.bouncingbenno.objekts.game.Player;
import net.beerfekt.bouncingbenno.objekts.game.SmokePuff;

import java.util.ArrayList;
import java.util.Random;


public class BouncingBennoView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder surfaceHolder;
    private RunTimeManager gameLoop;

    public static final int WIDTH = 1920,          // Abmessungen des Screens -> Bildfläche/Zeichenfläche
            HEIGHT = 1080,
            MOVESPEED = -5,       // Geschwindigkeit des Hintergrundes als Schritt
            SMOKE_DELAY = 120,    // Rauchabstände via Verzögerung
            MISSILES_DELAY = 2000; // Raketenabstände via Verzögerung


    //Elemente
    private RunTimeManager thread;
    private Background background;
    private Player player;

    //Smoke
    private ArrayList<SmokePuff> smoke; //SmokePuffs from the Helicopter - 3 puffs in one class drawn
    private long smokeStartTime;       //Timer for the smoke-timing (to prevent continous streaming out of the helicopter)

    //Missiles
    private ArrayList<Missile> missiles;
    private long missileStartTime,       //zeitpunkt wenn missile losfliegt -> um zeitliche abstände zwischen missiles zu takten
            missileElapsed;         //wenn rakete
    private Random random = new Random();  //Generator für Zufallswerte für y koordinate starterposition der missiles

    //Score
    private String renderedScoreString;
    private int renderedScore;
    private Paint paint;


    //Border Bottom ( aka Ground)

    private ArrayList<BorderBottomPart> bottomBorder;

    // decrease to speed up difficulty progression
    //we wanna know if border is going up or down
    //when they reach the max of borderheight,
    //they'll move to the other direction
    private boolean topDown = true,             //when the borders moving down = true
            botDown = true;             //                        up   = true

    private boolean newGameCreated;


    public BouncingBennoView(Context context, AttributeSet attrs) {
        super(context, attrs);


        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        thread = new RunTimeManager(getHolder(), this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);


        //TODO: FALLS DAS funktionieren sollte, code so legen dass er nur einmal ausgeführt wird
        //Style "malerei" erstellen
        paint = new Paint();

        AssetManager assetManager = getContext().getAssets();
        Typeface army = Typeface.createFromAsset(assetManager, "fonts/army_rust.ttf");

        //Typeface army = Typeface.createFromAsset(context.getAssets(),"fonts/army_rust.ttf" );
        //Typeface army = Typeface.create(plain, Typeface.DEFAULT_BOLD);
        paint.setTypeface(army);
        //Farbe
        paint.setColor(Color.BLACK);
        paint.setTextSize(30);


    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        //creating elements
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.grassbg1));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.helicopter), 65, 25, 3);

        //borders
        //TODO: Liste darf nicht wachsen, tut sie aber trotz remove
        bottomBorder = new ArrayList<BorderBottomPart>();

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
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //thread beenden
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                //In this example we have used the join() method in such a way that our threads execute in the specified order.
                thread.join();
                retry = false;  //stop the thread
                thread = null; //important - needed to allow the garbage collector to delete the object
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }//while
    }//surfaceDestroyed


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //TODO: Newgame versuch, hier newgame auf onTouch -> && !newgGameCreated als bedingung hier rein
            if (!player.getPlaying() && !newGameCreated) {
                //TODO: NewGame versuch, newGame(); auf onTouch()
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


            background.update();

            checkForCollision(bottomBorder);

            player.update();
            //TODO: SPeicher läuft voll, evt. borderbottom überprüfen, und generell überprüfen ob alte elemente aufgeräumt werden
            this.updateBottomBorder();
            this.updateSmoke();
            this.updateMissiles();

        } else {
            newGameCreated = false;
        } //if getPlaying

    }//update


    private void updateSmoke() {
        //TODO SMOKE ALS EIGENE METHODE
        //SMOKE ------------------------------------------------------------

        // timestamp difference between now and smokeStart
        long elapsed = (System.nanoTime() - smokeStartTime) / 1000000;

        //Nach 120 ms wird ein neuer Smokepuff erstellt
        if (elapsed > SMOKE_DELAY) {
            //add new smokepuff with the coordinates of the player,
            // it will move to left away
            smoke.add(new SmokePuff(player.getX(), player.getY() + 10));
            //reset the start time
            smokeStartTime = System.nanoTime();
        }


        //this loop go to every smokepuff object and update it

        for (int i = 0; i < smoke.size(); i++) {
            //update every smokepuff
            smoke.get(i).update();
            //wenn die Position des SmokePuffs < -10 (da größe 10px) ist, dann wird er entfernt
            // aus dem speicher
            if (smoke.get(i).getX() < -10) {
                smoke.remove(i);
            }
        }//for
    }


    private void updateMissiles() {

        //Log.d("MISSILES","ANZAHL ELEMENTE: --------" + Integer.toString(missiles.size()) + "----------" );

        long missilesElapsed = (System.nanoTime() - missileStartTime) / 1000000;

        //Verzögerung/Abstände zwischen den Raketen:
        //je höher der score, desto kleiner die abstände (Verzögerung - Score = kleinere Verzögerung = kleinere Abstände)
        if (missilesElapsed > (MISSILES_DELAY - player.getScore() /*vorher /4*/)) {


            //starthöhe ermitteln


            //die folgenden über zufällige y positionen
            int missilePositionY = (int) (random.nextDouble() * HEIGHT);

            if (missiles.size() == 0) {
                //erste startet in der mitte der bildschirmhöhe
                missilePositionY = HEIGHT / 2;
            }


            //Rakete mit einbezug der höhe (oben) erstellen
            missiles.add(

                    new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.missile),
                            WIDTH + 10,
                            missilePositionY,
                            45,
                            15,
                            player.getScore(),
                            13)
            );

            missileStartTime = System.nanoTime(); //Timer resetten
        }//if missilesElapsed


        //loop through every missile and look if its collidet with other
        //gameobject
        for (int i = 0; i < missiles.size(); i++) {

            Missile currentMissile = missiles.get(i);
            currentMissile.update();
            //check collision between missile, other gameobject passed to the function
            if (collision(currentMissile, player)) {
                //wenn kollidiert, rakete entfernen, player stoppen und spiel loop stoppen
                missiles.remove(i);
                player.setPlaying(false);
                Log.d("COLLISION", " -> MISSILE ---- SET PLAYING FALSE");
                break;
            }
            //wenn rakete rechts aus dem bildbereich verschwindet (-100 pixel nach links)
            //wird sie entfernt
            if (currentMissile.getX() < -100) {
                missiles.remove(i);
                break;
            }
        }//for
    }


    public void updateBottomBorder() {

        for (int i = 0; i < bottomBorder.size(); i++) {

            BorderBottomPart currentPart = bottomBorder.get(i);
            //bewege die steine
            currentPart.update();

            //entferne steine die den bildbereich um die länge eines steines links verlassen
            if (currentPart.getX() < -20) {
                bottomBorder.remove(i);
                //Log.d("BORDER-BOTTOM","BORDER-BOTTOM   element removed:" + i + " \n" + "NEUE ANZAHL ELEMENTE: --------" + Integer.toString(bottomBorder.size()) + "----------" );
                //if ( bottomBorder.remove(i) )
                addBorderPart();
            }//if
        }// for
    }//updateBottomBorder


    //append Brick to the Border
    private void addBorderPart() {
        int xPosition = WIDTH,
                yPosition = HEIGHT - 80;


        // wenn bereits mauer vorhanden:
        if (bottomBorder.size() > 0) {
            //Create next element
            BorderBottomPart lastElement = bottomBorder.get(bottomBorder.size() - 1);
            //place it next to the last element created
            xPosition = lastElement.getX() + 20;
            //random-height
            yPosition = (int) (4 * HEIGHT / 5 + lastElement.getHeight() / 4 * random.nextDouble());
        }//if


        //create element and append it to the border
        bottomBorder.add(
                new BorderBottomPart(
                        BitmapFactory.decodeResource(getResources(), R.drawable.brick),
                        xPosition,
                        yPosition)
        );
    }

    private boolean collision(AbstractObject a, AbstractObject b) {
        //Get the boxes of the gameobjects
        Rect collisionBoxA = a.getRectangle();
        Rect collisionBoxB = b.getRectangle();

        //Prüfen ob sie sich berühren
        if (Rect.intersects(collisionBoxA, collisionBoxB)) {
            return true;
        }
        return false;
    }//collision

    private <T extends AbstractObject> void checkForCollision(ArrayList<T> objects) {
        for (int i = 0; i < objects.size(); i++) {
            if (collision(objects.get(i), player)) {
                player.setPlaying(false);
                break;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);


        //Maße für Gesamtbildfläche holen:
        final float scaleFactorX = getWidth() / (WIDTH * 1.f);
        final float scaleFactorY = getHeight() / (HEIGHT * 1.f);

        if (canvas != null) {
            final int savedState = canvas.save();

            //Zeichenfläche auf Bildschirmgröße skalieren
            canvas.scale(scaleFactorX, scaleFactorY);
            //Hintergrund + Spielfigur zeichnen
            background.draw(canvas);
            player.draw(canvas);

            //draw the smokepuffs:
            for (SmokePuff smokePuff : smoke) {
                smokePuff.draw(canvas);
            }

            //draw the missiles
            for (Missile missile : missiles) {
                missile.draw(canvas);
            }

            //draw the bottom
            for (BorderBottomPart part : bottomBorder) {
                part.draw(canvas);
            }

            //draw the Score
            int score = player.getScore();

            //Nur alle 20 punkte score setzen
            if (score % 20 == 0) {
                //Score so setzen sodass nicht permanent neue String Objekte erzeugt werden
                if (score != this.renderedScore || this.renderedScoreString == null) {
                    this.renderedScore = score;
                    this.renderedScoreString = Integer.toString(this.renderedScore);
                }
            }

            //Score reinmalen
            //Text + Stil auf Zeichenfläche malen
            canvas.drawText("Score: " + this.renderedScoreString, WIDTH / 2, HEIGHT / 8, paint);

            canvas.restoreToCount(savedState);
        }
    }//draw

    public void newGame() {
        bottomBorder.clear();
        missiles.clear();
        smoke.clear();

        //TODO: resetDY() , wird das benötigt??
        player.resetDY();
        player.resetScore();
        this.renderedScoreString = Integer.toString(player.getScore());
        player.resetStartPosition();

        //Initialise the First border which is updated later
        for (int i = 0; (i * 20) < (WIDTH + 40); i++) {
            addBorderPart();
        }

        newGameCreated = true;
    }


}//class

