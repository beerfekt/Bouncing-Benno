package net.beerfekt.myfirstgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;


public class Player extends GameObject{
    private Bitmap spritesheet;  //Helicopter
    private int score;
    private boolean up, playing;
    private Animation animation = new Animation();
    private long startTime;

    //PARAMETERS FOR THE MOVEMENT / POSITION of the Player
    private static final int LIMIT_ACCELERATION  = 8,                                           //höchster/niedrigster Beschleunigungswert
                             //Helicopter Spielraum begrenzen (sodass dieser nicht aus Bild springt)
                             LIMIT_AREA_TOP      = GamePanel.HEIGHT / 10,                         //1/8 der Canvas-Höhe ist abstand nach oben
                             LIMIT_AREA_BOTTOM   = GamePanel.HEIGHT - ( GamePanel.HEIGHT/6),     //1/4 der Canvas-Höhe ist abstand nach unten
                             STARTPOSITION       = LIMIT_AREA_BOTTOM,                            //Startposition Hubschrauber auf y achse
                             SPEED_VERTICAL_UP   = 6,                                            //heli geschwindigkeit aufsteigen
                             SPEED_VERTICAL_DOWN = 2;                                            //heli                 absteigen


    public Player(Bitmap res, int w, int h, int numFrames) {

        x = 100;
        y = STARTPOSITION;  //Startposition des Helicopters auf y Achse
        dy = 0;
        score = 0;
        height = h;
        width = w;

        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;

        //Split the helicopter bitmap in three parts and take the parts for the animation
        for (int i = 0; i < image.length; i++)
        {
            image[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(10);
        startTime = System.nanoTime();

    }

    public void setUp(boolean b){up = b;}

    public void update()
    {
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed>100)
        {
            score++;
            startTime = System.nanoTime();
        }
        animation.update();

        if( up ){
               dy -= SPEED_VERTICAL_UP;
        }
        else {
              dy += SPEED_VERTICAL_DOWN;
        }

        //Acceleration limits
        if( dy >  LIMIT_ACCELERATION ) dy =   LIMIT_ACCELERATION-5;
        if( dy <- LIMIT_ACCELERATION ) dy = - LIMIT_ACCELERATION;

        //increase the height of helicopter
        // add the calculated height of the acceleration
        y += dy * 2;

        //Height limits
        if (y < LIMIT_AREA_TOP ) {
           y = LIMIT_AREA_TOP;
        } else if (y > LIMIT_AREA_BOTTOM) {
            y = LIMIT_AREA_BOTTOM;
        }
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(animation.getImage(),x,y,null);
    }

    public int getScore(){return score;}

    public boolean getPlaying(){return playing;}

    public void setPlaying(boolean b){playing = b;}

    public void resetScore(){score = 0;}

    public void resetDY(){ dy = 0;}

    public void resetStartPosition(){ y = STARTPOSITION;}

}//