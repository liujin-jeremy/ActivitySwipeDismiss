package teck.liujin.dragbacktest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

      public static void start ( Context context ) {

            Intent starter = new Intent( context, MainActivity.class );
            context.startActivity( starter );
      }

      @Override
      protected void onCreate ( Bundle savedInstanceState ) {

            super.onCreate( savedInstanceState );
            setContentView( R.layout.activity_main );
      }

      public void skipToMain2 ( View view ) {

            Main2Activity.start( this );
      }
}
