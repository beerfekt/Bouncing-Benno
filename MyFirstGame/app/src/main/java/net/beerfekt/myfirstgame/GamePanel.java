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
                            MOVESPEED = -5,       // Geschwindigkeit des Hintergrundes als Schritt
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





    //Border Bottom ( aka Ground)

    private ArrayList<BorderBottomPart> bottomBorder, testBottomBorder;

    //with more difficult, the max height increases in the game
    private int maxBorderHeight,                // Limit the Height when the Borders are generated
            minBorderHeight,
            progressDemon = 20;             // increase to slow down difficulty progression,
    // decrease to speed up difficulty progression
    //we wanna know if border is going up or down
    //when they reach the max of borderheight,
    //they'll move to the other direction
    private boolean topDown = true,             //when the borders moving down = true
            botDown = true;             //                        up   = true

    private boolean newGameCreated;



    public GamePanel(Context context)    {
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

        //borders
        bottomBorder = new ArrayList<BorderBottomPart>();
        //TODO: TEST1 borders
        testBottomBorder = new ArrayList<BorderBottomPart>();

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
                //In this example we have used the join() method in such a way that our threads execute in the specified order.
                thread.join();
                retry = false;  //stop the thread
                thread = null ; //important - needed to allow the garbage collector to delete the object
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }//while
    }//surfaceDestroyed



    @Override
    public boolean onTouchEvent(MotionEvent event)    {
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


    public void update()    {
        if(player.getPlaying()) {

            background.update();
            player.update();


            //TODO: Border logic
            /*
            //BORDERS ----------------------------------------------------------


            //borderheight logic -> min and max
            //calculate the threshold of height the border can have based on the score
            //max and min border heart are updated and the border switched direction when either max
            //or min is met

            maxBorderHeight = 30 + player.getScore()/progressDemon;

            //cap max border height so that borders can only take up 1/2 of the screen height
            if (maxBorderHeight > HEIGHT/4) maxBorderHeight = HEIGHT/4;
            minBorderHeight = 5 + player.getScore()/progressDemon;


             //bottom border
            this.updateBottomBorder();

*/

            //TODO: Update Methode welche Boden generiert

            this.updateBottomBorder();


            //SMOKE ------------------------------------------------------------

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
            }//for

        } else {
            //Wenn Collision passierte, dann erzeuge neues spiel (z.b. bei if(collision() = true ;
            if (!newGameCreated) newGame();
        } //if getPlaying

    }//update


    public void updateBottomBorder() {

        //pro frame /s :

        // wenn bereits mauer vorhanden:
        if (testBottomBorder.size() > 0){

            //Create next element
            BorderBottomPart  lastElement = testBottomBorder.get(testBottomBorder.size() - 1);

            testBottomBorder.add(
                    new BorderBottomPart(
                            BitmapFactory.decodeResource(getResources(), R.drawable.brick),
                            lastElement.getX() + 20,
                            400)
            );
        } else   {
            //create first element
            testBottomBorder.add(
                    new BorderBottomPart(
                            BitmapFactory.decodeResource(getResources(), R.drawable.brick),
                            WIDTH,
                            400)
            );

        }



        for(int i = 0; i < testBottomBorder.size(); i++ ) {
            //bewege die steine
            testBottomBorder.get(i).update();
            //Border aus dem Bildbereich links entfernen
            //entferne die steine die den bildbereich links um die eigene länge verlassen
            if(testBottomBorder.get(i).getX()<-20) {
                testBottomBorder.remove(i);
            }
            //testBottomBorder.get(i).draw(canvas);
        }

    }


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

            //TODO: BorderBottom drawing doesnt work?
/*
Original - geht nicht
            //draw botborder
            for(BorderBottomPart bb: bottomBorder)
            {
                bb.draw(canvas);
            }
*/



/*

            //pro frame /s :

            // wenn bereits mauer vorhanden:
            if (testBottomBorder.size() > 0){

                //Create next element
                BorderBottomPart  lastElement = testBottomBorder.get(testBottomBorder.size() - 1);

                testBottomBorder.add(
                        new BorderBottomPart(
                                BitmapFactory.decodeResource(getResources(), R.drawable.brick),
                                lastElement.getX() + 20,
                                400)
                );
            } else   {
                //create first element
                testBottomBorder.add(
                        new BorderBottomPart(
                                BitmapFactory.decodeResource(getResources(), R.drawable.brick),
                                WIDTH,
                                400)
                );

            }



            for(int i = 0; i < testBottomBorder.size(); i++ ) {
                //bewege die steine
                testBottomBorder.get(i).update();
                //Border aus dem Bildbereich links entfernen
                //entferne die steine die den bildbereich links um die eigene länge verlassen
                if(testBottomBorder.get(i).getX()<-20) {
                    testBottomBorder.remove(i);
                }
                //testBottomBorder.get(i).draw(canvas);
            }
*/
            for (BorderBottomPart part : testBottomBorder) part.draw(canvas);


            canvas.restoreToCount(savedState);
        }
    }//draw




    public void newGame()
    {
        bottomBorder.clear();
        missiles.clear();
        smoke.clear();

        minBorderHeight = 5;
        maxBorderHeight = 30;

        //TODO: resetDY();
        //player.resetDY();
        player.resetScore();
        player.setY(HEIGHT/2);

        //create initial borders


        //initial bottom border
        for(int i = 0; i*20<WIDTH+40; i++)
        {
            //first border ever created
            if(i==0)
            {
                bottomBorder.add(new BorderBottomPart(BitmapFactory.decodeResource(getResources(),R.drawable.brick)
                        ,i*20,HEIGHT - minBorderHeight));
            }
            //adding borders until the initial screen is filed
            else
            {
                bottomBorder.add(new BorderBottomPart(BitmapFactory.decodeResource(getResources(), R.drawable.brick),
                        i * 20, bottomBorder.get(i - 1).getY() - 1));
            }
        }

        newGameCreated = true;
    }




}//class

