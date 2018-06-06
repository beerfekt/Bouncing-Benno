package net.beerfekt.bouncingbenno;


import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    private int FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
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
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
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
}


/*


// ORIGINALCODE


import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread
{
    private int FPS = 30;                  // Bildrate
    private double averageFPS;             // Tatsächliche Bildrate
    private SurfaceHolder surfaceHolder;   // Oberflächen Container
    private GamePanel gamePanel;           // Spieleoberfläche
    private boolean running;               // Schalter (on/off) für MainThread
    public static Canvas canvas;           // Zeichenfläche

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel)
    {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }
    @Override
    public void run()
    {
        long startTime,             // anfangszeit
             timeMillis,            // zeit für bearbeitung des bildes
             waitTime,              // wartezeit für den thread (= zeit für bearbeitung)
             totalTime = 0;         // gesamtzeit
        int frameCount = 0;         // anzahl der schleifendurchläufe
        long targetTime = 1000/FPS; // bilder/Durchläufe pro 1000 ms /pro 1 s

        while(running) {
            //Timestamp - measurement of time
            startTime = System.nanoTime();
            canvas = null;

            //try locking the canvas for pixel editing
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            } catch (Exception e) {
            }
            finally{
                if(canvas!=null)
                {
                    try {
                        //If editing succeed :
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e){e.printStackTrace();}
                }
            }

            // How long took the pixel editing part? (in Milliseconds)
                  // timestamp now - timestamp bevore pixelediting / 1^6 (= convert to milliseconds
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            //differenz von framerate_zeit - dauer des vorgangs oben
            waitTime = targetTime-timeMillis;

            try{
                //Warte für die zeitspanne der bearbeitung
                this.sleep(waitTime);
            }catch(Exception e){}
            //Zeit die für bearbeitung und warten benötigt wurde
            totalTime += System.nanoTime()-startTime;
            //durchlauf zählen
            frameCount++;
            //wenn durchlaufanzahl == der bildrate
            if(frameCount == FPS)
            {
                //bildrate anhand der zeiten messen
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                //zähler zurücksetzen - reset
                frameCount =0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }
    }
    public void setRunning(boolean b)
    {
        running = b;
    }
}
*/