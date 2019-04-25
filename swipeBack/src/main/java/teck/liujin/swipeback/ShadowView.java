package teck.liujin.swipeback;

/**
 * @author: Liujin
 * @version: V1.0
 * @date: 2018-07-22
 * @time: 22:37
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 阴影类
 *
 * @author liujin
 */
public class ShadowView extends View {

      public ShadowView ( Context context ) {

            this( context, null, 0 );
      }

      public ShadowView ( Context context, @Nullable AttributeSet attrs ) {

            this( context, attrs, 0 );
      }

      public ShadowView ( Context context, @Nullable AttributeSet attrs, int defStyleAttr ) {

            super( context, attrs, defStyleAttr );
            initBackGround();
      }

      private void initBackGround ( ) {

            int[] colors = new int[]{ 0x00000000, 0x17000000, 0x43000000 };
            Drawable drawable = new GradientDrawable( GradientDrawable.Orientation.LEFT_RIGHT, colors );
            setBackground( drawable );
      }
}
