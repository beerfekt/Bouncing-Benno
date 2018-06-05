package net.beerfekt.myfirstgame;


//Alle Objekte im Spiel erben diese Klasse!

import android.graphics.Rect;

public abstract class GameObject {


    /*

    //Erklärung zu den Attributen

    For anyone who is wondering what some of the variables in this code do,
     here is a kind of detailed explanation: x and y are used to define the location of the object
      (in this case, the helicopter).
      The values given in the Player constructor (x = 100; y = GamePanel.HEIGHT/2) are used when the constructor is called. Thus, those are the starting coordinates of the helicopter.
       NOTE: for x, the 0 value is the left side of the screen; for y, the 0 value is the top of the screen.
        So a smaller x value will make the helicopter start further to the left and a smaller y value will make it start
         higher on the screen. The x value for the helicopter is never updated, which is why it never moves left or right. The y value is updated in the Player.update() method (y += dy*2), which causes it to move up or down based on the dy value.
          The dy and dx values are used for accelerations.
          Again, the x value stays constant, so dx (the horizontal acceleration) is not used for the helicopter
           (I haven't watched too far ahead, but I imagine it will be used for the missiles since they
           will be moving horizontally across the screen). dy, the vertical acceleration is used to determine
           how fast the helicopter moves up or down. The number added or subtracted from dy determines how quickly
            the helicopter rises or falls. Since y=0 is at the top, subtracting from dy makes it accelerate up and adding
             to dy makes it accelerate down. If you check GameObject.java, you will see that these values are defined
              as int, or integer. Thus they can only take integer input (...-2, -1, 0, 1, 2, ...).
               In other words, putting something after the decimal actually doesn't change anything here,
               which is why he went back and changed it from 1.1 to 1. If something is defined as int and a value
                is put after the decimal, it will round down to the nearest integer (e.g. 1.9 would round down to 1).
                 If you want to take advantage of decimals, define your variables as double. Finally,
                 he capped the acceleration at -14, 14 to make sure that the helicopter does not move too fast in one direction or the other.
                  In essence, this makes it more controllable than if it had no limit. If you think it is moving too fast, simply make those numbers closer to 0.
                  If you want it to move fast, move them farther from 0. TL;DR x - object's horizontal position y - object's vertical position dx - object's horizontal acceleration dy
     - object's vertical acceleration Limit the acceleration so the helicopter doesn't move too fast.﻿
    * */


    protected int x,
                  y,   // x y location of object
                  dx,
                  dy,  //bewegungen
                  width,
                  height;  //dimensionen vorraussetzung für kollisionen



    //getters and setters

    //Rahmen für Objekt (für Kollisionen etc.)
    public Rect getRectangle(){
        return new Rect(x,y,x+width,y+height);
    }






    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


}
