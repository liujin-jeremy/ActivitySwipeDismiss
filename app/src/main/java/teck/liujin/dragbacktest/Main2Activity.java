package teck.liujin.dragbacktest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import teck.liujin.swipeback.SwipeDismiss;

/**
 * @author liujin
 */
public class Main2Activity extends AppCompatActivity {

      private static final String       TAG = "Main2Activity";
      private              SwipeDismiss mSwipeDismiss;

      public static void start (Context context) {

            Intent starter = new Intent(context, Main2Activity.class);
            context.startActivity(starter);
      }

      @Override
      protected void onCreate (Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main2);

            /* 创建工具 */
            mSwipeDismiss = new SwipeDismiss(this);
            /* 设置触发点,小于该触发点才会侧滑 */
            mSwipeDismiss.setCouldSwipeX(200);
      }

      @Override
      public boolean onTouchEvent (MotionEvent event) {

            /* 传递给工具触摸事件 */
            mSwipeDismiss.onActivityTouchEvent(event);
            return true;
      }
}