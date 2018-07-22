[实现Activity的滑动返回效果 \- CSDN博客](https://blog.csdn.net/eiuly/article/details/46472783)



# activity 侧滑关闭辅助类

### 示例

```
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
```

![](img/1.gif)



## 引入

**Step 1.** Add the JitPack repository to your build file 

Add it in your root build.gradle at the end of repositories: 

```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

**Step 2.** Add the dependency

```
	dependencies {
	        implementation 'com.github.threekilogram:ActivitySwipeDismiss:1.1'
	}
```