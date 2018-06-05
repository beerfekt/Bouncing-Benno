    package net.beerfekt.bouncingbenno;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
    public class Game extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //0 Turn titlebar off
            //Weg 1 : res/values/styles parent property in NOACTIONBAR ändern (Siehe xml comment)

            //Weg2
            // extends Activity wird benötigt um Titlebar auszuschalten
            // this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //1. set to fullscreen:
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //2. create a Gamepanel
        setContentView(new GamePanel(this));
        //setContentView(R.layout.activity_game);

        //3. create a MainThread where the gameloop happens





    }
}
