package com.tt.miniapp.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.c;
import android.support.v4.graphics.drawable.a;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.tt.miniapp.view.dialog.AlertDialog;

public class ViewUtils {
  private static Drawable changeColor(Context paramContext, int paramInt1, int paramInt2) {
    Drawable drawable = a.f(c.a(paramContext, paramInt1).mutate());
    a.a(drawable, ColorStateList.valueOf(paramInt2));
    return drawable;
  }
  
  public static void setButtonStyle(Button paramButton, String paramString1, String paramString2, int paramInt) {
    if (paramButton == null)
      return; 
    GradientDrawable gradientDrawable = new GradientDrawable();
    if (!TextUtils.isEmpty(paramString1))
      gradientDrawable.setColor(Color.parseColor(paramString1)); 
    if (!TextUtils.isEmpty(paramString2))
      paramButton.setTextColor(Color.parseColor(paramString2)); 
    int i = paramInt;
    if (paramInt >= 0) {
      i = paramInt;
      if (paramButton.getMeasuredHeight() > 0) {
        i = paramInt;
        if (paramInt > paramButton.getMeasuredHeight() / 2)
          i = paramButton.getMeasuredHeight() / 2; 
      } 
      gradientDrawable.setCornerRadius(i);
    } 
    if (!TextUtils.isEmpty(paramString1) || i >= 0)
      paramButton.setBackground((Drawable)gradientDrawable); 
  }
  
  public static void setDialogButtonTextColor(AlertDialog paramAlertDialog, String paramString1, String paramString2) {
    if (paramAlertDialog == null)
      return; 
    if (!TextUtils.isEmpty(paramString1))
      paramAlertDialog.getButton(-1).setTextColor(Color.parseColor(paramString1)); 
    if (!TextUtils.isEmpty(paramString2))
      paramAlertDialog.getButton(-2).setTextColor(Color.parseColor(paramString2)); 
  }
  
  public static void setImageViewStyle(ImageView paramImageView, int paramInt) {
    if (paramImageView == null)
      return; 
    if (paramImageView instanceof RoundedImageView && paramInt >= 0)
      ((RoundedImageView)paramImageView).setCornerRadius(paramInt); 
  }
  
  public static void setSwitchBgColor(Context paramContext, AppbrandSwitch paramAppbrandSwitch, String paramString) {
    if (paramAppbrandSwitch == null)
      return; 
    if (TextUtils.isEmpty(paramString))
      return; 
    StateListDrawable stateListDrawable = new StateListDrawable();
    Drawable drawable2 = paramContext.getResources().getDrawable(2097479776);
    stateListDrawable.addState(new int[] { -16842912 }, drawable2);
    Drawable drawable1 = changeColor(paramContext, 2097479777, Color.parseColor(paramString));
    stateListDrawable.addState(new int[] { 16842912 }, drawable1);
    paramAppbrandSwitch.setTrackDrawable((Drawable)stateListDrawable);
  }
  
  public static void setTextColor(TextView paramTextView, String paramString) {
    if (paramTextView == null)
      return; 
    if (!TextUtils.isEmpty(paramString))
      paramTextView.setTextColor(Color.parseColor(paramString)); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\ViewUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */