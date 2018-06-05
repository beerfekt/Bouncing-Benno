package net.beerfekt.bouncingbenno.objekts;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import net.beerfekt.bouncingbenno.objekts.properties.Animation;
import net.beerfekt.bouncingbenno.objekts.properties.KillBox;

import java.util.Random;


public class Missile extends AbstractObject implements KillBox{

    private int score;  // wird mit geschwindigkeit verrechnet zwecks schwierigkeitsgrad
    private int speed;
    private Random rand = new Random();
    private Animation animation;
    private Bitmap spritesheet;  //BIld welches die ganze sequenz beinhaltet und zerstückelt wird in einzelbilder


    private static final int MAX_SPEED = 40;

    //Konstruktor

    public Missile(Bitmap res, int x, int y, int w, int h, int s, int numFrames)
    {
        super(x,y, 0,0,w,h);

        //wenn der score höher wird, wird er mit der geschwindigkeit verrechnet bei höherem schwierigkeitsgrad
        score = s;

        //geschwindigkeit (Zufallszahl * score / 30);  //man kann es auch anders konfigurieren - je nach geschmack
        speed = 7 + (int) (rand.nextDouble()*score/30);

        //Obergrenze für höchstgeschwindigkeit
        if( speed > MAX_SPEED )speed = MAX_SPEED;


        //Missile Animation erstellen aus Einzelbildern (anzahl is numFrames) ------------------------


        Bitmap[] image = new Bitmap[numFrames];

        //Bild ( zum zerstückeln in Einzelbilder ) reinladen
        spritesheet = res;

        // Bild zerstückeln in Einzelbilder und diese an Bitmap[] array anhängen
        for(int i = 0; i < image.length; i++)
        {                                                     //nimmt nächstes bild nach unten höhe *2  y wert = nächster bildausschnitt)
            image[i] = Bitmap.createBitmap(spritesheet, 0, i*getHeight(), getWidth(), getHeight());
        }

        animation = new Animation(image, 100-speed*1000);


        //--------------------------------------------------------------------
    }


    public void update()
    {
        setX(getX()- speed);
        animation.update();
    }

    public void draw(Canvas canvas)
    {
        try {
            canvas.drawBitmap(animation.getImage(),getX(),getY(),null);
        } catch(Exception e){}
    }

    @Override
    public int getWidth()
    {
        //offset slightly for more realistic collision detection
        //Rakete dringt vor explosion um 10px ein
        return getWidth()-10;
    }

}