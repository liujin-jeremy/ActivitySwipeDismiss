package teck.threekilogram.dragbacktest;

import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.annotation.NonNull;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import teck.threekilogram.swipeback.SwipeDismiss;

/**
 * @author liujin
 */
public class Main2Activity extends AppCompatActivity {

      private static final String TAG = "Main2Activity";
      private SwipeDismiss mSwipeDismiss;

      public static void start (Context context) {

            Intent starter = new Intent(context, Main2Activity.class);
            context.startActivity(starter);
      }

      @Override
      protected void onCreate (Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main2);

            mSwipeDismiss = new SwipeDismiss(this);
      }

      @Override
      public boolean onTouchEvent (MotionEvent event) {

            mSwipeDismiss.onActivityTouchEvent(event);
            return true;
      }
}