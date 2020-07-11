package com.facebook.react.modules.statusbar;

import android.animation.ArgbEvaluator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Build;
import android.support.v4.view.u;
import android.view.View;
import android.view.WindowInsets;
import com.facebook.common.e.a;
import com.facebook.react.bridge.GuardedRunnable;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.PixelUtil;
import java.util.Map;

@ReactModule(name = "StatusBarManager")
public class StatusBarModule extends ReactContextBaseJavaModule {
  public StatusBarModule(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
  }
  
  public Map<String, Object> getConstants() {
    float f;
    ReactApplicationContext reactApplicationContext = getReactApplicationContext();
    int i = reactApplicationContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (i > 0) {
      f = PixelUtil.toDIPFromPixel(reactApplicationContext.getResources().getDimensionPixelSize(i));
    } else {
      f = 0.0F;
    } 
    return MapBuilder.of("HEIGHT", Float.valueOf(f));
  }
  
  public String getName() {
    return "StatusBarManager";
  }
  
  @ReactMethod
  public void setColor(final int color, final boolean animated) {
    final Activity activity = getCurrentActivity();
    if (activity == null) {
      a.b("ReactNative", "StatusBarModule: Ignored status bar change, current activity is null.");
      return;
    } 
    if (Build.VERSION.SDK_INT >= 21)
      UiThreadUtil.runOnUiThread((Runnable)new GuardedRunnable((ReactContext)getReactApplicationContext()) {
            public void runGuarded() {
              activity.getWindow().addFlags(-2147483648);
              if (animated) {
                int i = activity.getWindow().getStatusBarColor();
                ValueAnimator valueAnimator = ValueAnimator.ofObject((TypeEvaluator)new ArgbEvaluator(), new Object[] { Integer.valueOf(i), Integer.valueOf(this.val$color) });
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                      public void onAnimationUpdate(ValueAnimator param2ValueAnimator) {
                        activity.getWindow().setStatusBarColor(((Integer)param2ValueAnimator.getAnimatedValue()).intValue());
                      }
                    });
                valueAnimator.setDuration(300L).setStartDelay(0L);
                valueAnimator.start();
                return;
              } 
              activity.getWindow().setStatusBarColor(color);
            }
          }); 
  }
  
  @ReactMethod
  public void setHidden(final boolean hidden) {
    final Activity activity = getCurrentActivity();
    if (activity == null) {
      a.b("ReactNative", "StatusBarModule: Ignored status bar change, current activity is null.");
      return;
    } 
    UiThreadUtil.runOnUiThread(new Runnable() {
          public void run() {
            if (hidden) {
              activity.getWindow().addFlags(1024);
              activity.getWindow().clearFlags(2048);
              return;
            } 
            activity.getWindow().addFlags(2048);
            activity.getWindow().clearFlags(1024);
          }
        });
  }
  
  @ReactMethod
  public void setStyle(final String style) {
    final Activity activity = getCurrentActivity();
    if (activity == null) {
      a.b("ReactNative", "StatusBarModule: Ignored status bar change, current activity is null.");
      return;
    } 
    if (Build.VERSION.SDK_INT >= 23)
      UiThreadUtil.runOnUiThread(new Runnable() {
            public void run() {
              boolean bool;
              View view = activity.getWindow().getDecorView();
              if (style.equals("dark-content")) {
                bool = true;
              } else {
                bool = false;
              } 
              view.setSystemUiVisibility(bool);
            }
          }); 
  }
  
  @ReactMethod
  public void setTranslucent(final boolean translucent) {
    final Activity activity = getCurrentActivity();
    if (activity == null) {
      a.b("ReactNative", "StatusBarModule: Ignored status bar change, current activity is null.");
      return;
    } 
    if (Build.VERSION.SDK_INT >= 21)
      UiThreadUtil.runOnUiThread((Runnable)new GuardedRunnable((ReactContext)getReactApplicationContext()) {
            public void runGuarded() {
              View view = activity.getWindow().getDecorView();
              if (translucent) {
                view.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                      public WindowInsets onApplyWindowInsets(View param2View, WindowInsets param2WindowInsets) {
                        WindowInsets windowInsets = param2View.onApplyWindowInsets(param2WindowInsets);
                        return windowInsets.replaceSystemWindowInsets(windowInsets.getSystemWindowInsetLeft(), 0, windowInsets.getSystemWindowInsetRight(), windowInsets.getSystemWindowInsetBottom());
                      }
                    });
              } else {
                view.setOnApplyWindowInsetsListener(null);
              } 
              u.r(view);
            }
          }); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\statusbar\StatusBarModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */