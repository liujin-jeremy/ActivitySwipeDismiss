package teck.liujin.swipeback;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.app.Activity;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.Window;
import android.widget.FrameLayout;
import java.lang.ref.WeakReference;

/**
 * 该工具用于{@link Activity}侧滑关闭,不必继承Activity,
 *
 * @author: Liujin
 * @version: V1.0
 * @date: 2018-07-19
 * @time: 12:29
 */
public class SwipeDismiss {

      /**
       * activity父布局
       */
      private ViewGroup           mAllContent;
      /**
       * 控制手势
       */
      private GestureDetector     mGestureDetector;
      /**
       * 识别手势
       */
      private GestureListener     mGestureListener;
      /**
       * 监听抬起后,动画完毕
       */
      private SwipeFinishListener mSwipeFinishListener;
      /**
       * 阴影大小
       */
      private int                 mShadowViewWidth = 80;
      /**
       * 添加阴影
       */
      private ShadowView          mShadowView;

      /**
       * @param activity 为一个Activity创建,侧滑关闭
       */
      public SwipeDismiss ( Activity activity ) {

            /* find out content view container */
            Window window = activity.getWindow();
            final View contentView = window.findViewById( Window.ID_ANDROID_CONTENT );

            /* find out actionBar and contentView container */
            ViewParent parent = contentView.getParent();

            if( parent != null ) {
                  mAllContent = (ViewGroup) parent;

                  /* find out first frameLayout which contains all content, then add a shadowView */
                  ViewParent contentParent = mAllContent.getParent();
                  while( contentParent != null ) {
                        if( contentParent instanceof FrameLayout ) {

                              mShadowView = new ShadowView( activity );
                              ( (ViewGroup) contentParent ).addView(
                                  mShadowView,
                                  new LayoutParams(
                                      mShadowViewWidth,
                                      LayoutParams.MATCH_PARENT
                                  )
                              );
                              mShadowView.setX( -mShadowViewWidth );
                              mShadowView.setY( 0 );
                              break;
                        }

                        contentParent = contentParent.getParent();
                  }
            }

            mSwipeFinishListener = new SwipeFinishListener( activity );
            mGestureListener = new GestureListener();
            mGestureDetector = new GestureDetector( activity, mGestureListener );
      }

      /**
       * @param x 只有在屏幕边缘按下时小于该值才会触发侧滑关闭
       */
      public void setCouldSwipeX ( float x ) {

            if( mGestureListener != null ) {
                  mGestureListener.mStartSwipeX = x;
            }
      }

      /**
       * 移动content
       *
       * @param distance 按照距离移动content
       */
      private void moveAllContent ( float distance ) {

            if( mAllContent != null ) {
                  float startX = mAllContent.getX();
                  float x = startX + distance;
                  if( x < 0 ) {
                        x = 0;
                  }
                  mAllContent.setX( x );

                  if( mShadowView != null ) {

                        float moved = x - startX;
                        mShadowView.setX( mShadowView.getX() + moved );
                  }
            }
      }

      /**
       * @param event 接管activity触摸事件
       */
      public void onActivityTouchEvent ( MotionEvent event ) {

            mGestureDetector.onTouchEvent( event );

            if( event.getAction() == MotionEvent.ACTION_UP && mAllContent != null ) {

                  mGestureListener.onUp();
            }
      }

      /**
       * 手势检测辅助类
       */
      private class GestureListener implements OnGestureListener {

            private boolean isToMove;
            private float   mStartSwipeX = 100;

            @Override
            public boolean onDown ( MotionEvent e ) {

                  isToMove = e.getX() <= mStartSwipeX;
                  return true;
            }

            private void onUp ( ) {

                  /* 手指抬起后如何处理activity */

                  float x = mAllContent.getX();
                  int width = mAllContent.getWidth();

                  if( x < width / 4 ) {

                        mAllContent.animate()
                                   .x( 0 )
                                   .setListener( mSwipeFinishListener )
                                   .start();
                        if( mShadowView != null ) {
                              mShadowView.animate()
                                         .x( -mShadowViewWidth )
                                         .start();
                        }
                  } else {

                        mAllContent.animate()
                                   .x( width )
                                   .setListener( mSwipeFinishListener )
                                   .start();
                        if( mShadowView != null ) {
                              mShadowView.animate()
                                         .x( width )
                                         .start();
                        }
                  }
            }

            @Override
            public void onShowPress ( MotionEvent e ) { }

            @Override
            public boolean onSingleTapUp ( MotionEvent e ) {

                  return false;
            }

            @Override
            public boolean onScroll (
                MotionEvent e1, MotionEvent e2,
                float distanceX, float distanceY ) {

                  if( Math.abs( distanceX ) > Math.abs( distanceY ) ) {

                        if( isToMove ) {
                              moveAllContent( -distanceX );
                        }

                        return true;
                  }

                  return false;
            }

            @Override
            public void onLongPress ( MotionEvent e ) { }

            @Override
            public boolean onFling (
                MotionEvent e1, MotionEvent e2, float velocityX, float velocityY ) {

                  return false;
            }
      }

      /**
       * 监听动画结束
       */
      private class SwipeFinishListener implements AnimatorListener {

            private WeakReference<Activity> mReference;

            SwipeFinishListener ( Activity activity ) {

                  mReference = new WeakReference<>( activity );
            }

            @Override
            public void onAnimationStart ( Animator animation ) { }

            @Override
            public void onAnimationEnd ( Animator animation ) {

                  if( mAllContent != null ) {

                        float x = mAllContent.getX();
                        if( x != 0 ) {

                              Activity activity = mReference.get();
                              if( activity != null ) {
                                    activity.finish();
                                    activity.overridePendingTransition( 0, 0 );
                              }
                        }
                  }
            }

            @Override
            public void onAnimationCancel ( Animator animation ) { }

            @Override
            public void onAnimationRepeat ( Animator animation ) { }
      }
}