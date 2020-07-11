package com.facebook.react.views.modal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import com.facebook.i.a.a;

class ModalHostHelper {
  private static final Point MAX_POINT;
  
  private static final Point MIN_POINT = new Point();
  
  private static final Point SIZE_POINT;
  
  static {
    MAX_POINT = new Point();
    SIZE_POINT = new Point();
  }
  
  public static Point getModalHostSize(Context paramContext) {
    Display display = ((WindowManager)a.b(paramContext.getSystemService("window"))).getDefaultDisplay();
    display.getCurrentSizeRange(MIN_POINT, MAX_POINT);
    display.getSize(SIZE_POINT);
    byte b = 0;
    boolean bool = paramContext.getTheme().obtainStyledAttributes(new int[] { 16843277 }).getBoolean(0, false);
    Resources resources = paramContext.getResources();
    int j = resources.getIdentifier("status_bar_height", "dimen", "android");
    int i = b;
    if (bool) {
      i = b;
      if (j > 0)
        i = (int)resources.getDimension(j); 
    } 
    return (SIZE_POINT.x < SIZE_POINT.y) ? new Point(MIN_POINT.x, MAX_POINT.y + i) : new Point(MAX_POINT.x, MIN_POINT.y + i);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\modal\ModalHostHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */