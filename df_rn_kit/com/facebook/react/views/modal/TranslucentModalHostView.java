package com.facebook.react.views.modal;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.u;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import com.facebook.react.bridge.ReactContext;

public class TranslucentModalHostView extends ReactModalHostView {
  public TranslucentModalHostView(Context paramContext) {
    super(paramContext);
  }
  
  private boolean isDark() {
    Activity activity = ((ReactContext)getContext()).getCurrentActivity();
    return (activity == null) ? true : (((activity.getWindow().getDecorView().getSystemUiVisibility() & 0x2000) != 0));
  }
  
  public static void setStatusBarColor(Window paramWindow, int paramInt) {
    if (Build.VERSION.SDK_INT >= 21) {
      paramWindow.addFlags(-2147483648);
      paramWindow.setStatusBarColor(paramInt);
    } 
  }
  
  public static void setStatusBarStyle(Window paramWindow, boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 23) {
      boolean bool;
      View view = paramWindow.getDecorView();
      if (paramBoolean) {
        bool = true;
      } else {
        bool = false;
      } 
      view.setSystemUiVisibility(bool);
    } 
  }
  
  public static void setStatusBarTranslucent(Window paramWindow, boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 21) {
      View view = paramWindow.getDecorView();
      if (paramBoolean) {
        view.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
              public final WindowInsets onApplyWindowInsets(View param1View, WindowInsets param1WindowInsets) {
                WindowInsets windowInsets = param1View.onApplyWindowInsets(param1WindowInsets);
                return windowInsets.replaceSystemWindowInsets(windowInsets.getSystemWindowInsetLeft(), 0, windowInsets.getSystemWindowInsetRight(), windowInsets.getSystemWindowInsetBottom());
              }
            });
      } else {
        view.setOnApplyWindowInsetsListener(null);
      } 
      u.r(view);
    } 
  }
  
  protected void showOrUpdate() {
    super.showOrUpdate();
    Dialog dialog = getDialog();
    if (dialog != null) {
      setStatusBarTranslucent(dialog.getWindow(), true);
      setStatusBarColor(dialog.getWindow(), 0);
      if (Build.VERSION.SDK_INT >= 23)
        setStatusBarStyle(dialog.getWindow(), isDark()); 
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\modal\TranslucentModalHostView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */