package teck.threekilogram.swipeback;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import java.lang.ref.WeakReference;

/**
 * @author: Liujin
 * @version: V1.0
 * @date: 2018-07-19
 * @time: 12:29
 */
public class SwipeDismiss {

      private static final String TAG = "SwipeDismiss";

      private ViewGroup           mAllContent;
      private GestureDetector     mGestureDetector;
      private SwipeFinishListener mSwipeFinishListener;

      public SwipeDismiss (Activity activity) {

            Window window = activity.getWindow();
            View contentView = window.findViewById(Window.ID_ANDROID_CONTENT);
            ViewParent parent = contentView.getParent();
            if(parent != null) {
                  mAllContent = (ViewGroup) parent;
            }

            mSwipeFinishListener = new SwipeFinishListener(activity);
            mGestureDetector = new GestureDetector(activity, new GestureListener());
      }

      /**
       * 移动content
       *
       * @param distance 按照距离移动content
       */
      private void moveAllContent (float distance) {

            if(mAllContent != null) {
                  float x = mAllContent.getX() + distance;
                  if(x < 0) {
                        x = 0;
                  }
                  mAllContent.setX(x);
            }
      }

      public void onActivityTouchEvent (MotionEvent event) {

            mGestureDetector.onTouchEvent(event);

            if(event.getAction() == MotionEvent.ACTION_UP && mAllContent != null) {

                  float x = mAllContent.getX();
                  int width = mAllContent.getWidth();

                  if(x < width / 4) {

                        mAllContent.animate().x(0).setListener(mSwipeFinishListener).start();
                  } else {

                        mAllContent.animate().x(width).setListener(mSwipeFinishListener).start();
                  }
            }
      }

      /**
       * 手势检测辅助类
       */
      private class GestureListener implements OnGestureListener {

            @Override
            public boolean onDown (MotionEvent e) {

                  return false;
            }

            @Override
            public void onShowPress (MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp (MotionEvent e) {

                  return false;
            }

            @Override
            public boolean onScroll (
                MotionEvent e1, MotionEvent e2,
                float distanceX, float distanceY) {

                  if(Math.abs(distanceX) > Math.abs(distanceY)) {
                        moveAllContent(-distanceX);
                        return true;
                  }

                  return false;
            }

            @Override
            public void onLongPress (MotionEvent e) {

            }

            @Override
            public boolean onFling (
                MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                  return false;
            }
      }

      /**
       * 监听动画结束
       */
      private class SwipeFinishListener implements AnimatorListener {

            private WeakReference<Activity> mReference;

            SwipeFinishListener (Activity activity) {

                  mReference = new WeakReference<>(activity);
            }

            @Override
            public void onAnimationStart (Animator animation) {

            }

            @Override
            public void onAnimationEnd (Animator animation) {

                  if(mAllContent != null) {

                        float x = mAllContent.getX();
                        if(x != 0) {

                              Activity activity = mReference.get();
                              if(activity != null) {
                                    activity.finish();
                                    activity.overridePendingTransition(0, 0);
                              }
                        }
                  }
            }

            @Override
            public void onAnimationCancel (Animator animation) {

            }

            @Override
            public void onAnimationRepeat (Animator animation) {

            }
      }

      /**
       * 阴影类
       */
      private class ShadowView extends View {

            private Drawable mDrawable;

            public ShadowView (Context context) {

                  super(context);
                  int[] colors = new int[]{0x00000000, 0x17000000, 0x43000000};
                  mDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
                  setBackground(mDrawable);
            }
      }
}
