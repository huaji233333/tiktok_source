package com.facebook.react.views.common;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

public class ViewHelper {
  public static void setBackground(View paramView, Drawable paramDrawable) {
    if (Build.VERSION.SDK_INT >= 16) {
      paramView.setBackground(paramDrawable);
      return;
    } 
    paramView.setBackgroundDrawable(paramDrawable);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\common\ViewHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */